package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPreschoolerSingleBoardInMonthDAO;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.services.IPreschoolerSingleBoardInMonthService;

@Service
public class PreschoolerSingleBoardInMonthServiceImpl implements IPreschoolerSingleBoardInMonthService {
    @Autowired
    IPreschoolerSingleBoardInMonthDAO preschoolerSingleBoardInMonthDAO;

    @Override
    public void persistPreschoolerSingleBoardInMonth(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth) {
        this.preschoolerSingleBoardInMonthDAO.persistPreschoolerSingleBoardInMonthDAO(preschoolerSingleBoardInMonth);
    }
}
