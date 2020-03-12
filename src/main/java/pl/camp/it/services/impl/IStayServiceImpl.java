package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IStayDAO;
import pl.camp.it.model.stay.Stay;
import pl.camp.it.services.IStayService;

@Service
public class IStayServiceImpl implements IStayService {

    @Autowired
    IStayDAO stayDAO;

    @Override
    public void persistStay(Stay stay) {
        stay.setQuantity(true);
        stay.setName(stay.getName().toUpperCase());
        this.stayDAO.persistStay(stay);
    }

    @Override
    public boolean isStayInDB(String name) {
        return this.stayDAO.isStayInDB(name);
    }
}
