package pl.camp.it.services;

import java.io.File;
import java.nio.file.Path;

public interface IPDFService {

    String[] dataToFilePDFLoginAndPass();
    Path createTempDirectory();
    public void createSettlementPDF(Path tempDirectory, File filePDF, Integer idPreschooler, String month);
    File createFilePDF(Path pathToFIle);
    void deleteTempDirectory(Path pathToDirectory);
    void deleteFilePDF(File file);
}
