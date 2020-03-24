package pl.camp.it.dao;

import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

public interface IPreschoolerActivityInMonthDAO {
    void persistPreschoolerActivityInMonth(PreschoolerActivityInMonth preschoolerActivityInMonth);
    boolean isInDBActivity(int idPreschooler, String month);
    List<PreschoolerActivityInMonth> listPreschoolerActivityInMonth(int idPreschooler, String month);
    PreschoolerActivityInMonth getPreschoolerActivityMonthById(int id);
}
