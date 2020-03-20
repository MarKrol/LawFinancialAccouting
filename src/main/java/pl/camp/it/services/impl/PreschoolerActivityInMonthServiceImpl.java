package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IActivityDAO;
import pl.camp.it.dao.IPreschoolerActivityInMonthDAO;
import pl.camp.it.model.activities.Activities;
import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IActivityService;
import pl.camp.it.services.IPreschoolerActivityInMonthService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreschoolerActivityInMonthServiceImpl implements IPreschoolerActivityInMonthService {
    @Autowired
    IPreschoolerActivityInMonthDAO preschoolerActivityInMonthDAO;
    @Autowired
    IActivityDAO activityDAO;

    @Override
    public void persistPreschoolerActivityInMonth(PreschoolerActivityInMonth preschoolerActivityInMonth) {
        this.preschoolerActivityInMonthDAO.persistPreschoolerActivityInMonth(preschoolerActivityInMonth);
    }

    @Override
    public boolean isSaveMonthActivitiesInDB(int idPreschooler, String month) {
        boolean inDB=false;
            if (preschoolerActivityInMonthDAO.isInDBActivity(idPreschooler, month)) {
                inDB=true;
            }
        return inDB;
    }

    @Override
    public void addMonthActivityToDB(Preschooler preschooler, List<Integer> activityId, String month) {
        Activities activities;
        for(Integer idActivity: activityId){
            if ((activities=activityDAO.getActivity(idActivity))!=null){
                PreschoolerActivityInMonth preschoolerActivityInMonth = new PreschoolerActivityInMonth();
                preschoolerActivityInMonth.setQuantity(true);
                preschoolerActivityInMonth.setNameAcivity(activities.getName());
                preschoolerActivityInMonth.setVAT(activities.getVAT());
                preschoolerActivityInMonth.setPriceNet(activities.getPriceNet());
                preschoolerActivityInMonth.setPreschooler(preschooler);
                preschoolerActivityInMonth.setMonth(month);
                preschoolerActivityInMonthDAO.persistPreschoolerActivityInMonth(preschoolerActivityInMonth);
            }
        }
    }

    @Override
    public List<PreschoolerActivityInMonth> listPreschoolerActivityInMonth(int idPreschooler, String month) {
        return this.preschoolerActivityInMonthDAO.listPreschoolerActivityInMonth(idPreschooler, month);
    }

    @Override
    public List<Activities> listNoPreschoolerActivityInMonth
            (List<Activities> activitiesList, List<PreschoolerActivityInMonth> preschoolerActivityInMonthList) {

        List<Activities> temp = new ArrayList<>();

        for(Activities activities: activitiesList){
            boolean isOnListActivity = false;
            for(PreschoolerActivityInMonth preschoolerActivityInMonth: preschoolerActivityInMonthList){
                if (activities.getName().equals(preschoolerActivityInMonth.getNameAcivity())){
                    isOnListActivity = true;
                    break;
                }
            }
            if (!isOnListActivity){
                temp.add(activities);
            }
        }
        return temp;
    }
}