package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPreschoolerStayMonthDAO;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.model.stay.PreschoolerStayMonth;
import pl.camp.it.model.stay.Stay;
import pl.camp.it.services.IPreschoolerStayMonthService;

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
}
