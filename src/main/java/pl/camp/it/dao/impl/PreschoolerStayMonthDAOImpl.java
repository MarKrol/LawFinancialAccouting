package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPreschoolerStayMonthDAO;
import pl.camp.it.model.stay.PreschoolerStayMonth;

import java.util.List;

@Repository
public class PreschoolerStayMonthDAOImpl implements IPreschoolerStayMonthDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistPreschoolerStayMonth(PreschoolerStayMonth preschoolerStayMonth) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(preschoolerStayMonth);
        session.getTransaction().commit();
    }

    @Override
    public boolean isPreschoolerStayMonthInDB(int idPreschooler, String month, String nameStay) {
        Session session=sessionFactory.openSession();
        PreschoolerStayMonth preschoolerStayMonth = session.createQuery
                ("FROM pl.camp.it.model.stay.PreschoolerStayMonth WHERE preschoolerId="+idPreschooler+
                                "and month='"+month.toUpperCase()+"'"+"and name='"+nameStay.toUpperCase()+"'"+"and quantity="+true,
                        PreschoolerStayMonth.class).uniqueResult();
        session.close();
        if (preschoolerStayMonth!=null){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public PreschoolerStayMonth preschoolerStayMonth(int idPreschooler, String month, String nameStay) {
        Session session=sessionFactory.openSession();
        PreschoolerStayMonth preschoolerStayMonth = session.createQuery
                ("FROM pl.camp.it.model.stay.PreschoolerStayMonth WHERE preschoolerId="+idPreschooler+
                                "and month='"+month.toUpperCase()+"'"+"and name='"+nameStay.toUpperCase()+"'"+"and quantity="+true,
                        PreschoolerStayMonth.class).uniqueResult();
        session.close();
        return preschoolerStayMonth;
    }

    @Override
    public List<PreschoolerStayMonth> listPreschoolerStayMonth(int idPreschooler, String month) {
        Session session=sessionFactory.openSession();
        List<PreschoolerStayMonth> listPreschoolerStayMonth = session.createQuery
                ("FROM pl.camp.it.model.stay.PreschoolerStayMonth WHERE preschoolerId="+idPreschooler+
                                "and month='"+month.toUpperCase()+"'"+"and quantity="+true,
                        PreschoolerStayMonth.class).list();
        session.close();
        return listPreschoolerStayMonth;
    }

    @Override
    public PreschoolerStayMonth getPreschoolerStayMonthById(int id) {
        Session session=sessionFactory.openSession();
        PreschoolerStayMonth preschoolerStayMonth = session.createQuery
                ("FROM pl.camp.it.model.stay.PreschoolerStayMonth WHERE id="+id+" and quantity="+true,
                        PreschoolerStayMonth.class).uniqueResult();
        session.close();
        return preschoolerStayMonth;
    }

    @Override
    public List<PreschoolerStayMonth> getPreschoolerStayMonthByIdPreschooler(int idPreschooler) {
        Session session=sessionFactory.openSession();
        List<PreschoolerStayMonth> preschoolerStayMonthList = session.createQuery
                ("FROM pl.camp.it.model.stay.PreschoolerStayMonth WHERE preschholerId="+idPreschooler+" and quantity="+true,
                        PreschoolerStayMonth.class).list();
        session.close();
        return preschoolerStayMonthList;
    }

}
