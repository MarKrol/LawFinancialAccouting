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
import java.util.ArrayList;
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
        employee.setRole(employee.getRole().toUpperCase());
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

    @Override
    public Employee getEmployeeByIdEmployee(int idEmployee) {
        return this.employeeDAO.getEmployeeByIdEmployee(idEmployee);
    }

    @Override
    public List<Employee> getEmployees() {
        return this.employeeDAO.getEmployees();
    }

    @Override
    public List<Employee> getEmployeesNoSuperAdmin() {
        List<Employee> employeeList = new ArrayList<>();
        for (Employee employee: this.employeeDAO.getEmployees()){
            if (!employee.getRole().toString().equals("SUPER_ADMIN")){
                employeeList.add(employee);
            }
        }
        return  employeeList;
    }

    @Override
    public void persistEmployee(Employee employee, Employee employeeEdit) {
        employee.setName(employeeEdit.getName().toUpperCase());
        employee.setSurname(employeeEdit.getSurname().toUpperCase());
        employee.setRole(employeeEdit.getRole().toUpperCase());
        this.employeeDAO.persistEmployee(employee);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employee.setQuantity(false);
        employee.setLocalDateDeleteFromDB(LocalDate.now());
        this.employeeDAO.persistEmployee(employee);
        EmployeeLogin employeeLogin = this.employeeDAO.getEmployeeById(employee.getId());
        EmployeeLogin employeeNewLoginPass=this.employeeDAO.returnEmployeeLoginNewPassAndLogin(employee);
        employeeLogin.setPass(employeeNewLoginPass.getPass());
        employeeLogin.setLogin(employeeNewLoginPass.getLogin());
        this.employeeDAO.persistEmployeeLogin(employeeLogin);
    }

    @Override
    public EmployeeLogin returnPassOrStarsEmployee(EmployeeLogin employeeLogin) {
        if (employeeLogin.getPass().length()==32){
            employeeLogin.setPass("********");
        }
        return employeeLogin;
    }

    @Override
    public EmployeeLogin genNewPassEmployee(Employee employee) {
         return this.employeeDAO.returnEmployeeLoginNewPassAndLogin(employee);
    }

    @Override
    public String saveNewLogin(Employee employee, String newlogin){
        if (loginHasGotWhiteChar(newlogin)){
            return "Zmiany nie są możliwe ponieważ login zawiera białe znaki!";
        } else{
            if(newLoginIsInDB(employee, newlogin)){
                return "Zmiany nie są możliwe ponieważ podany login jest już w bazie!";
            }else{
                return null;
            }
        }
    }

    private boolean newLoginIsInDB(Employee employee, String login){
        boolean loginInDB=false;
        for (EmployeeLogin employeeLogin: this.employeeDAO.getEmployeeLoginList()){
            if (employeeLogin.getLogin().equals(login) && employee.getId()!=employeeLogin.getEmployee().getId()){
                loginInDB=true;
                break;
            }
        }
        return loginInDB;
    }

    private boolean loginHasGotWhiteChar(String login){
        boolean whiteChar=false;
        for(int i=0; i<=login.length()-1;i++){
            if (login.charAt(i)==' '){
                whiteChar=true;
                break;
            }
        }
        return whiteChar;
    }
}
