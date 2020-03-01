package pl.camp.it.dao;

import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.userLogin.EmployeeLogin;

public interface IEmployeeDAO {
    void persistEmployee(Employee employee);
    void persistEmployeeLogin(Employee employee);
    EmployeeLogin getEmployeeByLogin(String login);
    EmployeeLogin getEmployeeById(int id);
    void changePassEmployee(EmployeeLogin employeeLogin);
    Employee getEmployeeByName();
    Employee getEmployeeBySurname();
}
