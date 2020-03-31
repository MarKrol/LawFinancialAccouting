package pl.camp.it.dao;

import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

public interface IPreschoolerSingleBoardInMonthDAO{
    void persistPreschoolerSingleBoardInMonthDAO(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth);
    boolean isPreschoolerSingleMealMonthInDB(int idPreschooler, String month, String name);
    PreschoolerSingleBoardInMonth preschoolerSingleMealMonthInDB(int idPreschooler, String month, String name);
    List<PreschoolerSingleBoardInMonth> listPreschoolerSingleMealMonthInDB(int idPreschooler, String month);
    PreschoolerSingleBoardInMonth getPreschoolerSingleBoardMonthById(int id);
    boolean isNameSingleMealPreschoolerInDB(String nameSingleMeal);
    PreschoolerSingleBoardInMonth getPreschoolerSingleMealMonthInDB(int idPreschooler, String month, String name);
}
