package pl.camp.it.dao;

import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschooler.Preschooler;

public interface IPreschoolerDAO {
    void persistPreschooler(Preschooler preschooler);
    Preschooler getPreschoolerByName();
    Preschooler getPreschoolerBySurname();
    Preschooler getPreschoolerById();
}
