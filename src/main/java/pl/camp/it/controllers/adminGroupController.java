package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.services.IEmployeeService;
import pl.camp.it.services.IPreschoolGroupService;
import pl.camp.it.services.IPreschoolerService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class adminGroupController {

    private int idGroup = -1;

    @Resource
    SessionObject sessionObject;

    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IEmployeeService employeeService;

    @RequestMapping(value = "admincontroller/grouppreschooler/group", method = RequestMethod.GET)
    public String showPageListGroup(Model model){
        if (sessionObject.getEmployee()!=null){
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroupNoOneGroup(
                    preschoolGroupService.getListPreschoolerGroup()).stream().sorted(Comparator.comparing
                    (PreschoolGroup::getNameGroup)).collect(Collectors.toList()));
            return "admincontroller/grouppreschooler/group";
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/groupE/{idGroup}", method = RequestMethod.GET)
    public String redirectPageChangeGroupPreschooler(@PathVariable String idGroup, Model model){
        if (sessionObject.getEmployee()!=null) {
            sessionObject.setSendData(Integer.parseInt(idGroup));
            return "redirect:../../../admincontroller/grouppreschooler/groupE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/groupE", method = RequestMethod.GET)
    public String openPageChangeGroupPreschooler(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            PreschoolGroup preschoolGroup = preschoolGroupService.getPreschoolerGroupByName
                                        (preschoolGroupService.getNameGroupPreschoolById(sessionObject.getSendData()));
            model.addAttribute("listPreschoolGroup", preschoolGroup);
            model.addAttribute("nameGroup", preschoolGroup.getNameGroup());
            model.addAttribute("preschholerGroup", preschoolGroup);

            List<Employee> teacherWithoutTutoring = new ArrayList<>();
            for (Employee employee: employeeService.getListEmployeeTeacher()){
                Boolean tutoring=false;
                for(PreschoolGroup tempPG: preschoolGroupService.getListPreschoolerGroup()){
                    if (tempPG.getEmployee()!=null && employee.getId()==tempPG.getEmployee().getId()){
                        tutoring=true;
                    }
                }
                if (!tutoring){
                    teacherWithoutTutoring.add(employee);
                }
            }
            model.addAttribute("allTeacher", teacherWithoutTutoring);

            //model.addAttribute("allTeacher", employeeService.getListEmployeeTeacher());
            if (preschoolGroup.getEmployee()!=null) {
                model.addAttribute("nameSurname", preschoolGroup.getEmployee().getSurname() + " "
                        + preschoolGroup.getEmployee().getName());
            }
            return "admincontroller/grouppreschooler/groupE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/groupE", method = RequestMethod.POST, params = "return=NIE ZAPISUJ DANYCH")
    public String noSaveChangeGroupPreschooler(){
        if (sessionObject.getEmployee()!=null) {
            return "redirect:../../admincontroller/grouppreschooler/group";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/groupE", method = RequestMethod.POST, params = "save=ZAPISZ DANE")
    public String saveChangeGroupPreschooler(@RequestParam("nameGroup") String nameGroup,
                                             @RequestParam("choose2") int idEmployee, Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            PreschoolGroup preschoolGroup = preschoolGroupService.getPreschoolerGroupByName
                    (preschoolGroupService.getNameGroupPreschoolById(sessionObject.getSendData()));

            if (preschoolGroupService.isInDBNamePreschoolGroupNoGroupChange(preschoolGroup.getId(),nameGroup)){
                model.addAttribute("message", "Grupa o podanej nazwie już istnieje!");

                model.addAttribute("listPreschoolGroup", preschoolGroup);
                model.addAttribute("nameGroup", preschoolGroup.getNameGroup());
                model.addAttribute("preschholerGroup", preschoolGroup);

                List<Employee> teacherWithoutTutoring = new ArrayList<>();
                for (Employee employee: employeeService.getListEmployeeTeacher()){
                    Boolean tutoring=false;
                    for(PreschoolGroup tempPG: preschoolGroupService.getListPreschoolerGroup()){
                        if (tempPG.getEmployee()!=null && employee.getId()==tempPG.getEmployee().getId()){
                            tutoring=true;
                        }
                    }
                    if (!tutoring){
                        teacherWithoutTutoring.add(employee);
                    }
                }

                model.addAttribute("allTeacher", teacherWithoutTutoring);
                //model.addAttribute("allTeacher", employeeService.getListEmployeeTeacher());
                if (preschoolGroup.getEmployee()!=null) {
                    model.addAttribute("nameSurname", preschoolGroup.getEmployee().getSurname() + " "
                            + preschoolGroup.getEmployee().getName());
                }

                return "admincontroller/grouppreschooler/groupE";
            } else {

                preschoolGroup.setNameGroup(nameGroup.toUpperCase());
                preschoolGroupService.saveChangePreschoolGroup(preschoolGroup, employeeService.getEmployeeByIdEmployee(idEmployee));
                return "redirect:../../admincontroller/grouppreschooler/group";
            }
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/groupD/{idGroup}", method = RequestMethod.GET)
    public String redirectPageDeleteGroupPreschooler(@PathVariable String idGroup, Model model){
        if (sessionObject.getEmployee()!=null) {
            sessionObject.setSendData(Integer.parseInt(idGroup));
            return "redirect:../../../admincontroller/grouppreschooler/groupD";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/groupD", method = RequestMethod.GET)
    public String openPageDeleteGroupPreschooler(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());
            PreschoolGroup preschoolGroup = preschoolGroupService.getPreschoolerGroupByName
                    (preschoolGroupService.getNameGroupPreschoolById(sessionObject.getSendData()));
            model.addAttribute("listPreschoolGroup", preschoolGroup);
            model.addAttribute("nameGroup", preschoolGroup.getNameGroup());
            model.addAttribute("preschholerGroup", preschoolGroup);
            model.addAttribute("allTeacher", employeeService.getListEmployeeTeacher());
            model.addAttribute("message", "Czy na pewno chcesz usunąć dane?");
            if (preschoolGroup.getEmployee()!=null) {
                model.addAttribute("nameSurname", preschoolGroup.getEmployee().getSurname() + " "
                        + preschoolGroup.getEmployee().getName());
            }
            return "admincontroller/grouppreschooler/groupD";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/groupD", method = RequestMethod.POST, params = "noDelete=NIE")
    public String noDeleteGroupPreschooler(){
        if (sessionObject.getEmployee()!=null) {
            return "redirect:../../admincontroller/grouppreschooler/group";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/grouppreschooler/groupD", method = RequestMethod.POST, params = "delete=TAK")
    public String deleteGroupPreschooler(Model model){
        if (sessionObject.getEmployee()!=null) {

            model.addAttribute("userRoleAfterLogged", sessionObject.getEmployee().getRole());

            if (preschoolerService.getPreschoolerList(sessionObject.getSendData()).size()==0) {
                preschoolGroupService.deletePreschoolGroup(preschoolGroupService.getPreschoolerGroupByName
                                (preschoolGroupService.getNameGroupPreschoolById(sessionObject.getSendData())));
                return "redirect:../../admincontroller/grouppreschooler/group";
            } else{
                PreschoolGroup preschoolGroup = preschoolGroupService.getPreschoolerGroupByName
                        (preschoolGroupService.getNameGroupPreschoolById(sessionObject.getSendData()));
                model.addAttribute("listPreschoolGroup", preschoolGroup);
                model.addAttribute("nameGroup", preschoolGroup.getNameGroup());
                model.addAttribute("preschholerGroup", preschoolGroup);
                model.addAttribute("allTeacher", employeeService.getListEmployeeTeacher());
                model.addAttribute("message", "Usunięcie grupy nie jest możliwe ponieważ " +
                        "są przypsani do niej przedszkolakowie! Usuń przedszkolaków z grupy, a uzyskasz możliwość usunięcia grupy!");
                if (preschoolGroup.getEmployee()!=null) {
                    model.addAttribute("nameSurname", preschoolGroup.getEmployee().getSurname() + " "
                            + preschoolGroup.getEmployee().getName());
                }
                return "admincontroller/grouppreschooler/groupD";
            }
        }else {
            return "redirect:../../login";
        }
    }
}
