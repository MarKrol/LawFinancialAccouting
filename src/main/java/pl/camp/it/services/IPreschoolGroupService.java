package pl.camp.it.services;

import org.springframework.stereotype.Service;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;

import java.util.List;

@Service
public interface IPreschoolGroupService {
    void persistPreschoolGroup(PreschoolGroup preschoolGroup);
    boolean isPreschoolGroupInDB(String nameGroup);
    List<PreschoolGroup> getListPreschoolerGroup();
    void setPreschoolGroupAndAddEmployeeToGroup(String nameGroup, Employee employee);
    String getNameGroupPreschoolById(int idGroup);
    PreschoolGroup getPreschoolerGroupByName(String nameGroup);
}
