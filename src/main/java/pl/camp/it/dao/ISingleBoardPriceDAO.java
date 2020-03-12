package pl.camp.it.dao;

import pl.camp.it.model.meals.SingleBoardPrice;

public interface ISingleBoardPriceDAO {
    void persistSingleBoardPrice (SingleBoardPrice singleBoardPrice);
    boolean isSingleMealInDB(String name);
}
