package com.fratics.ecom.similarity.main;

import com.fratics.ecom.similarity.func.*;

public class Main {

    public static void main(String[] args) {

        String[] s = {"dummy", "main"};
        System.err.println("Similarity Analysis");
        System.err.println("--------------------");
        System.err.println("Address Similarity:-");
        System.err.println("--------------------");
        AddressSimilarity.main(s);
        System.err.println("======================================================");
        System.err.println("Email Similarity:-");
        System.err.println("-------------------");
        EmailSimilarity.main(s);
        System.err.println("======================================================");
        System.err.println("Name Similarity:-");
        System.err.println("------------------");
        NameSimilarity.main(s);
        System.err.println("======================================================");
        System.err.println("Phone Similarity:-");
        System.err.println("------------------");
        PhoneSimilarity.main(s);
        System.err.println("======================================================");
        System.err.println("IP Similarity:-");
        System.err.println("---------------");
        IPSimilarity.main(s);
    }
}
