package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.company.Company;
import pl.camp.it.services.ICompanyService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class adminCompanyController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    ICompanyService companyService;

    @RequestMapping(value = "admincontroller/company/company", method = RequestMethod.GET)
    public String openPageCompany(Model model){
        if (sessionObject.getEmployee()!=null){
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("companyE", new Company());
            model.addAttribute("companyList", companyService.getListCompany());
            model.addAttribute("nameCompany","");
            return "admincontroller/company/company";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/company/company", method = RequestMethod.POST, params = "show=POKAŻ DANE")
    public String showCompany(@RequestParam("choose") int idCompany, Model model){
        if (sessionObject.getEmployee()!=null){
            Company company = companyService.getCompanyById(idCompany);
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("companyList", companyService.getListCompany());
            model.addAttribute("nameCompany",company.getNameCompany());
            model.addAttribute("companyE",company);
            return "admincontroller/company/company";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/company/company", method = RequestMethod.POST, params = "save=ZAPISZ DANE")
    public String saveAndShowCompany(@ModelAttribute Company companyEdit,
                                     @RequestParam("choose") int idCompany, Model model){
        if (sessionObject.getEmployee()!=null){
            Company company = companyService.getCompanyById(idCompany);
            companyService.saveChangeDataCompany(company, companyEdit);
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("companyList", companyService.getListCompany());
            model.addAttribute("nameCompany",company.getNameCompany());
            model.addAttribute("companyE",company);
            return "admincontroller/company/company";
        }else{
            return "redirect:../../login";
        }
    }
}
