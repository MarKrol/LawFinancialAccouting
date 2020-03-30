package pl.camp.it.dao;

import pl.camp.it.model.meals.FullBoardPrice;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;

import java.util.List;

public interface IPreschoolerFullBoardInMonthDAO {
    void persistPreschoolerFullBoardInMonth(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth);
    String getNameDietByIdPreschoolerFromFullBoardInMonth(int idPreschooler);
    boolean isMonthPreschoolerFullBoardInMonthInDB(int idPreschooler, String month);
    PreschoolerFullBoardInMonth getPreschoolerFullBoardInMonth(int idPreschooler, String month);
    boolean isNameFullMealPreschoolerInDB(String nameFullMeal);
    PreschoolerFullBoardInMonth getPreschoolerFullMealMonthByIdPreschoolerMonthFullMeal(int id);
}
