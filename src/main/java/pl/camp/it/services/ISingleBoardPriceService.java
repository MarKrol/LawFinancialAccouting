package pl.camp.it.services;

import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.meals.SingleBoardPrice;

import java.util.List;

public interface ISingleBoardPriceService {
    void persistSingleBoardPrice(SingleBoardPrice singleBoardPrice);
    boolean isSingleMealInDB(String name);
    List<SingleBoardPrice> getListSingleMeal();
    SingleBoardPrice getNameSingleMealById(int id);
    void saveChangeSingleMeal(SingleBoardPrice singleBoardPrice, List<String> singleEdit);
    void deleteSingleMeal(SingleBoardPrice singleBoardPrice);
}
