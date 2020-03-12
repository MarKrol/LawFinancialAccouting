package pl.camp.it.dao;

import pl.camp.it.model.stay.Stay;

public interface IStayDAO {
    void persistStay(Stay stay);
    boolean isStayInDB(String name);
}
