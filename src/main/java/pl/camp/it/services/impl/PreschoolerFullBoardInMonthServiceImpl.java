package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IFullBoardPriceDAO;
import pl.camp.it.dao.IPreschoolerFullBoardInMonthDAO;
import pl.camp.it.model.meals.FullBoardPrice;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IPreschoolerFullBoardInMonthService;
import pl.camp.it.services.IPreschoolerService;

import java.util.ArrayList;
import java.util.List;

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
                                                          PreschoolerFullBoardInMonth preschoolerFullBoardInMonthEdit,
                                                          String nameDiet) { //change

        preschoolerFullBoardInMonth.setNameDiet(nameDiet);
        preschoolerFullBoardInMonth.setNumberFirstBreakfast(preschoolerFullBoardInMonthEdit.getNumberFirstBreakfast());
        preschoolerFullBoardInMonth.setNumberSecondBreakfast(preschoolerFullBoardInMonthEdit.getNumberSecondBreakfast());
        preschoolerFullBoardInMonth.setNumberDinner(preschoolerFullBoardInMonthEdit.getNumberDinner());
        preschoolerFullBoardInMonth.setNumberTea(preschoolerFullBoardInMonthEdit.getNumberTea());

        this.preschoolerFullBoardInMonthDAO.persistPreschoolerFullBoardInMonth(preschoolerFullBoardInMonth);
    }

    @Override
    public void saveEditSettlementPreschoolerFullBoardInMonth(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth,
                                                              PreschoolerFullBoardInMonth preschoolerFullBoardInMonthEdit) {

        preschoolerFullBoardInMonth.setNumberFirstBreakfast(preschoolerFullBoardInMonthEdit.getNumberFirstBreakfast());
        preschoolerFullBoardInMonth.setNumberSecondBreakfast(preschoolerFullBoardInMonthEdit.getNumberSecondBreakfast());
        preschoolerFullBoardInMonth.setNumberDinner(preschoolerFullBoardInMonthEdit.getNumberDinner());
        preschoolerFullBoardInMonth.setNumberTea(preschoolerFullBoardInMonthEdit.getNumberTea());
        preschoolerFullBoardInMonth.setPriceNetFB(preschoolerFullBoardInMonthEdit.getPriceNetFB());
        preschoolerFullBoardInMonth.setPriceNetSB(preschoolerFullBoardInMonthEdit.getPriceNetSB());
        preschoolerFullBoardInMonth.setPriceNetDiner(preschoolerFullBoardInMonthEdit.getPriceNetDiner());
        preschoolerFullBoardInMonth.setPriceNetTea(preschoolerFullBoardInMonthEdit.getPriceNetTea());
        preschoolerFullBoardInMonth.setVAT(preschoolerFullBoardInMonthEdit.getVAT());

        this.preschoolerFullBoardInMonthDAO.persistPreschoolerFullBoardInMonth(preschoolerFullBoardInMonth);
    }

    @Override
    public boolean isNameFullMealPreschoolerInDB(String nameFullMeal) {
        return this.preschoolerFullBoardInMonthDAO.isNameFullMealPreschoolerInDB(nameFullMeal);
    }

    @Override
    public List<PreschoolerFullBoardInMonth> getAllPreschoolerListByIdPreschoolerByMonth(List<Preschooler> preschoolerList, String month) {
        List<PreschoolerFullBoardInMonth> preschoolerFullBoardInMonthList = new ArrayList<>();
        for(Preschooler preschooler: preschoolerList){
            PreschoolerFullBoardInMonth temp;
            if ((temp=preschoolerFullBoardInMonthDAO.getPreschoolerFullBoardInMonth(preschooler.getId(),month))!=null) {
                preschoolerFullBoardInMonthList.add(temp);
            }
        }
        return preschoolerFullBoardInMonthList;
    }

    @Override
    public PreschoolerFullBoardInMonth getPreschoolerFullMealMonthByIdPreschoolerMonthFullMeal(int id) {
        return this.preschoolerFullBoardInMonthDAO.getPreschoolerFullMealMonthByIdPreschoolerMonthFullMeal(id);
    }

    @Override
    public void deleteFullMealPreschoolInMonthByIdPreschoolerFullMealBoardPrice(int idPreschoolerFullMealBoardPrice) {
        PreschoolerFullBoardInMonth preschoolerFullBoardInMonth =
                preschoolerFullBoardInMonthDAO.getPreschoolerFullMealMonthByIdPreschoolerMonthFullMeal(idPreschoolerFullMealBoardPrice);
        preschoolerFullBoardInMonth.setQuantity(false);
        this.preschoolerFullBoardInMonthDAO.persistPreschoolerFullBoardInMonth(preschoolerFullBoardInMonth);
    }
}
