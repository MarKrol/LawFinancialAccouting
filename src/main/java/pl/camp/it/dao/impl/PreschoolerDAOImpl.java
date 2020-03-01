package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPreschoolerDAO;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschooler.Preschooler;

@Repository
public class PreschoolerDAOImpl implements IPreschoolerDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistPreschooler(Preschooler preschooler) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(preschooler);
        session.getTransaction().commit();
    }

    @Override
    public Preschooler getPreschoolerByName() {
        return null;
    }

    @Override
    public Preschooler getPreschoolerBySurname() {
        return null;
    }

    @Override
    public Preschooler getPreschoolerById() {
        return null;
    }
}
