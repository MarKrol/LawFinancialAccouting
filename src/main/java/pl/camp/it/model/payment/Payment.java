package pl.camp.it.model.payment;

import org.springframework.format.annotation.DateTimeFormat;
import pl.camp.it.model.company.Company;
import pl.camp.it.model.preschooler.Preschooler;

import javax.persistence.*;
import java.time.LocalDate;

@Entity (name = "tpayment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat
    private LocalDate localDate;
    private double payment;
    private String name;
    private boolean quantity;
    private String nameMonth;

    @ManyToOne
    @JoinColumn(name = "preschoolerId")
    Preschooler preschooler;

    @ManyToOne()
    @JoinColumn(name = "companyId")
    Company company;

    public String getNameMonth() {
        return nameMonth;
    }

    public void setNameMonth(String nameMonth) {
        this.nameMonth = nameMonth;
    }

    public boolean isQuantity() {
        return quantity;
    }

    public void setQuantity(boolean quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Preschooler getPreschooler() {
        return preschooler;
    }

    public void setPreschooler(Preschooler preschooler) {
        this.preschooler = preschooler;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
