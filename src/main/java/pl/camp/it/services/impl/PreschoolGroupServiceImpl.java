package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPreschoolGroupDAO;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.services.IPreschoolGroupService;

import java.time.LocalDate;
import java.util.List;

@Service
public class PreschoolGroupServiceImpl implements IPreschoolGroupService {

    @Autowired
    IPreschoolGroupDAO preschoolerGroupDAO;

    @Override
    public void persistPreschoolGroup(PreschoolGroup preschoolGroup) {
        preschoolGroup.setQuantity(true);
        preschoolGroup.setLocalDateAddToDB(LocalDate.now());
        preschoolGroup.setNameGroup(preschoolGroup.getNameGroup().toUpperCase());
        this.preschoolerGroupDAO.persistPreschoolGroup(preschoolGroup);
    }

    @Override
    public boolean isPreschoolGroupInDB(String nameGroup) {
        return this.preschoolerGroupDAO.isPreschoolGroupInDB(nameGroup);
    }

    @Override
    public List<PreschoolGroup> getListPreschoolerGroup() {
        return this.preschoolerGroupDAO.getListPreschoolerGroup();
    }

    @Override
    public void setPreschoolGroupAndAddEmployeeToGroup(String nameGroup, Employee employee) {
        PreschoolGroup preschoolGroup = this.preschoolerGroupDAO.getPreschoolGroup(nameGroup);
        preschoolGroup.setEmployee(employee);
        this.preschoolerGroupDAO.persistPreschoolGroup(preschoolGroup);
    }

    @Override
    public String getNameGroupPreschoolById(int idGroup) {
        return this.preschoolerGroupDAO.getPreschoolGroupById(idGroup).getNameGroup();
    }
}
