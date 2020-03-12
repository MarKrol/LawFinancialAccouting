package pl.camp.it.services;

import pl.camp.it.model.meals.FullBoardPrice;

public interface IFullBoardPriceService {
    void persistFullBoardPrice(FullBoardPrice fullBoardPrice);
    boolean isFullMealInDB(String name);
}
