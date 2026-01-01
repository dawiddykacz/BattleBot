package org.commons;

import lombok.NonNull;

import java.util.Objects;

public class AllyCode {
    private final String code;

    public AllyCode(@NonNull final String code) {
        if(code.isEmpty()) throw new IllegalArgumentException("ally code is empty");
        if(code.length() != 9) throw new IllegalArgumentException("ally code length must be 9");
        if(!isCorrect(code)) throw new IllegalArgumentException("ally code is incorrect");

        this.code = code;
    }

    private static boolean isCorrect(@NonNull final String code) {
        try {
            Integer.parseInt(code);
        }catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllyCode allyCode = (AllyCode) o;
        return Objects.equals(code, allyCode.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}
