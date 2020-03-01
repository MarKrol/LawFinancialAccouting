package pl.camp.it.services;

import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.model.preschooler.Preschooler;

public interface IPreschoolerService {
    void persistPreschooler(Preschooler preschooler);
}
