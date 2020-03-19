package pl.camp.it.services;

import pl.camp.it.model.activities.Activities;
import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

public interface IPreschoolerActivityInMonthService {
    void persistPreschoolerActivityInMonth(PreschoolerActivityInMonth preschoolerActivityInMonth);
    boolean isSaveMonthActivitiesInDB(int idPreschooler, String month);
    void addMonthActivityToDB(Preschooler preschooler, List<Integer> activityId, String month);
}
