package pl.camp.it.model.stay;

import pl.camp.it.model.preschooler.Preschooler;

import javax.persistence.*;

@Entity(name = "tpreschoolerStayMonth")
public class PreschoolerStayMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String month;
    private String name;
    private double priceNet;
    private  int number;
    private boolean quantity;
    private int VAT;

    @ManyToOne
    @JoinColumn(name = "preschoolerId")
    Preschooler preschooler;

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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceNet() {
        return priceNet;
    }

    public void setPriceNet(double priceNet) {
        this.priceNet = priceNet;
    }

    public int getVAT() {
        return VAT;
    }

    public void setVAT(int VAT) {
        this.VAT = VAT;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Preschooler getPreschooler() {
        return preschooler;
    }

    public void setPreschooler(Preschooler preschooler) {
        this.preschooler = preschooler;
    }
}
