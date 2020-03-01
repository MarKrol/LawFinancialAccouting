package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPreschoolGroupDAO;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;

@Repository
public class PreschoolGroupDAOImpl implements IPreschoolGroupDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistPreschoolGroup(PreschoolGroup preschoolGroup) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(preschoolGroup);
        session.getTransaction().commit();
    }
}
