package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.month.Month;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.model.stay.PreschoolerStayMonth;
import pl.camp.it.services.*;
import pl.camp.it.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class adminSettlementController {

    private int choose = -1;
    private int idPreschoolerChoose=-1;
    private String monthChoose=null;

    @Resource
    SessionObject sessionObject;

    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    IPreschoolerFullBoardInMonthService preschoolerFullBoardInMonthService;
    @Autowired
    IPreschoolerSingleBoardInMonthService preschoolerSingleBoardInMonthService;
    @Autowired
    IPreschoolerStayMonthService preschoolerStayMonthService;
    @Autowired
    IPreschoolerActivityInMonthService preschoolerActivityInMonthService;

    @RequestMapping(value = "/admincontroller/settlement/selectgroup", method = RequestMethod.GET)
    public String settlementSelectGroup(Model model) {
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listPreschoolGroup", preschoolGroupService.getListPreschoolerGroup());
            return "/admincontroller/settlement/selectgroup";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/selectgroup", method = RequestMethod.POST)
    public String settlementSelectGroupChoose(@RequestParam int choose, Model model) {
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            this.choose = choose;
            return "redirect:../../admincontroller/settlement/settlement";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlement", method = RequestMethod.GET)
    public String settlementChoose(Model model) {
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("nameGroup", preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));
            return "/admincontroller/settlement/settlement";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementshow", method = RequestMethod.GET)
    public String settlementShow(Model model) {
        if (sessionObject.getEmployee() != null) {

            Preschooler preschooler = preschoolerService.getPreschoolerById(this.idPreschoolerChoose);

            model.addAttribute("nameSurname",preschooler.getName()+" "+preschooler.getSurname());
            model.addAttribute("month",this.monthChoose);
            model.addAttribute("nameGroup", preschoolGroupService.getNameGroupPreschoolById(this.choose));
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            model.addAttribute("listMonth", Month.getMonth());
            model.addAttribute("preschoolerList", preschoolerService.getPreschoolerList(this.choose));

            model.addAttribute("fullMealMonth", preschoolerFullBoardInMonthService.
                                                getPreschoolerFullBoardInMonth(preschooler.getId(),this.monthChoose));

            List<PreschoolerSingleBoardInMonth> listSingleMealPreschoolerMonth =
                    preschoolerSingleBoardInMonthService.listPreschoolerSingleMealMonthInDB(this.idPreschoolerChoose, this.monthChoose);
            model.addAttribute("singleMealMonth", listSingleMealPreschoolerMonth);
            model.addAttribute("singleMealMonthToPay",
                    preschoolerSingleBoardInMonthService.singleMealMonthToPay(listSingleMealPreschoolerMonth));

            List<PreschoolerStayMonth> preschoolerStayMonthList =
                    preschoolerStayMonthService.listPreschoolerStayMonth(this.idPreschoolerChoose, this.monthChoose);
            model.addAttribute("stayMonth", preschoolerStayMonthList);
            model.addAttribute("stayMonthToPay",preschoolerStayMonthService.stayMonthToPay(preschoolerStayMonthList));

            List<PreschoolerActivityInMonth> preschoolerActivityInMonthList =
                    preschoolerActivityInMonthService.listPreschoolerActivityInMonth(this.idPreschoolerChoose, this.monthChoose);
            model.addAttribute("activityMonth",preschoolerActivityInMonthList);
            model.addAttribute("activityToPay",
                                    preschoolerActivityInMonthService.activityMonthToPay(preschoolerActivityInMonthList));


            return "/admincontroller/settlement/settlementshow";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlement", method = RequestMethod.POST)
    public String settlementShowPOST(@RequestParam("choose") int idPreschooler, @RequestParam("choose1") String month,
                                     Model model) {
        if (sessionObject.getEmployee() != null) {
                this.idPreschoolerChoose=idPreschooler;
                this.monthChoose=month;
            return "redirect:../../admincontroller/settlement/settlementshow";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementshow", method = RequestMethod.POST)
    public String settlementShowPOSTShow(@RequestParam("choose") int idPreschooler, @RequestParam("choose1") String month,
                                     Model model) {
        if (sessionObject.getEmployee() != null) {
            this.idPreschoolerChoose=idPreschooler;
            this.monthChoose=month;
            return "redirect:../../admincontroller/settlement/settlementshow";
        } else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementshow/{what}/{id}", method = RequestMethod.GET)
    public String showEditSettlementFullMeal(@PathVariable String what, @PathVariable int id, Model model) {
        if (sessionObject.getEmployee() != null) {
            if (what.equals("M")) {
                return "redirect:../../../../admincontroller/settlement/settlementE";
            } else {
                if (what.equals("S")){
                    sessionObject.setSendData(id);
                    return "redirect:../../../../admincontroller/settlement/settlementSingleE";
                }else{
                    if (what.equals("P")){
                        sessionObject.setSendData(id);
                        return "redirect:../../../../admincontroller/settlement/settlementStayE";
                    }else {
                        if(what.equals("A")){
                            sessionObject.setSendData(id);
                            return "redirect:../../../../admincontroller/settlement/settlementActivityE";
                        } else {
                            return "redirect:../../../../admincontroller/settlement/settlementshow";
                        }
                    }
                }
            }
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementE",method = RequestMethod.GET)
    public String editSettlementFullMeal(Model model) {
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());

            Preschooler preschooler = preschoolerService.getPreschoolerById(this.idPreschoolerChoose);

            model.addAttribute("fullMealMonth",
                    preschoolerFullBoardInMonthService.getPreschoolerFullBoardInMonth(this.idPreschoolerChoose, this.monthChoose));

            model.addAttribute("messageOK","Edytujesz dane przedszkolaka: "+
                    preschooler.getName()+" "+preschooler.getSurname()+" za miesiąc "+this.monthChoose.toUpperCase());

            return "/admincontroller/settlement/settlementE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementE", method = RequestMethod.GET,
            params = "nosave=WYJŚCIE BEZ ZAPISU ZMIAN")
    public String returnSettlementShowQ(Model model){
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "redirect:../../admincontroller/settlement/settlementshow";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementE", method = RequestMethod.POST,
                                                                            params = "nosave=WYJŚCIE BEZ ZAPISU ZMIAN")
    public String returnSettlementShowNoSave(){
        if (sessionObject.getEmployee() != null) {
            return "redirect:../../admincontroller/settlement/settlementshow";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementE", method = RequestMethod.POST,
                                                                                        params = "save=ZAPISZ ZMIANY")
    public String returnSettlementShowSave(@ModelAttribute PreschoolerFullBoardInMonth preschoolerFullBoardInMonthEdit){
        if (sessionObject.getEmployee() != null) {
            preschoolerFullBoardInMonthService.saveEditSettlementPreschoolerFullBoardInMonth
                    (preschoolerFullBoardInMonthService.getPreschoolerFullBoardInMonth(this.idPreschoolerChoose, this.monthChoose),
                            preschoolerFullBoardInMonthEdit);
            return "redirect:../../admincontroller/settlement/settlementshow";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementSingleE",method = RequestMethod.GET)
    public String editSettlementSingleMeal(Model model) {
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());

            Preschooler preschooler = preschoolerService.getPreschoolerById(this.idPreschoolerChoose);

            model.addAttribute("singleMealMonth",
                    preschoolerSingleBoardInMonthService.getPreschoolerSingleBoardMonthById(sessionObject.getSendData()));

            model.addAttribute("messageOK","Edytujesz dane przedszkolaka: "+
                    preschooler.getName()+" "+preschooler.getSurname()+" za miesiąc "+this.monthChoose.toUpperCase());

            return "/admincontroller/settlement/settlementSingleE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementSingleE", method = RequestMethod.GET,
            params = "nosave=WYJŚCIE BEZ ZAPISU ZMIAN")
    public String returnSettlementSingleShowQ(Model model){
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "redirect:../../admincontroller/settlement/settlementshow";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementSingleE", method = RequestMethod.POST,
            params = "nosave=WYJŚCIE BEZ ZAPISU ZMIAN")
    public String returnSettlementSingleShowNoSave(){
        if (sessionObject.getEmployee() != null) {
            return "redirect:../../admincontroller/settlement/settlementshow";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementSingleE", method = RequestMethod.POST,
            params = "save=ZAPISZ ZMIANY")
    public String returnSettlementSingleShowSave(@ModelAttribute PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonthEdit){
        if (sessionObject.getEmployee() != null) {
            preschoolerSingleBoardInMonthService.saveEditSettlementSingleMealMonth(
                    (preschoolerSingleBoardInMonthService.getPreschoolerSingleBoardMonthById(sessionObject.getSendData()))
                    ,preschoolerSingleBoardInMonthEdit);
            return "redirect:../../admincontroller/settlement/settlementshow";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementStayE",method = RequestMethod.GET)
    public String editSettlementStay(Model model) {
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());

            Preschooler preschooler = preschoolerService.getPreschoolerById(this.idPreschoolerChoose);

            model.addAttribute("stayMonth",
                    preschoolerStayMonthService.getPreschoolerStayMonthById(sessionObject.getSendData()));

            model.addAttribute("messageOK","Edytujesz dane przedszkolaka: "+
                    preschooler.getName()+" "+preschooler.getSurname()+" za miesiąc "+this.monthChoose.toUpperCase());

            return "/admincontroller/settlement/settlementStayE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementStayE", method = RequestMethod.GET,
            params = "nosave=WYJŚCIE BEZ ZAPISU ZMIAN")
    public String returnSettlementStayShowQ(Model model){
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "redirect:../../admincontroller/settlement/settlementshow";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementStayE", method = RequestMethod.POST,
            params = "nosave=WYJŚCIE BEZ ZAPISU ZMIAN")
    public String returnSettlementStayShowNoSave(){
        if (sessionObject.getEmployee() != null) {
            return "redirect:../../admincontroller/settlement/settlementshow";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementStayE", method = RequestMethod.POST,
            params = "save=ZAPISZ ZMIANY")
    public String returnSettlementStayShowSave(@ModelAttribute PreschoolerStayMonth preschoolerStayMonthEdit){
        if (sessionObject.getEmployee() != null) {
            preschoolerStayMonthService.saveEditSettlementStayMonth
                    (preschoolerStayMonthService.getPreschoolerStayMonthById(sessionObject.getSendData()),preschoolerStayMonthEdit);
            return "redirect:../../admincontroller/settlement/settlementshow";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementActivityE",method = RequestMethod.GET)
    public String editSettlementActivity(Model model) {
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());

            Preschooler preschooler = preschoolerService.getPreschoolerById(this.idPreschoolerChoose);

            model.addAttribute("activityMonth",
                    preschoolerActivityInMonthService.getPreschoolerActivityMonthById(sessionObject.getSendData()));

            model.addAttribute("messageOK","Edytujesz dane przedszkolaka: "+
                    preschooler.getName()+" "+preschooler.getSurname()+" za miesiąc "+this.monthChoose.toUpperCase());

            return "/admincontroller/settlement/settlementActivityE";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementActivityE", method = RequestMethod.GET,
            params = "nosave=WYJŚCIE BEZ ZAPISU ZMIAN")
    public String returnSettlementActivityShowQ(Model model){
        if (sessionObject.getEmployee() != null) {
            model.addAttribute("employeeLogged", sessionObject.getEmployee().getName() + " " +
                    sessionObject.getEmployee().getSurname());
            return "redirect:../../admincontroller/settlement/settlementshow";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementActivityE", method = RequestMethod.POST,
            params = "nosave=WYJŚCIE BEZ ZAPISU ZMIAN")
    public String returnSettlementActivityShowNoSave(){
        if (sessionObject.getEmployee() != null) {
            return "redirect:../../admincontroller/settlement/settlementshow";
        }else {
            return "redirect:../../login";
        }
    }

    @RequestMapping(value = "/admincontroller/settlement/settlementActivityE", method = RequestMethod.POST,
            params = "save=ZAPISZ ZMIANY")
    public String returnSettlementActivityShowSave(@ModelAttribute PreschoolerActivityInMonth preschoolerActivityInMonthEdit){
        if (sessionObject.getEmployee() != null) {
            preschoolerActivityInMonthService.saveEditSettlementActivityMonth
                    (preschoolerActivityInMonthService.getPreschoolerActivityMonthById(sessionObject.getSendData()),
                            preschoolerActivityInMonthEdit);
            return "redirect:../../admincontroller/settlement/settlementshow";
        }else {
            return "redirect:../../login";
        }
    }
}