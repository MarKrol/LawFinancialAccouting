package pl.camp.it.services;

import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.model.stay.PreschoolerStayMonth;
import pl.camp.it.model.stay.Stay;

public interface IPreschoolerStayMonthService {
    boolean isPreschoolerStayMonthInDB(int idPreschooler, String month, String nameStay);
    void addStayMonthToDB(Stay stay, PreschoolerStayMonth preschoolerStayMonth, String month, Preschooler preschooler);
}
