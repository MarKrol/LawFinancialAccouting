package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPreschoolerActivityInMonthDAO;
import pl.camp.it.model.activities.Activities;
import pl.camp.it.model.activities.PreschoolerActivityInMonth;

import java.util.List;

@Repository
public class PreschoolerActivityInMonthDAOImpl implements IPreschoolerActivityInMonthDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistPreschoolerActivityInMonth(PreschoolerActivityInMonth preschoolerActivityInMonth) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(preschoolerActivityInMonth);
        session.getTransaction().commit();
    }

    @Override
    public boolean isInDBActivity(int idPreschooler, String month) {
        Session session = sessionFactory.openSession();
        List<PreschoolerActivityInMonth> preschoolerActivityInMonth=session.createQuery
                ("FROM pl.camp.it.model.activities.PreschoolerActivityInMonth WHERE preschoolerId="+idPreschooler+
                        " and month='"+month.toUpperCase()+"'"+
                        " and quantity="+true, PreschoolerActivityInMonth.class).list();
        session.close();
        if(preschoolerActivityInMonth.size()!=0){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public List<PreschoolerActivityInMonth> listPreschoolerActivityInMonth(int idPreschooler, String month) {
        Session session =sessionFactory.openSession();
        List<PreschoolerActivityInMonth> preschoolerActivityInMonthList=session.createQuery
                ("FROM pl.camp.it.model.activities.PreschoolerActivityInMonth WHERE preschoolerID="+idPreschooler+
                        " and month='"+month.toUpperCase()+"'"+" and quantity="+true, PreschoolerActivityInMonth.class)
                .list();
        session.close();
        return preschoolerActivityInMonthList;
    }

    @Override
    public PreschoolerActivityInMonth getPreschoolerActivityMonthById(int id) {
        Session session = sessionFactory.openSession();
        PreschoolerActivityInMonth preschoolerActivityInMonth = session.createQuery
                ("FROM pl.camp.it.model.activities.PreschoolerActivityInMonth WHERE id="+id+" and quantity="+true,
                        PreschoolerActivityInMonth.class).uniqueResult();
        session.close();
        return preschoolerActivityInMonth;
    }
}
