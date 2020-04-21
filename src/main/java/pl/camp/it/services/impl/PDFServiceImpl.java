package pl.camp.it.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources_zh_CN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.*;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.model.stay.PreschoolerStayMonth;
import pl.camp.it.services.*;

import javax.print.Doc;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class PDFServiceImpl implements IPDFService {

    @Autowired
    IPDFDAO pdfdao;
    @Autowired
    IPreschoolerDAO preschoolerDAO;
    @Autowired
    ICompanyDAO companyDAO;
    @Autowired
    IPreschoolerSingleBoardInMonthDAO preschoolerSingleBoardInMonthDAO;
    @Autowired
    IPreschoolerFullBoardInMonthDAO preschoolerFullBoardInMonthDAO;
    @Autowired
    IPreschoolerStayMonthDAO preschoolerStayMonthDAO;
    @Autowired
    IPreschoolerActivityInMonthDAO preschoolerActivityInMonthDAO;

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
        Document document = new Document(PageSize.A4);
        try{
            PdfWriter.getInstance(document, new FileOutputStream(filePDF));
            document.open();
            writeSettlementPDF(document, idPreschooler, month);
            document.close();
        } catch (DocumentException | FileNotFoundException e){
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------------------------------------------------------------//

    private void writeSettlementPDF(Document document, Integer idPreschooler, String month){
        writeSettlementHeaderPDF(document, month);
        writeSettlementPreschoolerDataPDF(document, idPreschooler);
        writeSettlementMealsAndStay(document, idPreschooler, month);
        writeSettlementAllPay(document, idPreschooler, month);
        writeSettlementResumeInfo(document);
    }

    // -------------------------------------------------------------------------------------------------------//

    private void writeSettlementHeaderPDF(Document document, String month){
        try {
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 12);

            PdfPTable insideTable = new PdfPTable(2);
            insideTable.setWidths(new int[]{110, 75});
            PdfPCell cell1in = new PdfPCell(new Paragraph("Rozliczenie za miesiąc:",font));
            PdfPCell cell2in = new PdfPCell(new Paragraph(month,font));
            PdfPCell cell3in = new PdfPCell(new Paragraph("Data wystawienia:",font));
            PdfPCell cell4in = new PdfPCell(new Paragraph(LocalDate.now().toString(), font));
            PdfPCell cell5in = new PdfPCell(new Paragraph("Miejsce wystawienia:",font));
            PdfPCell cell6in = new PdfPCell(new Paragraph("Kraków",font));

            cell1in.setBorder(Rectangle.NO_BORDER);
            cell2in.setBorder(Rectangle.NO_BORDER);
            cell3in.setBorder(Rectangle.NO_BORDER);
            cell4in.setBorder(Rectangle.NO_BORDER);
            cell5in.setBorder(Rectangle.NO_BORDER);
            cell6in.setBorder(Rectangle.NO_BORDER);

            BaseColor myColor0 = new BaseColor(217, 217, 217);
            BaseColor myColor1 = new BaseColor(51, 214, 255);
            BaseColor myColor2 = new BaseColor(255, 218, 179);
            BaseColor myColor3 = new BaseColor(204, 238, 255);
            BaseColor myColor4 = new BaseColor(255, 243, 230);

            cell1in.setBackgroundColor(myColor1);
            cell2in.setBackgroundColor(myColor2);
            cell3in.setBackgroundColor(myColor3);
            cell4in.setBackgroundColor(myColor4);
            cell5in.setBackgroundColor(myColor1);
            cell6in.setBackgroundColor(myColor2);

            insideTable.addCell(cell1in);
            insideTable.addCell(cell2in);
            insideTable.addCell(cell3in);
            insideTable.addCell(cell4in);
            insideTable.addCell(cell5in);
            insideTable.addCell(cell6in);

            PdfPTable table = new PdfPTable(2);
            table.setWidths(new int[]{100,400});

            Image logo = Image.getInstance(".\\src\\main\\resources\\logo.png");
            logo.scalePercent(29f);

            PdfPCell cell1 = new PdfPCell(logo);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setBackgroundColor(myColor0);
            PdfPCell cell2 = new PdfPCell(insideTable);
            cell2.setBorder(Rectangle.NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);

            document.add(table);

        } catch (DocumentException | IOException e){
            e.printStackTrace();
        }
    }

    public void writeSettlementPreschoolerDataPDF(Document document, Integer idPreschooler){
        try {
            Preschooler preschooler = preschoolerDAO.getPreschoolerById(idPreschooler);
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 10);
            Font fontBold = new Font(helvetica, 10, Font.BOLD);

            document.add(new Paragraph(" "));

            Paragraph nameSurname = new Paragraph("NAZWISKO I IMIĘ: ",fontBold);
            Paragraph preschoolerNameSurname = new Paragraph(preschooler.getSurname()+" "+preschooler.getName(),font);
            Paragraph nameGroup = new Paragraph("GRUPA: ",fontBold);
            Paragraph preschoolerNameGroup = new Paragraph(preschooler.getPreschoolGroup().getNameGroup(),font);

            PdfPTable table = new PdfPTable(2);
            table.setWidths(new int[]{130,400});

            PdfPCell cell1 = new PdfPCell(nameSurname);
            PdfPCell cell2 = new PdfPCell(preschoolerNameSurname);
            PdfPCell cell3 = new PdfPCell(nameGroup);
            PdfPCell cell4 = new PdfPCell(preschoolerNameGroup);

            cell1.setBorder(Rectangle.NO_BORDER);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell3.setBorder(Rectangle.NO_BORDER);
            cell4.setBorder(Rectangle.NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);

            document.add(table);
        } catch (DocumentException | IOException e){
            e.printStackTrace();
        }
    }

    private void writeSettlementMealsAndStay(Document document, int idPreschooler, String month){

        try{
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 8);
            Font fontBold = new Font(helvetica, 8, Font.BOLD);

            BaseColor myColor0 = new BaseColor(153, 221, 255);
            BaseColor myColor1 = new BaseColor(204, 238, 255);

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            table.setWidths(new int[]{200, 40, 55, 75, 35, 75});

            PdfPCell cell = new PdfPCell(new Paragraph("ROZLICZENIE MIESIĘCZNE: "+companyDAO.getCompanyById(0).getNameCompany(), fontBold));
            cell.setColspan(6);
            cell.setBackgroundColor(myColor0);

            PdfPCell cell1 = new PdfPCell(new Paragraph("NAZWA",fontBold));
            PdfPCell cell2 = new PdfPCell(new Paragraph("ILOŚĆ",fontBold));
            PdfPCell cell3 = new PdfPCell(new Paragraph("CENA NETTO [ZŁ]",fontBold));
            PdfPCell cell4 = new PdfPCell(new Paragraph("WARTOŚĆ NETTO [ZŁ]",fontBold));
            PdfPCell cell5 = new PdfPCell(new Paragraph("VAT [%]",fontBold));
            PdfPCell cell6 = new PdfPCell(new Paragraph("WARTOŚĆ BRUTTO [ZŁ]",fontBold));

            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell6.setHorizontalAlignment(Element.ALIGN_CENTER);

            cell1.setBackgroundColor(myColor1);
            cell2.setBackgroundColor(myColor1);
            cell3.setBackgroundColor(myColor1);
            cell4.setBackgroundColor(myColor1);
            cell5.setBackgroundColor(myColor1);
            cell6.setBackgroundColor(myColor1);

            table.addCell(cell);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);

            document.add(table);

            addToPDFFullMeal(document, idPreschooler, month);
            addToPDFSingleMeal(document, idPreschooler, month);
            addToPDFStay(document, idPreschooler, month);

        }catch (DocumentException | IOException e){
            e.printStackTrace();
        }
    }

    private void addToPDFFullMeal(Document document, int idPreschooler, String month){
        try{
            PreschoolerFullBoardInMonth preschoolerFullBoardInMonth =
                                 preschoolerFullBoardInMonthDAO.getPreschoolerFullBoardInMonth(idPreschooler, month);

            if (preschoolerFullBoardInMonth !=null) {

                BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                Font font = new Font(helvetica, 8);
                Font fontBold = new Font(helvetica, 8, Font.BOLD);

                PdfPTable table = new PdfPTable(6);
                table.setWidths(new int[]{200, 40, 55, 75, 35, 75});

                PdfPCell cell11 = new PdfPCell(new Paragraph("PIERWSZE ŚNIADANIE", font));
                PdfPCell cell12 = new PdfPCell(new Paragraph(String.valueOf(preschoolerFullBoardInMonth.getNumberFirstBreakfast()),font));
                PdfPCell cell13 = new PdfPCell(new Paragraph(decimalTwoPalaces(preschoolerFullBoardInMonth.getPriceNetFB()),font));
                PdfPCell cell14 = new PdfPCell(new Paragraph(decimalTwoPalaces
                        (preschoolerFullBoardInMonth.getNumberFirstBreakfast()*preschoolerFullBoardInMonth.getPriceNetFB()),font));
                PdfPCell cell15 = new PdfPCell(new Paragraph(String.valueOf(preschoolerFullBoardInMonth.getVAT()),font));
                PdfPCell cell16 = new PdfPCell(new Paragraph(decimalTwoPalaces(toPay
                        (preschoolerFullBoardInMonth.getNumberFirstBreakfast(),preschoolerFullBoardInMonth.getVAT(),preschoolerFullBoardInMonth.getPriceNetFB())),font));

                PdfPCell cell21 = new PdfPCell(new Paragraph("DRUGIE ŚNIADANIE", font));
                PdfPCell cell22 = new PdfPCell(new Paragraph(String.valueOf(preschoolerFullBoardInMonth.getNumberSecondBreakfast()),font));
                PdfPCell cell23 = new PdfPCell(new Paragraph(decimalTwoPalaces(preschoolerFullBoardInMonth.getPriceNetSB()),font));
                PdfPCell cell24 = new PdfPCell(new Paragraph(decimalTwoPalaces
                        (preschoolerFullBoardInMonth.getNumberSecondBreakfast()*preschoolerFullBoardInMonth.getPriceNetSB()),font));
                PdfPCell cell25 = new PdfPCell(new Paragraph(String.valueOf(preschoolerFullBoardInMonth.getVAT()),font));
                PdfPCell cell26 = new PdfPCell(new Paragraph(decimalTwoPalaces(toPay
                        (preschoolerFullBoardInMonth.getNumberSecondBreakfast(),preschoolerFullBoardInMonth.getVAT(),preschoolerFullBoardInMonth.getPriceNetSB())),font));

                PdfPCell cell31 = new PdfPCell(new Paragraph("OBIAD", font));
                PdfPCell cell32 = new PdfPCell(new Paragraph(String.valueOf(preschoolerFullBoardInMonth.getNumberDinner()),font));
                PdfPCell cell33 = new PdfPCell(new Paragraph(decimalTwoPalaces(preschoolerFullBoardInMonth.getPriceNetDiner()),font));
                PdfPCell cell34 = new PdfPCell(new Paragraph(decimalTwoPalaces
                        (preschoolerFullBoardInMonth.getNumberDinner()*preschoolerFullBoardInMonth.getPriceNetDiner()),font));
                PdfPCell cell35 = new PdfPCell(new Paragraph(String.valueOf(preschoolerFullBoardInMonth.getVAT()),font));
                PdfPCell cell36 = new PdfPCell(new Paragraph(decimalTwoPalaces(toPay
                        (preschoolerFullBoardInMonth.getNumberDinner(),preschoolerFullBoardInMonth.getVAT(),preschoolerFullBoardInMonth.getPriceNetDiner())),font));

                PdfPCell cell41 = new PdfPCell(new Paragraph("PODWIECZOREK", font));
                PdfPCell cell42 = new PdfPCell(new Paragraph(String.valueOf(preschoolerFullBoardInMonth.getNumberTea()),font));
                PdfPCell cell43 = new PdfPCell(new Paragraph(decimalTwoPalaces(preschoolerFullBoardInMonth.getPriceNetTea()),font));
                PdfPCell cell44 = new PdfPCell(new Paragraph(decimalTwoPalaces
                        (preschoolerFullBoardInMonth.getNumberTea()*preschoolerFullBoardInMonth.getPriceNetTea()),font));
                PdfPCell cell45 = new PdfPCell(new Paragraph(String.valueOf(preschoolerFullBoardInMonth.getVAT()),font));
                PdfPCell cell46 = new PdfPCell(new Paragraph(decimalTwoPalaces(toPay
                        (preschoolerFullBoardInMonth.getNumberTea(),preschoolerFullBoardInMonth.getVAT(), preschoolerFullBoardInMonth.getPriceNetTea())),font));

                cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell42.setHorizontalAlignment(Element.ALIGN_CENTER);

                cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell43.setHorizontalAlignment(Element.ALIGN_CENTER);

                cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell34.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell44.setHorizontalAlignment(Element.ALIGN_CENTER);

                cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell35.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell45.setHorizontalAlignment(Element.ALIGN_CENTER);

                cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell36.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell46.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cell11);
                table.addCell(cell12);
                table.addCell(cell13);
                table.addCell(cell14);
                table.addCell(cell15);
                table.addCell(cell16);
                table.addCell(cell21);
                table.addCell(cell22);
                table.addCell(cell23);
                table.addCell(cell24);
                table.addCell(cell25);
                table.addCell(cell26);
                table.addCell(cell31);
                table.addCell(cell32);
                table.addCell(cell33);
                table.addCell(cell34);
                table.addCell(cell35);
                table.addCell(cell36);
                table.addCell(cell41);
                table.addCell(cell42);
                table.addCell(cell43);
                table.addCell(cell44);
                table.addCell(cell45);
                table.addCell(cell46);

                PdfPCell cellSumText = new PdfPCell(new Paragraph("Do zapłaty:",fontBold));
                cellSumText.setColspan(5);
                cellSumText.setHorizontalAlignment(Element.ALIGN_RIGHT);

                double toPay=toPay(preschoolerFullBoardInMonth.getNumberFirstBreakfast(),preschoolerFullBoardInMonth.getVAT(),preschoolerFullBoardInMonth.getPriceNetFB())+
                        toPay(preschoolerFullBoardInMonth.getNumberSecondBreakfast(),preschoolerFullBoardInMonth.getVAT(),preschoolerFullBoardInMonth.getPriceNetSB())+
                        toPay(preschoolerFullBoardInMonth.getNumberDinner(),preschoolerFullBoardInMonth.getVAT(),preschoolerFullBoardInMonth.getPriceNetDiner())+
                        toPay(preschoolerFullBoardInMonth.getNumberTea(),preschoolerFullBoardInMonth.getVAT(),preschoolerFullBoardInMonth.getPriceNetTea());

                PdfPCell cellSumPay = new PdfPCell(new Paragraph(decimalTwoPalaces(toPay),fontBold));
                cellSumPay.setHorizontalAlignment(Element.ALIGN_CENTER);

                BaseColor myColor1 = new BaseColor(204, 238, 255);
                cellSumPay.setBackgroundColor(myColor1);
                cellSumText.setBackgroundColor(myColor1);

                table.addCell(cellSumText);
                table.addCell(cellSumPay);

                document.add(table);
            }

        }catch (DocumentException | IOException e){
            e.printStackTrace();
        }
    }

    private void addToPDFSingleMeal(Document document, int idPreschooler, String month){
        try{
            List<PreschoolerSingleBoardInMonth> preschoolerSingleBoardInMonthList =
                    preschoolerSingleBoardInMonthDAO.listPreschoolerSingleMealMonthInDB(idPreschooler, month);
            if (preschoolerSingleBoardInMonthList.size()!=0) {
                double toPay=0.0;
                BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                Font font = new Font(helvetica, 8);
                Font fontBold = new Font(helvetica, 8, Font.BOLD);

                PdfPTable table = new PdfPTable(6);
                table.setWidths(new int[]{200, 40, 55, 75, 35, 75});

                for (PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth: preschoolerSingleBoardInMonthList){
                    PdfPCell cell11 = new PdfPCell(new Paragraph(preschoolerSingleBoardInMonth.getName(),font));
                    PdfPCell cell12 = new PdfPCell(new Paragraph(String.valueOf(preschoolerSingleBoardInMonth.getNumber()),font));
                    PdfPCell cell13 = new PdfPCell(new Paragraph(decimalTwoPalaces(preschoolerSingleBoardInMonth.getPrice()),font));
                    PdfPCell cell14 = new PdfPCell(new Paragraph(decimalTwoPalaces
                            (preschoolerSingleBoardInMonth.getNumber()*preschoolerSingleBoardInMonth.getPrice()),font));
                    PdfPCell cell15 = new PdfPCell(new Paragraph(String.valueOf(preschoolerSingleBoardInMonth.getVAT()),font));
                    PdfPCell cell16 = new PdfPCell(new Paragraph(decimalTwoPalaces(toPay
                            (preschoolerSingleBoardInMonth.getNumber(), preschoolerSingleBoardInMonth.getVAT(), preschoolerSingleBoardInMonth.getPrice())),font));

                    toPay=toPay+toPay(preschoolerSingleBoardInMonth.getNumber(), preschoolerSingleBoardInMonth.getVAT(), preschoolerSingleBoardInMonth.getPrice());

                    cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell16.setHorizontalAlignment(Element.ALIGN_CENTER);

                    table.addCell(cell11);
                    table.addCell(cell12);
                    table.addCell(cell13);
                    table.addCell(cell14);
                    table.addCell(cell15);
                    table.addCell(cell16);
                }

                PdfPCell cellSumText = new PdfPCell(new Paragraph("Do zapłaty:",fontBold));
                cellSumText.setColspan(5);
                cellSumText.setHorizontalAlignment(Element.ALIGN_RIGHT);

                PdfPCell cellSumPay = new PdfPCell(new Paragraph(decimalTwoPalaces(toPay),fontBold));
                cellSumPay.setHorizontalAlignment(Element.ALIGN_CENTER);

                BaseColor myColor1 = new BaseColor(204, 238, 255);
                cellSumPay.setBackgroundColor(myColor1);
                cellSumText.setBackgroundColor(myColor1);

                table.addCell(cellSumText);
                table.addCell(cellSumPay);

                document.add(table);
            }
        }catch (DocumentException | IOException e){
            e.printStackTrace();
        }
    }

    private void addToPDFStay(Document document, int idPreschooler, String month){
        try{
            List<PreschoolerStayMonth> preschoolerStayMonthList = preschoolerStayMonthDAO.listPreschoolerStayMonth(idPreschooler, month);
            if (preschoolerStayMonthList.size()!=0) {
                double toPay=0.0;
                BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                Font font = new Font(helvetica, 8);
                Font fontBold = new Font(helvetica, 8, Font.BOLD);

                PdfPTable table = new PdfPTable(6);
                table.setWidths(new int[]{200, 40, 55, 75, 35, 75});

                for (PreschoolerStayMonth preschoolerStayMonth:preschoolerStayMonthList){
                    PdfPCell cell11 = new PdfPCell(new Paragraph(preschoolerStayMonth.getName(),font));
                    PdfPCell cell12 = new PdfPCell(new Paragraph(String.valueOf(preschoolerStayMonth.getNumber()),font));
                    PdfPCell cell13 = new PdfPCell(new Paragraph(decimalTwoPalaces(preschoolerStayMonth.getPriceNet()),font));
                    PdfPCell cell14 = new PdfPCell(new Paragraph(decimalTwoPalaces
                            (preschoolerStayMonth.getNumber()*preschoolerStayMonth.getPriceNet()),font));
                    PdfPCell cell15 = new PdfPCell(new Paragraph(String.valueOf(preschoolerStayMonth.getVAT()),font));
                    PdfPCell cell16 = new PdfPCell(new Paragraph(decimalTwoPalaces(toPay
                            (preschoolerStayMonth.getNumber(), preschoolerStayMonth.getVAT(), preschoolerStayMonth.getPriceNet())),font));

                    toPay=toPay+toPay(preschoolerStayMonth.getNumber(), preschoolerStayMonth.getVAT(), preschoolerStayMonth.getPriceNet());

                    cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell16.setHorizontalAlignment(Element.ALIGN_CENTER);

                    table.addCell(cell11);
                    table.addCell(cell12);
                    table.addCell(cell13);
                    table.addCell(cell14);
                    table.addCell(cell15);
                    table.addCell(cell16);
                }

                PdfPCell cellSumText = new PdfPCell(new Paragraph("Do zapłaty:",fontBold));
                cellSumText.setColspan(5);
                cellSumText.setHorizontalAlignment(Element.ALIGN_RIGHT);

                PdfPCell cellSumPay = new PdfPCell(new Paragraph(decimalTwoPalaces(toPay),fontBold));
                cellSumPay.setHorizontalAlignment(Element.ALIGN_CENTER);

                BaseColor myColor1 = new BaseColor(204, 238, 255);
                cellSumPay.setBackgroundColor(myColor1);
                cellSumText.setBackgroundColor(myColor1);

                table.addCell(cellSumText);
                table.addCell(cellSumPay);

                document.add(table);
            }
        }catch (DocumentException | IOException e){
            e.printStackTrace();
        }
    }

    private void writeSettlementAllPay(Document document, int idPreschooler, String month){
        try {
            double toPay=0.0;
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font fontBold = new Font(helvetica, 8, Font.BOLD);

            PdfPTable table = new PdfPTable(6);
            table.setWidths(new int[]{200, 40, 55, 75, 35, 75});

            PdfPCell cellSumText = new PdfPCell(new Paragraph("Razem do zapłaty:", fontBold));
            cellSumText.setColspan(5);
            cellSumText.setHorizontalAlignment(Element.ALIGN_RIGHT);

            if (preschoolerFullBoardInMonthDAO.getPreschoolerFullBoardInMonth(idPreschooler, month)!=null ||
            preschoolerSingleBoardInMonthDAO.listPreschoolerSingleMealMonthInDB(idPreschooler, month).size()!=0 ||
            preschoolerStayMonthDAO.listPreschoolerStayMonth(idPreschooler, month).size()!=0){
                toPay=allToPayFM(idPreschooler, month);
                toPay=toPay+allToPaySM(idPreschooler,month);
                toPay=toPay+allToPayStay(idPreschooler, month);
            } else {
                toPay=0.0;
            }

            PdfPCell cellSumPay = new PdfPCell(new Paragraph(decimalTwoPalaces(toPay), fontBold));
            cellSumPay.setHorizontalAlignment(Element.ALIGN_CENTER);

            BaseColor myColor0 = new BaseColor(153, 221, 255);
            cellSumPay.setBackgroundColor(myColor0);
            cellSumText.setBackgroundColor(myColor0);

            table.addCell(cellSumText);
            table.addCell(cellSumPay);

            document.add(table);
        }catch(DocumentException | IOException e){
            e.printStackTrace();
        }
    }

    private void writeSettlementResumeInfo(Document document){
        try{
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font fontBold = new Font(helvetica, 8, Font.BOLD);

            String info = "Naliczoną powyżej kwotę należy wpłacić do 5 dnia każdego miesiąca, na poniższe konto "+
                    companyDAO.getCompanyById(0).getNameCompany()+": ";

            PdfPTable table = new PdfPTable(1);
            PdfPCell cell1 = new PdfPCell(new Paragraph(info,fontBold));
            PdfPCell cell2 = new PdfPCell(new Paragraph(companyDAO.getCompanyById(0).getNameBank()+": "+
                    companyDAO.getCompanyById(0).getAccountNumber(),fontBold));

            cell1.setBorder(Rectangle.NO_BORDER);
            cell2.setBorder(Rectangle.NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);

            document.add(table);

        }catch (DocumentException | IOException e){
            e.printStackTrace();
        }
    }

    private String decimalTwoPalaces(double value){
        DecimalFormat df = new DecimalFormat("#####0.00");
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();

        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        return df.format(value);
    }

    private double toPay(int number, int Vat, double priceNet){
        double x = number*priceNet*(1+Double.valueOf(Vat)/100);
        return x;
    }

    private double allToPayFM(int idPreschooler, String month){
        double toPayTemp=0.0;
        PreschoolerFullBoardInMonth pfmbim= preschoolerFullBoardInMonthDAO.getPreschoolerFullBoardInMonth(idPreschooler, month);
        toPayTemp=toPayTemp+pfmbim.getNumberFirstBreakfast()*pfmbim.getPriceNetFB()*(1+Double.valueOf(pfmbim.getVAT())/100);
        toPayTemp=toPayTemp+pfmbim.getNumberSecondBreakfast()*pfmbim.getPriceNetSB()*(1+Double.valueOf(pfmbim.getVAT())/100);
        toPayTemp=toPayTemp+pfmbim.getNumberDinner()*pfmbim.getPriceNetDiner()*(1+Double.valueOf(pfmbim.getVAT())/100);
        toPayTemp=toPayTemp+pfmbim.getNumberTea()*pfmbim.getPriceNetTea()*(1+Double.valueOf(pfmbim.getVAT())/100);
        return toPayTemp;
    }

    private double allToPaySM(int idPreschooler, String month){
        double toPayTemp=0.0;
        List<PreschoolerSingleBoardInMonth> psmbim =
                            preschoolerSingleBoardInMonthDAO.listPreschoolerSingleMealMonthInDB(idPreschooler, month);
        for (PreschoolerSingleBoardInMonth temp: psmbim){
            toPayTemp=toPayTemp+toPay(temp.getNumber(), temp.getVAT(),temp.getPrice());
        }
        return toPayTemp;
    }

    private double allToPayStay(int idPreschooler, String month){
        double toPayTemp=0.0;
        List<PreschoolerStayMonth> psm =
                preschoolerStayMonthDAO.listPreschoolerStayMonth(idPreschooler, month);
        for (PreschoolerStayMonth temp: psm){
            toPayTemp=toPayTemp+toPay(temp.getNumber(), temp.getVAT(),temp.getPriceNet());
        }
        return toPayTemp;
    }
}
