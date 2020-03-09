package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPreschoolGroupDAO;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;

import javax.persistence.criteria.From;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class PreschoolGroupDAOImpl implements IPreschoolGroupDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistPreschoolGroup(PreschoolGroup preschoolGroup) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(preschoolGroup);
        session.getTransaction().commit();
    }

    @Override
    public boolean isPreschoolGroupInDB(String nameGroup) {
        Session session=sessionFactory.openSession();
        PreschoolGroup preschoolGroup = session.createQuery
                ("FROM pl.camp.it.model.preschoolGroup.PreschoolGroup WHERE nameGroup='"+nameGroup.toUpperCase()+"'",
                                                                                PreschoolGroup.class).uniqueResult();
        session.close();
        if (preschoolGroup==null) {
            return false;
        } else
        {
            if (preschoolGroup.isQuantity()){
                return true;
            }
            else {
                return false;
            }
        }
    }

    @Override
    public List<PreschoolGroup> getListPreschoolerGroup() {
        List<PreschoolGroup> preschoolGroups = new ArrayList<>();
        Session session=sessionFactory.openSession();
        List<PreschoolGroup> temp = session.createQuery("FROM pl.camp.it.model.preschoolGroup.PreschoolGroup").list();
        for (PreschoolGroup preschoolGroup: temp){
            if (preschoolGroup.isQuantity()){
                preschoolGroups.add(preschoolGroup);
            }
        }
        return preschoolGroups;
    }

    @Override
    public PreschoolGroup getPreschoolGroup(String nameGroup) {
        Session session=sessionFactory.openSession();
        PreschoolGroup preschoolGroup = session.createQuery
                ("FROM pl.camp.it.model.preschoolGroup.PreschoolGroup WHERE nameGroup='"+nameGroup+"'",
                                                                            PreschoolGroup.class).uniqueResult();
        session.close();
        return preschoolGroup;
    }
}
