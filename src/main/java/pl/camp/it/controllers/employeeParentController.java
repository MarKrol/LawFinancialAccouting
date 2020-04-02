package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.parent.Parent;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IParentService;
import pl.camp.it.services.IPreschoolGroupService;
import pl.camp.it.services.IPreschoolerService;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class employeeParentController {

    private int idGroup = -1;

    @Resource
    SessionObject sessionObject;

    @Autowired
    IParentService parentService;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    IPreschoolGroupService preschoolGroupService;

    @RequestMapping(value = "admincontroller/preschooler/preschoolerAP", method = RequestMethod.POST,
            params = "return=NIE ZAPISUJ DANYCH")
    private String noSaveAddParent(Model model) {
        if (sessionObject.getEmployee() != null) {
            return "redirect:../../admincontroller/preschooler/preschoolerVED";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/preschoolerAP", method = RequestMethod.POST,
            params = "save=ZAPISZ DANE")
    private String saveAddParent(@ModelAttribute Parent parent, Model model) {
        if (sessionObject.getEmployee() != null) {
            parentService.addParentToDB(parent, preschoolerService.getPreschoolerById(sessionObject.getSendData()));
            return "redirect:../../admincontroller/preschooler/preschoolerVED";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/parent", method = RequestMethod.GET)
    public String openListParentsPage(Model model){
        if (sessionObject.getEmployee()!=null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());

            if (this.idGroup != -1) {
                List<Preschooler> preschoolers = preschoolerService.getPreschoolerList(this.idGroup);
                model.addAttribute("nameGroup", preschoolGroupService.getNameGroupPreschoolById(this.idGroup));
                model.addAttribute("allPreschoolers", preschoolers);
                model.addAttribute("allParents", parentService.getListParent(preschoolers));
                this.idGroup = -1;
            }
            return "admincontroller/preschooler/parent";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/parent", method = RequestMethod.POST)
    public String showListGroupParentsPage(@RequestParam("choose") int idGroup, Model model){
        if (sessionObject.getEmployee()!=null) {
            this.idGroup = idGroup;
            return "redirect:../../admincontroller/preschooler/parent";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/parentE/{idParent}", method = RequestMethod.GET)
    public String redirectPageChangePatentPreschooler(@PathVariable String idParent, Model model){
        if (sessionObject.getEmployee()!=null) {
            sessionObject.setSendData(Integer.parseInt(idParent));
            return "redirect:../../../admincontroller/preschooler/parentE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/parentE", method = RequestMethod.GET)
    public String openPageChangeParentPreschooler(Model model){
        if (sessionObject.getEmployee()!=null) {
            Parent parent = parentService.getParentById(sessionObject.getSendData());

            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("preschooler", parent.getPreschooler().getSurname()+" "+
                    parent.getPreschooler().getName());
            model.addAttribute("parent", parent);
            model.addAttribute("group",parent.getPreschooler().getPreschoolGroup().getNameGroup());
            return "admincontroller/preschooler/parentE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/parentE", method = RequestMethod.POST, params = "return=NIE ZAPISUJ DANYCH")
    public String returnToListPageParent(){
        if (sessionObject.getEmployee()!=null) {
            this.idGroup=parentService.getParentById(sessionObject.getSendData()).getPreschooler().getPreschoolGroup().getId();
            return "redirect:../../admincontroller/preschooler/parent";
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/parentE", method = RequestMethod.POST, params = "save=ZAPISZ DANE")
    public String saveAndReturnToListPageParent(@ModelAttribute Parent parentEdit){
        if (sessionObject.getEmployee()!=null) {
            this.idGroup=parentService.getParentById(sessionObject.getSendData()).getPreschooler().getPreschoolGroup().getId();
            parentService.saveEditParentInDB(parentService.getParentById(sessionObject.getSendData()),parentEdit);
            return "redirect:../../admincontroller/preschooler/parent";
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/parentD/{idParent}", method = RequestMethod.GET)
    public String redirectPageDeletePatentPreschooler(@PathVariable String idParent, Model model){
        if (sessionObject.getEmployee()!=null) {
            sessionObject.setSendData(Integer.parseInt(idParent));
            return "redirect:../../../admincontroller/preschooler/parentD";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/parentD", method = RequestMethod.GET)
    public String openPageDeleteParentPreschooler(Model model){
        if (sessionObject.getEmployee()!=null) {
            Parent parent = parentService.getParentById(sessionObject.getSendData());

            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("preschooler", parent.getPreschooler().getSurname()+" "+
                    parent.getPreschooler().getName());
            model.addAttribute("parent", parent);
            model.addAttribute("group",parent.getPreschooler().getPreschoolGroup().getNameGroup());
            model.addAttribute("message","Czy na pewno chcesz usunąć dane?");
            return "admincontroller/preschooler/parentD";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/parentD", method = RequestMethod.POST, params = "noDelete=NIE")
    public String returnToListPageParentNoDelete(){
        if (sessionObject.getEmployee()!=null) {
            this.idGroup=parentService.getParentById(sessionObject.getSendData()).getPreschooler().getPreschoolGroup().getId();
            return "redirect:../../admincontroller/preschooler/parent";
        } else{
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "admincontroller/preschooler/parentD", method = RequestMethod.POST, params = "delete=TAK")
    public String deleteAndReturnToListPageParent(@ModelAttribute Parent parentEdit){
        if (sessionObject.getEmployee()!=null) {
            this.idGroup=parentService.getParentById(sessionObject.getSendData()).getPreschooler().getPreschoolGroup().getId();
            parentService.deleteParent(parentService.getParentById(sessionObject.getSendData()));
            return "redirect:../../admincontroller/preschooler/parent";
        } else{
            return "redirect:../../login";
        }
    }


}

