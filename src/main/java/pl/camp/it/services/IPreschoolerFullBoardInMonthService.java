package pl.camp.it.services;

import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

public interface IPreschoolerFullBoardInMonthService {
    void persistPreschoolerFullBoardInMonth(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth,
                                            int idPreschooler, int idDiet, String month);
    String getNameDietByIdPreschoolerFromFullBoardInMonth(int idPreschooler);
    boolean isMonthPreschoolerFullBoardInMonthInDB(int idPreschooler, String month);
    PreschoolerFullBoardInMonth getPreschoolerFullBoardInMonth(int idPreschooler, String month);
    void editAndPersistPreschoolerFullBoardInMonth(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth,
                                                   PreschoolerFullBoardInMonth preschoolerFullBoardInMonthEdit,
                                                   String nameDiet); //change
    void saveEditSettlementPreschoolerFullBoardInMonth(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth,
                                                   PreschoolerFullBoardInMonth preschoolerFullBoardInMonthEdit);
    boolean isNameFullMealPreschoolerInDB(String nameFullMeal);
    List<PreschoolerFullBoardInMonth> getAllPreschoolerListByIdPreschoolerByMonth(List<Preschooler> preschoolerList, String month);
    PreschoolerFullBoardInMonth getPreschoolerFullMealMonthByIdPreschoolerMonthFullMeal(int id);
    void deleteFullMealPreschoolInMonthByIdPreschoolerFullMealBoardPrice(int idPreschoolerFullMealBoardPrice);
}
