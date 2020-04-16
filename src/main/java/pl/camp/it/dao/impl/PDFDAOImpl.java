package pl.camp.it.dao.impl;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPDFDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class PDFDAOImpl implements IPDFDAO {

    @Override
    public Path createTempDirectory() {
        try{
            return Files.createTempDirectory("LFA-java");
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public File createFilePDF(Path pathToFIle){
        return new File(pathToFIle+"\\document.pdf");
    }

    @Override
    public void deleteTempDirectory(Path pathToDirectory) {
        try {
            FileUtils.deleteDirectory(pathToDirectory.toFile());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFilePDF(File file) {
        file.delete();
    }
}
