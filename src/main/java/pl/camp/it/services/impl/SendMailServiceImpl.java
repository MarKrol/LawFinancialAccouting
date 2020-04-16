package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.impl.ParentDAOImpl;
import pl.camp.it.model.parent.Parent;
import pl.camp.it.services.ISendMailService;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;

@Service
public class SendMailServiceImpl implements ISendMailService {

    private static final String addressFrom = "krol.martin@gmail.com";
    private static final String subjectMail = "Rozliczenie miesięczne - Karmelkowy Zakątek";

    @Autowired
    protected JavaMailSender sender;
    @Autowired
    ParentDAOImpl parentDAO;

    @Override
    public String sendMailAttachment(String addressTo, Path pathToFilePDF){
        MimeMessage message = sender.createMimeMessage();
        try {
            InternetAddress email = new InternetAddress(addressTo);
            email.validate();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setFrom(this.addressFrom);
                helper.setTo(addressTo);
                helper.setSubject(this.subjectMail);
                helper.setText(getTextMail(),true);
                helper.addInline("identifier1234",getLogo());
                helper.addAttachment("document.pdf", new FileSystemResource
                                                                (new File(pathToFilePDF+"\\document.pdf")));
                //sender.send(message);
                return "Wysyłanie zakończone sukcesem!";
            } catch (MessagingException e){
                e.printStackTrace();
                return "Wystąpił błąd podczas przsyłania. "+e.getMessage();
            }
        }catch (AddressException e){
            e.printStackTrace();
            return "Błedny adres email adresata. "+e.getMessage();
        }
    }

    private String getTextMail() {
        String text = "<html><body><div style=\"width: 650px; margin-left: auto; margin-right: auto; text-allign: justify;" +
                "font-family: Arial, Helvetica, sans-serif; font-size: 14px\">" +
                "<B>Szanowana Pani/Szanowny Panie,</B><br><br>" +
                "w załączniku przesyłamy rozliczenie miesięczne za pobyt dziecka w przedszkolu." +
                "Prosimy o zapozananie się z dokumentem, a w przypadku pojawiających się pytań " +
                "pozostajemy do Państwa dyspozycji. <br><br> Kontakt z nami znajduje się na stronie:" +
                "<br><br><a style=\"text-decoration: none; color: blue\" href=\"http://www.karmelkowyzakatek.pl\">" +
                "http://www.karmelkowyzakatek.pl</a><br><br> " +
                "Powyższ wiadomość została  wygenerowana automatycznie. Prosimy nie odpowiwadać na nią.<br><br>" +
                "Z poważaniem <br><br>" +
                "<img src='cid:identifier1234'>" +
                "</body></html></div>";
        return text;
    }

    private FileSystemResource getLogo(){
        FileSystemResource res = new FileSystemResource(new File(".\\src\\main\\resources\\logo.png"));
        return res;
    }

    @Override
    public List<Parent> getParentPreschoolerByIdPreschooler(Integer idPreschooler) {
        List<Parent> parentList = this.parentDAO.getListParent(idPreschooler);
        return parentList;
    }
}
