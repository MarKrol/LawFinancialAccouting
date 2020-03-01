package pl.camp.it.services;

import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.userLogin.EmployeeLogin;

public interface IEmployeeService {
    void persistEmployee(Employee employee);
    void persistEmployeeLogin(Employee employee);
    EmployeeLogin getEmployeeByLogin(String login);
    EmployeeLogin getEmployeeById(int id);
    void changePassEmployee(EmployeeLogin employeeLogin);

    boolean isFirstLoginEmployee(EmployeeLogin employeeLogin);
    boolean oldPasswordsMatch (EmployeeLogin employeeLoginDB, EmployeeLogin employeeLoginForm);
    boolean newPasswordsMatch (String newPass, String repeatNewPass);
    String hashPassword (String pass);
}
