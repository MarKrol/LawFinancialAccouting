package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.month.Month;
import pl.camp.it.services.IPreschoolGroupService;
import pl.camp.it.services.IPreschoolerService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class adminSendEmailController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    SimpleMailController simpleMailController;

    @RequestMapping(value = "admincontroller/email/send", method = RequestMethod.GET)
    public String openPageSend(Model model) {
        if (sessionObject.getEmployee() != null) {
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
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth_copy1());
            model.addAttribute("listPreschoolGroup",
                    preschoolGroupService.getListPreschoolerGroupNoOneGroup(preschoolGroupService.getListPreschoolerGroup()));            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(idGroup));
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
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            if (idPreschooler==null){
                model.addAttribute("listMonth", Month.getMonth_copy1());
                model.addAttribute("listPreschoolGroup",
                        preschoolGroupService.getListPreschoolerGroupNoOneGroup(preschoolGroupService.getListPreschoolerGroup()));            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(idGroup));
                model.addAttribute("monthName", month);
                model.addAttribute("role", role);
                model.addAttribute("listPreschooler", preschoolerService.getPreschoolerList(idGroup));
                model.addAttribute("number_message",1);
                return "admincontroller/email/send";
            } else{
                simpleMailController.sendMailAttachment(idPreschooler);
                return "admincontroller/email/sending";
            }
        }else {
            return "redirect:../../login";
        }
    }

}
