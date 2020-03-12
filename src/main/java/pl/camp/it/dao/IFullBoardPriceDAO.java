package pl.camp.it.dao;

import pl.camp.it.model.meals.FullBoardPrice;

public interface IFullBoardPriceDAO {
    void persistFullBoardPrice(FullBoardPrice fullBoardPrice);
    boolean isFullMealInDB(String name);
}
