package pl.camp.it.services.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IEmployeeDAO;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.employee.EmployeeRole;
import pl.camp.it.model.userLogin.EmployeeLogin;
import pl.camp.it.services.IEmployeeService;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    IEmployeeDAO employeeDAO;

    @Override
    public void persistEmployee(Employee employee) {
        employee.setName(employee.getName().toUpperCase());
        employee.setSurname(employee.getSurname().toUpperCase());
        employee.setLocalDateAddToDB(LocalDate.now());
        employee.setQuantity(true);
        employee.setRole(employeeRole(employee).getRole().toUpperCase());
        this.employeeDAO.persistEmployee(employee);
    }

    @Override
    public void persistEmployeeLogin(Employee employee) {
        this.employeeDAO.persistEmployeeLogin(employee);
    }

    @Override
    public void changePassEmployee(EmployeeLogin employeeLogin) {
        this.employeeDAO.changePassEmployee(employeeLogin);
    }

    @Override
    public List<Employee> getListEmployeeTeacher() {
        return this.employeeDAO.getListEmployeeTeacher();
    }

    @Override
    public EmployeeLogin getEmployeeByLogin(String login) {
        return this.employeeDAO.getEmployeeByLogin(login);
    }

    @Override
    public EmployeeLogin getEmployeeById(int id) {
        return this.employeeDAO.getEmployeeById(id);
    }

    @Override
    public boolean isFirstLoginEmployee(EmployeeLogin employeeLogin) {
        if (employeeLogin.getPass().length()==8){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean oldPasswordsMatch(EmployeeLogin employeeLoginDB, EmployeeLogin employeeLoginForm) {
        if (employeeLoginDB.getPass().equals(employeeLoginForm.getPass())){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public boolean newPasswordsMatch(String newPass, String repeatNewPass) {
        if (newPass.equals(repeatNewPass)){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public String hashPassword(String pass) {
        return DigestUtils.md5Hex(pass);
    }

    private Employee employeeRole(Employee employee){
        if (employee.getRole()=="teacher") {
            employee.setRole(EmployeeRole.TEACHER.toString());
        }
        if (employee.getRole()=="teacher") {
            employee.setRole(EmployeeRole.ACCOUNTANT.toString());
        }
        if (employee.getRole()=="teacher") {
            employee.setRole(EmployeeRole.ADMIN.toString());
        }
        return employee;
    }
}
