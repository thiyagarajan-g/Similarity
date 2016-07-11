package com.fratics.ecom.similarity.func;

import com.fratics.ecom.similarity.utils.bit.BitIndexGenerator;
import com.fratics.ecom.similarity.utils.shingle.NGrams;
import java.util.ArrayList;
import static com.fratics.ecom.similarity.utils.sanitize.Sanitizer.emailSanitizer;

public class EmailSimilarity extends Similarable{

    public EmailSimilarity(String entity){
        this.entity = entity;
        preProcess();
    }

    @Override
    public boolean preProcess() {
        //Do sanitization
        String x = emailSanitizer(this.entity);
        //Generate Bi Grams
        NGrams nGrams = new NGrams();
        nGrams.generateNGrams(x,2);
        ArrayList<String> y = nGrams.getnGrams();
        System.err.println("Bi-gram of email = " + y.toString());
        //Populate Bit Set
        for( String s : y){
            bitSet.set(BitIndexGenerator.generateBitSetIndex(s));
        }
        System.err.println("Email bitset Cardinality = " + this.bitSet.cardinality());
        return true;
    }

    public static void main(String[] args) {
        EmailSimilarity x = new EmailSimilarity("sshankar@yahoo.com");
        EmailSimilarity y = new EmailSimilarity("sivashankar@gmail.com");
        System.err.println ("Score = " + x.similarTo(y));
    }
}
