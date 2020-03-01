package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPreschoolerActivityInMonthDAO;
import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.services.IPreschoolerActivityInMonthService;

@Service
public class PreschoolerActivityInMonthServiceImpl implements IPreschoolerActivityInMonthService {
    @Autowired
    IPreschoolerActivityInMonthDAO preschoolerActivityInMonthDAO;

    @Override
    public void persistPreschoolerActivityInMonth(PreschoolerActivityInMonth preschoolerActivityInMonth) {
        this.preschoolerActivityInMonthDAO.persistPreschoolerActivityInMonth(preschoolerActivityInMonth);
    }
}
