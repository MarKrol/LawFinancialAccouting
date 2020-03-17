package pl.camp.it.dao;

import pl.camp.it.model.meals.SingleBoardPrice;

import java.util.List;

public interface ISingleBoardPriceDAO {
    void persistSingleBoardPrice (SingleBoardPrice singleBoardPrice);
    boolean isSingleMealInDB(String name);
    List<SingleBoardPrice> getListSingleMeal();
    SingleBoardPrice getNameSingleMealById(int id);
}
