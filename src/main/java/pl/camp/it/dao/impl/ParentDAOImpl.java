package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IParentDAO;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.parent.Parent;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.model.userLogin.EmployeeLogin;
import pl.camp.it.model.userLogin.ParentLogin;

import java.util.List;
import java.util.Random;

@Repository
public class ParentDAOImpl implements IParentDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistParent(Parent parent) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(parent);
        session.getTransaction().commit();
    }

    @Override
    public void persistParentLogin(Preschooler preschooler) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(parentLoginPass(preschooler));
        session.getTransaction().commit();
    }


    @Override
    public List<Parent> getListParent(int idPreschooler) {
        Session session = sessionFactory.openSession();
        List<Parent> parentList=session.createQuery
                ("FROM pl.camp.it.model.parent.Parent WHERE preschoolerId="+idPreschooler+" and quantity="+true,
                        Parent.class).list();
        session.close();
        return parentList;
    }

    @Override
    public Parent getParentById(int idParent) {
        Session session = sessionFactory.openSession();
        Parent parent =session.createQuery
                ("FROM pl.camp.it.model.parent.Parent WHERE id="+idParent+" and quantity="+true,
                        Parent.class).uniqueResult();
        session.close();
        return parent;
    }

    private ParentLogin parentLoginPass(Preschooler preschooler){
        ParentLogin tempParentLoginPass = new ParentLogin();

        tempParentLoginPass.setLogin(generateLogin(preschooler));
        tempParentLoginPass.setPass(generatePass());
        tempParentLoginPass.setPreschooler(preschooler);

        return tempParentLoginPass;
    }


    private String generateLogin(Preschooler preschooler){
        return removePolishLettersFromLogin
                                ((preschooler.getName()+preschooler.getSurname()+preschooler.getId()).toLowerCase());
    }

    private String generatePass(){
        final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        final String NUMBER = "0123456789";
        final String OTHER_CHAR = "!@#$%&*()_+-=[]?";
        final String ALL_CHAR=CHAR_LOWER+CHAR_UPPER+NUMBER+OTHER_CHAR;

        String pass="";
        Random random = new Random();

        for (int i=0; i<8; i++){
            pass=pass+ ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length()));
        }
        return pass;
    }

    private String removePolishLettersFromLogin(String login){
        String newLogin="";
        for (int i=0; i<=login.length()-1; i++){
            switch (login.charAt(i)){
                case 'ą': newLogin=newLogin+'a';
                    break;
                case 'ć': newLogin=newLogin+'c';
                    break;
                case 'ę': newLogin=newLogin+'e';
                    break;
                case 'ł': newLogin=newLogin+'l';
                    break;
                case 'ń': newLogin=newLogin+'n';
                    break;
                case 'ó': newLogin=newLogin+'o';
                    break;
                case 'ś': newLogin=newLogin+'s';
                    break;
                case 'ź': newLogin=newLogin+'z';
                    break;
                case 'ż': newLogin=newLogin+'z';
                    break;
                default: newLogin=newLogin+login.charAt(i);
                    break;
            }
        }
        return newLogin;
    }
}
