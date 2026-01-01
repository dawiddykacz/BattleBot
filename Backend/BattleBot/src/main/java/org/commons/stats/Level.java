package org.commons.stats;

public class Level extends NumericStat{
    public Level(){
        this(1);
    }

    public Level(int level) {
        super(level);
    }

    public boolean isLessThan(Level other) {
        return super.isLessThan(other);
    }

    public boolean isGreaterThan(Level other) {
        return super.isGreaterThan(other);
    }
}
