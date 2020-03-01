package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IFullBoardPriceDAO;
import pl.camp.it.model.meals.FullBoardPrice;
import pl.camp.it.services.IFullBoardPriceService;

@Service
public class FullBoardPriceServiceImpl implements IFullBoardPriceService {

    @Autowired
    IFullBoardPriceDAO fullBoardPriceDAO;

    @Override
    public void persistFullBoardPrice(FullBoardPrice fullBoardPrice) {
        this.fullBoardPriceDAO.persistFullBoardPrice(fullBoardPrice);
    }
}
