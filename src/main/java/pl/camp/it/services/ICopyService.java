package pl.camp.it.services;

import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.stay.PreschoolerStayMonth;

import java.util.List;

public interface ICopyService {
    void copyDataToNextMonth(int idGroup, String monthToMonth);
    String getHowDataCopied();

    List<PreschoolerFullBoardInMonth> dataFromTheCopiedMonth();
    List<PreschoolerFullBoardInMonth> dataInTheCopiedMonth();
    List<PreschoolerFullBoardInMonth> dataToBeCopied();

    List<PreschoolerSingleBoardInMonth> dataFromTheCopiedMonthS();
    List<PreschoolerSingleBoardInMonth> dataInTheCopiedMonthS();
    List<PreschoolerSingleBoardInMonth> dataToBeCopiedS();

    List<PreschoolerActivityInMonth> dataFromTheCopiedMonthA();
    List<PreschoolerActivityInMonth> dataInTheCopiedMonthA();
    List<PreschoolerActivityInMonth> dataToBeCopiedA();

    List<PreschoolerStayMonth> dataFromTheCopiedMonthE();
    List<PreschoolerStayMonth> dataInTheCopiedMonthE();
    List<PreschoolerStayMonth> dataToBeCopiedE();
}
