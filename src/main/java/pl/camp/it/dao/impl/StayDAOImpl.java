package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IStayDAO;
import pl.camp.it.model.activities.Activities;
import pl.camp.it.model.stay.Stay;

import java.util.List;

@Repository
public class StayDAOImpl implements IStayDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistStay(Stay stay) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(stay);
        session.getTransaction().commit();
    }

    @Override
    public boolean isStayInDB(String name) {
        Session session = sessionFactory.openSession();
        List<Stay> stay=session.createQuery
                ("FROM pl.camp.it.model.stay.Stay WHERE name='"+name.toUpperCase()+"'",
                        Stay.class).list();
        session.close();
        boolean isInDB=false;
        if (stay!=null) {
            for (Stay temp : stay) {
                if (temp.isQuantity()) {
                    isInDB = true;
                    break;
                }
            }
        }
        return isInDB;
    }
}
