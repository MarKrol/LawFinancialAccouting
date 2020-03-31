package pl.camp.it.services;

import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.meals.SingleBoardPrice;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

public interface IPreschoolerSingleBoardInMonthService {
    void persistPreschoolerSingleBoardInMonth(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth);
    boolean isPreschoolerSingleMealMonthInDB(int idPreschooler, String month, String name);
    void saveSingleMealMonthPreschoolerInDB(Preschooler preschooler, String month, SingleBoardPrice singleBoardPrice,
                                            PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth);
    PreschoolerSingleBoardInMonth preschoolerSingleMealMonthInDB(int idPreschooler, String month, String name);
    void saveSingleMealAfterChange(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth,
                                   PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonthEdit);
    List<PreschoolerSingleBoardInMonth> listPreschoolerSingleMealMonthInDB(int idPreschooler, String month);
    double singleMealMonthToPay(List<PreschoolerSingleBoardInMonth> preschoolerSingleBoardInMonthList);
    PreschoolerSingleBoardInMonth getPreschoolerSingleBoardMonthById(int id);
    void saveEditSettlementSingleMealMonth(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth,
                                           PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonthEdit);
    boolean isNameSingleMealPreschoolerInDB(String nameSingleMeal);
    List<PreschoolerSingleBoardInMonth> getAllPreschoolerListByIdPreschoolerByMonth
            (List<Preschooler> preschoolerList, String month, String nameSingleMeal);
    void deleteSingleMealPreschoolInMonthByIdPreschoolerSingleMealBoardPrice(int idPreschoolerSingleMealBoardPrice);
}
