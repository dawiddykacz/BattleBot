package org.commons;

import lombok.NonNull;

import java.util.Objects;

public class Name {
    private final String name;

    public Name(){
        this("Uknown");
    }

    public Name(@NonNull final String name) {
        if(name.isEmpty()) throw new IllegalArgumentException("name cannot be empty");

        this.name = name;
    }

    public boolean contains(@NonNull final Name name) {
        return this.name.contains(name.name);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
