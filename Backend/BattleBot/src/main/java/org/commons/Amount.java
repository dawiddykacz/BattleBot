package org.commons;

public class Amount {
    private int amount;

    public Amount(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount must be positive");
        this.amount = amount;
    }

    public int getAmountAsInt() {
        return amount;
    }

    @Override
    public String toString() {
        return this.amount + "";
    }
}
