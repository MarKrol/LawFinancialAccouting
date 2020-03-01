package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IActivityDAO;
import pl.camp.it.model.activities.Activities;

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
}
