package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.impl.SingleBoardPriceDAOImpl;
import pl.camp.it.model.activities.Activities;
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

    @Override
    public void saveChangeSingleMeal(SingleBoardPrice singleBoardPrice, List<String> singleEdit) {
        singleBoardPrice.setPriceNet(Double.parseDouble(singleEdit.get(0)));
        singleBoardPrice.setVAT(Integer.parseInt(singleEdit.get(1)));
        this.singleBoardPriceDAO.persistSingleBoardPrice(singleBoardPrice);
    }

    @Override
    public void deleteSingleMeal(SingleBoardPrice singleBoardPrice) {
        singleBoardPrice.setLocalDateDeleteFromDB(LocalDate.now());
        singleBoardPrice.setQuantity(false);
        this.singleBoardPriceDAO.persistSingleBoardPrice(singleBoardPrice);
    }
}
