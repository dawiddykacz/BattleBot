package org.commons.stats;

import java.util.Objects;

abstract class NumericStat implements Comparable<NumericStat> {
    private final long stat;

    public NumericStat(final long stat) {
        this.stat = stat;
    }

    public long getStatAsLong() {
        return stat;
    }

    protected boolean isLessThan(final NumericStat other) {
        return stat < other.getStatAsLong();
    }
    protected boolean isGreaterThan(final NumericStat other) {
        return stat > other.getStatAsLong();
    }

    @Override
    public String toString() {
        return this.stat + "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumericStat that = (NumericStat) o;
        return stat == that.stat;
    }

    @Override
    public int compareTo(NumericStat o) {
        return Long.compare(this.stat, o.stat) * -1;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stat);
    }
}
