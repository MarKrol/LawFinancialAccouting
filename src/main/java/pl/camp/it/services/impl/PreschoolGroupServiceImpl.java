package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPreschoolGroupDAO;
import pl.camp.it.model.employee.Employee;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IPreschoolGroupService;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Override
    public PreschoolGroup getPreschoolerGroupByName(String nameGroup) {
        return this.preschoolerGroupDAO.getPreschoolGroup(nameGroup);
    }

    @Override
    public List<PreschoolGroup> getListPreschoolerGroupNoOneGroup(List<PreschoolGroup> preschoolGroupList) {
        List<PreschoolGroup> preschoolGroups = new ArrayList<>();
        for(PreschoolGroup preschoolGroup: preschoolGroupList){
            if (!preschoolGroup.getNameGroup().equals("BEZ GRUPY")) {
                preschoolGroups.add(preschoolGroup);
            }
        }
        return preschoolGroups;
    }

    @Override
    public PreschoolGroup getPreschoolGroupByIdEmployee(int idEmployee) {
        return this.preschoolerGroupDAO.getPreschoolGroupByIdEmployee(idEmployee);
    }

    @Override
    public void saveChangePreschoolGroup(PreschoolGroup preschoolGroup, Employee employee) {
        preschoolGroup.setNameGroup(preschoolGroup.getNameGroup().toUpperCase());
        preschoolGroup.setEmployee(employee);
        this.preschoolerGroupDAO.persistPreschoolGroup(preschoolGroup);
    }

    @Override
    public boolean isInDBNamePreschoolGroupNoGroupChange(int idGroupChange, String newNameGroup){
        boolean isInDB=false;
        List<PreschoolGroup> preschoolGroups = this.preschoolerGroupDAO.getListPreschoolerGroup();
        for (PreschoolGroup preschoolGroup: preschoolGroups){
            if (preschoolGroup.getId()!=idGroupChange){
                if (preschoolGroup.getNameGroup().equals(newNameGroup.toUpperCase())){
                    isInDB=true;
                    break;
                }
            }
        }
        return isInDB;
    }

    @Override
    public void deletePreschoolGroup(PreschoolGroup preschoolGroup) {
        preschoolGroup.setLocalDateDeleteFromDB(LocalDate.now());
        preschoolGroup.setQuantity(false);
        preschoolGroup.setEmployee(null);
        this.preschoolerGroupDAO.persistPreschoolGroup(preschoolGroup);
    }
}
