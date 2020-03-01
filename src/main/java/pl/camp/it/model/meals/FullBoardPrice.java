package pl.camp.it.model.meals;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "tfullMeals")
public class FullBoardPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double firstBreakfastPriceNet;
    private double secondBreakfastPriceNet;
    private double dinnerPriceNet;
    private double teaPriceNet;
    private int VAT;
    private boolean quantity;
    private LocalDate localDateAddToDB;
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

    public double getFirstBreakfastPriceNet() {
        return firstBreakfastPriceNet;
    }

    public void setFirstBreakfastPriceNet(double firstBreakfastPriceNet) {
        this.firstBreakfastPriceNet = firstBreakfastPriceNet;
    }

    public double getSecondBreakfastPriceNet() {
        return secondBreakfastPriceNet;
    }

    public void setSecondBreakfastPriceNet(double secondBreakfastPriceNet) {
        this.secondBreakfastPriceNet = secondBreakfastPriceNet;
    }

    public double getDinnerPriceNet() {
        return dinnerPriceNet;
    }

    public void setDinnerPriceNet(double dinnerPriceNet) {
        this.dinnerPriceNet = dinnerPriceNet;
    }

    public double getTeaPriceNet() {
        return teaPriceNet;
    }

    public void setTeaPriceNet(double teaPriceNet) {
        this.teaPriceNet = teaPriceNet;
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
