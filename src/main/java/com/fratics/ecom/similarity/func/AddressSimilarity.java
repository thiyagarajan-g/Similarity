package com.fratics.ecom.similarity.func;

import com.fratics.ecom.similarity.utils.bit.BitIndexGenerator;
import com.fratics.ecom.similarity.utils.shingle.NGrams;

import java.util.ArrayList;

import static com.fratics.ecom.similarity.utils.sanitize.Sanitizer.addressSanitizer;

public class AddressSimilarity extends Similarable {

    public AddressSimilarity(String entity) {
        this.entity = entity;
        preProcess();
    }

    public static void main(String[] args) {
        String a = "h.no 23, Mahatma gandi road, Indira nagar, Near Bus Stop, Bangalore-78";
        String b = "house no 23, M Gandhi Road, Nehru Colony, Indiraa nagar, Bangalore";
        System.err.println("Addresses to compare --> " + a + " -VS- " + b);
        System.err.println();
        AddressSimilarity x = new AddressSimilarity(a);
        AddressSimilarity y = new AddressSimilarity(b);
        System.err.println("Score = " + x.similarTo(y));
    }

    @Override
    public boolean preProcess() {
        //Do sanitization
        String x = addressSanitizer(this.entity);
        //Generate Bi Grams
        NGrams nGrams = new NGrams();
        nGrams.generateNGrams(x, 2);
        ArrayList<String> y = nGrams.getnGrams();
        System.err.println("Bi-gram of address = " + y.toString());
        //Populate Bit Set
        for (String s : y) {
            bitSet.set(BitIndexGenerator.generateBitSetIndex(s));
        }
        System.err.println("Address BitSet Cardinality = " + this.bitSet.cardinality());
        return true;
    }
}
