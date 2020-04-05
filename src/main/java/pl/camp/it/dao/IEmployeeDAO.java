package pl.camp.it.dao;

import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.userLogin.EmployeeLogin;

import java.util.List;

public interface IEmployeeDAO {
    void persistEmployee(Employee employee);
    List<Employee> getListEmployeeTeacher();
    void persistEmployeeLogin(Employee employee);
    EmployeeLogin getEmployeeByLogin(String login);
    EmployeeLogin getEmployeeById(int id);
    void changePassEmployee(EmployeeLogin employeeLogin);
    Employee getEmployeeByIdEmployee(int idEmployee);
    List<Employee> getEmployees();
    EmployeeLogin returnEmployeeLoginNewPassAndLogin(Employee employee);
    void persistEmployeeLogin(EmployeeLogin employeeLogin);
    List<EmployeeLogin> getEmployeeLoginList();
    Employee getEmployeeByName();
    Employee getEmployeeBySurname();
}
