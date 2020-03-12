package pl.camp.it.dao;

import pl.camp.it.model.activities.Activities;

public interface IActivityDAO {
    void persistActivity(Activities activities);
    boolean isActivityInDB(String name);
}
