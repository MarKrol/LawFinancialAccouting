package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.meals.SingleBoardPrice;
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
    private int idPreschoolerEditSave;
    private String monthEditSave;
    private int idStayMonthEditSave;

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

    @RequestMapping(value = "/admincontroller/stay/stayselectgroupE", method = RequestMethod.GET)
    public String addStaySelectGroupE(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            return "/admincontroller/stay/stayselectgroupE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/stay/stayselectgroupE", method = RequestMethod.POST)
    public String addStaySelectGroupChooseE(@RequestParam int choose, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            this.choose = choose;
            return "redirect:../../admincontroller/stay/staymonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/stay/staymonthE", method = RequestMethod.GET)
    public String addStayMonthE(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listTypeOfStay", stayService.getListStay());
            model.addAttribute("stayMonth", new PreschoolerStayMonth());
            return "/admincontroller/stay/staymonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="/admincontroller/stay/staymonthE", method = RequestMethod.POST,params = "edit=EDYTUJ DANE")
    public String editDataToSaveStayMonth(@RequestParam("choose") int idPreschooler,
                                          @RequestParam("choose1") String month,
                                          @RequestParam("choose2") int idStayMonth, Model model) {
        if (sessionObject.getEmployee() != null) {
            this.idPreschoolerEditSave=idPreschooler;
            this.monthEditSave=month;
            this.idStayMonthEditSave=idStayMonth;

            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listTypeOfStay", stayService.getListStay());
            model.addAttribute("nameSurname",preschooler.getName()+" "+preschooler.getSurname());
            model.addAttribute("nameTypeOfStay", Integer.valueOf(idStayMonth));

            Stay stay =stayService.getStayById(idStayMonth);

            if (preschoolerStayMonthService.isPreschoolerStayMonthInDB(idPreschooler, month, stay.getName())){
                model.addAttribute("stayMonth", preschoolerStayMonthService.preschoolerStayMonth(idPreschooler,month, stay.getName()));
                model.addAttribute("month", month);
                model.addAttribute("messageOK", "Edytujesz dane dla przedszkolaka: " +
                        " " + preschooler.getName() + " " + preschooler.getSurname() + " w miesiącu " + month.toUpperCase() + ".");
            } else{
                this.idPreschoolerEditSave=-1;
                this.idStayMonthEditSave=-1;
                model.addAttribute("month", "");
                model.addAttribute("stayMonth",new PreschoolerStayMonth());
                model.addAttribute("message", "Brak danych zadanych ustawień dla przedszkolaka: " +
                        " " + preschooler.getName() + " " + preschooler.getSurname() + " w miesiącu " + month.toUpperCase() + ".");
            }
            return "/admincontroller/stay/staymonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="/admincontroller/stay/staymonthE", method = RequestMethod.POST,params = "save=ZAPISZ DANE")
    public String saveDataStayMonthEdit(@ModelAttribute PreschoolerStayMonth preschoolerStayMonthEdit,
                                        @RequestParam("choose") int idPreschooler, @RequestParam("choose1") String month,
                                          @RequestParam("choose2") int idStayMonth, Model model) {
        if (sessionObject.getEmployee() != null) {

            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listTypeOfStay", stayService.getListStay());
            model.addAttribute("stayMonth", new PreschoolerStayMonth());

            if (idPreschooler!=this.idPreschoolerEditSave || !month.equals(this.monthEditSave) || idStayMonth!=this.idStayMonthEditSave){

                model.addAttribute("message", "Zapis nie jest możliwy z powodu zaznaczenia innego " +
                        "przedszkolaka i/lub miesiąca i/lub pobytu niż edytowane dane! Edytuj dane jeszcze raz!");
            }else{

                preschoolerStayMonthService.saveChangeStayMonth
                        (preschoolerStayMonthService.preschoolerStayMonth(idPreschooler, month,
                                            stayService.getStayById(idStayMonth).getName()), preschoolerStayMonthEdit);

                model.addAttribute("messageOK", "Zaktualizowano dane w bazie" +
                        " dla przedszkolaka: " + " " + preschooler.getName() + " " + preschooler.getSurname()
                        + " za miesiąc " + month.toUpperCase() + ".");

            }
            return "/admincontroller/stay/staymonthE";
        } else {
            return "redirect:../../login";
        }
    }

}
