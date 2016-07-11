package com.fratics.ecom.similarity.func;

import com.fratics.ecom.similarity.utils.shingle.NGrams;

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
