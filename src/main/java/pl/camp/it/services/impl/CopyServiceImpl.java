package pl.camp.it.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPreschoolerActivityInMonthDAO;
import pl.camp.it.dao.IPreschoolerFullBoardInMonthDAO;
import pl.camp.it.dao.IPreschoolerSingleBoardInMonthDAO;
import pl.camp.it.dao.IPreschoolerStayMonthDAO;
import pl.camp.it.model.activities.PreschoolerActivityInMonth;
import pl.camp.it.model.meals.PreschoolerFullBoardInMonth;
import pl.camp.it.model.meals.PreschoolerSingleBoardInMonth;
import pl.camp.it.model.preschooler.Preschooler;
import pl.camp.it.model.stay.PreschoolerStayMonth;
import pl.camp.it.services.ICopyService;
import pl.camp.it.services.IPreschoolerService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CopyServiceImpl implements ICopyService {

    static List<PreschoolerFullBoardInMonth> dataFromTheCopiedMonth;
    static List<PreschoolerFullBoardInMonth> dataInTheCopiedMonth;
    static List<PreschoolerFullBoardInMonth> dataToBeCopied;

    static List<PreschoolerSingleBoardInMonth> dataFromTheCopiedMonthS;
    static List<PreschoolerSingleBoardInMonth> dataInTheCopiedMonthS;
    static List<PreschoolerSingleBoardInMonth> dataToBeCopiedS;

    static List<PreschoolerActivityInMonth> dataFromTheCopiedMonthA;
    static List<PreschoolerActivityInMonth> dataInTheCopiedMonthA;
    static List<PreschoolerActivityInMonth> dataToBeCopiedA;

    static List<PreschoolerStayMonth> dataFromTheCopiedMonthE;
    static List<PreschoolerStayMonth> dataInTheCopiedMonthE;
    static List<PreschoolerStayMonth> dataToBeCopiedE;

    static String howDataCopied;

    @Autowired
    IPreschoolerFullBoardInMonthDAO preschoolerFullBoardInMonthDAO;
    @Autowired
    IPreschoolerActivityInMonthDAO preschoolerActivityInMonthDAO;
    @Autowired
    IPreschoolerStayMonthDAO preschoolerStayMonthDAO;
    @Autowired
    IPreschoolerSingleBoardInMonthDAO preschoolerSingleBoardInMonthDAO;
    @Autowired
    IPreschoolerService preschoolerService;

    @Override
    public void copyDataToNextMonth(int idGroup, String monthToMonth){
        String[] covertData = convertMonthToMonth(monthToMonth);
        if (covertData[2].equals("F")){
            dataFromTheCopiedMonth = listFullMealMonthPreschoolerFromMonthToCopy(idGroup, covertData);
            dataInTheCopiedMonth = listFullMealMonthPreschoolerInTheCopiedMonth(idGroup,covertData);
            dataToBeCopied = listFullMealMonthPreschoolerDataToCopy(dataFromTheCopiedMonth, dataInTheCopiedMonth);
            howDataCopied=covertData[2];

            if (dataToBeCopied.size()!=0){
                for (PreschoolerFullBoardInMonth preschoolerFullBoardInMonth: dataToBeCopied){
                    PreschoolerFullBoardInMonth preschoolerFullBoardInMonth1 = new PreschoolerFullBoardInMonth();
                    preschoolerFullBoardInMonth1.setMonth(covertData[1]);
                    preschoolerFullBoardInMonth1.setQuantity(preschoolerFullBoardInMonth.isQuantity());
                    preschoolerFullBoardInMonth1.setNameDiet(preschoolerFullBoardInMonth.getNameDiet());
                    preschoolerFullBoardInMonth1.setVAT(preschoolerFullBoardInMonth.getVAT());
                    preschoolerFullBoardInMonth1.setPriceNetTea(preschoolerFullBoardInMonth.getPriceNetTea());
                    preschoolerFullBoardInMonth1.setPriceNetDiner(preschoolerFullBoardInMonth.getPriceNetDiner());
                    preschoolerFullBoardInMonth1.setPriceNetSB(preschoolerFullBoardInMonth.getPriceNetSB());
                    preschoolerFullBoardInMonth1.setPriceNetFB(preschoolerFullBoardInMonth1.getPriceNetFB());
                    preschoolerFullBoardInMonth1.setPreschooler(preschoolerFullBoardInMonth.getPreschooler());
                    preschoolerFullBoardInMonth1.setNumberTea(preschoolerFullBoardInMonth.getNumberTea());
                    preschoolerFullBoardInMonth1.setNumberDinner(preschoolerFullBoardInMonth.getNumberDinner());
                    preschoolerFullBoardInMonth1.setNumberFirstBreakfast(preschoolerFullBoardInMonth.getNumberFirstBreakfast());
                    preschoolerFullBoardInMonth1.setNumberSecondBreakfast(preschoolerFullBoardInMonth.getNumberSecondBreakfast());
                    preschoolerFullBoardInMonthDAO.persistPreschoolerFullBoardInMonth(preschoolerFullBoardInMonth1);
                }

            }
        }
        if (covertData[2].equals("S")){
            dataFromTheCopiedMonthS = listSingleMealMonthPreschoolerFromMonthToCopy(idGroup, covertData);
            dataInTheCopiedMonthS = listSingleMealMonthPreschoolerInTheCopiedMonth(idGroup,covertData);
            dataToBeCopiedS = listSingleMealMonthPreschoolerDataToCopy(dataFromTheCopiedMonthS, dataInTheCopiedMonthS);
            howDataCopied=covertData[2];

            if (dataToBeCopiedS.size()!=0){
                for (PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth: dataToBeCopiedS){
                    PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth1 = new PreschoolerSingleBoardInMonth();
                    preschoolerSingleBoardInMonth1.setMonth(covertData[1]);
                    preschoolerSingleBoardInMonth1.setName(preschoolerSingleBoardInMonth.getName());
                    preschoolerSingleBoardInMonth1.setQuantity(preschoolerSingleBoardInMonth.isQuantity());
                    preschoolerSingleBoardInMonth1.setPreschooler(preschoolerSingleBoardInMonth.getPreschooler());
                    preschoolerSingleBoardInMonth1.setVAT(preschoolerSingleBoardInMonth.getVAT());
                    preschoolerSingleBoardInMonth1.setPrice(preschoolerSingleBoardInMonth.getPrice());
                    preschoolerSingleBoardInMonth1.setNumber(preschoolerSingleBoardInMonth.getNumber());
                    preschoolerSingleBoardInMonthDAO.persistPreschoolerSingleBoardInMonthDAO(preschoolerSingleBoardInMonth1);
                }

            }
        }
        if (covertData[2].equals("A")){
            dataFromTheCopiedMonthA = listActivityMonthPreschoolerFromMonthToCopy(idGroup, covertData);
            dataInTheCopiedMonthA = listActivityMonthPreschoolerInTheCopiedMonth(idGroup, covertData);
            dataToBeCopiedA = listActivityMonthPreschoolerDataToCopy(dataFromTheCopiedMonthA, dataInTheCopiedMonthA);
            howDataCopied=covertData[2];

            if (dataToBeCopiedA.size()!=0){
                for (PreschoolerActivityInMonth preschoolerActivityInMonth: dataToBeCopiedA){
                    PreschoolerActivityInMonth preschoolerActivityInMonth1 = new PreschoolerActivityInMonth();
                    preschoolerActivityInMonth1.setQuantity(preschoolerActivityInMonth.isQuantity());
                    preschoolerActivityInMonth1.setVAT(preschoolerActivityInMonth.getVAT());
                    preschoolerActivityInMonth1.setPriceNet(preschoolerActivityInMonth.getPriceNet());
                    preschoolerActivityInMonth1.setMonth(covertData[1]);
                    preschoolerActivityInMonth1.setPreschooler(preschoolerActivityInMonth.getPreschooler());
                    preschoolerActivityInMonth1.setNameAcivity(preschoolerActivityInMonth.getNameAcivity());
                    preschoolerActivityInMonthDAO.persistPreschoolerActivityInMonth(preschoolerActivityInMonth1);
                }
            }

        }
        if (covertData[2].equals("E")){
            dataFromTheCopiedMonthE = listStayMonthPreschoolerFromMonthToCopy(idGroup, covertData);
            dataInTheCopiedMonthE = listStayMonthPreschoolerInTheCopiedMonth(idGroup, covertData);
            dataToBeCopiedE = listStayMonthPreschoolerDataToCopy(dataFromTheCopiedMonthE, dataInTheCopiedMonthE);
            howDataCopied=covertData[2];

            if (dataToBeCopiedE.size()!=0){
                for (PreschoolerStayMonth preschoolerStayMonth: dataToBeCopiedE){
                    PreschoolerStayMonth preschoolerStayMonth1 = new PreschoolerStayMonth();
                    preschoolerStayMonth1.setQuantity(preschoolerStayMonth.isQuantity());
                    preschoolerStayMonth1.setVAT(preschoolerStayMonth.getVAT());
                    preschoolerStayMonth1.setPriceNet(preschoolerStayMonth.getPriceNet());
                    preschoolerStayMonth1.setNumber(preschoolerStayMonth.getNumber());
                    preschoolerStayMonth1.setName(preschoolerStayMonth.getName());
                    preschoolerStayMonth1.setPreschooler(preschoolerStayMonth.getPreschooler());
                    preschoolerStayMonth1.setMonth(covertData[1]);
                    preschoolerStayMonthDAO.persistPreschoolerStayMonth(preschoolerStayMonth1);
                }
            }
        }

    }


    // ---------------- FULL MEAL -------------------------- //

    @Override
    public List<PreschoolerFullBoardInMonth> dataFromTheCopiedMonth(){ return dataFromTheCopiedMonth; }

    @Override
    public List<PreschoolerFullBoardInMonth> dataInTheCopiedMonth(){ return dataInTheCopiedMonth; }

    @Override
    public List<PreschoolerFullBoardInMonth> dataToBeCopied(){ return dataToBeCopied; }


    // ---------------------- SINGLE MEAL ----------------------- //

    @Override
    public List<PreschoolerSingleBoardInMonth> dataFromTheCopiedMonthS() { return dataFromTheCopiedMonthS; }

    @Override
    public List<PreschoolerSingleBoardInMonth> dataInTheCopiedMonthS() { return dataInTheCopiedMonthS; }

    @Override public List<PreschoolerSingleBoardInMonth> dataToBeCopiedS() { return dataToBeCopiedS; }


    // -------------------- ACTIVITY -------------------------//

    @Override
    public List<PreschoolerActivityInMonth> dataFromTheCopiedMonthA() { return dataFromTheCopiedMonthA; }

    @Override
    public List<PreschoolerActivityInMonth> dataInTheCopiedMonthA() { return dataInTheCopiedMonthA; }

    @Override
    public List<PreschoolerActivityInMonth> dataToBeCopiedA() { return dataToBeCopiedA; }

    // -------------------- Stay -------------------------//

    @Override
    public List<PreschoolerStayMonth> dataFromTheCopiedMonthE() { return dataFromTheCopiedMonthE; }

    @Override
    public List<PreschoolerStayMonth> dataInTheCopiedMonthE() { return dataInTheCopiedMonthE; }

    @Override
    public List<PreschoolerStayMonth> dataToBeCopiedE() { return dataToBeCopiedE; }

    // -------------- HOW DATA COPIED -------------------------//

    @Override
    public String getHowDataCopied(){ return howDataCopied; }


    //------------------------- FULL MEAL -------------------------//

    private List<PreschoolerFullBoardInMonth> listFullMealMonthPreschoolerFromMonthToCopy(int idGroup, String[] covertMonth){
        List<Preschooler> preschoolerList = preschoolerService.getPreschoolerList(idGroup);
        List<PreschoolerFullBoardInMonth> preschoolerFullBoardInMonthList= new ArrayList<>();

        for(Preschooler preschooler: preschoolerList){
            if (preschoolerFullBoardInMonthDAO.getPreschoolerFullBoardInMonth(preschooler.getId(),covertMonth[0])!=null){
                preschoolerFullBoardInMonthList.add
                        (preschoolerFullBoardInMonthDAO.getPreschoolerFullBoardInMonth(preschooler.getId(),covertMonth[0]));
            }
        }
        return preschoolerFullBoardInMonthList;
    }

    private List<PreschoolerFullBoardInMonth> listFullMealMonthPreschoolerInTheCopiedMonth(int idGroup, String[] covertMonth){
        List<Preschooler> preschoolerList = preschoolerService.getPreschoolerList(idGroup);
        List<PreschoolerFullBoardInMonth> preschoolerFullBoardInMonthList= new ArrayList<>();

        for(Preschooler preschooler: preschoolerList){
            if (preschoolerFullBoardInMonthDAO.getPreschoolerFullBoardInMonth(preschooler.getId(),covertMonth[1])!=null){
                preschoolerFullBoardInMonthList.add
                        (preschoolerFullBoardInMonthDAO.getPreschoolerFullBoardInMonth(preschooler.getId(),covertMonth[1]));
            }
        }
        return preschoolerFullBoardInMonthList;
    }


    private List<PreschoolerFullBoardInMonth> listFullMealMonthPreschoolerDataToCopy
            (List<PreschoolerFullBoardInMonth> dataFromTheCopiedMonth, List<PreschoolerFullBoardInMonth> dataInTheCopiedMonth){

        List<PreschoolerFullBoardInMonth> preschoolerFullBoardInMonths = new ArrayList<>();

        boolean dataCopiedInTwoList=false;

        for(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth : dataFromTheCopiedMonth){
            dataCopiedInTwoList=false;
            for(PreschoolerFullBoardInMonth preschoolerFullBoardInMonth1: dataInTheCopiedMonth){
                if (preschoolerFullBoardInMonth.getPreschooler().getId()==preschoolerFullBoardInMonth1.getPreschooler().getId()) {
                    dataCopiedInTwoList = true;
                    break;
                }
            }
            if (!dataCopiedInTwoList){
                preschoolerFullBoardInMonths.add(preschoolerFullBoardInMonth);
            }
        }
        return preschoolerFullBoardInMonths;
    }


    // ------------------ SINGLE MEAL ---------------------//

    private List<PreschoolerSingleBoardInMonth> listSingleMealMonthPreschoolerFromMonthToCopy(int idGroup, String[] covertMonth){
        List<Preschooler> preschoolerList = preschoolerService.getPreschoolerList(idGroup);
        List<PreschoolerSingleBoardInMonth> preschoolerSingleBoardInMonthList= new ArrayList<>();

        for(Preschooler preschooler: preschoolerList){
            List<PreschoolerSingleBoardInMonth> listSingleMelaByIdPreschoolerAndMonth=
                    preschoolerSingleBoardInMonthDAO.listPreschoolerSingleMealMonthInDB(preschooler.getId(),covertMonth[0]);
            if (listSingleMelaByIdPreschoolerAndMonth.size()!=0){
                for(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth: listSingleMelaByIdPreschoolerAndMonth)
                preschoolerSingleBoardInMonthList.add(preschoolerSingleBoardInMonth);
            }
        }
        return preschoolerSingleBoardInMonthList;
    }

    private List<PreschoolerSingleBoardInMonth> listSingleMealMonthPreschoolerInTheCopiedMonth(int idGroup, String[] covertMonth){
        List<Preschooler> preschoolerList = preschoolerService.getPreschoolerList(idGroup);
        List<PreschoolerSingleBoardInMonth> preschoolerSingleBoardInMonthList= new ArrayList<>();

        for(Preschooler preschooler: preschoolerList){
            List<PreschoolerSingleBoardInMonth> listSingleMelaByIdPreschoolerAndMonth=
                    preschoolerSingleBoardInMonthDAO.listPreschoolerSingleMealMonthInDB(preschooler.getId(),covertMonth[1]);
            if (listSingleMelaByIdPreschoolerAndMonth.size()!=0){
                for(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth: listSingleMelaByIdPreschoolerAndMonth)
                    preschoolerSingleBoardInMonthList.add(preschoolerSingleBoardInMonth);
            }
        }
        return preschoolerSingleBoardInMonthList;
    }

    private List<PreschoolerSingleBoardInMonth> listSingleMealMonthPreschoolerDataToCopy
            (List<PreschoolerSingleBoardInMonth> dataFromTheCopiedMonth, List<PreschoolerSingleBoardInMonth> dataInTheCopiedMonth){

        List<PreschoolerSingleBoardInMonth> preschoolerSingleBoardInMonthList = new ArrayList<>();

        boolean dataCopiedInTwoList=false;

        for(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth : dataFromTheCopiedMonth){
            dataCopiedInTwoList=false;
            for(PreschoolerSingleBoardInMonth preschoolerSingleBoardInMonth1: dataInTheCopiedMonth){
                if (preschoolerSingleBoardInMonth.getPreschooler().getId()==preschoolerSingleBoardInMonth1.getPreschooler().getId()
                && preschoolerSingleBoardInMonth.getName().equals(preschoolerSingleBoardInMonth1.getName())) {
                    dataCopiedInTwoList = true;
                    break;
                }
            }
            if (!dataCopiedInTwoList){
                preschoolerSingleBoardInMonthList.add(preschoolerSingleBoardInMonth);
            }
        }
        return preschoolerSingleBoardInMonthList;
    }


    // ------------------------ ACTIVITY -----------------------------//

    private List<PreschoolerActivityInMonth> listActivityMonthPreschoolerFromMonthToCopy(int idGroup, String[] covertMonth){
        List<Preschooler> preschoolerList = preschoolerService.getPreschoolerList(idGroup);
        List<PreschoolerActivityInMonth> preschoolerActivityInMonthList= new ArrayList<>();

        for(Preschooler preschooler: preschoolerList){
            List<PreschoolerActivityInMonth> listActivityByIdPreschoolerAndMonth=
                    preschoolerActivityInMonthDAO.listPreschoolerActivityInMonth(preschooler.getId(),covertMonth[0]);
            if (listActivityByIdPreschoolerAndMonth.size()!=0){
                for(PreschoolerActivityInMonth preschoolerActivityInMonth: listActivityByIdPreschoolerAndMonth)
                    preschoolerActivityInMonthList.add(preschoolerActivityInMonth);
            }
        }
        return preschoolerActivityInMonthList;
    }

    private List<PreschoolerActivityInMonth> listActivityMonthPreschoolerInTheCopiedMonth(int idGroup, String[] covertMonth){
        List<Preschooler> preschoolerList = preschoolerService.getPreschoolerList(idGroup);
        List<PreschoolerActivityInMonth> preschoolerActivityInMonthList= new ArrayList<>();

        for(Preschooler preschooler: preschoolerList){
            List<PreschoolerActivityInMonth> listActivityByIdPreschoolerAndMonth=
                    preschoolerActivityInMonthDAO.listPreschoolerActivityInMonth(preschooler.getId(),covertMonth[1]);
            if (listActivityByIdPreschoolerAndMonth.size()!=0){
                for(PreschoolerActivityInMonth preschoolerActivityInMonth: listActivityByIdPreschoolerAndMonth)
                    preschoolerActivityInMonthList.add(preschoolerActivityInMonth);
            }
        }
        return preschoolerActivityInMonthList;
    }

    private List<PreschoolerActivityInMonth> listActivityMonthPreschoolerDataToCopy
            (List<PreschoolerActivityInMonth> dataFromTheCopiedMonth, List<PreschoolerActivityInMonth> dataInTheCopiedMonth){

        List<PreschoolerActivityInMonth> preschoolerActivityInMonthList = new ArrayList<>();

        boolean dataCopiedInTwoList=false;

        for(PreschoolerActivityInMonth preschoolerActivityInMonth : dataFromTheCopiedMonth){
            dataCopiedInTwoList=false;
            for(PreschoolerActivityInMonth preschoolerActivityInMonth1: dataInTheCopiedMonth){
                if (preschoolerActivityInMonth.getPreschooler().getId()==preschoolerActivityInMonth1.getPreschooler().getId()
                        && preschoolerActivityInMonth.getNameAcivity().equals(preschoolerActivityInMonth1.getNameAcivity())) {
                    dataCopiedInTwoList = true;
                    break;
                }
            }
            if (!dataCopiedInTwoList){
                preschoolerActivityInMonthList.add(preschoolerActivityInMonth);
            }
        }
        return preschoolerActivityInMonthList;
    }


    // ------------------------ STAY -----------------------------//

    private List<PreschoolerStayMonth> listStayMonthPreschoolerFromMonthToCopy(int idGroup, String[] covertMonth){
        List<Preschooler> preschoolerList = preschoolerService.getPreschoolerList(idGroup);
        List<PreschoolerStayMonth> preschoolerStayInMonthList= new ArrayList<>();

        for(Preschooler preschooler: preschoolerList){
            List<PreschoolerStayMonth> listStayByIdPreschoolerAndMonth=
                   preschoolerStayMonthDAO.listPreschoolerStayMonth(preschooler.getId(),covertMonth[0]);
            if (listStayByIdPreschoolerAndMonth.size()!=0){
                for(PreschoolerStayMonth preschoolerStayInMonth: listStayByIdPreschoolerAndMonth)
                    preschoolerStayInMonthList.add(preschoolerStayInMonth);
            }
        }
        return preschoolerStayInMonthList;
    }

    private List<PreschoolerStayMonth> listStayMonthPreschoolerInTheCopiedMonth(int idGroup, String[] covertMonth){
        List<Preschooler> preschoolerList = preschoolerService.getPreschoolerList(idGroup);
        List<PreschoolerStayMonth> preschoolerStayInMonthList= new ArrayList<>();

        for(Preschooler preschooler: preschoolerList){
            List<PreschoolerStayMonth> listStayByIdPreschoolerAndMonth=
                    preschoolerStayMonthDAO.listPreschoolerStayMonth(preschooler.getId(),covertMonth[1]);
            if (listStayByIdPreschoolerAndMonth.size()!=0){
                for(PreschoolerStayMonth preschoolerStayInMonth: listStayByIdPreschoolerAndMonth)
                    preschoolerStayInMonthList.add(preschoolerStayInMonth);
            }
        }
        return preschoolerStayInMonthList;
    }

    private List<PreschoolerStayMonth> listStayMonthPreschoolerDataToCopy
            (List<PreschoolerStayMonth> dataFromTheCopiedMonth, List<PreschoolerStayMonth> dataInTheCopiedMonth){

        List<PreschoolerStayMonth> preschoolerStayInMonthList = new ArrayList<>();

        boolean dataCopiedInTwoList=false;

        for(PreschoolerStayMonth preschoolerStayInMonth : dataFromTheCopiedMonth){
            dataCopiedInTwoList=false;
            for(PreschoolerStayMonth preschoolerStayInMonth1: dataInTheCopiedMonth){
                if (preschoolerStayInMonth.getPreschooler().getId()==preschoolerStayInMonth1.getPreschooler().getId()
                        && preschoolerStayInMonth.getName().equals(preschoolerStayInMonth1.getName())) {
                    dataCopiedInTwoList = true;
                    break;
                }
            }
            if (!dataCopiedInTwoList){
                preschoolerStayInMonthList.add(preschoolerStayInMonth);
            }
        }
        return preschoolerStayInMonthList;
    }



    private String[] convertMonthToMonth(String monthToMonth){
        String[] tempField = new String[3];
        int a, b;

        for (int i=0; i <= monthToMonth.length()-1; i++) {
            a=i+1; b=i+2;
            if (monthToMonth.charAt(i)=='<' && monthToMonth.charAt(a)=='F' && monthToMonth.charAt(b)=='>') {
                String[] field = monthToMonth.split("<F>");
                tempField[0]=field[0];
                tempField[1]=field[1];
                tempField[2]=Character.toString(monthToMonth.charAt(a));
                break;
            }
            if (monthToMonth.charAt(i)=='<' && monthToMonth.charAt(i+1)=='S' && monthToMonth.charAt(i+2)=='>') {
                String[] field = monthToMonth.split("<S>");
                tempField[0]=field[0];
                tempField[1]=field[1];
                tempField[2]=Character.toString(monthToMonth.charAt(a));
                break;
            }
            if (monthToMonth.charAt(i)=='<' && monthToMonth.charAt(i+1)=='A' && monthToMonth.charAt(i+2)=='>') {
                String[] field = monthToMonth.split("<A>");
                tempField[0]=field[0];
                tempField[1]=field[1];
                tempField[2]=Character.toString(monthToMonth.charAt(a));
                break;
            }
            if (monthToMonth.charAt(i)=='<' && monthToMonth.charAt(i+1)=='E' && monthToMonth.charAt(i+2)=='>') {
                String[] field = monthToMonth.split("<E>");
                tempField[0]=field[0];
                tempField[1]=field[1];
                tempField[2]=Character.toString(monthToMonth.charAt(a));
                break;
            }
        }
        return tempField;
    }
}
