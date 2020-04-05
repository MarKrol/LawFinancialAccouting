package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.ICompanyDAO;
import pl.camp.it.model.company.Company;
import pl.camp.it.services.ICompanyService;

import java.util.List;

@Service
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    ICompanyDAO companyDAO;

    @Override
    public void saveChangeDataCompany(Company company) {
        this.companyDAO.saveChangeDataCompany(company);
    }

    @Override
    public void saveChangeDataCompany(Company company, Company companyEdit) {
        company.setAccountNumber(companyEdit.getAccountNumber());
        company.setAddress(companyEdit.getAddress().toUpperCase());
        company.setAddressWWWW(companyEdit.getAddressWWWW());
        company.setCity(companyEdit.getCity().toUpperCase());
        company.setNameCompany(companyEdit.getNameCompany().toUpperCase());
        company.setEmail(companyEdit.getEmail());
        company.setNameBank(companyEdit.getNameBank().toUpperCase());
        company.setNIP(companyEdit.getNIP());
        company.setPhone(companyEdit.getPhone());
        company.setZip(companyEdit.getZip());
        this.companyDAO.saveChangeDataCompany(company);
    }

    @Override
    public List<Company> getListCompany() {
        return this.companyDAO.getListCompany();
    }

    @Override
    public Company getCompanyById(int id) {
        return this.companyDAO.getCompanyById(id);
    }
}
