package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IFullBoardPriceDAO;
import pl.camp.it.model.meals.FullBoardPrice;

@Repository
public class FullBoardPriceDAOImpl implements IFullBoardPriceDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistFullBoardPrice(FullBoardPrice fullBoardPrice) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(fullBoardPrice);
        session.getTransaction().commit();
    }
}
