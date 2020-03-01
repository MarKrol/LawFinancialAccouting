package pl.camp.it.model.activities;

import pl.camp.it.model.preschooler.Preschooler;

import javax.persistence.*;

@Entity(name = "tpreschoolerActivity")
public class PreschoolerActivityInMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String month;
    private double priceNet;
    private int VAT;
    private String nameAcivity;
    private boolean quantity;

    @ManyToOne
    @JoinColumn(name = "preschoolerId")
    Preschooler preschooler;

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

    public Preschooler getPreschooler() {
        return preschooler;
    }

    public void setPreschooler(Preschooler preschooler) {
        this.preschooler = preschooler;
    }

    public String getNameAcivity() {
        return nameAcivity;
    }

    public void setNameAcivity(String nameAcivity) {
        this.nameAcivity = nameAcivity;
    }

    public boolean isQuantity() {
        return quantity;
    }

    public void setQuantity(boolean quantity) {
        this.quantity = quantity;
    }
}
