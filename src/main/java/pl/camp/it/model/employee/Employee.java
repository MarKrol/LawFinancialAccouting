package pl.camp.it.model.employee;

import pl.camp.it.model.employee.EmployeeRole;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "temployee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String role;
    private boolean quantity;
    private LocalDate localDateAddToDB;

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    private LocalDate localDateDeleteFromDB;

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
}
