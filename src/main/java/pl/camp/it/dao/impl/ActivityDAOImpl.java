package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IActivityDAO;
import pl.camp.it.model.activities.Activities;
import pl.camp.it.model.meals.SingleBoardPrice;
import pl.camp.it.session.SessionObject;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityDAOImpl implements IActivityDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistActivity(Activities activities) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(activities);
        session.getTransaction().commit();
    }

    @Override
    public boolean isActivityInDB(String name) {
        Session session = sessionFactory.openSession();
        List<Activities> activities=session.createQuery
                ("FROM pl.camp.it.model.activities.Activities WHERE name='"+name.toUpperCase()+"'",
                                                                                            Activities.class).list();
        session.close();
        boolean isInDB=false;
        if (activities!=null) {
            for (Activities temp : activities) {
                if (temp.isQuantity()) {
                    isInDB = true;
                    break;
                }
            }
        }
        return isInDB;
    }

    @Override
    public List<Activities> activitiesList() {
        Session session = sessionFactory.openSession();
        List<Activities>activitiesList=session.createQuery
                ("FROM pl.camp.it.model.activities.Activities WHERE quantity="+true, Activities.class).list();
        session.close();
        if (activitiesList==null){
            activitiesList = new ArrayList<>();
            return activitiesList;
        } else{
            return activitiesList;
        }
    }

    @Override
    public Activities getActivity(int idActivity) {
        Session session = sessionFactory.openSession();
        Activities activities=session.createQuery
                ("FROM pl.camp.it.model.activities.Activities WHERE id="+idActivity+
                                                        " and quantity="+true, Activities.class).uniqueResult();
        session.close();
        if(activities==null){
            return new Activities();
        } else{
            return activities;
        }
    }
}
