package pl.camp.it.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.employee.EmployeeRole;
import pl.camp.it.model.month.Month;
import pl.camp.it.model.userLogin.EmployeeLogin;
import pl.camp.it.services.IPDFService;
import pl.camp.it.services.IPreschoolGroupService;
import pl.camp.it.services.IPreschoolerService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@Controller
public class AdminPrintController {

    private String month;

    @Resource
    SessionObject sessionObject;
    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    IPDFService pdfService;

    @RequestMapping(value = "admincontroller/print/print", method = RequestMethod.GET)
    public String openPagePrint(Model model) {
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString())){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth_copy1());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            return "admincontroller/print/print";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/print/print", method = RequestMethod.POST)
    public String printGroup(@RequestParam("chooseMonth") String month, @RequestParam("choose") int idGroup, Model model) {
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString())){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth_copy1());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("monthChoose", month);
            this.month=month;
            model.addAttribute("nameGroup", preschoolGroupService.getNameGroupPreschoolById(idGroup));
            model.addAttribute("allPreschoolers", preschoolerService.getPreschoolerList(idGroup));
            return "admincontroller/print/print";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/print/print/{idPreschooler}", method = RequestMethod.GET)
    protected void printPDF(@PathVariable String idPreschooler, HttpServletResponse response) {
        if (sessionObject.getEmployee() != null && !sessionObject.getEmployee().getRole().equals("TEACHER")) {
            Document document = new Document();
            try{
                response.setContentType("application/pdf");
                PdfWriter.getInstance(document, response.getOutputStream());
                pdfService.setEmployee(sessionObject.getEmployee());
                document.open();
                pdfService.writeSettlementToPrint(document,Integer.parseInt(idPreschooler),this.month );
                document.close();
            }catch(Exception e){
                e.printStackTrace();
            } finally {
                document.close();
            }
        }
    }
}
