package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.month.Month;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.model.stay.PreschoolerStayMonth;
import pl.camp.it.model.stay.Stay;
import pl.camp.it.services.IPreschoolGroupService;
import pl.camp.it.services.IPreschoolerService;
import pl.camp.it.services.IPreschoolerStayMonthService;
import pl.camp.it.services.IStayService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class employeeStayController {

    private int choose;

    @Resource
    SessionObject sessionObject;
    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    IStayService stayService;
    @Autowired
    IPreschoolerStayMonthService preschoolerStayMonthService;

    @RequestMapping(value = "/admincontroller/stay/stayselectgroup", method = RequestMethod.GET)
    public String addStaySelectGroup(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            return "/admincontroller/stay/stayselectgroup";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/stay/stayselectgroup", method = RequestMethod.POST)
    public String addStaySelectGroupChoose(@RequestParam int choose, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            this.choose = choose;
            return "redirect:../../admincontroller/stay/staymonth";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/stay/staymonth", method = RequestMethod.GET)
    public String addStayMonth(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listTypeOfStay", stayService.getListStay());
            model.addAttribute("stayMonth", new PreschoolerStayMonth());
            return "/admincontroller/stay/staymonth";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/stay/staymonth", method = RequestMethod.POST)
    public String saveStayMonthInDB(@ModelAttribute PreschoolerStayMonth preschoolerStayMonth,
                                    @RequestParam("choose") int idPreschooler, @RequestParam("choose1") String month,
                                    @RequestParam("choose2") int idStay, Model model){
        if (sessionObject.getEmployee()!=null) {

            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", pl.camp.it.model.month.Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listTypeOfStay", stayService.getListStay());
            model.addAttribute("stayMonth", new PreschoolerStayMonth());

            Stay stay = stayService.getStayById(idStay);

            if (preschoolerStayMonthService.isPreschoolerStayMonthInDB(idPreschooler, month, stay.getName())){
                model.addAttribute("message", "Rozliczenie za dany miesiąc z godzinami " +
                        "dla przedszkolaka: " + preschooler.getName()+" "+preschooler.getSurname()+" jest już w bazie! " +
                        "Aby dokonać zmian dokonaj edycji!");
            } else {

                preschoolerStayMonthService.addStayMonthToDB(stay, preschoolerStayMonth, month, preschooler);
                model.addAttribute("messageOK", "Dodano rozliczenie godzin do bazy dla przedszkolaka: " +
                        preschooler.getName()+" "+preschooler.getSurname()+" za miesiąc "+ month.toUpperCase()+".");
            }
            return "/admincontroller/stay/staymonth";
        }else {
            return "redirect:../../login";
        }
    }
}
