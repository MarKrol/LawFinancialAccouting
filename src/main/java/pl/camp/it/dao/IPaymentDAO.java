package pl.camp.it.dao;

import pl.camp.it.model.payment.Payment;

import java.util.List;

public interface IPaymentDAO {
    void persistPayment(Payment payment);
    List<Payment> getListPayment(int idPreschooler, String month, int idCompany);
    Payment getPaymentById(int id);
}
