package pl.camp.it.dao;

import pl.camp.it.model.stay.PreschoolerStayMonth;

public interface IPreschoolerStayMonthDAO {
    boolean isPreschoolerStayMonthInDB(int idPreschooler, String month, String nameStay);
    void persistPreschoolerStayMonth(PreschoolerStayMonth preschoolerStayMonth);
    PreschoolerStayMonth preschoolerStayMonth(int idPreschooler, String month, String nameStay);
}
