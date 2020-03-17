package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.ISingleBoardPriceDAO;
import pl.camp.it.model.meals.FullBoardPrice;
import pl.camp.it.model.meals.SingleBoardPrice;

import java.util.List;

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

    @Override
    public boolean isSingleMealInDB(String name) {
        Session session = sessionFactory.openSession();
        List<SingleBoardPrice> singleBoardPrice=session.createQuery
                ("FROM pl.camp.it.model.meals.SingleBoardPrice WHERE name='"+name.toUpperCase()+"'",
                                                                            SingleBoardPrice.class).list();
        session.close();
        boolean isInDB=false;
        if (singleBoardPrice!=null) {
            for (SingleBoardPrice temp : singleBoardPrice) {
                if (temp.isQuantity()) {
                    isInDB = true;
                    break;
                }
            }
        }
        return isInDB;
    }

    @Override
    public List<SingleBoardPrice> getListSingleMeal() {
        Session session=sessionFactory.openSession();
        List<SingleBoardPrice> singleBoardPrices = session.createQuery
                ("FROM pl.camp.it.model.meals.SingleBoardPrice WHERE quantity="+true, SingleBoardPrice.class).list();
        session.close();
        return  singleBoardPrices;
    }

    @Override
    public SingleBoardPrice getNameSingleMealById(int id) {
        Session session = sessionFactory.openSession();
        SingleBoardPrice singleBoardPrice = session.createQuery
                ("FROM pl.camp.it.model.meals.SingleBoardPrice WHERE id="+id+"and quantity="+true,
                        SingleBoardPrice.class).uniqueResult();
        session.close();
        if(singleBoardPrice==null){
            return new SingleBoardPrice();
        } else{
            return singleBoardPrice;
        }
    }
}
