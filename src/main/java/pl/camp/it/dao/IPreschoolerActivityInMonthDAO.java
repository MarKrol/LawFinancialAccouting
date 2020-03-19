package pl.camp.it.dao;

import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.model.preschooler.Preschooler;

public interface IPreschoolerActivityInMonthDAO {
    void persistPreschoolerActivityInMonth(PreschoolerActivityInMonth preschoolerActivityInMonth);
    boolean isInDBActivity(int idPreschooler, String month);
}
