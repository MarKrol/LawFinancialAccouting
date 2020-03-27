package pl.camp.it.dao;

import pl.camp.it.model.company.Company;

import java.util.List;

public interface ICompanyDAO {

    void saveChangeDataCompany(Company company);
    List<Company> getListCompany();
    Company getCompanyById(int id);
}
