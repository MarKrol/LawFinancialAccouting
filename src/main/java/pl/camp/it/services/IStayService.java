package pl.camp.it.services;

import pl.camp.it.model.stay.Stay;

import java.util.List;

public interface IStayService {
    void persistStay(Stay stay);
    boolean isStayInDB(String name);
    List<Stay> getListStay();
    Stay getStayById(int idStay);
}
