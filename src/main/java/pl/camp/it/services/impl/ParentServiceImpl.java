package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IParentDAO;
import pl.camp.it.model.parent.Parent;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IParentService;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void addParentToDB(Parent parent, Preschooler preschooler) {
        parent.setPreschooler(preschooler);
        parent.setQuantity(true);
        parent.setStreet(parent.getStreet().toUpperCase());
        parent.setCity(parent.getCity().toUpperCase());
        parent.setSurname(parent.getSurname().toUpperCase());
        parent.setName(parent.getName().toUpperCase());
        this.parentDAO.persistParent(parent);
    }

    @Override
    public void saveEditParentInDB(Parent parent, Parent parentEdit) {
        parent.setName(parentEdit.getName().toUpperCase());
        parent.setSurname(parentEdit.getSurname().toUpperCase());
        parent.setCity(parentEdit.getCity().toUpperCase());
        parent.setStreet(parentEdit.getStreet().toUpperCase());
        parent.setZip(parentEdit.getZip());
        parent.setEmail(parentEdit.getEmail());
        parent.setNumberHouse(parentEdit.getNumberHouse());
        parent.setPhone(parentEdit.getPhone());
        this.parentDAO.persistParent(parent);
    }

    @Override
    public List<Parent> getListParent(List<Preschooler> preschoolerList) {
        List<Parent> parentList = new ArrayList<>();
        for(Preschooler preschooler :preschoolerList){
            List<Parent> temp = new ArrayList<>();
            temp=this.parentDAO.getListParent(preschooler.getId());
            for (Parent parent: temp){
                parentList.add(parent);
            }
        }
        return parentList;
    }

    @Override
    public Parent getParentById(int idParent) {
        return this.parentDAO.getParentById(idParent);
    }

    @Override
    public void deleteParent(Parent parent) {
        parent.setQuantity(false);
        this.parentDAO.persistParent(parent);
    }
}
