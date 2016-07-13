package com.fratics.ecom.similarity.func;

import java.util.BitSet;

public abstract class Similarable {

    protected String entity;
    protected BitSet bitSet = new BitSet();

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public BitSet getBitSet() {
        return bitSet;
    }

    public void setBitSet(BitSet bitSet) {
        this.bitSet = bitSet;
    }

    public abstract boolean preProcess();

    /*
     *
     *  similarTo() compares & returns the score between the 2 similar objects.
     *
     */

    public double similarTo(Similarable o) {
        // Logic
        // a1 = vector1
        // a2 = vector2
        // minC = minCardinality (a1,a2)
        // xor_a1a2 = a1 xor a2;
        // score = 1 - (xor_a1a2 bitwise_and minC / minC)
        int minC = (o.getBitSet().cardinality() < this.bitSet.cardinality()) ? o.getBitSet().cardinality() : this.bitSet.cardinality();

        System.err.println("minc = " + minC);

        BitSet xor = (BitSet) this.bitSet.clone();
        xor.xor(o.getBitSet());

        System.err.println("xor cardinality = " + xor.cardinality());

        BitSet xor_minc = (BitSet) xor.clone();
        xor_minc.and((o.getBitSet().cardinality() < this.bitSet.cardinality()) ? o.getBitSet() : this.bitSet);

        System.err.println("xor and minC cardinality = " + xor_minc.cardinality());

        double score = 1.0 - (xor_minc.cardinality() * 1.0 / minC);

        return score;
    }

    @Override
    public String toString() {
        return "Similarable{" + ", entity='" + entity + '\'' + ", bitSet=" + bitSet + "}";
    }

}
