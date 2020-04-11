package pl.camp.it.services.impl;

import org.springframework.stereotype.Service;
import pl.camp.it.services.IPDFService;

@Service
public class PDFServiceImpl implements IPDFService {

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
}
