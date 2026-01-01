package org.commons.stats;

public class GearLevel extends NumericStat{
    public GearLevel(){
        this(1);
    }

    public GearLevel(int gear) {
        super(gear);
    }

    public boolean isLessThan(final GearLevel other){
        return super.isLessThan(other);
    }

    public boolean isGreaterThan(final GearLevel other){
        return super.isGreaterThan(other);
    }
}
