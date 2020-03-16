package pl.camp.it.model.month;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;

public class Month {
    static ArrayList<String> month =new ArrayList(Arrays.asList("STYCZEŃ","LUTY","MARZEC","KWIECIEŃ",
            "MAJ","CZERWIEC","LIPIEC","SIEPRIEŃ","WRZESIEŃ","PAŹDZIERNIK","LISTOPAD","GRUDZIEŃ"));

    static public ArrayList<String> getMonth() {
        return month;
    }
}
