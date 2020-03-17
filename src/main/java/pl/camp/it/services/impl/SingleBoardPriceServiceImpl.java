package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.impl.SingleBoardPriceDAOImpl;
import pl.camp.it.model.meals.SingleBoardPrice;
import pl.camp.it.services.ISingleBoardPriceService;

import java.time.LocalDate;
import java.util.List;

@Service
public class SingleBoardPriceServiceImpl implements ISingleBoardPriceService {
    @Autowired
    SingleBoardPriceDAOImpl singleBoardPriceDAO;

    @Override
    public void persistSingleBoardPrice(SingleBoardPrice singleBoardPrice) {
        singleBoardPrice.setQuantity(true);
        singleBoardPrice.setName(singleBoardPrice.getName().toUpperCase());
        singleBoardPrice.setLocalDateAddToDB(LocalDate.now());
        this.singleBoardPriceDAO.persistSingleBoardPrice(singleBoardPrice);
    }

    @Override
    public boolean isSingleMealInDB(String name) {
        return this.singleBoardPriceDAO.isSingleMealInDB(name);
    }

    @Override
    public List<SingleBoardPrice> getListSingleMeal() {
        return this.singleBoardPriceDAO.getListSingleMeal();
    }

    @Override
    public SingleBoardPrice getNameSingleMealById(int id) {
        return this.singleBoardPriceDAO.getNameSingleMealById(id);
    }
}
