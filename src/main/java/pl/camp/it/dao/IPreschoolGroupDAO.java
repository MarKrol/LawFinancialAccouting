package pl.camp.it.dao;

import pl.camp.it.model.preschoolGroup.PreschoolGroup;

import java.util.List;

public interface IPreschoolGroupDAO {
    void persistPreschoolGroup(PreschoolGroup preschoolGroup);
    boolean isPreschoolGroupInDB(String nameGroup);
    List<PreschoolGroup> getListPreschoolerGroup();
    PreschoolGroup getPreschoolGroup(String nameGroup);
}
