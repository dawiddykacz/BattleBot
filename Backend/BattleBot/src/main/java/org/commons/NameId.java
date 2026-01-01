package org.commons;

import lombok.NonNull;

import java.util.Objects;

public class NameId {
    private final String id;

    public NameId(@NonNull final String id) {
        if(id.isEmpty()) throw new IllegalArgumentException("id cannot be empty");

        this.id = id;
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NameId nameId = (NameId) o;
        return Objects.equals(id, nameId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
