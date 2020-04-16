package pl.camp.it.dao;

import java.io.File;
import java.nio.file.Path;

public interface IPDFDAO {
    Path createTempDirectory();
    File createFilePDF(Path pathToFIle);
    void deleteTempDirectory(Path pathToDirectory);
    void deleteFilePDF(File file);
}
