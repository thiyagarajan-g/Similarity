package com.fratics.ecom.similarity.utils.shingle;

import java.util.ArrayList;

//Got the code from stackoverflow & modified to remove tail grams.
//http://stackoverflow.com/questions/22441276/how-to-find-n-grams-of-a-word-in-java

public class NGrams {

    private ArrayList<String> nGrams = new ArrayList<String>();

    public void generateNGrams(String str, int n) {

        if(str.length() < n){
            throw new IllegalArgumentException("String " + str + " length is less then NGram lenght ::" + n);
        }

        if (str.length() == n) {
            nGrams.add(str);
            return;
        }

        int counter = 0;
        String gram = "";
        while (counter < n) {
            gram += str.charAt(counter);
            counter++;
        }
        nGrams.add(gram);
        generateNGrams(str.substring(1), n);
    }

    public void printNGrams() {
        for (String str : nGrams) {
            System.out.println(str);
        }
    }

    public ArrayList<String> getnGrams(){
        return nGrams;
    }

    public void clear(){
        nGrams.clear();
    }

    public static void main(String[] args) {
        NGrams ng = new NGrams();
        ng.generateNGrams("helloworld", 3);
        ng.printNGrams();
        ng.clear();
        System.out.println("========================");
        ng.generateNGrams("helloworld", 2);
        ng.printNGrams();
    }
}
