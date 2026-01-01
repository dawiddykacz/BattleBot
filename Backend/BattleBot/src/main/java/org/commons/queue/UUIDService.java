package org.commons.queue;

import org.commons.NameId;

import java.util.UUID;

class UUIDService {
    public NameId generateUUID(){
        return new NameId(UUID.randomUUID().toString()+System.currentTimeMillis()+
                UUID.randomUUID().toString()+System.currentTimeMillis()+
                UUID.randomUUID().toString()+System.currentTimeMillis()+
                UUID.randomUUID().toString()+System.currentTimeMillis()+
                UUID.randomUUID().toString()+System.currentTimeMillis());
    }
}
