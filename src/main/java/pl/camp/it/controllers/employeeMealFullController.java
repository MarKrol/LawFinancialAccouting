package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.meals.FullBoardPrice;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.model.meals.SingleBoardPrice;
import pl.camp.it.model.month.Month;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.*;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class employeeMealFullController {

    private int choose=-1; //id group;
    private int idPreschoolerEditSave;
    private String monthEditSave=null;
    private int idFullMealEdit;
    private int tempChoose;
    private String tempNameMonth;

    @Resource
    SessionObject sessionObject;

    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    IFullBoardPriceService fullBoardPriceService;
    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IPreschoolerFullBoardInMonthService preschoolerFullBoardInMonthService;

    @RequestMapping(value = "admincontroller/meals/fullselectgroup", method = RequestMethod.GET)
    public String addFullSelectGroup(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("fullMeal", new PreschoolerFullBoardInMonth());
            return "admincontroller/meals/fullselectgroup";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullselectgroup", method = RequestMethod.POST)
    public String addFullSelectGroupChoose(@RequestParam int choose, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            this.choose = choose;
            return "redirect:../../admincontroller/meals/fullmonth";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullmonth", method = RequestMethod.GET)
    public String addFullMonth(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listFullMeal", fullBoardPriceService.getListFullMeal());
            model.addAttribute("fullMeal", new PreschoolerFullBoardInMonth());
            return "admincontroller/meals/fullmonth";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullmonth", method = RequestMethod.POST)
    public String addFullMonthToDB(@ModelAttribute PreschoolerFullBoardInMonth preschoolerFullBoardInMonth,
                                   @RequestParam("choose") int idPreschooler, @RequestParam("choose1") String month,
                                   @RequestParam("choose2") int idDiet, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);
            if (!preschoolerFullBoardInMonthService.isMonthPreschoolerFullBoardInMonthInDB(idPreschooler, month)) {
                if (idDiet != -1) {
                    preschoolerFullBoardInMonthService.persistPreschoolerFullBoardInMonth(preschoolerFullBoardInMonth,
                            idPreschooler, idDiet, month);
                    model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
                    model.addAttribute("listMonth", Month.getMonth());
                    model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
                    model.addAttribute("listFullMeal", fullBoardPriceService.getListFullMeal());
                    model.addAttribute("fullMeal",new PreschoolerFullBoardInMonth());
                    model.addAttribute("messageOK","Dodano dane do bazy dla przedszkolaka:" +
                                                    " "+preschooler.getName()+" "+preschooler.getSurname()+".");
                    return "admincontroller/meals/fullmonth";
                } else {
                    int idDiet1 = fullBoardPriceService.getIdFullBoardPriceByName(
                            preschoolerFullBoardInMonthService.getNameDietByIdPreschoolerFromFullBoardInMonth(idPreschooler));
                    if (idDiet1 != -1) {
                        preschoolerFullBoardInMonthService.persistPreschoolerFullBoardInMonth(preschoolerFullBoardInMonth,
                                idPreschooler, idDiet1, month);
                        model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
                        model.addAttribute("listMonth", Month.getMonth());
                        model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
                        model.addAttribute("listFullMeal", fullBoardPriceService.getListFullMeal());
                        model.addAttribute("fullMeal",new PreschoolerFullBoardInMonth());
                        model.addAttribute("messageOK","Dodano dane do bazy dla przedszkolaka:" +
                                " "+preschooler.getName()+" "+preschooler.getSurname()+".");
                        return "admincontroller/meals/fullmonth";
                    } else {
                        model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
                        model.addAttribute("listMonth", Month.getMonth());
                        model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
                        model.addAttribute("listFullMeal", fullBoardPriceService.getListFullMeal());
                        model.addAttribute("fullMeal",new PreschoolerFullBoardInMonth());
                        model.addAttribute("message", "Brak w bazie przypisanej diety do " +
                                "przedszkolaka: "+preschooler.getName()+" "+preschooler.getSurname() +"! Określ rodzaj diety!");
                        return "admincontroller/meals/fullmonth";
                    }
                }
            } else {
                model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
                model.addAttribute("listMonth", Month.getMonth());
                model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
                model.addAttribute("listFullMeal", fullBoardPriceService.getListFullMeal());
                model.addAttribute("fullMeal",new PreschoolerFullBoardInMonth());
                model.addAttribute("message", "Rozliczenie za dany miesiąc dla przedszkolaka: " +
                        preschooler.getName()+" "+preschooler.getSurname()+" jest już w bazie! Aby dokonać zmian dokonaj edycji!");
                return "admincontroller/meals/fullmonth";
            }
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullselectgroupE", method = RequestMethod.GET)
    public String loadFullMealSelectGroup(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("fullMeal", new PreschoolerFullBoardInMonth());
            return "admincontroller/meals/fullselectgroupE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullselectgroupE", method = RequestMethod.POST)
    public String loadFullSelectGroupChoose(@RequestParam int choose, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            this.choose = choose;
            return "redirect:../../admincontroller/meals/fullmonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullmonthE", method = RequestMethod.GET)
    public String editFullMonth(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listFullMeal", fullBoardPriceService.getListFullMeal());
            model.addAttribute("fullMeal", new PreschoolerFullBoardInMonth());
            return "admincontroller/meals/fullmonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullmonthE", method = RequestMethod.POST, params = "edit=EDYTUJ DANE")
    public String editToSaveFullMonth(@ModelAttribute PreschoolerFullBoardInMonth preschoolerFullBoardInMonthEdit,
                                      @RequestParam("choose") int idPreschooler, @RequestParam("choose1") String month,
                                      Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);
            idPreschoolerEditSave = idPreschooler;
            monthEditSave = month;
            if (preschoolerFullBoardInMonthService.isMonthPreschoolerFullBoardInMonthInDB(idPreschooler, month)) {
                model.addAttribute("nameGroup", preschoolGroupService.getNameGroupPreschoolById(this.choose));
                model.addAttribute("listMonth", Month.getMonth());
                model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
                model.addAttribute("listFullMeal", fullBoardPriceService.getListFullMeal());
                model.addAttribute("fullMeal",
                        preschoolerFullBoardInMonthService.getPreschoolerFullBoardInMonth(idPreschooler, month));
                model.addAttribute("nameSurnamePreschooler",
                        preschooler.getName() + " " + preschooler.getSurname());
                model.addAttribute("monthEdit", month);
                model.addAttribute("dietEdit",
                        preschoolerFullBoardInMonthService.getPreschoolerFullBoardInMonth(idPreschooler, month).getNameDiet());
                model.addAttribute("messageOK", "Edytujesz dane dla przedszkolaka: " +
                        " " + preschooler.getName() + " " + preschooler.getSurname() + " w miesiącu " + month.toUpperCase() + ".");
            } else {
                this.idPreschoolerEditSave=-1;
                model.addAttribute("nameGroup", preschoolGroupService.getNameGroupPreschoolById(this.choose));
                model.addAttribute("listMonth", Month.getMonth());
                model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
                model.addAttribute("listFullMeal", fullBoardPriceService.getListFullMeal());
                model.addAttribute("fullMeal", new PreschoolerFullBoardInMonth());
                model.addAttribute("nameSurnamePreschooler",
                        preschooler.getName() + " " + preschooler.getSurname());
                model.addAttribute("monthEdit", month);
                model.addAttribute("message", "Brak danych dla przedszkolaka: " +
                        " " + preschooler.getName() + " " + preschooler.getSurname() + " w miesiącu " + month.toUpperCase() + ".");
            }
            return "admincontroller/meals/fullmonthE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullmonthE", method = RequestMethod.POST, params = "save=ZAPISZ DANE")
    public String saveFullMonth(@ModelAttribute PreschoolerFullBoardInMonth preschoolerFullBoardInMonthEdit,
                                      @RequestParam("choose") int idPreschooler, @RequestParam("choose1") String month,
                                      @RequestParam("choose2") int idDiet, Model model){

        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());

            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);

            model.addAttribute("nameGroup", preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listFullMeal", fullBoardPriceService.getListFullMeal());
            model.addAttribute("nameSurnamePreschooler",
                    preschooler.getName() + " " + preschooler.getSurname());
            model.addAttribute("monthEdit", month);
            if (idPreschoolerEditSave == idPreschooler && monthEditSave.equals(month)) {
                if (preschoolerFullBoardInMonthService.isMonthPreschoolerFullBoardInMonthInDB(idPreschooler, month)) {
                    if (idDiet == -1) {
                        model.addAttribute("message", "Zapis nie był możliwy z powodu braku określenia diety" +
                                " dla przedszkolaka: " + " " + preschooler.getName() + " " + preschooler.getSurname()
                                + " w miesiącu " + month.toUpperCase() + ". Spróbuj ponownie!");
                    } else {

                        preschoolerFullBoardInMonthService.editAndPersistPreschoolerFullBoardInMonth
                                (preschoolerFullBoardInMonthService.getPreschoolerFullBoardInMonth(idPreschooler, month),
                                        preschoolerFullBoardInMonthEdit,fullBoardPriceService.getFullBoardPriceById(idDiet).getName()); //change
                        model.addAttribute("messageOK", "Zaktualizowano dane w bazie" +
                                " dla przedszkolaka: " + " " + preschooler.getName() + " " + preschooler.getSurname()
                                + " za miesiąc " + month.toUpperCase() + ".");
                    }
                    model.addAttribute("fullMeal",
                            preschoolerFullBoardInMonthService.getPreschoolerFullBoardInMonth(idPreschooler, month));
                    model.addAttribute("dietEdit",
                            preschoolerFullBoardInMonthService.getPreschoolerFullBoardInMonth(idPreschooler, month).getNameDiet());
                } else {
                    model.addAttribute("fullMeal", new PreschoolerFullBoardInMonth());
                    model.addAttribute("message", "Zapis nie jest możliwy z powodu braku danych do " +
                            "edycji w bazie dla przedszkolaka: " + " " + preschooler.getName() + " " + preschooler.getSurname()
                            + " za miesiąc " + month.toUpperCase() + ".");
                }
            } else {
                model.addAttribute("fullMeal", new PreschoolerFullBoardInMonth());
                model.addAttribute("message", "Zapis nie jest możliwy z powodu zaznaczenia innego " +
                        "przedszkolaka i/lub miesiąca niż edytowany! Edytuj dane jeszcze raz!");
            }
            return "admincontroller/meals/fullmonthE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullE", method = RequestMethod.GET)
    public String editFullMealPage(Model model){
        if (sessionObject.getEmployee() != null) {
            this.idFullMealEdit=-1;
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("full", new FullBoardPrice());
            model.addAttribute("fullList",fullBoardPriceService.getListFullMeal());
            return "admincontroller/meals/fullE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullE", method = RequestMethod.POST, params = "edit=EDYTUJ DIETĘ")
    public String editFullMealShow(@RequestParam("choose") int idFullMeal, Model model) {
        if (sessionObject.getEmployee() != null) {
            this.idFullMealEdit=idFullMeal;
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            FullBoardPrice fullBoardPrice= fullBoardPriceService.getFullBoardPriceById(idFullMeal);
            model.addAttribute("full", fullBoardPrice);
            model.addAttribute("nameFull",fullBoardPrice.getName());
            model.addAttribute("fullList",fullBoardPriceService.getListFullMeal());
            return "admincontroller/meals/fullE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullE", method = RequestMethod.POST, params = "save=ZAPISZ ZMIANY")
    public String saveEditFullMealShow(@RequestParam("data") List<String> fullEdit, @RequestParam("choose") int idFullEdit, Model model) {
        if (sessionObject.getEmployee() != null) {

            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("full", new FullBoardPrice());

            if (this.idFullMealEdit==idFullMealEdit) {
                FullBoardPrice fullBoardPrice= fullBoardPriceService.getFullBoardPriceById(idFullEdit);
                fullBoardPriceService.saveChangeFullMeal(fullBoardPrice, fullEdit);
                model.addAttribute("fullList", fullBoardPriceService.getListFullMeal());
                model.addAttribute("nameFull", fullBoardPrice.getName());
                model.addAttribute("messageOK", "Zapisano zmiany w bazie dla edytowanego pobytu!");
            } else{
                model.addAttribute("message", "Dokunujesz zmian w wyedytowanym posiłku, " +
                        "który jest inny niż zaznaczony! Edytuj pobyt jeszcze raz!");
            }
            return "admincontroller/meals/fullE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullE", method = RequestMethod.POST, params = "delete=USUŃ DIETĘ")
    public String deleteSingleMealShow(@RequestParam("choose") int idFullMeal, Model model) {
        if (sessionObject.getEmployee() != null) {
            this.idFullMealEdit=idFullMeal;
            return "redirect:../../admincontroller/meals/fullD";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullD",method = RequestMethod.GET)
    public String confirmDeleteFullMeal(Model model){
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            FullBoardPrice fullBoardPrice= fullBoardPriceService.getFullBoardPriceById(this.idFullMealEdit);
            model.addAttribute("full", fullBoardPrice);
            model.addAttribute("nameFull",fullBoardPrice.getName());
            model.addAttribute("fullList",fullBoardPriceService.getListFullMeal());
            model.addAttribute("message","Czy na pewno chcesz usunąć wybraną dietę?");
            return "admincontroller/meals/fullD";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullD",method = RequestMethod.POST,params = "nodelete=NIE")
    public String noDeleteFullMeal(){
        if (sessionObject.getEmployee() != null) {
            return "redirect:../../admincontroller/meals/fullE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullD",method = RequestMethod.POST,params = "delete=TAK")
    public String yesDeleteSingleMeal(Model model){
        if (sessionObject.getEmployee() != null) {
            if (!preschoolerFullBoardInMonthService.isNameFullMealPreschoolerInDB
                    (fullBoardPriceService.getFullBoardPriceById(this.idFullMealEdit).getName())) {
                fullBoardPriceService.deleteFullMeal(fullBoardPriceService.getFullBoardPriceById(this.idFullMealEdit));
                return "redirect:../../admincontroller/meals/fullE";
            }else{
                model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                        sessionObject.getEmployee().getSurname());
                FullBoardPrice fullBoardPrice= fullBoardPriceService.getFullBoardPriceById(this.idFullMealEdit);
                model.addAttribute("full", fullBoardPrice);
                model.addAttribute("nameFull",fullBoardPrice.getName());
                model.addAttribute("fullList",fullBoardPriceService.getListFullMeal());

                model.addAttribute("message","Nie można usunąć diety ponieważ jest on już używany przez" +
                        " program w innym miejscu. Usuń wszystkie przydzielone diety o tej nazwie, a dopiero " +
                        "uzyskasz możliwość usunięcia diety!");

                return "admincontroller/meals/fullD";
            }
        }else {
            return "redirect:../../login";
        }
    }


    @RequestMapping(value = "admincontroller/meals/fullmonthVD", method = RequestMethod.GET)
    public String showViewFullMeal(Model model){
        if (sessionObject.getEmployee() != null) {

            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());

            if (this.choose!=-1 && this.monthEditSave!=null) {
                model.addAttribute("fullMealAllPreschoolers",
                        preschoolerFullBoardInMonthService.getAllPreschoolerListByIdPreschoolerByMonth
                                (preschoolerService.getPreschoolerList(this.choose), this.monthEditSave));
                model.addAttribute("nameGroup", preschoolGroupService.getNameGroupPreschoolById(this.choose));
                model.addAttribute("month", this.monthEditSave);
                this.choose=-1;
                this.monthEditSave=null;
            }
            return "admincontroller/meals/fullmonthVD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/fullmonthVD", method = RequestMethod.POST)
    public String showViewFullMealAllPreschooler(@RequestParam("choose1") String nameMonth,
                                                 @RequestParam("choose") int idGroup,  Model model){
        if (sessionObject.getEmployee() != null) {
            this.monthEditSave=nameMonth;   this.tempNameMonth=nameMonth;
            this.choose=idGroup;            this.tempChoose =idGroup;
//            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
//                    sessionObject.getEmployee().getSurname());
//            model.addAttribute("listMonth", Month.getMonth());
//            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
//            model.addAttribute("fullMealAllPreschoolers",
//                    preschoolerFullBoardInMonthService.getAllPreschoolerListByIdPreschoolerByMonth
//                            (preschoolerService.getPreschoolerList(idGroup),nameMonth));
//            model.addAttribute("nameGroup", preschoolGroupService.getNameGroupPreschoolById(idGroup));
//            model.addAttribute("month",nameMonth);
            return "redirect:../../admincontroller/meals/fullmonthVD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/fullmonthVD/{id}", method = RequestMethod.GET)
    public String ShowEditFullMealPreschooler(@PathVariable String id, Model model){
        if (sessionObject.getEmployee() != null) {
                this.choose=this.tempChoose;
                this.monthEditSave=this.tempNameMonth;
                sessionObject.setSendData(Integer.parseInt(id));
            return "redirect:../../../admincontroller/meals/fullmonthVE";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/fullmonthVE", method = RequestMethod.GET)
    public String editFullMealPreschooler(Model model){
        if (sessionObject.getEmployee() != null) {
            PreschoolerFullBoardInMonth preschoolerFullBoardInMonth=
                    preschoolerFullBoardInMonthService.getPreschoolerFullMealMonthByIdPreschoolerMonthFullMeal(sessionObject.getSendData());

            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());

            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("month",this.monthEditSave);
            model.addAttribute("preschooler",
                    preschoolerFullBoardInMonth.getPreschooler().getSurname()+" "+preschoolerFullBoardInMonth.getPreschooler().getName());
            model.addAttribute("fullMeal",preschoolerFullBoardInMonth);
            model.addAttribute("listFullMeal",fullBoardPriceService.getListFullMeal());
            model.addAttribute("dietEdit",preschoolerFullBoardInMonth.getNameDiet());
            model.addAttribute("fullMealPreschooler", new PreschoolerFullBoardInMonth());
            return "admincontroller/meals/fullmonthVE";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/fullmonthVE", method = RequestMethod.POST, params = "return=POWRÓT DO PODGLĄDU")
    public String noSaveChangeFullMealEditPOT(){
        if (sessionObject.getEmployee() != null) {
            return "redirect:../../admincontroller/meals/fullmonthVD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/fullmonthVE", method = RequestMethod.POST, params = "save=ZAPISZ DANE")
    public String saveChangeFullMealEditPOST(@ModelAttribute PreschoolerFullBoardInMonth preschoolerFullBoardInMonthEdit,
                                             @RequestParam("choose2") String nameDiet){
        if (sessionObject.getEmployee() != null) {
            preschoolerFullBoardInMonthService.editAndPersistPreschoolerFullBoardInMonth
                    (preschoolerFullBoardInMonthService.getPreschoolerFullMealMonthByIdPreschoolerMonthFullMeal(sessionObject.getSendData())
                            ,preschoolerFullBoardInMonthEdit,nameDiet);
            return "redirect:../../admincontroller/meals/fullmonthVD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/fullmonthVD/D/{id}", method = RequestMethod.GET)
    public String showDeleteFullMealPreschooler(@PathVariable String id, Model model){
        if (sessionObject.getEmployee() != null) {
            this.choose=this.tempChoose;
            this.monthEditSave=this.tempNameMonth;
            sessionObject.setSendData(Integer.parseInt(id));
            return "redirect:../../../../admincontroller/meals/fullmonthVDD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/fullmonthVDD", method = RequestMethod.GET)
    public String deleteFullMealPreschooler(Model model){
        if (sessionObject.getEmployee() != null) {
            PreschoolerFullBoardInMonth preschoolerFullBoardInMonth=
                    preschoolerFullBoardInMonthService.getPreschoolerFullMealMonthByIdPreschoolerMonthFullMeal(sessionObject.getSendData());

            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());

            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("month",this.monthEditSave);
            model.addAttribute("preschooler",
                    preschoolerFullBoardInMonth.getPreschooler().getSurname()+" "+preschoolerFullBoardInMonth.getPreschooler().getName());
            model.addAttribute("fullMeal",preschoolerFullBoardInMonth);
            model.addAttribute("listFullMeal",fullBoardPriceService.getListFullMeal());
            model.addAttribute("dietEdit",preschoolerFullBoardInMonth.getNameDiet());
            model.addAttribute("fullMealPreschooler", new PreschoolerFullBoardInMonth());
            model.addAttribute("message","Czy na pewno chcesz usunąć wybrane dane?");
            return "admincontroller/meals/fullmonthVDD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/fullmonthVDD", method = RequestMethod.POST, params = "noDelete=NIE")
    public String noDeleteFullMealEditPOT(){
        if (sessionObject.getEmployee() != null) {
            return "redirect:../../admincontroller/meals/fullmonthVD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/fullmonthVDD", method = RequestMethod.POST, params = "delete=TAK")
    public String deleteFullMealEditPOST(){
        if (sessionObject.getEmployee() != null) {
            preschoolerFullBoardInMonthService.
                    deleteFullMealPreschoolInMonthByIdPreschoolerFullMealBoardPrice(sessionObject.getSendData());
            return "redirect:../../admincontroller/meals/fullmonthVD";
        }else{
            return "redirect:../../login";
        }
    }
}