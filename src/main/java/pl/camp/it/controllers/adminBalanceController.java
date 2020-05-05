package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.month.Month;
import pl.camp.it.services.IBalanceService;
import pl.camp.it.services.ICompanyService;
import pl.camp.it.services.IPreschoolGroupService;
import pl.camp.it.services.IPreschoolerService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class adminBalanceController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    ICompanyService companyService;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    IBalanceService balanceService;

    @RequestMapping(value = "admincontroller/balance/balance", method = RequestMethod.GET)
    public String openPageBalance(Model model) {
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth_copy1());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("company1", companyService.getListCompany().get(0).getNameCompany());
            model.addAttribute("company2", companyService.getListCompany().get(1).getNameCompany());
            return "admincontroller/balance/balance";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/balance/balance", method = RequestMethod.POST)
    public String openPageBalancePreschooler(@RequestParam("chooseMonth") String month, @RequestParam("choose") int idGroup, Model model) {
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth_copy1());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("company1", companyService.getListCompany().get(0).getNameCompany());
            model.addAttribute("company2", companyService.getListCompany().get(1).getNameCompany());
            model.addAttribute("monthChoose",month);
            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(idGroup));
            model.addAttribute("allPreschoolers", balanceService.getBalanceMonth(idGroup,month));
            model.addAttribute("allPreschoolers2", balanceService.getBalanceMonth2(idGroup,month));
            return "admincontroller/balance/balance";
        } else {
            return "redirect:../../login";
        }
    }
}
