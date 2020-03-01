package pl.camp.it.model.preschoolGroup;

import pl.camp.it.model.employee.Employee;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name="tpreschoolerGroup")
public class PreschoolGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nameGroup;
    private boolean quantity;
    private LocalDate localDateAddToDB;
    private LocalDate localDateDeleteFromDB;

    @OneToOne
    @JoinColumn(name = "employeeId")
    Employee employee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
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
}
