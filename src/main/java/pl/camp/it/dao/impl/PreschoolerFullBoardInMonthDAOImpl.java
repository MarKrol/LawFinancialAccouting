package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPreschoolerFullBoardInMonthDAO;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;

@Repository
public class PreschoolerFullBoardInMonthDAOImpl implements IPreschoolerFullBoardInMonthDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void pesistPreschoolerFullBoardInMonth(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(preschoolerFullBoardInMonth);
        session.getTransaction().commit();
    }
}
