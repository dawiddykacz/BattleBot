package org.commons;

import lombok.NonNull;

public class Url {
    private final String url;

    public Url(@NonNull final String url) {
        if(url.isEmpty()) throw new IllegalArgumentException("url is empty");
        if(!url.startsWith("http")) throw new IllegalArgumentException("url is not http");

        this.url = url;
    }

    @Override
    public String toString() {
        return this.url;
    }
}
