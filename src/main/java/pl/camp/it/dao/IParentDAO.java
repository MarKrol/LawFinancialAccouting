package pl.camp.it.dao;

import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.parent.Parent;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

public interface IParentDAO {
    void persistParent(Parent parent);
    void persistParentLogin(Preschooler preschooler);
    List<Parent> getListParent(int idPreschooler);
    Parent getParentById(int idParent);
}
