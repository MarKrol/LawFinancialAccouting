package pl.camp.it.services;

import pl.camp.it.model.company.Company;
import pl.camp.it.model.payment.Payment;
import pl.camp.it.model.preschooler.Preschooler;

import java.util.List;

public interface IPaymentService {
    void persistPayment(Payment payment);
    String getNameMonthByDate(String date);
    void addPaymentToDataBase(List<String> nameAndPayment, String date, Preschooler preschooler, Company company);
    List<Payment> getListPayment(int idPreschooler, String month, int idCompany);
    double sumPayment(List<Payment> paymentList);
    Payment getPaymentById(int id);
    void savePaymentChange(Payment payment, String name, double pay, String date, Company company);
    void deletePayment(Payment payment);
    void savePaymentChangeCompany(Payment payment, String name, double pay, String date, Company company);

}
