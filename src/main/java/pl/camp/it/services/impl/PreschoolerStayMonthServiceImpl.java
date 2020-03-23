package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPreschoolerStayMonthDAO;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.model.stay.PreschoolerStayMonth;
import pl.camp.it.model.stay.Stay;
import pl.camp.it.services.IPreschoolerStayMonthService;

import java.util.List;

@Service
public class PreschoolerStayMonthServiceImpl implements IPreschoolerStayMonthService {

    @Autowired
    IPreschoolerStayMonthDAO preschoolerStayMonthDAO;

    @Override
    public boolean isPreschoolerStayMonthInDB(int idPreschooler, String month, String nameStay) {
        return this.preschoolerStayMonthDAO.isPreschoolerStayMonthInDB(idPreschooler, month, nameStay);
    }

    @Override
    public void addStayMonthToDB(Stay stay, PreschoolerStayMonth preschoolerStayMonth, String month, Preschooler preschooler) {
        preschoolerStayMonth.setMonth(month);
        preschoolerStayMonth.setPreschooler(preschooler);
        preschoolerStayMonth.setName(stay.getName());
        preschoolerStayMonth.setVAT(stay.getVAT());
        preschoolerStayMonth.setPriceNet(stay.getPriceNet());
        preschoolerStayMonth.setQuantity(true);

        this.preschoolerStayMonthDAO.persistPreschoolerStayMonth(preschoolerStayMonth);
    }

    @Override
    public PreschoolerStayMonth preschoolerStayMonth(int idPreschooler, String month, String nameStay) {
        return this.preschoolerStayMonthDAO.preschoolerStayMonth(idPreschooler, month,nameStay);
    }

    @Override
    public void saveChangeStayMonth(PreschoolerStayMonth preschoolerStayMonth, PreschoolerStayMonth preschoolerStayMonthEdit) {
        preschoolerStayMonth.setNumber(preschoolerStayMonthEdit.getNumber());
        this.preschoolerStayMonthDAO.persistPreschoolerStayMonth(preschoolerStayMonth);
    }

    @Override
    public List<PreschoolerStayMonth> listPreschoolerStayMonth(int idPreschooler, String month) {
        return this.preschoolerStayMonthDAO.listPreschoolerStayMonth(idPreschooler,month);
    }

    @Override
    public double stayMonthToPay(List<PreschoolerStayMonth> preschoolerStayInMonthList) {
        double toPay=0.00;

        for(PreschoolerStayMonth preschoolerStayMonth: preschoolerStayInMonthList){
            toPay=toPay+(preschoolerStayMonth.getNumber()*preschoolerStayMonth.getPriceNet()*
                    (1+preschoolerStayMonth.getVAT()*0.01));
        }

        return toPay;
    }

}
