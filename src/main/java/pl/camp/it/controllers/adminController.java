package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.camp.it.dao.IPreschoolerSingleBoardInMonthDAO;
import pl.camp.it.model.activities.Activities;
import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.employee.EmployeeRole;
import pl.camp.it.model.meals.FullBoardPrice;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.meals.SingleBoardPrice;
import pl.camp.it.model.month.Month;
import pl.camp.it.model.parent.Parent;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.*;

import java.time.LocalDate;

@Controller
public class adminController {

    @Autowired
    IEmployeeService employeeService;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IParentService parentService;
    @Autowired
    IActivityService activityService;
    @Autowired
    IPreschoolerActivityInMonthService preschoolerActivityInMonthService;
    @Autowired
    IFullBoardPriceService fullBoardPriceService;
    @Autowired
    ISingleBoardPriceService singleBoardPriceService;
    @Autowired
    IPreschoolerFullBoardInMonthService preschoolerFullBoardInMonthService;
    @Autowired
    IPreschoolerSingleBoardInMonthService preschoolerSingleBoardInMonthService;

    @GetMapping("/addE")
    public String employee(){
        Employee employee = new Employee();

        employee.setName("Marcin");
        employee.setSurname("Królź");
        employee.setQuantity(true);
        employee.setRole(EmployeeRole.ADMIN.toString());
        employee.setLocalDateAddToDB(LocalDate.now());
        employeeService.persistEmployee(employee);
        employeeService.persistEmployeeLogin(employee);

        Employee employee1 = new Employee();
        employee1.setName("Marek");
        employee1.setSurname("Grabowski");
        employee1.setQuantity(true);
        employee1.setRole(EmployeeRole.TEACHER.toString());
        employee1.setLocalDateAddToDB(LocalDate.now());
        employeeService.persistEmployee(employee1);
        employeeService.persistEmployeeLogin(employee1);

        Preschooler preschooler = new Preschooler();
        preschooler.setName("Monika");
        preschooler.setSurname("Mół");
        preschooler.setQuantity(true);
        preschooler.setLocalDateAddToDB(LocalDate.now());
        preschooler.setEmployee(employee);

        PreschoolGroup preschoolGroup = new PreschoolGroup();
        preschoolGroup.setNameGroup("Pszczółki");
        preschoolGroup.setQuantity(true);
        preschoolGroup.setLocalDateAddToDB(LocalDate.now());
        preschoolGroup.setEmployee(employee);

        preschooler.setPreschoolGroup(preschoolGroup);
        preschoolGroupService.persistPreschoolGroup(preschoolGroup);

        preschoolerService.persistPreschooler(preschooler);


        Preschooler preschooler1 = new Preschooler();
        preschooler1.setName("Mirek");
        preschooler1.setSurname("Nążć");
        preschooler1.setQuantity(true);
        preschooler1.setLocalDateAddToDB(LocalDate.now());
        preschooler1.setEmployee(employee);

        PreschoolGroup preschoolGroup1 = new PreschoolGroup();
        preschoolGroup1.setNameGroup("Pszczółki1");
        preschoolGroup1.setQuantity(true);
        preschoolGroup1.setLocalDateAddToDB(LocalDate.now());
        preschoolGroup1.setEmployee(employee);

        preschooler1.setPreschoolGroup(preschoolGroup1);
        preschoolGroupService.persistPreschoolGroup(preschoolGroup1);

        preschoolerService.persistPreschooler(preschooler1);

        Preschooler preschooler2 = new Preschooler();
        preschooler2.setName("Justyna");
        preschooler2.setSurname("Jążźśćłó");
        preschooler2.setQuantity(true);
        preschooler2.setLocalDateAddToDB(LocalDate.now());
        preschooler2.setEmployee(employee1);

        PreschoolGroup preschoolGroup2 = new PreschoolGroup();
        preschoolGroup2.setNameGroup("Pszczółki2");
        preschoolGroup2.setQuantity(true);
        preschoolGroup2.setLocalDateAddToDB(LocalDate.now());
        preschoolGroup2.setEmployee(employee1);

        preschooler2.setPreschoolGroup(preschoolGroup2);
        preschoolGroupService.persistPreschoolGroup(preschoolGroup2);

        preschoolerService.persistPreschooler(preschooler2);


        Preschooler preschooler3 = new Preschooler();
        preschooler3.setName("Norbert");
        preschooler3.setSurname("Z");
        preschooler3.setQuantity(true);
        preschooler3.setLocalDateAddToDB(LocalDate.now());
        preschooler3.setEmployee(employee);

        PreschoolGroup preschoolGroup3 = new PreschoolGroup();
        preschoolGroup3.setNameGroup("Pszczółki3");
        preschoolGroup3.setQuantity(true);
        preschoolGroup3.setLocalDateAddToDB(LocalDate.now());
        preschoolGroup3.setEmployee(employee);

        preschooler3.setPreschoolGroup(preschoolGroup3);
        preschoolGroupService.persistPreschoolGroup(preschoolGroup3);

        preschoolerService.persistPreschooler(preschooler3);

        Preschooler preschooler4 = new Preschooler();
        preschooler4.setName("Mateusz");
        preschooler4.setSurname("K");
        preschooler4.setQuantity(true);
        preschooler4.setLocalDateAddToDB(LocalDate.now());
        preschooler4.setEmployee(employee1);

        preschoolGroup3.setNameGroup("Pszczółki3");
        preschoolGroup3.setQuantity(true);
        preschoolGroup3.setLocalDateAddToDB(LocalDate.now());
        preschoolGroup3.setEmployee(employee1);

        preschooler4.setPreschoolGroup(preschoolGroup3);
        preschoolGroupService.persistPreschoolGroup(preschoolGroup3);

        preschoolerService.persistPreschooler(preschooler4);

        Parent parent=new Parent();
        parent.setPreschooler(preschooler2);
        parent.setName("Euzebiusz");
        parent.setSurname("W");
        parent.setCity("Kraków");
        parent.setEmail("@@@@@@");
        parent.setStreet("sdasdds");
        parent.setZip("31-412");
        parentService.persistParent(parent);
        parentService.persistParentLogin(preschooler2);

        Parent parent1=new Parent();
        parent1.setPreschooler(preschooler2);
        parent1.setName("Monika");
        parent1.setSurname("W");
        parent1.setCity("Kraków");
        parent1.setEmail("@@@@@@");
        parent1.setStreet("sdasdds");
        parent1.setZip("31-412");
        parentService.persistParent(parent1);
        parentService.persistParentLogin(preschooler2);

        Activities activities = new Activities();
        activities.setName("Tańce");
        activities.setPriceNet(30.33);
        activities.setQuantity(true);
        activities.setVAT(22);
        activityService.persistActivity(activities);

        Activities activities1 = new Activities();
        activities1.setName("Język angielski");
        activities1.setPriceNet(303.33);
        activities1.setQuantity(true);
        activities1.setVAT(8);
        activityService.persistActivity(activities1);


        PreschoolerActivityInMonth preschoolerActivityInMonth = new PreschoolerActivityInMonth();
        preschoolerActivityInMonth.setNameAcivity(activities.getName());
        preschoolerActivityInMonth.setMonth(Month.MAJ.toString());
        preschoolerActivityInMonth.setPreschooler(preschooler3);
        preschoolerActivityInMonth.setPriceNet(activities.getPriceNet());
        preschoolerActivityInMonth.setVAT(activities.getVAT());
        preschoolerActivityInMonth.setQuantity(true);
        preschoolerActivityInMonthService.persistPreschoolerActivityInMonth(preschoolerActivityInMonth);

        PreschoolerActivityInMonth preschoolerActivityInMonth1 = new PreschoolerActivityInMonth();
        preschoolerActivityInMonth1.setNameAcivity(activities1.getName());
        preschoolerActivityInMonth1.setMonth(Month.LISTOPAD.toString());
        preschoolerActivityInMonth1.setPreschooler(preschooler);
        preschoolerActivityInMonth1.setPriceNet(activities1.getPriceNet());
        preschoolerActivityInMonth1.setVAT(activities1.getVAT());
        preschoolerActivityInMonth1.setQuantity(true);
        preschoolerActivityInMonthService.persistPreschoolerActivityInMonth(preschoolerActivityInMonth1);


        FullBoardPrice fullBoardPrice = new FullBoardPrice();
        fullBoardPrice.setName("Dieta1");
        fullBoardPrice.setLocalDateAddToDB(LocalDate.now());
        fullBoardPrice.setFirstBreakfastPriceNet(2.50);
        fullBoardPrice.setSecondBreakfastPriceNet(1.00);
        fullBoardPrice.setDinnerPriceNet(6.50);
        fullBoardPrice.setTeaPriceNet(3.00);
        fullBoardPrice.setVAT(0);
        fullBoardPrice.setQuantity(true);
        fullBoardPriceService.persistFullBoardPrice(fullBoardPrice);

        SingleBoardPrice singleBoardPrice = new SingleBoardPrice();
        singleBoardPrice.setName("Drożdżówki");
        singleBoardPrice.setLocalDateAddToDB(LocalDate.now());
        singleBoardPrice.setPrice(1.00);
        singleBoardPrice.setQuantity(true);
        singleBoardPrice.setVAT(0);
        singleBoardPriceService.persistSingleBoardPrice(singleBoardPrice);

        PreschoolerFullBoardInMonth preschoolerFullBoardInMonth = new PreschoolerFullBoardInMonth();
        preschoolerFullBoardInMonth.setMonth("Maj");
        preschoolerFullBoardInMonth.setPreschooler(preschooler2);
        preschoolerFullBoardInMonth.setNameDiet(fullBoardPrice.getName());
        preschoolerFullBoardInMonth.setNumberDinner(3);
        preschoolerFullBoardInMonth.setNumberFirstBreakfast(2);
        preschoolerFullBoardInMonth.setNumberSecondBreakfast(5);
        preschoolerFullBoardInMonth.setNumberTea(3);
        preschoolerFullBoardInMonth.setPriceNetTea(fullBoardPrice.getTeaPriceNet());
        preschoolerFullBoardInMonth.setVAT(fullBoardPrice.getVAT());
        preschoolerFullBoardInMonthService.pesistPreschoolerFullBoardInMonth(preschoolerFullBoardInMonth);

        PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth =new PreschoolerSingleBoardInMonth();
        preschoolerSingleBoardInMonth.setMonth("Marzec");
        preschoolerSingleBoardInMonth.setName(singleBoardPrice.getName());
        preschoolerSingleBoardInMonth.setNumber(5);
        preschoolerSingleBoardInMonth.setPrice(singleBoardPrice.getPrice());
        preschoolerSingleBoardInMonth.setVAT(singleBoardPrice.getVAT());
        preschoolerSingleBoardInMonth.setQuantity(true);
        preschoolerSingleBoardInMonth.setPreschooler(preschooler3);
        preschoolerSingleBoardInMonthService.persistPreschoolerSingleBoardInMonth(preschoolerSingleBoardInMonth);

        return "afterloggingin";
    }
}
