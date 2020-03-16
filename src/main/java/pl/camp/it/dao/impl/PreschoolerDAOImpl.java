package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPreschoolerDAO;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

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
    public List<Preschooler> getPreschoolerList(int idGroup) {
        Session session=sessionFactory.openSession();
        List<Preschooler> preschoolerList = session.createQuery
                ("FROM pl.camp.it.model.preschooler.Preschooler WHERE preschoolerGroupId="+idGroup+
                        "and quantity="+true+" ORDER BY surname ASC, name ASC",Preschooler.class).list();
        session.close();
        return preschoolerList;
    }

    @Override
    public Preschooler getPreschoolerById(int id) {
        Session session=sessionFactory.openSession();
        Preschooler preschooler =session.createQuery
                ("FROM pl.camp.it.model.preschooler.Preschooler WHERE id="+id, Preschooler.class).uniqueResult();
        session.close();
        return preschooler;
    }

    @Override
    public Preschooler getPreschoolerByName() {
        return null;
    }

    @Override
    public Preschooler getPreschoolerBySurname() {
        return null;
    }

}
