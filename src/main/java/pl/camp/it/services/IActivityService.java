package pl.camp.it.services;

import pl.camp.it.model.activities.Activities;

import java.util.List;

public interface IActivityService {
    void persistActivity(Activities activities);
    boolean isActivityInDB(String name);
    List<Activities> activitiesList();
    Activities getActivity(int idActivity);
    void saveChangeActivity(Activities activities, List<String> activityEditSData);
    void deleteActivity(Activities activities);
}
