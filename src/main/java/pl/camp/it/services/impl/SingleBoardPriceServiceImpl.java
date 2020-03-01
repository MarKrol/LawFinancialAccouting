package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.impl.SingleBoardPriceDAOImpl;
import pl.camp.it.model.meals.SingleBoardPrice;
import pl.camp.it.services.ISingleBoardPriceService;

@Service
public class SingleBoardPriceServiceImpl implements ISingleBoardPriceService {
    @Autowired
    SingleBoardPriceDAOImpl singleBoardPriceDAO;

    @Override
    public void persistSingleBoardPrice(SingleBoardPrice singleBoardPrice) {
        this.singleBoardPriceDAO.persistSingleBoardPrice(singleBoardPrice);
    }
}
