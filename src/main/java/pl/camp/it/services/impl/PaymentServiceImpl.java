package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.ICompanyDAO;
import pl.camp.it.dao.IPaymentDAO;
import pl.camp.it.model.company.Company;
import pl.camp.it.model.payment.Payment;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IPaymentService;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    IPaymentDAO paymentDAO;
    @Autowired
    ICompanyDAO companyDAO;

    @Override
    public void persistPayment(Payment payment) {
        this.paymentDAO.persistPayment(payment);
    }

    @Override
    public void addPaymentToDataBase(List<String> nameAndPayment, String date, Preschooler preschooler, Company company) {
        Payment payment = new Payment();
        payment.setQuantity(true);
        payment.setLocalDate(LocalDate.parse(date));
        payment.setName(nameAndPayment.get(0).toUpperCase());
        payment.setPayment(Double.parseDouble(nameAndPayment.get(1)));
        payment.setPreschooler(preschooler);
        payment.setCompany(company);
        payment.setNameMonth(getNameMonthByDate(date));
        this.paymentDAO.persistPayment(payment);
    }

    @Override
    public List<Payment> getListPayment(int idPreschooler, String month, int idCompany) {
        return this.paymentDAO.getListPayment(idPreschooler,month,idCompany);
    }

    @Override
    public double sumPayment(List<Payment> paymentList) {
        double sumPayment = 0.00;

        for(Payment temp: paymentList){
            sumPayment=sumPayment+temp.getPayment();
        }

        return sumPayment;
    }

    @Override
    public Payment getPaymentById(int id) {
        return this.paymentDAO.getPaymentById(id);
    }

    @Override
    public void savePaymentChange(Payment payment, String name, double pay, String date, Company company) {
        payment.setName(name);
        payment.setPayment(pay);
        payment.setLocalDate(LocalDate.parse(date));
        payment.setCompany(company);
        payment.setNameMonth(getNameMonthByDate(date));
        this.paymentDAO.persistPayment(payment);
    }

    @Override
    public void savePaymentChangeCompany(Payment payment, String name, double pay, String date, Company company) {
        List<String> namePayment = new ArrayList<>();
        namePayment.add(name);
        namePayment.add(String.valueOf(pay));
        addPaymentToDataBase(namePayment,date,payment.getPreschooler(),company);
        deletePayment(payment);
    }

    @Override
    public void deletePayment(Payment payment) {
        payment.setQuantity(false);
        this.paymentDAO.persistPayment(payment);
    }

    @Override
    public String getNameMonthByDate(String date) {
        String[] field = date.split("-");
        String month="STYCZEŃ";

        switch (field[1]){
            case "01": month="STYCZEŃ"; break;
            case "02": month="LUTY"; break;
            case "03": month="MARZEC"; break;
            case "04": month="KWIECIEŃ"; break;
            case "05": month="MAJ"; break;
            case "06": month="CZERWIEC"; break;
            case "07": month="LIPIEC"; break;
            case "08": month="SIERPIEŃ"; break;
            case "09": month="WRZESIEŃ"; break;
            case "10": month="PAŹDZIERNIK"; break;
            case "11": month="LISTOPAD"; break;
            case "12": month="GRUDZIEŃ"; break;
            default:
                month="STYCZEŃ";
                break;
        }
        return month;
    }
}
