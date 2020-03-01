package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPreschoolerFullBoardInMonthDAO;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.services.IPreschoolerFullBoardInMonthService;

@Service
public class PreschoolerFullBoardInMonthServiceImpl implements IPreschoolerFullBoardInMonthService {
    @Autowired
    IPreschoolerFullBoardInMonthDAO preschoolerFullBoardInMonthDAO;

    @Override
    public void pesistPreschoolerFullBoardInMonth(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth) {
        this.preschoolerFullBoardInMonthDAO.pesistPreschoolerFullBoardInMonth(preschoolerFullBoardInMonth);
    }
}
