package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.employee.EmployeeRole;
import pl.camp.it.model.month.Month;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IPDFService;
import pl.camp.it.services.IPreschoolGroupService;
import pl.camp.it.services.IPreschoolerService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class adminSendEmailController {

    private List<PreschoolGroup> preschoolGroups = new ArrayList<>();

    @Resource
    SessionObject sessionObject;

    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    SimpleMailController simpleMailController;
    @Autowired
    IPDFService pdf;

    @RequestMapping(value = "admincontroller/email/send", method = RequestMethod.GET)
    public String openPageSend(Model model) {
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth_copy1());
            model.addAttribute("listPreschoolGroup",
                    preschoolGroupService.getListPreschoolerGroupNoOneGroup(preschoolGroupService.getListPreschoolerGroup()));
            model.addAttribute("role","all");
            model.addAttribute("number_message",0);
            return "admincontroller/email/send";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/email/send", method = RequestMethod.POST, params = "show=POKAŻ PRZEDSZKOLAKÓW")
    public String openPageSendShowPreschooler(@RequestParam("choose") int idGroup, @RequestParam("chooseMonth") String month,
                                              @RequestParam("role") String role, Model model) {
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth_copy1());
            model.addAttribute("listPreschoolGroup",
                    preschoolGroupService.getListPreschoolerGroupNoOneGroup(preschoolGroupService.getListPreschoolerGroup()));
            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(idGroup));
            model.addAttribute("monthName", month);
            model.addAttribute("role", role);
            model.addAttribute("listPreschooler", preschoolerService.getPreschoolerList(idGroup));
            model.addAttribute("number_message",0);
            return "admincontroller/email/send";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/email/send", method = RequestMethod.POST,
                                                            params = "btnSendPreschooler=WYŚLIJ ROZLICZENIE")
    public String sendSettlementPreschooler(@RequestParam("chooseMonth") String month, @RequestParam("choose") int idGroup,
                                            @RequestParam(name = "idPreschooler", required = false) List<Integer> idPreschooler,
                                            @RequestParam("role") String role, Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            if (idPreschooler==null){
                model.addAttribute("listMonth", Month.getMonth_copy1());
                model.addAttribute("listPreschoolGroup",
                        preschoolGroupService.getListPreschoolerGroupNoOneGroup(preschoolGroupService.getListPreschoolerGroup()));
                model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(idGroup));
                model.addAttribute("monthName", month);
                model.addAttribute("role", role);
                model.addAttribute("listPreschooler", preschoolerService.getPreschoolerList(idGroup));
                model.addAttribute("number_message",1);
                return "admincontroller/email/send";
            } else{
                this.pdf.setEmployee(sessionObject.getEmployee());
                this.preschoolGroups.clear();
                this.preschoolGroups.add(preschoolGroupService.getPreschoolerGroupByName(
                                                            preschoolGroupService.getNameGroupPreschoolById(idGroup)));
                simpleMailController.threadSendMailAttachment(idPreschooler, month);
                return "redirect:../../admincontroller/email/sending";
            }
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/email/sending", method = RequestMethod.GET)
    public String afterSendSettlementPreschooler(Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("mailSending", simpleMailController.isMailSending());
            simpleMailController.setMailSending(false);
            return "admincontroller/email/sending";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/email/report", method = RequestMethod.GET)
    public String reportSendMail(Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listGroup", this.preschoolGroups);
            model.addAttribute("reportMap", simpleMailController.getInfoAfterSendMail());
            return "admincontroller/email/report";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/email/send", method = RequestMethod.POST,
                                                                       params = "btnSendPreschoolGroup=WYŚLIJ ROZLICZENIE")
    public String sendSettlementPreschoolGroup(@RequestParam("chooseMonth") String month, //@RequestParam("choose") int idGroup,
                                            //@RequestParam(name = "idPreschooler", required = false) List<Integer> idPreschooler,
                                            @RequestParam(name="idGroup", required = false) List<Integer> listIdGroup,
                                            @RequestParam("role") String role, Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            if (listIdGroup==null){
                model.addAttribute("listMonth", Month.getMonth_copy1());
                model.addAttribute("listPreschoolGroup",
                        preschoolGroupService.getListPreschoolerGroupNoOneGroup(preschoolGroupService.getListPreschoolerGroup()));
                model.addAttribute("monthName", month);
                model.addAttribute("role", role);
                model.addAttribute("number_message",1);
                return "admincontroller/email/send";
            } else{
                this.pdf.setEmployee(sessionObject.getEmployee());
                this.preschoolGroups.clear();
                this.preschoolGroups=preschoolGroupService.getListPreschoolGroup(listIdGroup);
                simpleMailController.threadSendMailAttachmentGroupOrAll(listIdGroup, month);
                return "redirect:../../admincontroller/email/sending";
            }
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/email/send", method = RequestMethod.POST,
                                                                    params = "btnSendAll=WYŚLIJ ROZLICZENIE")
    public String sendSettlementPreschoolAll(@RequestParam("chooseMonth") String month, Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            this.pdf.setEmployee(sessionObject.getEmployee());
            this.preschoolGroups.clear();
            this.preschoolGroups=
                    preschoolGroupService.getListPreschoolerGroupNoOneGroup(preschoolGroupService.getListPreschoolerGroup());
            simpleMailController.threadSendMailAttachmentGroupOrAll
                                        (simpleMailController.listIdGroupByPreschoolerGroup(this.preschoolGroups), month);
            return "redirect:../../admincontroller/email/sending";
        } else {
            return "redirect:../../login";
        }
    }
}
