package pl.camp.it.dao;

import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

public interface IPreschoolerDAO {
    void persistPreschooler(Preschooler preschooler);
    List<Preschooler> getPreschoolerList(int idGroup);
    Preschooler getPreschoolerById(int id);
    Preschooler getPreschoolerByName();
    Preschooler getPreschoolerBySurname();
}
