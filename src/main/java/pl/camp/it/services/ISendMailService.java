package pl.camp.it.services;

import pl.camp.it.model.parent.Parent;

import java.nio.file.Path;
import java.util.List;

public interface ISendMailService {
    String sendMailAttachment(String addressTo, Path pathToFilePDF);
    List<Parent> getParentPreschoolerByIdPreschooler(Integer idPreschooler);
}
