package pl.camp.it.services;

import org.springframework.stereotype.Service;
import pl.camp.it.model.company.Company;

import java.util.List;

public interface ICompanyService {
    void saveChangeDataCompany(Company company);
    List<Company> getListCompany();
    Company getCompanyById(int id);
}