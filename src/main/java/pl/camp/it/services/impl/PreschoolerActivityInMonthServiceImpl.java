package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IActivityDAO;
import pl.camp.it.dao.IPreschoolerActivityInMonthDAO;
import pl.camp.it.model.activities.Activities;
import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
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


    @Override
    public List<Integer> activityListIdToSave
            (List<PreschoolerActivityInMonth> preschoolerActivityListInMonthBeforeSave, List<Integer> activityListId,
             String month) {

        List<Integer> activityListIdToSave = new ArrayList<>();
        Activities activities = new Activities();
        List<Activities> tempActivitiesList = new ArrayList<>();

        for (Integer idActivity: activityListId) {
            if(((activities= activityDAO.getActivity(idActivity))!=null)){
                tempActivitiesList.add(activities);
            }
        }
        for(PreschoolerActivityInMonth tempActivityInMonthPreschooler: preschoolerActivityListInMonthBeforeSave){
            boolean delete = false;
            for(Activities tempActivity:tempActivitiesList ){
                if(tempActivityInMonthPreschooler.getNameAcivity().equals(tempActivity.getName())){
                    delete=false;
                    break;
                } else{
                    delete=true;
                }
            }
            if (delete){
                tempActivityInMonthPreschooler.setQuantity(false);
                preschoolerActivityInMonthDAO.persistPreschoolerActivityInMonth(tempActivityInMonthPreschooler);
            }
        }

        for(Activities tempActivity:tempActivitiesList){
            boolean add = false;
            for(PreschoolerActivityInMonth tempActivityInMonthPreschooler: preschoolerActivityListInMonthBeforeSave){
                if(tempActivityInMonthPreschooler.getNameAcivity().equals(tempActivity.getName())){
                    add=false;
                    break;
                } else{
                    add=true;
                }
            }
            if (add){
                activityListIdToSave.add(tempActivity.getId());
            }
        }
        return activityListIdToSave;
    }


    @Override
    public double activityMonthToPay(List<PreschoolerActivityInMonth> preschoolerActivityInMonthList) {
        double toPay=0.00;

        for(PreschoolerActivityInMonth preschoolerActivityInMonth: preschoolerActivityInMonthList){
            toPay=toPay+(preschoolerActivityInMonth.getPriceNet()* (1+preschoolerActivityInMonth.getVAT()*0.01));
        }

        return toPay;
    }

    @Override
    public PreschoolerActivityInMonth getPreschoolerActivityMonthById(int id) {
        return this.preschoolerActivityInMonthDAO.getPreschoolerActivityMonthById(id);
    }

    @Override
    public void saveEditSettlementActivityMonth(PreschoolerActivityInMonth preschoolerActivityInMonth,
                                                PreschoolerActivityInMonth preschoolerActivityInMonthEdit) {
        preschoolerActivityInMonth.setPriceNet(preschoolerActivityInMonthEdit.getPriceNet());
        preschoolerActivityInMonth.setVAT(preschoolerActivityInMonthEdit.getVAT());

        preschoolerActivityInMonthDAO.persistPreschoolerActivityInMonth(preschoolerActivityInMonth);
    }

    @Override
    public boolean isNameActivityPreschoolerInDB(String nameActivity) {
        return this.preschoolerActivityInMonthDAO.isNameActivityPreschoolerInDB(nameActivity);
    }
}