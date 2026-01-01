package org.commons;

import lombok.NonNull;

public class Key {
    private final String key;

    public Key(@NonNull final String key) {
        if(key.isEmpty()) throw new IllegalArgumentException("Key cannot be empty");

        this.key = key;
    }

    @Override
    public String toString() {
        return this.key;
    }
}
