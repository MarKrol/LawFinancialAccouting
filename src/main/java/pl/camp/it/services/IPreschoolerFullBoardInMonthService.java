package pl.camp.it.services;

import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;

public interface IPreschoolerFullBoardInMonthService {
    void persistPreschoolerFullBoardInMonth(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth,
                                            int idPreschooler, int idDiet, String month);
    String getNameDietByIdPreschoolerFromFullBoardInMonth(int idPreschooler);
    boolean isMonthPreschoolerFullBoardInMonthInDB(int idPreschooler, String month);
    PreschoolerFullBoardInMonth getPreschoolerFullBoardInMonth(int idPreschooler, String month);
    void editAndPersistPreschoolerFullBoardInMonth(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth,
                                                   PreschoolerFullBoardInMonth preschoolerFullBoardInMonthEdit);

}
