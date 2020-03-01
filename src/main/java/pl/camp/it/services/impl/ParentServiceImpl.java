package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IParentDAO;
import pl.camp.it.model.parent.Parent;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IParentService;

@Service
public class ParentServiceImpl implements IParentService {

    @Autowired
    IParentDAO parentDAO;

    @Override
    public void persistParent(Parent parent) {
        this.parentDAO.persistParent(parent);
    }

    @Override
    public void persistParentLogin(Preschooler preschooler) {
        this.parentDAO.persistParentLogin(preschooler);
    }
}
