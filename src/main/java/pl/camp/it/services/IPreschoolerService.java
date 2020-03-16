package pl.camp.it.services;

import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

public interface IPreschoolerService {
    void persistPreschooler(Preschooler preschooler);
    Preschooler setPreschoolerAndAddPreschoolGroupToPreschooler(Preschooler preschooler, String nameGroup);
    List<Preschooler> getPreschoolerList(int idGroup);
    Preschooler getPreschoolerById(int id);
}
