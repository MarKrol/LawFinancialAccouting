package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPaymentDAO;
import pl.camp.it.model.payment.Payment;

import java.util.List;

@Repository
public class PaymentDAOImpl implements IPaymentDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistPayment(Payment payment) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(payment);
        session.getTransaction().commit();
    }

    @Override
    public List<Payment> getListPayment(int idPreschooler, String month, int idCompany) {
        Session session=sessionFactory.openSession();
        List<Payment> paymentList=session.createQuery
                ("FROM pl.camp.it.model.payment.Payment WHERE preschoolerId="+idPreschooler+
                        " and nameMonth='"+month.toUpperCase()+"'"+" and companyId="+idCompany+
                        " and quantity="+true+" ORDER BY localDate ASC", Payment.class).list();
        session.close();
        return paymentList;
    }

    @Override
    public Payment getPaymentById(int id) {
        Session session=sessionFactory.openSession();
        Payment payment = session.createQuery("FROM pl.camp.it.model.payment.Payment WHERE id="+id+
                "and quantity="+true, Payment.class).uniqueResult();
        session.close();
        return payment;
    }
}
