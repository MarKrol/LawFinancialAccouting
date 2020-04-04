package pl.camp.it.services;

import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.userLogin.EmployeeLogin;

import java.util.List;

public interface IEmployeeService {
    void persistEmployee(Employee employee);
    void persistEmployeeLogin(Employee employee);
    void persistEmployee(Employee employee, Employee employeeEdit);
    EmployeeLogin getEmployeeByLogin(String login);
    EmployeeLogin getEmployeeById(int id);
    void changePassEmployee(EmployeeLogin employeeLogin);

    List<Employee> getListEmployeeTeacher();

    boolean isFirstLoginEmployee(EmployeeLogin employeeLogin);
    boolean oldPasswordsMatch (EmployeeLogin employeeLoginDB, EmployeeLogin employeeLoginForm);
    boolean newPasswordsMatch (String newPass, String repeatNewPass);
    String hashPassword (String pass);

    Employee getEmployeeByIdEmployee(int idEmployee);
    List<Employee> getEmployees();
    List<Employee> getEmployeesNoSuperAdmin();

    void deleteEmployee(Employee employee);
}
