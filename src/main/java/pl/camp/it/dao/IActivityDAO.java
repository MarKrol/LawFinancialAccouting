package pl.camp.it.dao;

import pl.camp.it.model.activities.Activities;

import java.util.List;

public interface IActivityDAO {
    void persistActivity(Activities activities);
    boolean isActivityInDB(String name);
    List<Activities> activitiesList();
    Activities getActivity(int idActivity);
}
