package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.employee.EmployeeRole;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.meals.SingleBoardPrice;
import pl.camp.it.model.month.Month;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.model.stay.Stay;
import pl.camp.it.services.*;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class EmployeeMealSingleController {

    private int choose; //choose groupPreschooler
    private int idPreschoolerEditSave;
    private String monthEditSave;
    private int idSingleMealEditSave;
    private int tempChoose;
    private String tempNameMonth;
    private int tempSingleMealEditSave;

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
    @Autowired
    IEmployeeService employeeService;

    @RequestMapping(value = "admincontroller/meals/singleselectgroup", method = RequestMethod.GET)
    public String addSingleSelectGroup(Model model){
        if (sessionObject.getEmployee()!=null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", employeeService.getListPreschoolerGroupByUserRole(sessionObject.getEmployee()));
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            return "admincontroller/meals/singleselectgroup";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singleselectgroup", method = RequestMethod.POST)
    public String addSingleSelectGroupChoose(@RequestParam int choose, Model model){
        if (sessionObject.getEmployee()!=null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            this.choose = choose;
            return "redirect:../../admincontroller/meals/singlemonth";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singlemonth", method = RequestMethod.GET)
    public String addSingleMonth(Model model){
        if (sessionObject.getEmployee()!=null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listSingleMeal", singleBoardPriceService.getListSingleMeal());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            model.addAttribute("nameSingle","");
            return "admincontroller/meals/singlemonth";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singlemonth", method = RequestMethod.POST)
    public String saveSingleMonthInDB(@ModelAttribute PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth,
                                      @RequestParam("choose") int idPreschooler, @RequestParam("choose1") String month,
                                      @RequestParam("choose2") int idSingleEat, Model model){
        if (sessionObject.getEmployee()!=null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }else {
                if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString())
                        && !employeeService.userAuthorizationByEmployeeAndPreschooler(sessionObject.getEmployee(),
                        preschoolerService.getPreschoolerById(idPreschooler))) {
                    return "redirect:../../notauthorized";
                }
            }

            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
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
            return "admincontroller/meals/singlemonth";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singleselectgroupE", method = RequestMethod.GET)
    public String addSingleSelectGroupE(Model model){
        if (sessionObject.getEmployee()!=null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", employeeService.getListPreschoolerGroupByUserRole(sessionObject.getEmployee()));
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            return "admincontroller/meals/singleselectgroupE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singleselectgroupE", method = RequestMethod.POST)
    public String addSingleSelectGroupChooseE(@RequestParam int choose, Model model){
        if (sessionObject.getEmployee()!=null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            this.choose = choose;
            return "redirect:../../admincontroller/meals/singlemonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singlemonthE", method = RequestMethod.GET)
    public String addSingleMonthE(Model model){
        if (sessionObject.getEmployee()!=null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listSingleMeal", singleBoardPriceService.getListSingleMeal());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            model.addAttribute("nameSingle","");
            model.addAttribute("nameSurname","");
            model.addAttribute("month","");
            return "admincontroller/meals/singlemonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/singlemonthE", method = RequestMethod.POST,params = "edit=EDYTUJ DANE")
    public String editDataToSaveSingleMealMonth(@RequestParam("choose") int idPreschooler,
                                                @RequestParam("choose1") String month,
                                                @RequestParam("choose2") int idSingleMeal, Model model) {
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }else {
                if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString())
                        && !employeeService.userAuthorizationByEmployeeAndPreschooler(sessionObject.getEmployee(),
                        preschoolerService.getPreschoolerById(idPreschooler))) {
                    return "redirect:../../notauthorized";
                }
            }

            this.idPreschoolerEditSave=idPreschooler;
            this.monthEditSave=month;
            this.idSingleMealEditSave=idSingleMeal;

            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);
            SingleBoardPrice singleBoardPrice = singleBoardPriceService.getNameSingleMealById(idSingleMeal);

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
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
                this.idPreschoolerEditSave=-1;
                this.idSingleMealEditSave=-1;
                model.addAttribute("nameSingle","");
                model.addAttribute("nameSurname","");
                model.addAttribute("month","");
                model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
                model.addAttribute("message", "Brak danych dla zadanych ustawień dla przedszkolaka: " +
                        " " + preschooler.getName() + " " + preschooler.getSurname() + " w miesiącu " + month.toUpperCase() + ".");
            }
            return "admincontroller/meals/singlemonthE";

        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/singlemonthE", method = RequestMethod.POST,params = "save=ZAPISZ DANE")
    public String saveChangeDataSingleMealMonth(@ModelAttribute PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonthEdit,
                                                @RequestParam("choose") int idPreschooler,
                                                @RequestParam("choose1") String month,
                                                @RequestParam("choose2") int idSingleMeal, Model model) {
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }else {
                if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString())
                        && !employeeService.userAuthorizationByEmployeeAndPreschooler(sessionObject.getEmployee(),
                        preschoolerService.getPreschoolerById(idPreschooler))) {
                    return "redirect:../../notauthorized";
                }
            }

            Preschooler preschooler = preschoolerService.getPreschoolerById(idPreschooler);

            model.addAttribute("nameGroup",preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            model.addAttribute("listSingleMeal", singleBoardPriceService.getListSingleMeal());
            model.addAttribute("singleMeal", new PreschoolerSingleBoardInMonth());
            model.addAttribute("nameSurname","");
            model.addAttribute("month","");
            model.addAttribute("nameSingle","");

            if (idPreschooler!=this.idPreschoolerEditSave || !month.equals(this.monthEditSave) || idSingleMeal!=this.idSingleMealEditSave){

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
            return "admincontroller/meals/singlemonthE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singleE", method = RequestMethod.GET)
    public String editSingleMealPage(Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            this.idSingleMealEditSave=-1;
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("single", new SingleBoardPrice());
            model.addAttribute("singleList",singleBoardPriceService.getListSingleMeal());
            return "admincontroller/meals/singleE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singleE", method = RequestMethod.POST, params = "edit=EDYTUJ POSIŁEK")
    public String editSingleMealShow(@RequestParam("choose") int idSingleMeal, Model model) {
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            this.idSingleMealEditSave=idSingleMeal;
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            SingleBoardPrice singleBoardPrice = singleBoardPriceService.getNameSingleMealById(idSingleMeal);
            model.addAttribute("single", singleBoardPrice);
            model.addAttribute("nameSingle",singleBoardPrice.getName());
            model.addAttribute("singleList",singleBoardPriceService.getListSingleMeal());
            return "admincontroller/meals/singleE";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singleE", method = RequestMethod.POST, params = "save=ZAPISZ ZMIANY")
    public String saveEditSingleMealShow(@RequestParam("data") List<String> singleEdit, @RequestParam("choose") int idSingleEdit, Model model) {
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("single", new SingleBoardPrice());

            if (this.idSingleMealEditSave==idSingleEdit) {
                SingleBoardPrice singleBoardPrice = singleBoardPriceService.getNameSingleMealById(idSingleEdit);
                singleBoardPriceService.saveChangeSingleMeal(singleBoardPrice, singleEdit);
                model.addAttribute("singleList", singleBoardPriceService.getListSingleMeal());
                model.addAttribute("nameSingle", singleBoardPrice.getName());
                model.addAttribute("messageOK", "Zapisano zmiany w bazie dla edytowanego pobytu!");
            } else{
                model.addAttribute("message", "Dokunujesz zmian w wyedytowanym posiłku, " +
                        "który jest inny niż zaznaczony! Edytuj pobyt jeszcze raz!");
            }
            return "admincontroller/meals/singleE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singleE", method = RequestMethod.POST, params = "delete=USUŃ POSIŁEK")
    public String deleteSingleMealShow(@RequestParam("choose") int idSingleMeal, Model model) {
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            this.idSingleMealEditSave=idSingleMeal;
            return "redirect:../../admincontroller/meals/singleD";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singleD",method = RequestMethod.GET)
    public String confirmDeleteSingleMeal(Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            SingleBoardPrice singleBoardPrice = singleBoardPriceService.getNameSingleMealById(this.idSingleMealEditSave);
            model.addAttribute("single", singleBoardPrice);
            model.addAttribute("nameSingle",singleBoardPrice.getName());
            model.addAttribute("singleList",singleBoardPriceService.getListSingleMeal());
            model.addAttribute("message","Czy na pewno chcesz usunąć wybrany pobyt?");
            return "admincontroller/meals/singleD";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singleD",method = RequestMethod.POST,params = "nodelete=NIE")
    public String noDeleteSingleMeal(){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            return "redirect:../../admincontroller/meals/singleE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singleD",method = RequestMethod.POST,params = "delete=TAK")
    public String yesDeleteSingleMeal(Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString()) ||
                    (sessionObject.getEmployee().getRole().equals("ACCOUNT"))){
                return "redirect:../../notauthorized";
            }

            if (!preschoolerSingleBoardInMonthService.isNameSingleMealPreschoolerInDB
                    (singleBoardPriceService.getNameSingleMealById(this.idSingleMealEditSave).getName())) {
                singleBoardPriceService.deleteSingleMeal(singleBoardPriceService.getNameSingleMealById(this.idSingleMealEditSave));
                return "redirect:../../admincontroller/meals/singleE";
            }else{
                model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
                model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                        sessionObject.getEmployee().getSurname());
                SingleBoardPrice singleBoardPrice = singleBoardPriceService.getNameSingleMealById(this.idSingleMealEditSave);
                model.addAttribute("single", singleBoardPrice);
                model.addAttribute("nameSingle",singleBoardPrice.getName());
                model.addAttribute("singleList",singleBoardPriceService.getListSingleMeal());

                model.addAttribute("message","Nie można usunąć posiłku ponieważ jest on już używany przez" +
                        " program w innym miejscu. Usuń wszystkie przydzielone posiłki o tej nazwie, a dopiero " +
                        "uzyskasz możliwość usunięcia posiłku!");

                return "admincontroller/meals/singleD";
            }
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singlemonthVD", method = RequestMethod.GET)
    public String showViewStay(Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("listPreschoolGroup", employeeService.getListPreschoolerGroupByUserRole(sessionObject.getEmployee()));
            model.addAttribute("listSingleMeal",singleBoardPriceService.getListSingleMeal());

            if (this.choose!=-1 && this.monthEditSave!=null && this.idSingleMealEditSave!=-1) {
                model.addAttribute("singleMealAllPreschoolers",
                        preschoolerSingleBoardInMonthService.getAllPreschoolerListByIdPreschoolerByMonth
                                (preschoolerService.getPreschoolerList(this.choose),this.monthEditSave,
                                        singleBoardPriceService.getNameSingleMealById(this.idSingleMealEditSave).getName()));
                model.addAttribute("nameGroup", preschoolGroupService.getNameGroupPreschoolById(this.choose));
                model.addAttribute("month", this.monthEditSave);
                model.addAttribute("nameSingleMeal", singleBoardPriceService.getNameSingleMealById(this.idSingleMealEditSave).getName());
                this.choose=-1;
                this.monthEditSave=null;
                this.idSingleMealEditSave=-1;
            }
            return "admincontroller/meals/singlemonthVD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/meals/singlemonthVD", method = RequestMethod.POST)
    public String showViewSingleMealAllPreschooler(@RequestParam("choose1") String nameMonth,
                                                   @RequestParam("choose2") int idSingleMeal,
                                                   @RequestParam("choose") int idGroup,  Model model){

        if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
            return "redirect:../../notauthorized";
        }else {
            if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString())
                    && !employeeService.userAuthorizationByEmployeeAndGroupPreschooler(sessionObject.getEmployee(),
                    preschoolGroupService.getPreschoolerGroupByName(preschoolGroupService.getNameGroupPreschoolById(idGroup)))) {
                return "redirect:../../notauthorized";
            }
        }

        if (sessionObject.getEmployee() != null) {
            this.monthEditSave=nameMonth;                this.tempNameMonth=nameMonth;
            this.choose=idGroup;                         this.tempChoose =idGroup;
            this.idSingleMealEditSave= idSingleMeal;     this.tempSingleMealEditSave=idSingleMeal;

            return "redirect:../../admincontroller/meals/singlemonthVD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/singlemonthVD/{id}", method = RequestMethod.GET)
    public String ShowEditSingleMealPreschooler(@PathVariable String id, Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../../notauthorized";
            }else {
                if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString())
                        && !employeeService.userAuthorizationByEmployeeAndPreschooler(sessionObject.getEmployee(),
                        preschoolerSingleBoardInMonthService.getPreschoolerSingleBoardMonthById(
                                (Integer.valueOf(id))).getPreschooler())) {
                    return "redirect:../../../notauthorized";
                }
            }

            this.choose=this.tempChoose;
            this.monthEditSave=this.tempNameMonth;
            this.idSingleMealEditSave=this.tempSingleMealEditSave;
            sessionObject.setSendData(Integer.parseInt(id));
            return "redirect:../../../admincontroller/meals/singlemonthVE";
        }else{
            return "redirect:../../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/singlemonthVE", method = RequestMethod.GET)
    public String editSingleMealPreschooler(Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }

            PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth=
                    preschoolerSingleBoardInMonthService.getPreschoolerSingleBoardMonthById(sessionObject.getSendData());

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());

            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("month",this.monthEditSave);
            model.addAttribute("preschooler",
                    preschoolerSingleBoardInMonth.getPreschooler().getSurname()+" "+preschoolerSingleBoardInMonth.getPreschooler().getName());
            model.addAttribute("singleMeal",preschoolerSingleBoardInMonth);
            model.addAttribute("singleMealPreschooler", new PreschoolerSingleBoardInMonth());
            return "admincontroller/meals/singlemonthVE";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/singlemonthVE", method = RequestMethod.POST, params = "return=POWRÓT DO PODGLĄDU")
    public String noSaveChangeSingleMealEditPOT(){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }else {
                if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString())
                        && !employeeService.userAuthorizationByEmployeeAndPreschooler(sessionObject.getEmployee(),
                        preschoolerService.getPreschoolerById(sessionObject.getSendData()))) {
                    return "redirect:../../notauthorized";
                }
            }

            return "redirect:../../admincontroller/meals/singlemonthVD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/singlemonthVE", method = RequestMethod.POST, params = "save=ZAPISZ DANE")
    public String saveChangeSingleMealEditPOST(@ModelAttribute PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonthEdit){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }else {
                if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString())
                        && !employeeService.userAuthorizationByEmployeeAndPreschooler(sessionObject.getEmployee(),
                        preschoolerService.getPreschoolerById(sessionObject.getSendData()))) {
                    return "redirect:../../notauthorized";
                }
            }

            preschoolerSingleBoardInMonthService.saveSingleMealAfterChange
                    (preschoolerSingleBoardInMonthService.getPreschoolerSingleBoardMonthById(sessionObject.getSendData())
                                                                                    ,preschoolerSingleBoardInMonthEdit);
            return "redirect:../../admincontroller/meals/singlemonthVD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/singlemonthVD/D/{id}", method = RequestMethod.GET)
    public String showDeleteFullMealPreschooler(@PathVariable String id, Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../../../notauthorized";
            }else {
                if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString())
                        && !employeeService.userAuthorizationByEmployeeAndPreschooler(sessionObject.getEmployee(),
                        preschoolerSingleBoardInMonthService.getPreschoolerSingleBoardMonthById
                                (Integer.valueOf(id)).getPreschooler())) {
                    return "redirect:../../../../notauthorized";
                }
            }

            this.choose=this.tempChoose;
            this.monthEditSave=this.tempNameMonth;
            this.idSingleMealEditSave=this.tempSingleMealEditSave;
            sessionObject.setSendData(Integer.parseInt(id));
            return "redirect:../../../../admincontroller/meals/singlemonthVDD";
        }else{
            return "redirect:../../../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/singlemonthVDD", method = RequestMethod.GET)
    public String deleteFullMealPreschooler(Model model){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }

            PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth=
                    preschoolerSingleBoardInMonthService.getPreschoolerSingleBoardMonthById(sessionObject.getSendData());

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());

            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("month",this.monthEditSave);
            model.addAttribute("preschooler",
                    preschoolerSingleBoardInMonth.getPreschooler().getSurname()+" "+preschoolerSingleBoardInMonth.getPreschooler().getName());
            model.addAttribute("singleMeal",preschoolerSingleBoardInMonth);
            model.addAttribute("singleMealPreschooler", new PreschoolerSingleBoardInMonth());
            model.addAttribute("message","Czy na pewno chcesz usunąć wybrane dane?");
            return "admincontroller/meals/singlemonthVDD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/singlemonthVDD", method = RequestMethod.POST, params = "noDelete=NIE")
    public String noDeleteSingleMealEditPOT(){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }else {
                if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString())
                        && !employeeService.userAuthorizationByEmployeeAndPreschooler(sessionObject.getEmployee(),
                        preschoolerService.getPreschoolerById(sessionObject.getSendData()))) {
                    return "redirect:../../notauthorized";
                }
            }

            return "redirect:../../admincontroller/meals/singlemonthVD";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/meals/singlemonthVDD", method = RequestMethod.POST, params = "delete=TAK")
    public String deleteSingleMealEditPOST(){
        if (sessionObject.getEmployee() != null) {

            if (sessionObject.getEmployee().getRole().equals("ACCOUNT")){
                return "redirect:../../notauthorized";
            }else {
                if (sessionObject.getEmployee().getRole().equals(EmployeeRole.TEACHER.toString())
                        && !employeeService.userAuthorizationByEmployeeAndPreschooler(sessionObject.getEmployee(),
                        preschoolerService.getPreschoolerById(sessionObject.getSendData()))) {
                    return "redirect:../../notauthorized";
                }
            }

            preschoolerSingleBoardInMonthService.deleteSingleMealPreschoolInMonthByIdPreschoolerSingleMealBoardPrice
                    (sessionObject.getSendData());
            return "redirect:../../admincontroller/meals/singlemonthVD";
        }else{
            return "redirect:../../login";
        }
    }

}
