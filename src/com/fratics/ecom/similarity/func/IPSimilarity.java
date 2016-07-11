package com.fratics.ecom.similarity.func;

public class IPSimilarity extends Similarable{

    public IPSimilarity(String entity){
        this.entity = entity;
        preProcess();
    }

    @Override
    public boolean preProcess(){
        return true;
    }

    @Override
    public double similarTo(Similarable o){
        String [] x1 = o.getEntity().split("\\.");
        String [] x2 = this.entity.split("\\.");
        int diff = 0;
        double score = 0;
        for(int i = 0; i < x1.length; i++) if(!x1[i].equalsIgnoreCase(x2[i])) diff++;
        System.err.println("IP Difference " + diff);
        score = 1.0 - ( diff * 1.0 )/x1.length;
        return score;
    }
    
    public static void main(String[] args) {
        String a = "10.12.34.345";
        String b = "10.11.34.345";
        System.err.println("IPs to compare --> " + a + " -VS- " + b);
        System.err.println();
        IPSimilarity x = new IPSimilarity(a);
        IPSimilarity y = new IPSimilarity(b);
        System.err.println ("Score = " + x.similarTo(y));
    }
}
