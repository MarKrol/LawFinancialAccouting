package pl.camp.it.dao;

import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;

public interface IPreschoolerSingleBoardInMonthDAO{
    void persistPreschoolerSingleBoardInMonthDAO(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth);
    boolean isPreschoolerSingleMealMonthInDB(int idPreschooler, String month, String name);
    PreschoolerSingleBoardInMonth preschoolerSingleMealMonthInDB(int idPreschooler, String month, String name);
}
