package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IFullBoardPriceDAO;
import pl.camp.it.model.meals.FullBoardPrice;

import java.util.List;

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

    @Override
    public boolean isFullMealInDB(String name) {
        Session session = sessionFactory.openSession();
        List<FullBoardPrice> fullBoardPrice = session.createQuery
                ("FROM pl.camp.it.model.meals.FullBoardPrice WHERE name='"+name.toUpperCase()+"'",
                                                                            FullBoardPrice.class).list();
        session.close();
        boolean isInDB=false;
        if (fullBoardPrice!=null) {
            for (FullBoardPrice temp : fullBoardPrice) {
                if (temp.isQuantity()) {
                    isInDB = true;
                    break;
                }
            }
        }
        return isInDB;
    }
}
