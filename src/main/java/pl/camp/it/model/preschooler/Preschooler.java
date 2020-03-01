package pl.camp.it.model.preschooler;

import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "tpreschooler")
public class Preschooler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private boolean quantity;
    private LocalDate localDateAddToDB;
    private LocalDate localDateDeleteFromDB;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    Employee employee;

    @ManyToOne
    @JoinColumn(name = "preschoolerGroupId")
    PreschoolGroup preschoolGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isQuantity() {
        return quantity;
    }

    public void setQuantity(boolean quantity) {
        this.quantity = quantity;
    }

    public LocalDate getLocalDateAddToDB() {
        return localDateAddToDB;
    }

    public void setLocalDateAddToDB(LocalDate localDateAddToDB) {
        this.localDateAddToDB = localDateAddToDB;
    }

    public LocalDate getLocalDateDeleteFromDB() {
        return localDateDeleteFromDB;
    }

    public void setLocalDateDeleteFromDB(LocalDate localDateDeleteFromDB) {
        this.localDateDeleteFromDB = localDateDeleteFromDB;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public PreschoolGroup getPreschoolGroup() {
        return preschoolGroup;
    }

    public void setPreschoolGroup(PreschoolGroup preschoolGroup) {
        this.preschoolGroup = preschoolGroup;
    }
}
