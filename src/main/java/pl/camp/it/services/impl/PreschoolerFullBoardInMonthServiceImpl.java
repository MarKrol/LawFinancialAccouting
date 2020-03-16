package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IFullBoardPriceDAO;
import pl.camp.it.dao.IPreschoolerFullBoardInMonthDAO;
import pl.camp.it.model.meals.FullBoardPrice;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.services.IPreschoolerFullBoardInMonthService;
import pl.camp.it.services.IPreschoolerService;

@Service
public class PreschoolerFullBoardInMonthServiceImpl implements IPreschoolerFullBoardInMonthService {
    @Autowired
    IPreschoolerFullBoardInMonthDAO preschoolerFullBoardInMonthDAO;
    @Autowired
    IFullBoardPriceDAO fullBoardPriceDAO;
    @Autowired
    IPreschoolerService preschoolerService;

    @Override
    public void persistPreschoolerFullBoardInMonth(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth,
                                                   int idPreschooler, int idDiet, String month) {
        FullBoardPrice fullBoardPrice = fullBoardPriceDAO.getFullBoardPriceById(idDiet);

        preschoolerFullBoardInMonth.setMonth(month.toUpperCase());
        preschoolerFullBoardInMonth.setVAT(fullBoardPrice.getVAT());
        preschoolerFullBoardInMonth.setQuantity(true);

        preschoolerFullBoardInMonth.setPriceNetTea(fullBoardPrice.getTeaPriceNet());
        preschoolerFullBoardInMonth.setPriceNetDiner(fullBoardPrice.getDinnerPriceNet());
        preschoolerFullBoardInMonth.setPriceNetFB(fullBoardPrice.getFirstBreakfastPriceNet());
        preschoolerFullBoardInMonth.setPriceNetSB(fullBoardPrice.getSecondBreakfastPriceNet());

        preschoolerFullBoardInMonth.setNameDiet(fullBoardPrice.getName());
        preschoolerFullBoardInMonth.setPreschooler(preschoolerService.getPreschoolerById(idPreschooler));

        this.preschoolerFullBoardInMonthDAO.persistPreschoolerFullBoardInMonth(preschoolerFullBoardInMonth);
    }

    @Override
    public String getNameDietByIdPreschoolerFromFullBoardInMonth(int idPreschooler) {
        return preschoolerFullBoardInMonthDAO.getNameDietByIdPreschoolerFromFullBoardInMonth(idPreschooler);
    }

    @Override
    public boolean isMonthPreschoolerFullBoardInMonthInDB(int idPreschooler, String month) {
        return this.preschoolerFullBoardInMonthDAO.isMonthPreschoolerFullBoardInMonthInDB(idPreschooler,month);
    }

    @Override
    public PreschoolerFullBoardInMonth getPreschoolerFullBoardInMonth(int idPreschooler, String month) {
        return this.preschoolerFullBoardInMonthDAO.getPreschoolerFullBoardInMonth(idPreschooler,month);
    }

    @Override
    public void editAndPersistPreschoolerFullBoardInMonth(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth,
                                                          PreschoolerFullBoardInMonth preschoolerFullBoardInMonthEdit) {

        preschoolerFullBoardInMonth.setNumberFirstBreakfast(preschoolerFullBoardInMonthEdit.getNumberFirstBreakfast());
        preschoolerFullBoardInMonth.setNumberSecondBreakfast(preschoolerFullBoardInMonthEdit.getNumberSecondBreakfast());
        preschoolerFullBoardInMonth.setNumberDinner(preschoolerFullBoardInMonthEdit.getNumberDinner());
        preschoolerFullBoardInMonth.setNumberTea(preschoolerFullBoardInMonthEdit.getNumberTea());

        this.preschoolerFullBoardInMonthDAO.persistPreschoolerFullBoardInMonth(preschoolerFullBoardInMonth);
    }
}
