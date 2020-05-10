package pl.camp.it.services;

import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.parent.Parent;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.model.preschooler.Preschooler;
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

    EmployeeLogin returnPassOrStarsEmployee(EmployeeLogin employeeLogin);
    EmployeeLogin genNewPassEmployee(Employee employee);
    String saveNewLogin(Employee employee, String newlogin);

    boolean userAuthorizationByEmployeeAndGroupPreschooler(Employee employee, PreschoolGroup preschoolGroup);
    boolean userAuthorizationByEmployeeAndPreschooler(Employee employee, Preschooler preschooler);
    boolean userAuthorizationByEmployeeAndParent(Employee employee, Parent parent);

    List<PreschoolGroup> getListPreschoolerGroupByUserRole(Employee employee);
}
