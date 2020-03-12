package pl.camp.it.services;

import pl.camp.it.model.meals.SingleBoardPrice;

public interface ISingleBoardPriceService {
    void persistSingleBoardPrice(SingleBoardPrice singleBoardPrice);
    boolean isSingleMealInDB(String name);
}
