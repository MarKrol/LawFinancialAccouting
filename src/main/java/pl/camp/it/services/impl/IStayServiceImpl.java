package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IStayDAO;
import pl.camp.it.model.stay.Stay;
import pl.camp.it.services.IStayService;

import java.util.List;

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

    @Override
    public List<Stay> getListStay() {
        return this.stayDAO.getListStay();
    }

    @Override
    public Stay getStayById(int idStay) {
        return this.stayDAO.getStayById(idStay);
    }

    @Override
    public void saveChangeStay(Stay stay, List<String> stayEditSData) {
        stay.setPriceNet(Double.parseDouble(stayEditSData.get(0)));
        stay.setVAT(Integer.parseInt(stayEditSData.get(1)));
        this.stayDAO.persistStay(stay);
    }

    @Override
    public List<Stay> getListAllStay() {
        return this.stayDAO.getListAllStay();
    }

    @Override
    public Stay getOldAndActualStayById(int idStay) {
        return this.stayDAO.getOldAndActualStayById(idStay);
    }

    @Override
    public void deleteStay(Stay stay) {
        stay.setQuantity(false);
        this.stayDAO.persistStay(stay);
    }
}
