package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.*;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class adminAddController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IEmployeeService employeeService;
    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    IParentService parentService;

    @RequestMapping(value = "admincontroller/employee/addemployee",method = RequestMethod.GET)
    public String addEmployee(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employee", new Employee());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup",preschoolGroupService.getListPreschoolerGroup());
            return "admincontroller/employee/addemployee";
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/employee/addemployee",method = RequestMethod.POST)
    public String addEmployeeToDB(@ModelAttribute Employee employee, @RequestParam String choose, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            if (employee.getRole().equals("teacher")) {
                if (preschoolGroupService.isPreschoolGroupInDB(choose)) {
                    employeeService.persistEmployee(employee);
                    employeeService.persistEmployeeLogin(employee);
                    preschoolGroupService.setPreschoolGroupAndAddEmployeeToGroup(choose.toUpperCase(),employee);
                    return "redirect:../../admincontroller/employee/statement";
                } else {
                    model.addAttribute("message", "Wybierz grupę przedszkolną!");
                    model.addAttribute("listPreschoolGroup",preschoolGroupService.getListPreschoolerGroup());
                    return "admincontroller/employee/addemployee";
                }
            } else{
                employeeService.persistEmployee(employee);
                employeeService.persistEmployeeLogin(employee);
                return "redirect:../../admincontroller/employee/statement";
            }
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/addgroup",method = RequestMethod.GET)
    public String addgroup(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("preschoolGroup", new PreschoolGroup());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/grouppreschooler/addgroup";
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/addgroup", method = RequestMethod.POST)
    public String addgroupaToDB(@ModelAttribute PreschoolGroup preschoolGroup, Model model){
        if (sessionObject.getEmployee()!=null){
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            if (!preschoolGroupService.isPreschoolGroupInDB(preschoolGroup.getNameGroup())) {
                preschoolGroupService.persistPreschoolGroup(preschoolGroup);

                return "redirect:../../admincontroller/grouppreschooler/statement";
            } else {
                model.addAttribute("message","Grupa o podanej nazwie istnieje już w bazie!");
                return "admincontroller/grouppreschooler/addgroup";
            }
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/addpreschooler",method = RequestMethod.GET)
    public String addpreschooler(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("preschooler",new Preschooler());
            model.addAttribute("listGroupPreschool", preschoolGroupService.getListPreschoolerGroup());
            return "admincontroller/preschooler/addpreschooler";
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/addpreschooler",method = RequestMethod.POST)
    public String addpreschoolerToDB(@ModelAttribute Preschooler preschooler,
                                     @RequestParam String chooseGroupPreschool, Model model){
        if (sessionObject.getEmployee()!=null){
            if(preschoolGroupService.isPreschoolGroupInDB(chooseGroupPreschool)){
                preschoolerService.setPreschoolerAndAddPreschoolGroupToPreschooler(preschooler, chooseGroupPreschool);
                preschoolerService.persistPreschooler(preschooler);
                parentService.persistParentLogin(preschooler);
                return "redirect:../../admincontroller/preschooler/statement";
            } else {
                model.addAttribute("listGroupPreschool", preschoolGroupService.getListPreschoolerGroup());
                model.addAttribute("message","Wybierz grupę przedszkolną!");
                return "admincontroller/preschooler/addpreschooler";
            }
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/employee/statement", method = RequestMethod.GET)
    public String statementEmployee(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/employee/statement";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/grouppreschooler/statement", method = RequestMethod.GET)
    public String statementPreschoolGroup(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/grouppreschooler/statement";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/statement", method = RequestMethod.GET)
    public String statementPreschooler(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/preschooler/statement";
        } else {
            return "redirect:../../login";
        }
    }

}
