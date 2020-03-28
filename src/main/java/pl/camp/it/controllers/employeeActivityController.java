package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.activities.Activities;
import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.month.Month;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
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
    private int idActivityEdit;


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


    @RequestMapping(value = "/admincontroller/activity/activityselectgroupE", method = RequestMethod.GET)
    public String addStaySelectGroupE(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            return "/admincontroller/activity/activityselectgroupE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activityselectgroupE", method = RequestMethod.POST)
    public String addStaySelectGroupChooseE(@RequestParam int choose, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            this.choose = choose;
            return "redirect:../../admincontroller/activity/activitymonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activitymonthE", method = RequestMethod.GET)
    public String addStayMonthE(Model model){
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

    @RequestMapping(value = "/admincontroller/activity/activitymonthE", method = RequestMethod.POST, params = "edit=EDYTUJ DANE")
    public String editActivityMonth(@RequestParam("choose") int idPreschooler, @RequestParam("choose1") String month,
                                    Model model){

        if (sessionObject.getEmployee()!=null) {
            this.idPreschoolerEdit=idPreschooler;
            this.monthPreviousEdit=month;

            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));

            List<Activities> activitiesList = activityService.activitiesList();
            List<PreschoolerActivityInMonth> preschoolerActivityInMonthList =
                    preschoolerActivityInMonthService.listPreschoolerActivityInMonth(idPreschooler,month);
            List<Activities> noActivitiesPreschoolerInMonth = preschoolerActivityInMonthService.listNoPreschoolerActivityInMonth
                    (activitiesList,preschoolerActivityInMonthList);

            model.addAttribute("activityList", activitiesList);

            if (preschoolerActivityInMonthList.size()!=0) {

                model.addAttribute("nameSurnamePreschooler",
                        preschooler.getName() + " " + preschooler.getSurname());
                model.addAttribute("monthEdit", month);
                model.addAttribute("listActivityPreschoolerInMonth", preschoolerActivityInMonthList);
                model.addAttribute("listNoActivityPreschoolerInMonth", noActivitiesPreschoolerInMonth);

                model.addAttribute("messageOK", "Edytujesz dane dla przedszkolaka: " +
                        " " + preschooler.getName() + " " + preschooler.getSurname() + " za miesiąc " + month.toUpperCase() + ".");
            }else{
                model.addAttribute("message", "Edycja nie jest możliwa z powodu braku przydzielenia zajęć dla " +
                        "edytowanego przedszkolaka! Przejdź do modułu przydziału zajęć!");
            }

            return "/admincontroller/activity/activitymonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activitymonthE", method = RequestMethod.POST, params = "save=ZAPISZ DANE")
    public String saveEditActivityMonthE(@RequestParam("choose") int idPreschooler,
                                         @RequestParam("choose1") String month,
                                         @RequestParam("activity") List<Integer> activityListId,
                                         Model model){

        if (sessionObject.getEmployee()!=null) {

            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));

            List<Activities> activitiesList = activityService.activitiesList();
            model.addAttribute("activityList", activitiesList);

            if (idPreschoolerEdit==idPreschooler && month.equals(monthPreviousEdit)) {

                List<PreschoolerActivityInMonth> preschoolerActivityInMonthListBeforeSave =
                        preschoolerActivityInMonthService.listPreschoolerActivityInMonth(idPreschooler, month);
                List<Integer> activityListIdToSave = preschoolerActivityInMonthService.activityListIdToSave
                                                            (preschoolerActivityInMonthListBeforeSave, activityListId, month);

                preschoolerActivityInMonthService.addMonthActivityToDB(preschooler, activityListIdToSave, month);

                List<PreschoolerActivityInMonth> preschoolerActivityInMonthList =
                        preschoolerActivityInMonthService.listPreschoolerActivityInMonth(idPreschooler, month);

                List<Activities> noActivitiesPreschoolerInMonth = preschoolerActivityInMonthService.listNoPreschoolerActivityInMonth
                        (activitiesList, preschoolerActivityInMonthList);

                model.addAttribute("nameSurnamePreschooler",
                        preschooler.getName() + " " + preschooler.getSurname());
                model.addAttribute("monthEdit",month);
                model.addAttribute("listActivityPreschoolerInMonth", preschoolerActivityInMonthList);
                model.addAttribute("listNoActivityPreschoolerInMonth", noActivitiesPreschoolerInMonth);

                model.addAttribute("messageOK", "Zapisano zmodyfikowane dane dla przedszkolaka: " +
                        " " + preschooler.getName() + " " + preschooler.getSurname() + " za miesiąc " + month.toUpperCase() + ".");
            } else {
                model.addAttribute("message", "Zapis nie jest możliwy z powodu zaznaczenia innego " +
                        "przedszkolaka i/lub miesiąca niż edytowane dane! Edytuj dane jeszcze raz!");
            }
            return "/admincontroller/activity/activitymonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activityE", method = RequestMethod.GET)
    public String openPageActivityE(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("activityList", activityService.activitiesList());
            model.addAttribute("activity", new Activities());
            return "/admincontroller/activity/activityE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activityE", method = RequestMethod.POST, params = "edit=EDYTUJ ZAJĘCIA")
    public String editActivityShow(@RequestParam("choose") int idActivity, Model model) {
        if (sessionObject.getEmployee() != null) {
            this.idActivityEdit=idActivity;
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            Activities activities = activityService.getActivity(idActivityEdit);
            model.addAttribute("activity", activities);
            model.addAttribute("nameActivity",activities.getName());
            model.addAttribute("activityList",activityService.activitiesList());
            return "/admincontroller/activity/activityE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activityE", method = RequestMethod.POST, params = "save=ZAPISZ ZMIANY")
    public String saveEditActivityShow(@RequestParam("data")List<String> activityEdit, @RequestParam("choose") int idActivity, Model model) {
        if (sessionObject.getEmployee() != null) {

            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("activity", new Activities());

            if (this.idActivityEdit==idActivity) {
                Activities activities = activityService.getActivity(idActivity);

                activityService.saveChangeActivity(activities, activityEdit);

                model.addAttribute("activityList", activityService.activitiesList());
                model.addAttribute("nameActivity", activities.getName());
                model.addAttribute("messageOK", "Zapisano zmiany w bazie dla edytowanego pobytu!");
            } else{
                model.addAttribute("message", "Dokunujesz zmian w wyedytowanym pobycie, " +
                        "który jest inny niż zaznaczony! Edytuj pobyt jeszcze raz!");
            }
            return "/admincontroller/activity/activityE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activityE", method = RequestMethod.POST, params = "delete=USUŃ ZAJĘCIA")
    public String deleteStayShow(@RequestParam("choose") int idActivity, Model model) {
        if (sessionObject.getEmployee() != null) {
            this.idActivityEdit=idActivity;
            return "redirect:../../admincontroller/activity/activityD";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activityD",method = RequestMethod.GET)
    public String confirmDeleteStay(Model model){
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            Activities activities = activityService.getActivity(this.idActivityEdit);
            model.addAttribute("activity", activities);
            model.addAttribute("nameActivity",activities.getName());
            model.addAttribute("activityList",activityService.activitiesList());
            model.addAttribute("message","Czy na pewno chcesz usunąć wybrane zajęcia?");
            return "/admincontroller/activity/activityD";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activityD",method = RequestMethod.POST,params = "nodelete=NIE")
    public String noDeleteStay(){
        if (sessionObject.getEmployee() != null) {
            return "redirect:../../admincontroller/activity/activityE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/activity/activityD",method = RequestMethod.POST,params = "delete=TAK")
    public String yesDeleteStay(Model model){
        if (sessionObject.getEmployee() != null) {
            if (!preschoolerActivityInMonthService.isNameActivityPreschoolerInDB(activityService.getActivity(this.idActivityEdit).getName())) {
                activityService.deleteActivity(activityService.getActivity(this.idActivityEdit));
                return "redirect:../../admincontroller/activity/activityE";
            }else{
                model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                        sessionObject.getEmployee().getSurname());
                Activities activities = activityService.getActivity(this.idActivityEdit);
                model.addAttribute("activity", activities);
                model.addAttribute("nameActivity",activities.getName());
                model.addAttribute("activityList",activityService.activitiesList());

                model.addAttribute("message","Nie można usunąć zajęć ponieważ są one już używane przez" +
                        " program w innym miejscu. Usuń wszystkie przydzielone zajęcia o tej nazwie, a dopiero " +
                        "uzyskasz możliwość usunięcia zajęć!");

                return "/admincontroller/activity/activityD";
            }
        }else {
            return "redirect:../../login";
        }
    }
}