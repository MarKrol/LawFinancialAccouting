package pl.camp.it.services;

import pl.camp.it.model.stay.Stay;

public interface IStayService {
    void persistStay(Stay stay);
    boolean isStayInDB(String name);
}
