package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPreschoolerFullBoardInMonthDAO;
import pl.camp.it.model.meals.FullBoardPrice;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;

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
                        +idPreschooler+"and quantity="+true,PreschoolerFullBoardInMonth.class).list();
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

    @Override
    public boolean isNameFullMealPreschoolerInDB(String nameFullMeal) {
        Session session = sessionFactory.openSession();
        List<PreschoolerFullBoardInMonth> preschoolerFullBoardInMonthList = session.createQuery
                ("FROM pl.camp.it.model.meals.PreschoolerFullBoardInMonth WHERE nameDiet='"+nameFullMeal+"'"+
                        "and quantity="+true, PreschoolerFullBoardInMonth.class).list();
        session.close();
        boolean inDB;
        if (preschoolerFullBoardInMonthList.size()==0){
            inDB=false;
        }else {
            inDB=true;
        }
        return inDB;
    }

    @Override
    public PreschoolerFullBoardInMonth getPreschoolerFullMealMonthByIdPreschoolerMonthFullMeal(int id) {
        Session session = sessionFactory.openSession();
        PreschoolerFullBoardInMonth preschoolerFullBoardInMonth = session.createQuery
                ("FROM pl.camp.it.model.meals.PreschoolerFullBoardInMonth WHERE id="
                        +id+" and quantity="+true, PreschoolerFullBoardInMonth.class).uniqueResult();
        session.close();
        return preschoolerFullBoardInMonth;
    }
}
