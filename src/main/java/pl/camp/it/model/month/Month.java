package pl.camp.it.model.month;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;

public class Month {
    static ArrayList<String> month =new ArrayList(Arrays.asList("STYCZEŃ","LUTY","MARZEC","KWIECIEŃ",
            "MAJ","CZERWIEC","LIPIEC","SIEPRIEŃ","WRZESIEŃ","PAŹDZIERNIK","LISTOPAD","GRUDZIEŃ"));

    static ArrayList<String> month_copy1 =new ArrayList(Arrays.asList("WRZESIEŃ","PAŹDZIERNIK","LISTOPAD","GRUDZIEŃ",
            "STYCZEŃ","LUTY","MARZEC","KWIECIEŃ", "MAJ","CZERWIEC","LIPIEC"));

    static ArrayList<String> month_copy2 =new ArrayList(Arrays.asList("PAŹDZIERNIK","LISTOPAD","GRUDZIEŃ",
            "STYCZEŃ","LUTY","MARZEC","KWIECIEŃ", "MAJ","CZERWIEC","LIPIEC", "SIEPRIEŃ"));

    static public ArrayList<String> getMonth() {
        return month;
    }

    static public ArrayList<String> getMonth_copy1() {
        return month_copy1;
    }

    static public ArrayList<String> getMonth_copy2() {
        return month_copy2;
    }
}
