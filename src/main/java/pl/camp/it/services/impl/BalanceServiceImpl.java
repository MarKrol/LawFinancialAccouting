package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IBalanceService;
import pl.camp.it.services.IPDFService;
import pl.camp.it.services.IPreschoolGroupService;
import pl.camp.it.services.IPreschoolerService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceServiceImpl implements IBalanceService {

    @Autowired
    IPDFService pdfService;
    @Autowired
    IPreschoolGroupService preschoolGroupService;
    @Autowired
    IPreschoolerService preschoolerService;

    public class listBalance{
        private Preschooler preschooler;
        private String calculate;
        private String payment;
        private String balance;

        public Preschooler getPreschoolerBalance() {
            return preschooler;
        }

        public String getCalculateBalance() {
            return calculate;
        }

        public String getPaymentBalance() {
            return payment;
        }

        public String getBalance() {
            return balance;
        }

        public void setPreschoolerBalance(Preschooler preschooler) {
            this.preschooler = preschooler;
        }

        public void setCalculateBalance(String calculate) {
            this.calculate = calculate;
        }

        public void setPaymentBalance(String payment) {
            this.payment = payment;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }

    // --------------------------------- FIRST COMPANY ---------------------------------------------- //

    public List<listBalance> getBalanceMonth(int idGroup, String month){
        List<listBalance> listBalances = new ArrayList<>();
        List<Preschooler> preschoolerList = preschoolerService.getPreschoolerList(idGroup);

        if (preschoolerList==null){
            return new ArrayList<>();
        }

        for (Preschooler preschooler:preschoolerList){
            double calculate =0.0;
            double payment =0.0;

            calculate = pdfService.getAllMonthCalculation(preschooler.getId(),month);
            payment = pdfService.getAllPaymentCompany(preschooler.getId(),month,1);

            listBalance balance = new listBalance();

            balance.setPreschoolerBalance(preschooler);
            balance.setCalculateBalance(pdfService.getDecimalTwoPalaces(calculate));
            balance.setPaymentBalance(pdfService.getDecimalTwoPalaces(payment));
            balance.setBalance(pdfService.getDecimalTwoPalaces(payment-calculate));

            listBalances.add(balance);
        }
        return listBalances;
    }
    // ---------------------------------------------------------------------------------------- //

    // --------------------------------- SECOND COMPANY ---------------------------------------------- //

    public List<listBalance> getBalanceMonth2(int idGroup, String month){
        List<listBalance> listBalances = new ArrayList<>();
        List<Preschooler> preschoolerList = preschoolerService.getPreschoolerList(idGroup);

        if (preschoolerList==null){
            return new ArrayList<>();
        }

        for (Preschooler preschooler:preschoolerList){
            double calculate =0.0;
            double payment =0.0;

            calculate = pdfService.getAllMonthCalculation1(preschooler.getId(),month);
            payment = pdfService.getAllPaymentCompany(preschooler.getId(),month,2);

            listBalance balance = new listBalance();

            balance.setPreschoolerBalance(preschooler);
            balance.setCalculateBalance(pdfService.getDecimalTwoPalaces(calculate));
            balance.setPaymentBalance(pdfService.getDecimalTwoPalaces(payment));
            balance.setBalance(pdfService.getDecimalTwoPalaces(payment-calculate));

            listBalances.add(balance);
        }
        return listBalances;
    }

    // ---------------------------------------------------------------------------------------- //

}
