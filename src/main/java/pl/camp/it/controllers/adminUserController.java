package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.parent.Parent;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.*;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class adminUserController {

    private String nameGroup=null;
    private int idGroupEdit=-1;

    @Resource
    SessionObject sessionObject;

    @Autowired
    IEmployeeService employeeService;
    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    IParentService parentService;

    @RequestMapping(value = "admincontroller/employee/addemployee",method = RequestMethod.GET)
    public String addEmployee(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employee", new Employee());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup",preschoolGroupService.getListPreschoolerGroup());
            return "admincontroller/employee/addemployee";
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/employee/addemployee",method = RequestMethod.POST)
    public String addEmployeeToDB(@ModelAttribute Employee employee, @RequestParam String choose, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            if (employee.getRole().equals("teacher")) {
                if (preschoolGroupService.isPreschoolGroupInDB(choose)) {
                    employeeService.persistEmployee(employee);
                    employeeService.persistEmployeeLogin(employee);
                    preschoolGroupService.setPreschoolGroupAndAddEmployeeToGroup(choose.toUpperCase(),employee);
                    return "redirect:../../admincontroller/employee/statement";
                } else {
                    model.addAttribute("message", "Wybierz grupę przedszkolną!");
                    model.addAttribute("listPreschoolGroup",preschoolGroupService.getListPreschoolerGroup());
                    return "admincontroller/employee/addemployee";
                }
            } else{
                employeeService.persistEmployee(employee);
                employeeService.persistEmployeeLogin(employee);
                return "redirect:../../admincontroller/employee/statement";
            }
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/addgroup",method = RequestMethod.GET)
    public String addgroup(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("preschoolGroup", new PreschoolGroup());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/grouppreschooler/addgroup";
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/addgroup", method = RequestMethod.POST)
    public String addgroupaToDB(@ModelAttribute PreschoolGroup preschoolGroup, Model model){
        if (sessionObject.getEmployee()!=null){
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            if (!preschoolGroupService.isPreschoolGroupInDB(preschoolGroup.getNameGroup())) {
                preschoolGroupService.persistPreschoolGroup(preschoolGroup);

                return "redirect:../../admincontroller/grouppreschooler/statement";
            } else {
                model.addAttribute("message","Grupa o podanej nazwie istnieje już w bazie!");
                return "admincontroller/grouppreschooler/addgroup";
            }
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/addpreschooler",method = RequestMethod.GET)
    public String addpreschooler(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("preschooler",new Preschooler());
            model.addAttribute("listGroupPreschool", preschoolGroupService.getListPreschoolerGroup());
            return "admincontroller/preschooler/addpreschooler";
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/addpreschooler",method = RequestMethod.POST)
    public String addpreschoolerToDB(@ModelAttribute Preschooler preschooler,
                                     @RequestParam String chooseGroupPreschool, Model model){
        if (sessionObject.getEmployee()!=null){
            if(preschoolGroupService.isPreschoolGroupInDB(chooseGroupPreschool)){
                preschoolerService.setPreschoolerAndAddPreschoolGroupToPreschooler(preschooler, chooseGroupPreschool);
                preschoolerService.persistPreschooler(preschooler);
                parentService.persistParentLogin(preschooler);
                return "redirect:../../admincontroller/preschooler/statement";
            } else {
                model.addAttribute("listGroupPreschool", preschoolGroupService.getListPreschoolerGroup());
                model.addAttribute("message","Wybierz grupę przedszkolną!");
                return "admincontroller/preschooler/addpreschooler";
            }
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/employee/statement", method = RequestMethod.GET)
    public String statementEmployee(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/employee/statement";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/statement", method = RequestMethod.GET)
    public String statementPreschoolGroup(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/grouppreschooler/statement";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/statement", method = RequestMethod.GET)
    public String statementPreschooler(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "admincontroller/preschooler/statement";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/preschoolerVED", method = RequestMethod.GET)
    public String openPageEditPreschooler(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());

            if (this.nameGroup!=null && this.idGroupEdit!=-1){
                model.addAttribute("allPreschoolers", preschoolerService.getPreschoolerList(this.idGroupEdit));
                model.addAttribute("nameGroup", this.nameGroup=preschoolGroupService.getNameGroupPreschoolById(this.idGroupEdit));
                this.idGroupEdit=-1;
                this.nameGroup=null;
            }

            return "admincontroller/preschooler/preschoolerVED";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/preschoolerVED", method = RequestMethod.POST)
    public String showPageEditGroupPreschooler(@RequestParam("choose") int idGroup, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("allPreschoolers", preschoolerService.getPreschoolerList(idGroup));
            model.addAttribute("nameGroup", this.nameGroup=preschoolGroupService.getNameGroupPreschoolById(idGroup));
            this.idGroupEdit=idGroup;
            return "admincontroller/preschooler/preschoolerVED";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/preschoolerE/{idPreschooler}", method = RequestMethod.GET)
    public String openPageEditPreschooler(@PathVariable String idPreschooler, Model model){
        if (sessionObject.getEmployee()!=null) {
            this.idGroupEdit=preschoolerService.getPreschoolerById(Integer.parseInt(idPreschooler)).getPreschoolGroup().getId();
            this.nameGroup=preschoolGroupService.getNameGroupPreschoolById(this.idGroupEdit);
            sessionObject.setSendData(Integer.parseInt(idPreschooler));
            return "redirect:../../../admincontroller/preschooler/preschoolerE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/preschoolerE", method = RequestMethod.GET)
    public String editPreschooler(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("preschooler", new Preschooler());
            model.addAttribute("preschooler", preschoolerService.getPreschoolerById(sessionObject.getSendData()));
            model.addAttribute("listGroupPreschool", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("nameGroup",this.nameGroup);
            return "admincontroller/preschooler/preschoolerE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/preschooler/preschoolerE", method = RequestMethod.POST, params = "return=NIE ZAPISUJ ZMIAN")
    public String noSaveChangePreschoolerEditPOT(){
        if (sessionObject.getEmployee() != null) {
            return "redirect:../../admincontroller/preschooler/preschoolerVED";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/preschooler/preschoolerE", method = RequestMethod.POST, params = "save=ZAPISZ ZMIANY")
    public String saveChangePreschoolerEditPOT(@ModelAttribute Preschooler preschoolerEdit,
                                               @RequestParam("chooseGroupPreschool") int idGroup){
        if (sessionObject.getEmployee() != null) {
            preschoolerService.setPreschoolerAndAddPreschoolGroupToPreschooler
                                        (preschoolerEdit, preschoolGroupService.getNameGroupPreschoolById(idGroup));
            preschoolerService.saveChangePreschooler
                                (preschoolerService.getPreschoolerById(sessionObject.getSendData()),preschoolerEdit);
            return "redirect:../../admincontroller/preschooler/preschoolerVED";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/preschoolerD/{idPreschooler}", method = RequestMethod.GET)
    public String openPageDeletePreschooler(@PathVariable String idPreschooler, Model model){
        if (sessionObject.getEmployee()!=null) {
            this.idGroupEdit=preschoolerService.getPreschoolerById(Integer.parseInt(idPreschooler)).getPreschoolGroup().getId();
            this.nameGroup=preschoolGroupService.getNameGroupPreschoolById(this.idGroupEdit);
            sessionObject.setSendData(Integer.parseInt(idPreschooler));
            return "redirect:../../../admincontroller/preschooler/preschoolerD";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/preschoolerD", method = RequestMethod.GET)
    public String deletePreschooler(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("preschooler", new Preschooler());
            model.addAttribute("preschooler", preschoolerService.getPreschoolerById(sessionObject.getSendData()));
            model.addAttribute("listGroupPreschool", preschoolGroupService.getListPreschoolerGroup());
            model.addAttribute("nameGroup",this.nameGroup);
            model.addAttribute("message", "Czy na pewno chcesz usunąć dane?");
            return "admincontroller/preschooler/preschoolerD";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/preschooler/preschoolerD", method = RequestMethod.POST, params = "noDelete=NIE")
    public String noDeletePreschoolerEditPOT(){
        if (sessionObject.getEmployee() != null) {
            return "redirect:../../admincontroller/preschooler/preschoolerVED";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value ="admincontroller/preschooler/preschoolerD", method = RequestMethod.POST, params = "delete=TAK")
    public String deletePreschoolerEditPOT(){
        if (sessionObject.getEmployee() != null) {
            preschoolerService.deletePreschooler(preschoolerService.getPreschoolerById(sessionObject.getSendData()),
                    preschoolGroupService.getPreschoolerGroupByName("BEZ GRUPY"));
            return "redirect:../../admincontroller/preschooler/preschoolerVED";
        }else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/preschoolerAP/{idPreschooler}", method = RequestMethod.GET)
    public String openPageToAddParent(@PathVariable String idPreschooler, Model model){
        if (sessionObject.getEmployee()!=null) {
            this.idGroupEdit=preschoolerService.getPreschoolerById(Integer.parseInt(idPreschooler)).getPreschoolGroup().getId();
            this.nameGroup=preschoolGroupService.getNameGroupPreschoolById(this.idGroupEdit);
            sessionObject.setSendData(Integer.parseInt(idPreschooler));
            return "redirect:../../../admincontroller/preschooler/preschoolerAP";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/preschoolerAP", method = RequestMethod.GET)
    private String openPageToAddParent(Model model){
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            Preschooler preschooler=preschoolerService.getPreschoolerById(sessionObject.getSendData());

            model.addAttribute("preschooler", preschooler.getSurname()+" "+preschooler.getName());
            model.addAttribute("group", this.nameGroup);
            model.addAttribute("parent", new Parent());
            return "admincontroller/preschooler/preschoolerAP";
        }else {
            return "redirect:../../login";
        }
    }
}
