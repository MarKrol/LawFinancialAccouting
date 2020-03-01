package pl.camp.it.services.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IEmployeeDAO;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.userLogin.EmployeeLogin;
import pl.camp.it.services.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    IEmployeeDAO employeeDAO;

    @Override
    public void persistEmployee(Employee employee) {
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
}
