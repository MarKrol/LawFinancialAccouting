package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPreschoolerFullBoardInMonthDAO;
import pl.camp.it.model.meals.FullBoardPrice;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;

import java.util.List;

@Repository
public class PreschoolerFullBoardInMonthDAOImpl implements IPreschoolerFullBoardInMonthDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistPreschoolerFullBoardInMonth(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(preschoolerFullBoardInMonth);
        session.getTransaction().commit();
    }

    @Override
    public String getNameDietByIdPreschoolerFromFullBoardInMonth(int idPreschooler) {
        Session session = sessionFactory.openSession();
        List<PreschoolerFullBoardInMonth> preschoolerFullBoardInMonth = session.createQuery
                ("FROM pl.camp.it.model.meals.PreschoolerFullBoardInMonth WHERE preschoolerId="
                        +idPreschooler,PreschoolerFullBoardInMonth.class).list();
        session.close();
        if (preschoolerFullBoardInMonth.size()!=0){
            return preschoolerFullBoardInMonth.get(preschoolerFullBoardInMonth.size()-1).getNameDiet();
        } else{
            return "";
        }
    }

    @Override
    public boolean isMonthPreschoolerFullBoardInMonthInDB(int idPreschooler, String month) {
        Session session = sessionFactory.openSession();
        PreschoolerFullBoardInMonth preschoolerFullBoardInMonth = session.createQuery
                ("FROM pl.camp.it.model.meals.PreschoolerFullBoardInMonth WHERE preschoolerId="
                        +idPreschooler+"and month='"+month.toUpperCase()+"'"+
                        "and quantity="+true,PreschoolerFullBoardInMonth.class).uniqueResult();
        session.close();
        if (preschoolerFullBoardInMonth!=null){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public PreschoolerFullBoardInMonth getPreschoolerFullBoardInMonth(int idPreschooler, String month) {
        Session session = sessionFactory.openSession();
        PreschoolerFullBoardInMonth preschoolerFullBoardInMonth = session.createQuery
                ("FROM pl.camp.it.model.meals.PreschoolerFullBoardInMonth WHERE preschoolerId="
                        +idPreschooler+"and month='"+month.toUpperCase()+"'"+
                        "and quantity="+true,PreschoolerFullBoardInMonth.class).uniqueResult();
        session.close();
        return preschoolerFullBoardInMonth;
    }
}
