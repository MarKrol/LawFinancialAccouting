package pl.camp.it.services;

import org.springframework.stereotype.Service;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

@Service
public interface IPreschoolGroupService {
    void persistPreschoolGroup(PreschoolGroup preschoolGroup);
    boolean isPreschoolGroupInDB(String nameGroup);
    List<PreschoolGroup> getListPreschoolerGroup();
    void setPreschoolGroupAndAddEmployeeToGroup(String nameGroup, Employee employee);
    String getNameGroupPreschoolById(int idGroup);
    PreschoolGroup getPreschoolerGroupByName(String nameGroup);
    List<PreschoolGroup> getListPreschoolerGroupNoOneGroup(List<PreschoolGroup> preschoolGroupList);
    PreschoolGroup getPreschoolGroupByIdEmployee(int idEmployee);
    void saveChangePreschoolGroup(PreschoolGroup preschoolGroup, Employee employee);
    boolean isInDBNamePreschoolGroupNoGroupChange(int idGroupChange, String newNameGroup);
    void deletePreschoolGroup (PreschoolGroup preschoolGroup);
    void persistPreschoolGroupAfterChangeEmployee(PreschoolGroup preschoolGroup, Employee employee);
    void persistPreschoolGroupAfterChangeEmployeeNoTeacher(Employee employee);
}
