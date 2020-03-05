package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.camp.it.services.*;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class adminController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IEmployeeService employeeService;

    @RequestMapping(value = "/add",  method = RequestMethod.GET)
    public String addEmployee(Model model){
        model.addAttribute("employeeLogged", "zenek");
        return "redirect:/admincontroller/employee/addemployee";
    }

    @RequestMapping(value = "admincontroller/employee/addemployee",method = RequestMethod.GET)
    public String addEmployeeToDB(Model model){
        model.addAttribute("employeeLogged", "zenek");
        return "admincontroller/employee/addemployee";
    }

    @RequestMapping(value = "/addg",  method = RequestMethod.GET)
    public String addgroup(Model model){
        model.addAttribute("employeeLogged", "zenek");
        return "redirect:/admincontroller/grouppreschooler/addgroup";
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/addgroup",method = RequestMethod.GET)
    public String addgroupToDB(Model model){
        model.addAttribute("employeeLogged", "zenek");
        return "admincontroller/grouppreschooler/addgroup";
    }

    @RequestMapping(value = "/addp",  method = RequestMethod.GET)
    public String addpreschooler(Model model){
        model.addAttribute("employeeLogged", "zenek");
        return "redirect:/admincontroller/preschooler/addpreschooler";
    }

    @RequestMapping(value = "admincontroller/preschooler/addpreschooler",method = RequestMethod.GET)
    public String addpreschoolerToDB(Model model){
        model.addAttribute("employeeLogged", "zenek");
        return "admincontroller/preschooler/addpreschooler";
    }
}
