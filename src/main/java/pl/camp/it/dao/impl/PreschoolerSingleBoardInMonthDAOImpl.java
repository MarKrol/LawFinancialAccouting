package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPreschoolerSingleBoardInMonthDAO;
import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;

import java.util.List;

@Repository
public class PreschoolerSingleBoardInMonthDAOImpl implements IPreschoolerSingleBoardInMonthDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistPreschoolerSingleBoardInMonthDAO(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(preschoolerSingleBoardInMonth);
        session.getTransaction().commit();
    }

    @Override
    public boolean isPreschoolerSingleMealMonthInDB(int idPreschooler, String month, String name) {
        Session session = sessionFactory.openSession();
        PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth =session.createQuery
                ("FROM pl.camp.it.model.meals.PreschoolerSingleBoardInMonth WHERE preschoolerId="+idPreschooler+
                        "and month='"+month.toUpperCase()+"'"+"and name='"+name.toUpperCase()+"'"+"and quantity="+true,
                        PreschoolerSingleBoardInMonth.class).uniqueResult();
        session.close();
        if (preschoolerSingleBoardInMonth!=null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public PreschoolerSingleBoardInMonth preschoolerSingleMealMonthInDB(int idPreschooler, String month, String name) {
        Session session = sessionFactory.openSession();
        PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth =session.createQuery
                ("FROM pl.camp.it.model.meals.PreschoolerSingleBoardInMonth WHERE preschoolerId="+idPreschooler+
                                "and month='"+month.toUpperCase()+"'"+"and name='"+name.toUpperCase()+"'"+"and quantity="+true,
                        PreschoolerSingleBoardInMonth.class).uniqueResult();
        session.close();

        return preschoolerSingleBoardInMonth;
    }

    @Override
    public List<PreschoolerSingleBoardInMonth> listPreschoolerSingleMealMonthInDB(int idPreschooler, String month) {
        Session session = sessionFactory.openSession();
        List<PreschoolerSingleBoardInMonth> preschoolerSingleBoardInMonthList=session.createQuery
                ("FROM pl.camp.it.model.meals.PreschoolerSingleBoardInMonth WHERE preschoolerId="+idPreschooler+
                        " and month='"+month+"'"+" and quantity="+true, PreschoolerSingleBoardInMonth.class).list();
        session.close();
        return preschoolerSingleBoardInMonthList;
    }

    @Override
    public PreschoolerSingleBoardInMonth getPreschoolerSingleBoardMonthById(int id) {
        Session session = sessionFactory.openSession();
        PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth =session.createQuery
                ("FROM pl.camp.it.model.meals.PreschoolerSingleBoardInMonth WHERE id="+id+" and quantity="+true,
                        PreschoolerSingleBoardInMonth.class).uniqueResult();
        session.close();
        return preschoolerSingleBoardInMonth;
    }

    @Override
    public boolean isNameSingleMealPreschoolerInDB(String nameSingleMeal) {
        Session session = sessionFactory.openSession();
        List<PreschoolerSingleBoardInMonth> preschoolerSingleBoardInMonthList = session.createQuery
                ("FROM pl.camp.it.model.meals.PreschoolerSingleBoardInMonth WHERE name='"+nameSingleMeal+"'"+
                        "and quantity="+true, PreschoolerSingleBoardInMonth.class).list();
        session.close();
        boolean inDB;
        if (preschoolerSingleBoardInMonthList.size()==0){
            inDB=false;
        }else {
            inDB=true;
        }
        return inDB;
    }

    @Override
    public PreschoolerSingleBoardInMonth getPreschoolerSingleMealMonthInDB(int idPreschooler, String month, String name) {
        Session session = sessionFactory.openSession();
        PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth =session.createQuery
                ("FROM pl.camp.it.model.meals.PreschoolerSingleBoardInMonth WHERE preschoolerId="+idPreschooler+
                                "and month='"+month.toUpperCase()+"'"+"and name='"+name.toUpperCase()+"'"+"and quantity="+true,
                        PreschoolerSingleBoardInMonth.class).uniqueResult();
        session.close();
        return preschoolerSingleBoardInMonth;
    }
}
