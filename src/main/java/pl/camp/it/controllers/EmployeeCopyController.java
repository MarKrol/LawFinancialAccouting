package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.employee.EmployeeRole;
import pl.camp.it.model.month.Month;
import pl.camp.it.services.ICopyService;
import pl.camp.it.services.IPreschoolGroupService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class EmployeeCopyController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    ICopyService copyService;

    @RequestMapping(value = "admincontroller/copy/copyForGroup", method = RequestMethod.GET)
    public String openPageCopy(Model model) {
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("month", new String());
            model.addAttribute("listMonth1", Month.getMonth_copy1());
            model.addAttribute("listMonth2", Month.getMonth_copy2());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            return "admincontroller/copy/copyForGroup";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/copy/copyForGroupInfo", method = RequestMethod.GET)
    public String afterCopyInfo(Model model) {
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("howDataCopied", this.copyService.getHowDataCopied());
            if (this.copyService.getHowDataCopied().equals("F")) {
                model.addAttribute("dataFromTheCopiedMonth", this.copyService.dataFromTheCopiedMonth());
                model.addAttribute("dataInTheCopiedMonth", this.copyService.dataInTheCopiedMonth());
                model.addAttribute("dataToBeCopied", this.copyService.dataToBeCopied());
            }
            if (this.copyService.getHowDataCopied().equals("S")) {
                model.addAttribute("dataFromTheCopiedMonth", this.copyService.dataFromTheCopiedMonthS());
                model.addAttribute("dataInTheCopiedMonth", this.copyService.dataInTheCopiedMonthS());
                model.addAttribute("dataToBeCopied", this.copyService.dataToBeCopiedS());
            }
            if (this.copyService.getHowDataCopied().equals("A")) {
                model.addAttribute("dataFromTheCopiedMonth", this.copyService.dataFromTheCopiedMonthA());
                model.addAttribute("dataInTheCopiedMonth", this.copyService.dataInTheCopiedMonthA());
                model.addAttribute("dataToBeCopied", this.copyService.dataToBeCopiedA());
            }
            if (this.copyService.getHowDataCopied().equals("E")) {
                model.addAttribute("dataFromTheCopiedMonth", this.copyService.dataFromTheCopiedMonthE());
                model.addAttribute("dataInTheCopiedMonth", this.copyService.dataInTheCopiedMonthE());
                model.addAttribute("dataToBeCopied", this.copyService.dataToBeCopiedE());
            }
            return "admincontroller/copy/copyForGroupInfo";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/copy/copyForGroup", method = RequestMethod.POST, params = "show=KOPIUJ DANE")
    public String copy(@RequestParam("choose") int idGroup, @RequestParam(name="month", required = false) String monthToMonth,
                       Model model) {
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }

            this.copyService.copyDataToNextMonth(idGroup, monthToMonth);
            return "redirect:../../admincontroller/copy/copyForGroupInfo";
        } else {
            return "redirect:../../login";
        }
    }
}
