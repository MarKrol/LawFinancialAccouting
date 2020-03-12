package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.camp.it.model.activities.Activities;
import pl.camp.it.model.meals.FullBoardPrice;
import pl.camp.it.model.meals.SingleBoardPrice;
import pl.camp.it.model.stay.Stay;
import pl.camp.it.services.IActivityService;
import pl.camp.it.services.IFullBoardPriceService;
import pl.camp.it.services.ISingleBoardPriceService;
import pl.camp.it.services.IStayService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class adminAddMealActivityStayController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IFullBoardPriceService fullBoardPriceService;
    @Autowired
    ISingleBoardPriceService singleBoardPriceService;
    @Autowired
    IActivityService activityService;
    @Autowired
    IStayService stayService;

    @RequestMapping(value = "admincontroller/meals/full", method = RequestMethod.GET)
    public String addFullMeal(Model model){
        if (sessionObject.getEmployee()!=null){
            model.addAttribute("fullMeal", new FullBoardPrice());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/meals/full";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/full", method = RequestMethod.POST)
    public String addFullMealToDB(@ModelAttribute FullBoardPrice fullBoardPrice, Model model){
        if (sessionObject.getEmployee()!=null){
            if (fullBoardPriceService.isFullMealInDB(fullBoardPrice.getName().toUpperCase())){
                model.addAttribute("message","Wyżwienie o podanej nazwie istnieje już w bazie!");
                model.addAttribute("fullMeal", new FullBoardPrice());
                model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                        sessionObject.getEmployee().getSurname());
                return "admincontroller/meals/full";
            } else {
                fullBoardPriceService.persistFullBoardPrice(fullBoardPrice);
                return "redirect:../../admincontroller/meals/statement";
            }
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/single", method = RequestMethod.GET)
    public String addSingleMeal(Model model){
        if (sessionObject.getEmployee()!=null){
            model.addAttribute("singleMeal", new SingleBoardPrice());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/meals/single";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/single", method = RequestMethod.POST)
    public String addSingleMealToDB(@ModelAttribute SingleBoardPrice singleBoardPrice, Model model){
        if (sessionObject.getEmployee()!=null){
            if (singleBoardPriceService.isSingleMealInDB(singleBoardPrice.getName().toUpperCase())){
                model.addAttribute("message","Wyżwienie o podanej nazwie istnieje już w bazie!");
                model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                        sessionObject.getEmployee().getSurname());
                model.addAttribute("singleMeal", new SingleBoardPrice());
                return "admincontroller/meals/single";
            } else {
                singleBoardPriceService.persistSingleBoardPrice(singleBoardPrice);
                return "redirect:../../admincontroller/meals/statement";
            }
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/activity", method = RequestMethod.GET)
    public String addActivity(Model model){
        if (sessionObject.getEmployee()!=null){
            model.addAttribute("activity", new Activities());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/meals/activity";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/activity", method = RequestMethod.POST)
    public String addActivityToDB(@ModelAttribute Activities activities, Model model){
        if (sessionObject.getEmployee()!=null){
            if (activityService.isActivityInDB(activities.getName().toUpperCase())){
                model.addAttribute("message","Zajęcia o podanej nazwie istnieją już w bazie!");
                model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                        sessionObject.getEmployee().getSurname());
                model.addAttribute("activity", new Activities());
                return "admincontroller/meals/activity";
            } else{
                activityService.persistActivity(activities);
                return "redirect:../../admincontroller/meals/statementa";
            }
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/stay", method = RequestMethod.GET)
    public String addstay(Model model){
        if (sessionObject.getEmployee()!=null){
            model.addAttribute("stay", new Stay());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/meals/stay";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/stay", method = RequestMethod.POST)
    public String addStayToDB(@ModelAttribute Stay stay, Model model){
        if (sessionObject.getEmployee()!=null){
            if (stayService.isStayInDB(stay.getName().toUpperCase())){
                model.addAttribute("message","Pobyt o podanej nazwie istniejw już w bazie!");
                model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                        sessionObject.getEmployee().getSurname());
                model.addAttribute("stay", new Stay());
                return "admincontroller/meals/stay";
            } else{
                stayService.persistStay(stay);
                return "redirect:../../admincontroller/meals/statements";
            }
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="/admincontroller/meals/statement", method = RequestMethod.GET)
    public String statementMeal(Model model){
        if (sessionObject.getEmployee()!=null){
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/meals/statement";
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="/admincontroller/meals/statementa", method = RequestMethod.GET)
    public String statementActivity(Model model){
        if (sessionObject.getEmployee()!=null){
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/meals/statementa";
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="/admincontroller/meals/statements", method = RequestMethod.GET)
    public String statementStay(Model model){
        if (sessionObject.getEmployee()!=null){
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/meals/statements";
        } else{
            return "redirect:../../login";
        }
    }
}
