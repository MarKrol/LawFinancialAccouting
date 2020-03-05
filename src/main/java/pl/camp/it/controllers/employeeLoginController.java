package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.userLogin.EmployeeLogin;
import pl.camp.it.services.IEmployeeService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class employeeLoginController {

    @Resource
    SessionObject sessionObject;
    @Autowired
    IEmployeeService employeeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(Model model){
        sessionObject.setEmployee(null);
        sessionObject.setLogged(false);
        model.addAttribute("employeeLogin", new EmployeeLogin());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
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

    @RequestMapping(value = "/passchange", method = RequestMethod.POST)
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
}