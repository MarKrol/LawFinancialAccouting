package pl.camp.it.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.userLogin.EmployeeLogin;
import pl.camp.it.services.IEmployeeService;
import pl.camp.it.services.IPDFService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Date;

@Controller
public class employeeLoginController {

    @Resource
    SessionObject sessionObject;
    @Autowired
    IEmployeeService employeeService;
    @Autowired
    IPDFService pdfService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(Model model){
        sessionObject.setEmployee(null);
        sessionObject.setLogged(false);
        model.addAttribute("employeeLogin", new EmployeeLogin());
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login1(Model model){
        sessionObject.setEmployee(null);
        sessionObject.setLogged(false);
        model.addAttribute("employeeLogin", new EmployeeLogin());
        return "login";
    }

    @RequestMapping(value = "afterloggingin", method = RequestMethod.GET)
    public String afterloggingin(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            return "afterloggingin";
        } else{
            return "redirect:login";
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String login(@ModelAttribute EmployeeLogin employeeLogin, Model model) {
        sessionObject.setLogged(false);
        if (!sessionObject.isLogged()) {
            EmployeeLogin tempEmployeeLogin = employeeService.getEmployeeByLogin(employeeLogin.getLogin());
            if (tempEmployeeLogin!= null) {
                if (employeeService.isFirstLoginEmployee(tempEmployeeLogin)){
                    if (tempEmployeeLogin.getPass().equals(employeeLogin.getPass())){
                        sessionObject.setEmployee(tempEmployeeLogin.getEmployee());
                        return "redirect:passchange";
                    } else {
                        model.addAttribute("message", "Wprawadzono niepoprawne hasło!");
                        return "login";
                    }
                } else{
                    if (tempEmployeeLogin.getPass().equals(employeeService.hashPassword(employeeLogin.getPass()))) {
                        sessionObject.setLogged(true);
                        sessionObject.setEmployee(tempEmployeeLogin.getEmployee());
                        return "redirect:afterloggingin";
                    } else{
                        model.addAttribute("message", "Wprawadzono niepoprawne hasło!");
                        return "login";
                    }
                }
            } else {
                model.addAttribute("message", "Brak pracownika w bazie o podanym loginie!");
                return "login";
            }
        } else {
            return "redirect:afterloggingin";
        }
    }

    @RequestMapping(value = "passchange", method = RequestMethod.GET)
    public String firstLogin(Model model){
        if (sessionObject.getEmployee()!=null) {
            EmployeeLogin tempEmployeeLogin = employeeService.getEmployeeById(sessionObject.getEmployee().getId());
            if (employeeService.isFirstLoginEmployee(tempEmployeeLogin)) {
                model.addAttribute("employeeLogin", new EmployeeLogin());
                model.addAttribute("newPass", new String());
                model.addAttribute("repeatNewPass", new String());
                return "passchange";
            } else {
                return "redirect:login";
            }
        } else{
            return "redirect:login";
        }
    }

    @RequestMapping(value = "passchange", method = RequestMethod.POST)
    public String passChange(@ModelAttribute EmployeeLogin employeeLogin,
                             @RequestParam String newPass, @RequestParam String repeatNewPass, Model model){
        if (sessionObject.getEmployee()!=null) {
            EmployeeLogin tempEmployeeLogin = employeeService.getEmployeeById(sessionObject.getEmployee().getId());

            if (employeeService.isFirstLoginEmployee(tempEmployeeLogin)) {

                if (employeeService.oldPasswordsMatch(tempEmployeeLogin, employeeLogin)) {
                    if (employeeService.newPasswordsMatch(newPass, repeatNewPass)) {
                        tempEmployeeLogin.setPass(employeeService.hashPassword(newPass));
                        employeeService.changePassEmployee(tempEmployeeLogin);
                        sessionObject.setLogged(true);
                        return "redirect:afterloggingin";
                    } else {
                        model.addAttribute("message", "Niezgodność nowych haseł!");
                        return "passchange";
                    }
                } else {
                    model.addAttribute("message", "Błędnie wprowadzono stare hasło!");
                    return "passchange";
                }
            } else {
                return "redirect:login";
            }

        } else {
            return "redirect:login";
        }
    }

    @RequestMapping(value = "admincontroller/employee/employeeL/{idEmployee}", method = RequestMethod.GET)
    public String openPagChangePassEmployee(@PathVariable String idEmployee) {
        if (sessionObject.getEmployee() != null) {
            sessionObject.setSendData(Integer.parseInt(idEmployee));
            return "redirect:../../../admincontroller/employee/employeeL";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/employee/employeeL", method = RequestMethod.GET)
    public String pageChangePassEmployee(Model model) {
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("employeeLoginPass",
                    employeeService.returnPassOrStarsEmployee(employeeService.getEmployeeById(sessionObject.getSendData())));
            return "admincontroller/employee/employeeL";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/employee/employeeL", method = RequestMethod.POST, params = "return=POWRÓT")
    public String noChangePassEmployee() {
        if (sessionObject.getEmployee() != null) {
            return "redirect:../../admincontroller/employee/employee";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/employee/employeeL", method = RequestMethod.POST,
                                                                                params = "generatePass=GENERUJ HASŁO")
    public String changePassEmployee() {
        if (sessionObject.getEmployee() != null) {
            EmployeeLogin employeeLoginPass= employeeService.getEmployeeById(sessionObject.getSendData());
            employeeLoginPass.setPass
                    (employeeService.genNewPassEmployee(employeeService.getEmployeeByIdEmployee(sessionObject.getSendData())).getPass());
            employeeService.changePassEmployee(employeeLoginPass);
            return "redirect:../../admincontroller/employee/employeeL";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/employee/pdf", method = RequestMethod.GET)
    protected void printLoginPassPDF(HttpServletResponse response){
        if (sessionObject.getEmployee() != null) {
            String[] data = pdfService.dataToFilePDFLoginAndPass();
            Document document = new Document();
            try{
                response.setContentType("application/pdf");
                PdfWriter.getInstance(document, response.getOutputStream());
                document.open();
                BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                Font font=new Font(helvetica,10);

                Paragraph p1 = new Paragraph(data[0]+ LocalDate.now().toString(), font);
                p1.setAlignment(Element.ALIGN_RIGHT);
                document.add(p1);
                document.add(Chunk.NEWLINE);

                Font fontBold=new Font(helvetica,10, Font.BOLD);
                Paragraph p2 = new Paragraph(data[1], fontBold);
                p2.setAlignment(Element.ALIGN_CENTER);
                document.add(p2);
                document.add(Chunk.NEWLINE);

                Paragraph p3 = new Paragraph(data[2], font);
                p3.setMultipliedLeading(2);
                document.add(p3);
                document.add(Chunk.NEWLINE);

                Paragraph nameSurname = new Paragraph(data[3], fontBold);
                Paragraph idEmployee = new Paragraph(data[4], fontBold);
                Paragraph login = new Paragraph(data[5], fontBold);
                Paragraph pass = new Paragraph(data[6], fontBold);

                Employee employee = employeeService.getEmployeeByIdEmployee(sessionObject.getSendData());
                EmployeeLogin employeeLogin = employeeService.getEmployeeById(sessionObject.getSendData());

                Paragraph getNameSurname = new Paragraph(employee.getSurname()+" "+employee.getName(), font);
                Paragraph getIdEmployee = new Paragraph(String.valueOf(employee.getId()), font);
                Paragraph getLogin = new Paragraph(employeeLogin.getLogin(), font);
                Paragraph getPass = new Paragraph();
                if (employeeLogin.getPass().length()==8) {
                    Paragraph temp = new Paragraph(employeeLogin.getPass(), font);
                    getPass=temp;
                } else{
                    Paragraph temp = new Paragraph("********", font);
                    getPass=temp;
                }

                PdfPTable table = new PdfPTable(4);
                table.setWidths(new int[]{300, 150, 250, 150});

                PdfPCell cell1 = new PdfPCell(nameSurname);
                PdfPCell cell2 = new PdfPCell(idEmployee);
                PdfPCell cell3 = new PdfPCell(login);
                PdfPCell cell4 = new PdfPCell(pass);

                PdfPCell cell5 = new PdfPCell(getNameSurname);
                PdfPCell cell6 = new PdfPCell(getIdEmployee);
                PdfPCell cell7 = new PdfPCell(getLogin);
                PdfPCell cell8 = new PdfPCell(getPass);

                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell8.setHorizontalAlignment(Element.ALIGN_CENTER);


                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);


                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                table.addCell(cell5);
                table.addCell(cell6);
                table.addCell(cell7);
                table.addCell(cell8);

                document.add(table);

            }catch(Exception e){
                e.printStackTrace();
            } finally {
                document.close();
            }
        }
    }
}
