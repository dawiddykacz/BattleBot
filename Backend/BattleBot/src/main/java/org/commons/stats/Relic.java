package org.commons.stats;

public class Relic extends NumericStat{
    public Relic(){
        this(0);
    }

    public Relic(int relic) {
        super(relic);
    }

    public boolean isLessThan(final Relic other){
        return super.isLessThan(other);
    }

    public boolean isGreaterThan(final Relic other){
        return super.isGreaterThan(other);
    }
}
