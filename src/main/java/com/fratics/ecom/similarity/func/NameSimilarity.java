package com.fratics.ecom.similarity.func;

import com.fratics.ecom.similarity.utils.bit.BitIndexGenerator;
import com.fratics.ecom.similarity.utils.shingle.NGrams;

import java.util.ArrayList;

import static com.fratics.ecom.similarity.utils.sanitize.Sanitizer.nameSanitizer;

public class NameSimilarity extends Similarable {

    public NameSimilarity(String entity) {
        this.entity = entity;
        preProcess();
    }

    public static void main(String[] args) {
        String a = "Siva Rama Krishnan";
        String b = "SivaKrish R";
        System.err.println("Names to compare --> " + a + " -VS- " + b);
        System.err.println();
        NameSimilarity x = new NameSimilarity(a);
        NameSimilarity y = new NameSimilarity(b);
        System.err.println("Score = " + x.similarTo(y));
    }

    @Override
    public boolean preProcess() {
        //Do sanitization
        String x = nameSanitizer(this.entity);
        //Generate Bi Grams
        NGrams nGrams = new NGrams();
        nGrams.generateNGrams(x, 2);
        ArrayList<String> y = nGrams.getnGrams();
        System.err.println("Bi-gram of Name = " + y.toString());
        //Populate Bit Set
        for (String s : y) {
            bitSet.set(BitIndexGenerator.generateBitSetIndex(s));
        }
        System.err.println("Name BitSet Cardinality = " + this.bitSet.cardinality());
        return true;
    }
}
