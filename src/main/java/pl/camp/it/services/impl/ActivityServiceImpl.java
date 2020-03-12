package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IActivityDAO;
import pl.camp.it.model.activities.Activities;
import pl.camp.it.services.IActivityService;

@Service
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    IActivityDAO activityDAO;

    @Override
    public void persistActivity(Activities activities) {
        activities.setQuantity(true);
        activities.setName(activities.getName().toUpperCase());
        this.activityDAO.persistActivity(activities);
    }

    @Override
    public boolean isActivityInDB(String name) {
        return this.activityDAO.isActivityInDB(name);
    }
}
