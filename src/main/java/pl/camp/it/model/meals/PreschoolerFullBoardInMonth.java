package pl.camp.it.model.meals;

import pl.camp.it.model.preschooler.Preschooler;

import javax.persistence.*;

@Entity(name = "tpreschoolerFullMeal")
public class PreschoolerFullBoardInMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String month;
    private String nameDiet;
    private int numberFirstBreakfast;
    private double priceNetFB;
    private int numberSecondBreakfast;
    private double priceNetSB;
    private int numberDinner;
    private double priceNetDiner;
    private int numberTea;
    private double priceNetTea;
    private int VAT;
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

    public String getNameDiet() {
        return nameDiet;
    }

    public void setNameDiet(String nameDiet) {
        this.nameDiet = nameDiet;
    }

    public int getNumberFirstBreakfast() {
        return numberFirstBreakfast;
    }

    public void setNumberFirstBreakfast(int numberFirstBreakfast) {
        this.numberFirstBreakfast = numberFirstBreakfast;
    }

    public double getPriceNetFB() {
        return priceNetFB;
    }

    public void setPriceNetFB(double priceNetFB) {
        this.priceNetFB = priceNetFB;
    }

    public int getNumberSecondBreakfast() {
        return numberSecondBreakfast;
    }

    public void setNumberSecondBreakfast(int numberSecondBreakfast) {
        this.numberSecondBreakfast = numberSecondBreakfast;
    }

    public double getPriceNetSB() {
        return priceNetSB;
    }

    public void setPriceNetSB(double priceNetSB) {
        this.priceNetSB = priceNetSB;
    }

    public int getNumberDinner() {
        return numberDinner;
    }

    public void setNumberDinner(int numberDinner) {
        this.numberDinner = numberDinner;
    }

    public double getPriceNetDiner() {
        return priceNetDiner;
    }

    public void setPriceNetDiner(double priceNetDiner) {
        this.priceNetDiner = priceNetDiner;
    }

    public int getNumberTea() {
        return numberTea;
    }

    public void setNumberTea(int numberTea) {
        this.numberTea = numberTea;
    }

    public double getPriceNetTea() {
        return priceNetTea;
    }

    public void setPriceNetTea(double priceNetTea) {
        this.priceNetTea = priceNetTea;
    }

    public int getVAT() {
        return VAT;
    }

    public void setVAT(int VAT) {
        this.VAT = VAT;
    }

    public boolean isQuantity() {
        return quantity;
    }

    public void setQuantity(boolean quantity) {
        this.quantity = quantity;
    }

    public Preschooler getPreschooler() {
        return preschooler;
    }

    public void setPreschooler(Preschooler preschooler) {
        this.preschooler = preschooler;
    }
}
