package pl.camp.it.services;

import pl.camp.it.model.meals.FullBoardPrice;

import java.util.List;

public interface IFullBoardPriceService {
    void persistFullBoardPrice(FullBoardPrice fullBoardPrice);
    boolean isFullMealInDB(String name);
    List<FullBoardPrice> getListFullMeal();
    int getIdFullBoardPriceByName(String name);
    FullBoardPrice getFullBoardPriceById(int id);
}
