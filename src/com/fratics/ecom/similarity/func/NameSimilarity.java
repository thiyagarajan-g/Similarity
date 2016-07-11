package com.fratics.ecom.similarity.func;

import com.fratics.ecom.similarity.utils.bit.BitIndexGenerator;
import com.fratics.ecom.similarity.utils.shingle.NGrams;
import java.util.ArrayList;
import static com.fratics.ecom.similarity.utils.sanitize.Sanitizer.nameSanitizer;

public class NameSimilarity extends Similarable{

    public NameSimilarity(String entity){
        this.entity = entity;
        preProcess();
    }

    @Override
    public boolean preProcess(){
        //Do sanitization
        String x = nameSanitizer(this.entity);
        //Generate Bi Grams
        NGrams nGrams = new NGrams();
        nGrams.generateNGrams(x,2);
        ArrayList<String> y = nGrams.getnGrams();
        System.err.println("Bi-gram of Name = " + y.toString());
        //Populate Bit Set
        for( String s : y){
            bitSet.set(BitIndexGenerator.generateBitSetIndex(s));
        }
        System.err.println("Name BitSet Cardinality = " + this.bitSet.cardinality());
        return true;
    }

    public static void main(String[] args) {
        NameSimilarity x = new NameSimilarity("Siva Rama Krishnan");
        NameSimilarity y = new NameSimilarity("SivaKrish R");
        System.err.println ("Score = " + x.similarTo(y));
    }
}
