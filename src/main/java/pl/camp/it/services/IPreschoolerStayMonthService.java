package pl.camp.it.services;

import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.model.stay.PreschoolerStayMonth;
import pl.camp.it.model.stay.Stay;

import java.util.List;

public interface IPreschoolerStayMonthService {
    boolean isPreschoolerStayMonthInDB(int idPreschooler, String month, String nameStay);
    void addStayMonthToDB(Stay stay, PreschoolerStayMonth preschoolerStayMonth, String month, Preschooler preschooler);
    PreschoolerStayMonth preschoolerStayMonth(int idPreschooler, String month, String nameStay);
    void saveChangeStayMonth(PreschoolerStayMonth preschoolerStayMonth, PreschoolerStayMonth preschoolerStayMonthEdit);
    List<PreschoolerStayMonth> listPreschoolerStayMonth(int idPreschooler, String month);
    double stayMonthToPay(List<PreschoolerStayMonth> preschoolerStayInMonthList);
    PreschoolerStayMonth getPreschoolerStayMonthById(int id);
    void saveEditSettlementStayMonth(PreschoolerStayMonth preschoolerStayMonth,
                                     PreschoolerStayMonth preschoolerStayMonthEdit);
    List<PreschoolerStayMonth> getPreschoolerStayMonthByIdPreschooler(int idPreschooler);
    boolean isNameStayPreschoolerInDB(String nameStay);
}
