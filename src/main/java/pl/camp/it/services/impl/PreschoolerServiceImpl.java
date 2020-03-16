package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPreschoolGroupDAO;
import pl.camp.it.dao.IPreschoolerDAO;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IPreschoolerService;

import java.time.LocalDate;
import java.util.List;

@Service
public class PreschoolerServiceImpl implements IPreschoolerService {

    @Autowired
    IPreschoolerDAO preschoolerDAO;
    @Autowired
    IPreschoolGroupDAO preschoolGroupDAO;

    @Override
    public void persistPreschooler(Preschooler preschooler) {
        preschooler.setLocalDateAddToDB(LocalDate.now());
        preschooler.setQuantity(true);
        preschooler.setName(preschooler.getName().toUpperCase());
        preschooler.setSurname(preschooler.getSurname().toUpperCase());
        this.preschoolerDAO.persistPreschooler(preschooler);
    }

    @Override
    public Preschooler setPreschoolerAndAddPreschoolGroupToPreschooler(Preschooler preschooler, String nameGroup) {
        PreschoolGroup preschoolGroup = this.preschoolGroupDAO.getPreschoolGroup(nameGroup);
        preschooler.setPreschoolGroup(preschoolGroup);
        return preschooler;
    }

    @Override
    public List<Preschooler> getPreschoolerList(int idGroup) {
        return this.preschoolerDAO.getPreschoolerList(idGroup);
    }

    @Override
    public Preschooler getPreschoolerById(int id) {
        return this.preschoolerDAO.getPreschoolerById(id);
    }
}
