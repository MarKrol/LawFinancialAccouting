package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPreschoolerDAO;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IPreschoolerService;

@Service
public class PreschoolerServiceImpl implements IPreschoolerService {

    @Autowired
    IPreschoolerDAO preschoolerDAO;

    @Override
    public void persistPreschooler(Preschooler preschooler) {
        this.preschoolerDAO.persistPreschooler(preschooler);
    }
}
