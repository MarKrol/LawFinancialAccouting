package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPreschoolerSingleBoardInMonthDAO;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.meals.SingleBoardPrice;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IPreschoolerSingleBoardInMonthService;

import java.util.List;

@Service
public class PreschoolerSingleBoardInMonthServiceImpl implements IPreschoolerSingleBoardInMonthService {
    @Autowired
    IPreschoolerSingleBoardInMonthDAO preschoolerSingleBoardInMonthDAO;

    @Override
    public void persistPreschoolerSingleBoardInMonth(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth) {
        this.preschoolerSingleBoardInMonthDAO.persistPreschoolerSingleBoardInMonthDAO(preschoolerSingleBoardInMonth);
    }

    @Override
    public boolean isPreschoolerSingleMealMonthInDB(int idPreschooler, String month, String name) {
        return this.preschoolerSingleBoardInMonthDAO.isPreschoolerSingleMealMonthInDB(idPreschooler,month, name);
    }

    @Override
    public void saveSingleMealMonthPreschoolerInDB(Preschooler preschooler, String month, SingleBoardPrice singleBoardPrice,
                                                   PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth) {

        preschoolerSingleBoardInMonth.setPreschooler(preschooler);
        preschoolerSingleBoardInMonth.setMonth(month);
        preschoolerSingleBoardInMonth.setQuantity(true);
        preschoolerSingleBoardInMonth.setName(singleBoardPrice.getName());
        preschoolerSingleBoardInMonth.setVAT(singleBoardPrice.getVAT());
        preschoolerSingleBoardInMonth.setPrice(singleBoardPrice.getPriceNet());

        this.preschoolerSingleBoardInMonthDAO.persistPreschoolerSingleBoardInMonthDAO(preschoolerSingleBoardInMonth);
    }

    @Override
    public PreschoolerSingleBoardInMonth preschoolerSingleMealMonthInDB(int idPreschooler, String month, String name) {
        return this.preschoolerSingleBoardInMonthDAO.preschoolerSingleMealMonthInDB(idPreschooler, month,name);
    }

    @Override
    public void saveSingleMealAfterChange(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth,
                                          PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonthEdit) {
        preschoolerSingleBoardInMonth.setNumber(preschoolerSingleBoardInMonthEdit.getNumber());
        this.preschoolerSingleBoardInMonthDAO.persistPreschoolerSingleBoardInMonthDAO(preschoolerSingleBoardInMonth);

    }

    @Override
    public List<PreschoolerSingleBoardInMonth> listPreschoolerSingleMealMonthInDB(int idPreschooler, String month) {
        return this.preschoolerSingleBoardInMonthDAO.listPreschoolerSingleMealMonthInDB(idPreschooler,month);
    }

    @Override
    public double singleMealMonthToPay(List<PreschoolerSingleBoardInMonth> preschoolerSingleBoardInMonthList) {
        double toPay=0.00;

        for(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth: preschoolerSingleBoardInMonthList){
            toPay=toPay+(preschoolerSingleBoardInMonth.getNumber()*preschoolerSingleBoardInMonth.getPrice()*
                    (1+preschoolerSingleBoardInMonth.getVAT()*0.01));
        }

        return toPay;
    }
}
