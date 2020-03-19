package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.month.Month;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.model.stay.PreschoolerStayMonth;
import pl.camp.it.model.stay.Stay;
import pl.camp.it.services.IActivityService;
import pl.camp.it.services.IPreschoolGroupService;
import pl.camp.it.services.IPreschoolerActivityInMonthService;
import pl.camp.it.services.IPreschoolerService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class employeeActivityController {

    private int choose; //choose idGroup;
    private int idPreschoolerEdit=-1;
    private String monthPreviousEdit=null;


    @Resource
    SessionObject sessionObject;

    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    IActivityService activityService;
    @Autowired
    IPreschoolerActivityInMonthService preschoolerActivityInMonthService;

    @RequestMapping(value = "/admincontroller/activity/activityselectgroup", method = RequestMethod.GET)
    public String addStaySelectGroup(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            return "/admincontroller/activity/activityselectgroup";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activityselectgroup", method = RequestMethod.POST)
    public String addStaySelectGroupChoose(@RequestParam int choose, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            this.choose = choose;
            return "redirect:../../admincontroller/activity/activitymonth";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activitymonth", method = RequestMethod.GET)
    public String addStayMonth(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("activityList", activityService.activitiesList());
            return "/admincontroller/activity/activitymonth";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activitymonth", method = RequestMethod.POST, params = "save=ZAPISZ DANE")
    public String addActivityMonth(@RequestParam("activity") List<Integer> activityListId,
                                   @RequestParam("choose") int idPreschooler, @RequestParam("choose1") String month,
                                   Model model){
        if (sessionObject.getEmployee()!=null) {
            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("activityList", activityService.activitiesList());

            if (preschoolerActivityInMonthService.isSaveMonthActivitiesInDB(idPreschooler, month)){
                model.addAttribute("message", "Nie zapisano w bazie dodatkowych zajęć przedszkolaka: " +
                        preschooler.getName()+" "+preschooler.getSurname()+" w miesiącu "+ month.toUpperCase()+"."+" Zajęcia z tego miesiąc " +
                        "są w bazie. Przejdź do edycji zajęć i wprowadź zmiany.");
            } else{
                preschoolerActivityInMonthService.addMonthActivityToDB(preschooler, activityListId, month);
                model.addAttribute("messageOK", "Zapisano w bazie dodatkowe zajęcia przedszkolaka: " +
                        preschooler.getName()+" "+preschooler.getSurname()+" w miesiącu "+ month.toUpperCase()+".");
            }

            return "/admincontroller/activity/activitymonth";
        } else {
            return "redirect:../../login";
        }
    }






    @RequestMapping(value = "/admincontroller/activity/activitymonthE", method = RequestMethod.POST, params = "edit=EDYTUJ DANE")
    public String editActivityMonth(@RequestParam("activity") List<String> activity,
                                   @RequestParam("choose") int idPreschooler, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("activityList", activityService.activitiesList());

            return "/admincontroller/activity/activitymonthE";
        } else {
            return "redirect:../../login";
        }
    }
}
