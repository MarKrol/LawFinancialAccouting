package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IEmployeeDAO;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.employee.EmployeeRole;
import pl.camp.it.model.userLogin.EmployeeLogin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class EmployeeDAOImpl implements IEmployeeDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistEmployee(Employee employee) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(employee);
        session.getTransaction().commit();
    }

    @Override
    public void persistEmployeeLogin(Employee employee) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(employeeLoginPass(employee));
        session.getTransaction().commit();
    }

    @Override
    public void changePassEmployee(EmployeeLogin employeeLogin) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(employeeLogin);
        session.getTransaction().commit();
    }

    @Override
    public EmployeeLogin getEmployeeByLogin(String login) {
        Session session=sessionFactory.openSession();
        EmployeeLogin employeeLogin = session.createQuery
                ("FROM pl.camp.it.model.userLogin.EmployeeLogin WHERE login='"+login+"'", EmployeeLogin.class).
                uniqueResult();
        session.close();
        return  employeeLogin;
    }

    @Override
    public EmployeeLogin getEmployeeById(int id) {
        Session session=sessionFactory.openSession();
        EmployeeLogin employeeLogin = session.createQuery
                ("FROM pl.camp.it.model.userLogin.EmployeeLogin WHERE employeeId="+ id, EmployeeLogin.class).
                uniqueResult();
        session.close();
        return employeeLogin;
    }

    @Override
    public List<Employee> getListEmployeeTeacher() {
        List<Employee> tempolary=new ArrayList<>();
        Session session=sessionFactory.openSession();
        List<Employee> employee = session.createQuery("FROM pl.camp.it.model.employee.Employee").list();
        for (Employee tempEmploye : employee){
            if (tempEmploye.isQuantity() && tempEmploye.getRole().equals(EmployeeRole.TEACHER.toString())){
                tempolary.add(tempEmploye);
            }
        }
        return tempolary;
    }

    @Override
    public Employee getEmployeeByName() {
        return null;
    }

    @Override
    public Employee getEmployeeBySurname() {
        return null;
    }



    private EmployeeLogin employeeLoginPass(Employee employee){
        EmployeeLogin tempEmployeeLoginPass = new EmployeeLogin();

        tempEmployeeLoginPass.setLogin(generateLogin(employee));
        tempEmployeeLoginPass.setPass(generatePass());
        tempEmployeeLoginPass.setEmployee(employee);

        return tempEmployeeLoginPass;
    }

    private String generateLogin(Employee employee){
        return removePolishLettersFromLogin((employee.getName()+employee.getSurname()+employee.getId()).toLowerCase());
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
