package pl.camp.it.services;

import pl.camp.it.model.activities.Activities;
import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

public interface IPreschoolerActivityInMonthService {
    void persistPreschoolerActivityInMonth(PreschoolerActivityInMonth preschoolerActivityInMonth);
    boolean isSaveMonthActivitiesInDB(int idPreschooler, String month);
    void addMonthActivityToDB(Preschooler preschooler, List<Integer> activityId, String month);
    List<PreschoolerActivityInMonth> listPreschoolerActivityInMonth(int idPreschooler, String month);
    List<Activities> listNoPreschoolerActivityInMonth
                    (List<Activities> activitiesList, List<PreschoolerActivityInMonth> preschoolerActivityInMonthList);
    List<Integer> activityListIdToSave(List<PreschoolerActivityInMonth> preschoolerActivityInMonth,
                                       List<Integer> activityListId, String month);
    double activityMonthToPay(List<PreschoolerActivityInMonth> preschoolerActivityInMonthList);
    PreschoolerActivityInMonth getPreschoolerActivityMonthById(int id);
    void saveEditSettlementActivityMonth(PreschoolerActivityInMonth preschoolerActivityInMonth,
                                         PreschoolerActivityInMonth preschoolerActivityInMonthEdit);
    boolean isNameActivityPreschoolerInDB(String nameActivity);
}
