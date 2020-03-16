package pl.camp.it.dao;

import pl.camp.it.model.meals.FullBoardPrice;

import java.util.List;

public interface IFullBoardPriceDAO {
    void persistFullBoardPrice(FullBoardPrice fullBoardPrice);
    boolean isFullMealInDB(String name);
    List<FullBoardPrice> getListFullMeal();
    FullBoardPrice getFullBoardPriceById(int id);
    int getIdFullBoardPriceByName(String name);
}
