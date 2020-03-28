package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IActivityDAO;
import pl.camp.it.model.activities.Activities;
import pl.camp.it.model.stay.Stay;
import pl.camp.it.services.IActivityService;

import java.util.List;

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

    @Override
    public List<Activities> activitiesList() {
        return this.activityDAO.activitiesList();
    }

    @Override
    public Activities getActivity(int idActivity) {
        return this.activityDAO.getActivity(idActivity);
    }

    @Override
    public void saveChangeActivity(Activities activities, List<String> activityEditSData) {
        activities.setPriceNet(Double.parseDouble(activityEditSData.get(0)));
        activities.setVAT(Integer.parseInt(activityEditSData.get(1)));
        this.activityDAO.persistActivity(activities);
    }

    @Override
    public void deleteActivity(Activities activities) {
        activities.setQuantity(false);
        this.activityDAO.persistActivity(activities);
    }
}
