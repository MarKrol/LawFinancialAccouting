package pl.camp.it.services;

import pl.camp.it.model.parent.Parent;
import pl.camp.it.model.preschooler.Preschooler;

public interface IParentService {
    void persistParent(Parent parent);
    void persistParentLogin(Preschooler preschooler);
}
