package pl.camp.it.dao;

import pl.camp.it.model.stay.Stay;

import java.util.List;

public interface IStayDAO {
    void persistStay(Stay stay);
    boolean isStayInDB(String name);
    List<Stay> getListStay();
    Stay getStayById(int idStay);
}
