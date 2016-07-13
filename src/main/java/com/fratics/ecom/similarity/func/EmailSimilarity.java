package com.fratics.ecom.similarity.func;

import com.fratics.ecom.similarity.utils.bit.BitIndexGenerator;
import com.fratics.ecom.similarity.utils.shingle.NGrams;

import java.util.ArrayList;

import static com.fratics.ecom.similarity.utils.sanitize.Sanitizer.emailSanitizer;

public class EmailSimilarity extends Similarable {

    public EmailSimilarity(String entity) {
        this.entity = entity;
        preProcess();
    }

    public static void main(String[] args) {

        String a = "sshankar@yahoo.com";
        String b = "sivashankar@gmail.com";
        System.err.println("Emails to compare --> " + a + " -VS- " + b);
        System.err.println();
        EmailSimilarity x = new EmailSimilarity(a);
        EmailSimilarity y = new EmailSimilarity(b);
        System.err.println("Score = " + x.similarTo(y));
    }

    @Override
    public boolean preProcess() {
        //Do sanitization
        String x = emailSanitizer(this.entity);
        //Generate Bi Grams
        NGrams nGrams = new NGrams();
        nGrams.generateNGrams(x, 2);
        ArrayList<String> y = nGrams.getnGrams();
        System.err.println("Bi-gram of email = " + y.toString());
        //Populate Bit Set
        for (String s : y) {
            bitSet.set(BitIndexGenerator.generateBitSetIndex(s));
        }
        System.err.println("Email bitset Cardinality = " + this.bitSet.cardinality());
        return true;
    }
}
