package pl.camp.it.services;

import pl.camp.it.model.parent.Parent;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

public interface IParentService {
    void persistParent(Parent parent);
    void persistParentLogin(Preschooler preschooler);
    void addParentToDB(Parent parent, Preschooler preschooler);
    List<Parent> getListParent(List<Preschooler> preschoolerList);
    Parent getParentById(int idParent);
    void saveEditParentInDB(Parent parent, Parent parentEdit);
    void deleteParent(Parent parent);
}
