package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.ICompanyDAO;
import pl.camp.it.model.company.Company;

import java.util.List;


@Repository
public class CompanyDAOImpl implements ICompanyDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void saveChangeDataCompany(Company company) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(company);
        session.getTransaction().commit();
    }

    @Override
    public List<Company> getListCompany() {
        Session session = sessionFactory.openSession();
        List<Company> companyList = session.createQuery("FROM pl.camp.it.model.company.Company").list();
        session.close();
        return companyList;
    }

    @Override
    public Company getCompanyById(int id) {
        Session session = sessionFactory.openSession();
        Company company = session.createQuery("FROM pl.camp.it.model.company.Company WHERE id="+id, Company.class).uniqueResult();
        session.close();
        return company;
    }
}
