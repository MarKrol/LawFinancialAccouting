package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPreschoolGroupDAO;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.services.IPreschoolGroupService;

@Service
public class PreschoolGroupServiceImpl implements IPreschoolGroupService {

    @Autowired
    IPreschoolGroupDAO preschoolerGroupDAO;

    @Override
    public void persistPreschoolGroup(PreschoolGroup preschoolGroup) {
        this.preschoolerGroupDAO.persistPreschoolGroup(preschoolGroup);
    }
}
