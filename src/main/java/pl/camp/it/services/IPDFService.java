package pl.camp.it.services;

import com.itextpdf.text.Document;
import pl.camp.it.model.employee.Employee;

import java.io.File;
import java.nio.file.Path;

public interface IPDFService {

    String[] dataToFilePDFLoginAndPass();
    Path createTempDirectory();
    void createSettlementPDF(Path tempDirectory, File filePDF, Integer idPreschooler, String month);
    File createFilePDF(Path pathToFIle);
    void deleteTempDirectory(Path pathToDirectory);
    void deleteFilePDF(File file);
    void setEmployee(Employee employee);
    void writeSettlementToPrint(Document document, Integer idPreschooler, String month);
    String getDecimalTwoPalaces(double value);
    double getAllMonthCalculation(int idPreschooler, String month);
    double getAllPaymentCompany(int idPreschooler, String month, int idCompany);
    double getAllMonthCalculation1(int idPreschooler, String month);
}
