package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.ISingleBoardPriceDAO;
import pl.camp.it.model.meals.SingleBoardPrice;

@Repository
public class SingleBoardPriceDAOImpl implements ISingleBoardPriceDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistSingleBoardPrice(SingleBoardPrice singleBoardPrice) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(singleBoardPrice);
        session.getTransaction().commit();
    }
}
