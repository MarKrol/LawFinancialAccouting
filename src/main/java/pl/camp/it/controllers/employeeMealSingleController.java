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
import pl.camp.it.services.IPreschoolGroupService;
import pl.camp.it.services.IPreschoolerService;
import pl.camp.it.services.IPreschoolerSingleBoardInMonthService;
import pl.camp.it.services.ISingleBoardPriceService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class employeeMealSingleController {

    private int choose; //choose groupPreschooler
    private int idPreschoolerEditSave;
    private String monthEditSave;
    private int idSingleMealEditSave;

    @Resource
    SessionObject sessionObject;
    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    ISingleBoardPriceService singleBoardPriceService;
    @Autowired
    IPreschoolerSingleBoardInMonthService preschoolerSingleBoardInMonthService;

    @RequestMapping(value = "/admincontroller/meals/singleselectgroup", method = RequestMethod.GET)
    public String addSingleSelectGroup(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            return "/admincontroller/meals/singleselectgroup";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/meals/singleselectgroup", method = RequestMethod.POST)
    public String addSingleSelectGroupChoose(@RequestParam int choose, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            this.choose = choose;
            return "redirect:../../admincontroller/meals/singlemonth";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/meals/singlemonth", method = RequestMethod.GET)
    public String addSingleMonth(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listSingleMeal", singleBoardPriceService.getListSingleMeal());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            model.addAttribute("nameSingle","");
            return "/admincontroller/meals/singlemonth";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/meals/singlemonth", method = RequestMethod.POST)
    public String saveSingleMonthInDB(@ModelAttribute PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth,
                                      @RequestParam("choose") int idPreschooler, @RequestParam("choose1") String month,
                                      @RequestParam("choose2") int idSingleEat, Model model){
        if (sessionObject.getEmployee()!=null) {

            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", pl.camp.it.model.month.Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listSingleMeal", singleBoardPriceService.getListSingleMeal());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            model.addAttribute("nameSingle","");

            SingleBoardPrice singleBoardPrice = singleBoardPriceService.getNameSingleMealById(idSingleEat);
            String nameSingleEat=singleBoardPrice.getName();

            if (preschoolerSingleBoardInMonthService.isPreschoolerSingleMealMonthInDB(idPreschooler,month, nameSingleEat)){
                model.addAttribute("message", "Rozliczenie za dany miesiąc i z danym posiłkiem " +
                        "dla przedszkolaka: " + preschooler.getName()+" "+preschooler.getSurname()+" jest już w bazie! " +
                                                                                    "Aby dokonać zmian dokonaj edycji!");
            } else {
                preschoolerSingleBoardInMonthService.saveSingleMealMonthPreschoolerInDB(
                                                 preschooler, month, singleBoardPrice, preschoolerSingleBoardInMonth);
                model.addAttribute("messageOK", "Dodano rozliczenie do bazy dla przedszkolaka: " +
                        preschooler.getName()+" "+preschooler.getSurname()+".");
            }
            return "/admincontroller/meals/singlemonth";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/meals/singleselectgroupE", method = RequestMethod.GET)
    public String addSingleSelectGroupE(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            return "/admincontroller/meals/singleselectgroupE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/meals/singleselectgroupE", method = RequestMethod.POST)
    public String addSingleSelectGroupChooseE(@RequestParam int choose, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            this.choose = choose;
            return "redirect:../../admincontroller/meals/singlemonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/meals/singlemonthE", method = RequestMethod.GET)
    public String addSingleMonthE(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listSingleMeal", singleBoardPriceService.getListSingleMeal());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            model.addAttribute("nameSingle","");
            model.addAttribute("nameSurname","");
            model.addAttribute("month","");
            return "/admincontroller/meals/singlemonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="/admincontroller/meals/singlemonthE", method = RequestMethod.POST,params = "edit=EDYTUJ DANE")
    public String editDataToSaveSingleMealMonth(@RequestParam("choose") int idPreschooler,
                                                @RequestParam("choose1") String month,
                                                @RequestParam("choose2") int idSingleMeal, Model model) {
        if (sessionObject.getEmployee() != null) {
            this.idPreschoolerEditSave=idPreschooler;
            this.monthEditSave=month;
            this.idSingleMealEditSave=idSingleMeal;

            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);
            SingleBoardPrice singleBoardPrice = singleBoardPriceService.getNameSingleMealById(idSingleMeal);

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listSingleMeal", singleBoardPriceService.getListSingleMeal());

            if (preschoolerSingleBoardInMonthService.isPreschoolerSingleMealMonthInDB(idPreschooler, month,singleBoardPrice.getName())){
                PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth =
                        preschoolerSingleBoardInMonthService.preschoolerSingleMealMonthInDB(idPreschooler,month,singleBoardPrice.getName());
                model.addAttribute("nameSurname",preschooler.getName()+" "+preschooler.getSurname());
                model.addAttribute("month", preschoolerSingleBoardInMonth.getMonth());
                model.addAttribute("singleMeal", preschoolerSingleBoardInMonth);
                model.addAttribute("nameSingle",preschoolerSingleBoardInMonth.getName());
                model.addAttribute("messageOK", "Edytujesz dane dla przedszkolaka: " +
                        " " + preschooler.getName() + " " + preschooler.getSurname() + " w miesiącu " + month.toUpperCase() + ".");
            } else{
                model.addAttribute("nameSingle","");
                model.addAttribute("nameSurname","");
                model.addAttribute("month","");
                model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
                model.addAttribute("message", "Brak danych zadanych ustawień dla przedszkolaka: " +
                        " " + preschooler.getName() + " " + preschooler.getSurname() + " w miesiącu " + month.toUpperCase() + ".");
            }
            return "/admincontroller/meals/singlemonthE";

        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="/admincontroller/meals/singlemonthE", method = RequestMethod.POST,params = "save=ZAPISZ DANE")
    public String saveChangeDataSingleMealMonth(@ModelAttribute PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonthEdit,
                                                @RequestParam("choose") int idPreschooler,
                                                @RequestParam("choose1") String month,
                                                @RequestParam("choose2") int idSingleMeal, Model model) {
        if (sessionObject.getEmployee() != null) {

            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listSingleMeal", singleBoardPriceService.getListSingleMeal());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            model.addAttribute("nameSurname","");
            model.addAttribute("month","");
            model.addAttribute("nameSingle","");

            if (idPreschooler!=this.idPreschoolerEditSave || !month.equals(this.monthEditSave) || idSingleMeal!=idSingleMealEditSave){

                model.addAttribute("message", "Zapis nie jest możliwy z powodu zaznaczenia innego " +
                        "przedszkolaka i/lub miesiąca i/lub posiłku niż edytowane dane! Edytuj dane jeszcze raz!");
            }else{

                SingleBoardPrice singleBoardPrice = singleBoardPriceService.getNameSingleMealById(idSingleMeal);
                PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth =
                        preschoolerSingleBoardInMonthService.preschoolerSingleMealMonthInDB(idPreschooler,month,singleBoardPrice.getName());

                preschoolerSingleBoardInMonthService.saveSingleMealAfterChange(preschoolerSingleBoardInMonth,
                                                                                    preschoolerSingleBoardInMonthEdit);

                model.addAttribute("messageOK", "Zaktualizowano dane w bazie" +
                        " dla przedszkolaka: " + " " + preschooler.getName() + " " + preschooler.getSurname()
                        + " za miesiąc " + month.toUpperCase() + ".");

            }
            return "/admincontroller/meals/singlemonthE";
        } else {
            return "redirect:../../login";
        }
    }
}
