package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPreschoolerActivityInMonthDAO;
import pl.camp.it.model.activities.PreschoolerActivityInMonth;

@Repository
public class PreschoolerActivityInMonthDAODAOImpl implements IPreschoolerActivityInMonthDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistPreschoolerActivityInMonth(PreschoolerActivityInMonth preschoolerActivityInMonth) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(preschoolerActivityInMonth);
        session.getTransaction().commit();
    }
}
