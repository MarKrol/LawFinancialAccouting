package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IActivityDAO;
import pl.camp.it.model.activities.Activities;
import pl.camp.it.model.meals.SingleBoardPrice;

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

}
