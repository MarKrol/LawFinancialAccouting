package pl.camp.it.dao;

import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

public interface IPreschoolGroupDAO {
    void persistPreschoolGroup(PreschoolGroup preschoolGroup);
    boolean isPreschoolGroupInDB(String nameGroup);
    List<PreschoolGroup> getListPreschoolerGroup();
    PreschoolGroup getPreschoolGroup(String nameGroup);
    PreschoolGroup getPreschoolGroupById(int id);
}
