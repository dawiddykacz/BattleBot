package org.commons.stats;

public class Power extends NumericStat{
    public Power(){
        this(0);
    }

    public Power(long power){
        super(power);
    }

    public boolean isLessThan(Power other){
        return super.isLessThan(other);
    }
    public boolean isGreaterThan(Power other){
        return super.isGreaterThan(other);
    }
}
