package pl.camp.it.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.camp.it.model.parent.Parent;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.services.IPDFService;
import pl.camp.it.services.IPreschoolerService;
import pl.camp.it.services.impl.SendMailServiceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SimpleMailController {

    private Map<Preschooler, Map<Parent,String>> infoAfterSendMail;
    private boolean mailSending = false;

    @Autowired
    private JavaMailSender sender;
    @Autowired
    IPreschoolerService preschoolerService;
    @Autowired
    IPDFService pdfService;
    @Autowired
    SendMailServiceImpl sendMailService;

    public Map<Preschooler, Map<Parent, String>> getInfoAfterSendMail() {
        return infoAfterSendMail;
    }

    public boolean isMailSending() {
        return mailSending;
    }

    public void setMailSending(boolean mailSending) {
        this.mailSending = mailSending;
    }

    public void sendMailAttachment(Integer idPreschooler, String month){
        Path pathToFilePDF = pdfService.createTempDirectory();                                                                      //Utworzenie tymczasowego folderu
        File filePDF = pdfService.createFilePDF(pathToFilePDF);                                                                  //utworzenie pliku pdf na dysku
        pdfService.createSettlementPDF(pathToFilePDF, filePDF, idPreschooler, month);                                                      // utworzenie pdf Document
        List<Parent> listDataParentsPreschooler = sendMailService.getParentPreschoolerByIdPreschooler(idPreschooler);
        if (listDataParentsPreschooler.size()!=0) {
            for (Parent parent : listDataParentsPreschooler) {
                Map<Parent, String> mapTemp= new HashMap<>();
                mapTemp.put(parent, sendMailService.sendMailAttachment(parent.getEmail(), pathToFilePDF));
                this.infoAfterSendMail.put(preschoolerService.getPreschoolerById(idPreschooler),mapTemp);                          //przesłanie pdf -> w zależności od wybranej opcji ma się odbywać przesłanie tzn wybranym/grupie/wszystkim
            }
        } else{
            Map<Parent, String> mapTemp= new HashMap<>();
            mapTemp.put(null, "Brak danych opiekunów prawnych w bazie!");
            this.infoAfterSendMail.put(preschoolerService.getPreschoolerById(idPreschooler),mapTemp);
        }
        pdfService.deleteFilePDF(filePDF);                                                                                          //skasowanie pliku pdf na dysku
        pdfService.deleteTempDirectory(pathToFilePDF);                                                                               //usunięcie folderu tymczasowego
    }

    public void threadSendMailAttachment(List<Integer> idPreschoolerList, String month){
        this.infoAfterSendMail= new HashMap<>();
        Thread thread = new Thread(()->{
            for (int i=0; i<=idPreschoolerList.size()-1; i++) {
                sendMailAttachment(idPreschoolerList.get(i), month);
                if (i==idPreschoolerList.size()-1){
                    setMailSending(true);
                }
            }
        });
        thread.start();
    }

    public void threadSendMailAttachmentGroupOrAll(List<Integer> listIdGroup, String month){
        this.infoAfterSendMail= new HashMap<>();
        Thread thread = new Thread(()->{
            for (int i=0; i<=listIdGroup.size()-1; i++) {
                for(Preschooler preschooler : preschoolerService.getPreschoolerList(listIdGroup.get(i))) {
                    sendMailAttachment(preschooler.getId(), month);
                }
                if (i == listIdGroup.size() - 1) {
                    setMailSending(true);
                }
            }
        });
        thread.start();
    }

    public List<Integer> listIdGroupByPreschoolerGroup(List<PreschoolGroup> preschoolerGroupList){
        List<Integer> listIdGroup = new ArrayList<>();
        for (PreschoolGroup preschoolGroup : preschoolerGroupList){
            listIdGroup.add(preschoolGroup.getId());
        }
        return listIdGroup;
    }

}
