package pl.camp.it.dao;

import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.parent.Parent;
import pl.camp.it.model.preschooler.Preschooler;

public interface IParentDAO {
    void persistParent(Parent parent);
    void persistParentLogin(Preschooler preschooler);
}
