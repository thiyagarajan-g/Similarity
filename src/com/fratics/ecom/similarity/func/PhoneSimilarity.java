package com.fratics.ecom.similarity.func;

public class PhoneSimilarity extends Similarable{

    public PhoneSimilarity(String entity){
        this.entity = entity;
        preProcess();
    }

    @Override
    public boolean preProcess() {
        return true;
    }
}
