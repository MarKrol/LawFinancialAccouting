package pl.camp.it.services.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPDFDAO;
import pl.camp.it.services.IPDFService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class PDFServiceImpl implements IPDFService {

    @Autowired
    IPDFDAO pdfdao;

    @Override
    public String[] dataToFilePDFLoginAndPass(){
        String[] data = new String[7];
        data[0] = "Data wydruku: ";
        data[1] = "LISTA KONT PRACOWNIKÓW";
        data[2] ="Poniższa lista zawiera dane identyfikujące pracownika w programie LOW FINANCIAL ACCOUNTING. " +
                "Informacje te są poufne i ze względów bezpieczeństwa należy zadbać, aby nie trafiły w niepowołane ręce." +
                "Proszę zwrócić uwagę na wielkość liter w haśle, ponieważ ma ona znaczenie.";
        data[3] = "Nazwisko i imię";
        data[4] = "Id pracownika";
        data[5] = "Login";
        data[6] = "Hasło";
        return data;
    }

    @Override
    public Path createTempDirectory() {
        return this.pdfdao.createTempDirectory();
    }

    @Override
    public File createFilePDF(Path pathToFIle) {
        return this.pdfdao.createFilePDF(pathToFIle);
    }

    @Override
    public void deleteTempDirectory(Path pathToDirectory) {
        this.pdfdao.deleteTempDirectory(pathToDirectory);
    }

    @Override
    public void deleteFilePDF(File file) {
        this.pdfdao.deleteFilePDF(file);
    }

    @Override
    public void createSettlementPDF(Path tempDirectory, File filePDF, Integer idPreschooler, String month){
        Document document = new Document();
        try{
            PdfWriter.getInstance(document, new FileOutputStream(filePDF));
            document.open();
            writeSettlementPDF(document, idPreschooler);
            document.close();
        } catch (DocumentException | FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void writeSettlementPDF(Document document, Integer idPreschooler){
        writeSettlementHeaderPDF(document, idPreschooler);
    }

    private void writeSettlementHeaderPDF(Document document, Integer idPreschooler){
        try {
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 10);
            document.add(new Paragraph("Rzliczenie miesięczne dla ĘĄĆŚĆŹŹążźćśęóÓłŁ!!!",font));
        } catch (DocumentException | IOException e){
            e.printStackTrace();
        }
    }
}
