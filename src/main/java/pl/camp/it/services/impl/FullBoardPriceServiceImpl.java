package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IFullBoardPriceDAO;
import pl.camp.it.model.meals.FullBoardPrice;
import pl.camp.it.model.meals.SingleBoardPrice;
import pl.camp.it.services.IFullBoardPriceService;

import java.time.LocalDate;
import java.util.List;

@Service
public class FullBoardPriceServiceImpl implements IFullBoardPriceService {

    @Autowired
    IFullBoardPriceDAO fullBoardPriceDAO;

    @Override
    public void persistFullBoardPrice(FullBoardPrice fullBoardPrice) {
        fullBoardPrice.setQuantity(true);
        fullBoardPrice.setLocalDateAddToDB(LocalDate.now());
        fullBoardPrice.setName(fullBoardPrice.getName().toUpperCase());
        this.fullBoardPriceDAO.persistFullBoardPrice(fullBoardPrice);
    }

    @Override
    public boolean isFullMealInDB(String name) {
        return this.fullBoardPriceDAO.isFullMealInDB(name);
    }

    @Override
    public List<FullBoardPrice> getListFullMeal() {
        return this.fullBoardPriceDAO.getListFullMeal();
    }

    @Override
    public FullBoardPrice getFullBoardPriceById(int id) {
        return this.fullBoardPriceDAO.getFullBoardPriceById(id);
    }

    @Override
    public int getIdFullBoardPriceByName(String name) {
        return this.fullBoardPriceDAO.getIdFullBoardPriceByName(name);
    }

    @Override
    public void saveChangeFullMeal(FullBoardPrice fullBoardPrice, List<String> fullEdit) {
        fullBoardPrice.setFirstBreakfastPriceNet(Double.parseDouble(fullEdit.get(0)));
        fullBoardPrice.setSecondBreakfastPriceNet(Double.parseDouble(fullEdit.get(1)));
        fullBoardPrice.setDinnerPriceNet(Double.parseDouble(fullEdit.get(2)));
        fullBoardPrice.setTeaPriceNet(Double.parseDouble(fullEdit.get(3)));
        fullBoardPrice.setVAT(Integer.parseInt(fullEdit.get(4)));
        this.fullBoardPriceDAO.persistFullBoardPrice(fullBoardPrice);
    }

    @Override
    public void deleteFullMeal(FullBoardPrice fullBoardPrice) {
        fullBoardPrice.setLocalDateDeleteFromDB(LocalDate.now());
        fullBoardPrice.setQuantity(false);
        this.fullBoardPriceDAO.persistFullBoardPrice(fullBoardPrice);
    }
}
