package org.commons.stats;

public class Star extends NumericStat{
    public Star(){
        this(0);
    }

    public Star(int star) {
        super(star);
    }

    public boolean isLessThan(final GearLevel other){
        return super.isLessThan(other);
    }

    public boolean isGreaterThan(final GearLevel other){
        return super.isGreaterThan(other);
    }
}
