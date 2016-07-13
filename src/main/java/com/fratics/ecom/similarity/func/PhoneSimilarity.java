package com.fratics.ecom.similarity.func;

public class PhoneSimilarity extends Similarable {

    public PhoneSimilarity(String entity) {
        this.entity = entity;
        preProcess();
    }

    public static void main(String[] args) {
        String a = "9886147185";
        String b = "9883142185";
        System.err.println("Phones to compare --> " + a + " -VS- " + b);
        System.err.println();
        PhoneSimilarity x = new PhoneSimilarity(a);
        PhoneSimilarity y = new PhoneSimilarity(b);
        System.err.println("Score = " + x.similarTo(y));
    }

    @Override
    public boolean preProcess() {
        return true;
    }

    @Override
    public double similarTo(Similarable o) {
        char[] x1 = o.getEntity().toCharArray();
        char[] x2 = this.entity.toCharArray();
        int diff = 0;
        double score = 0;
        for (int i = 0; i < x1.length; i++) if (x1[i] != x2[i]) diff++;
        System.err.println("Phone Number Difference " + diff);
        score = 1.0 - (diff * 1.0) / x1.length;
        return score;
    }
}
