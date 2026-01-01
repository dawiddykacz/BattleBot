package org.commons.queue;

import org.commons.NameId;

public interface QueueRepository<T> {
    NameId addRequest(NameId requestUUID,T request); //return process uuid
    boolean containsRequest(NameId requestUUID);
    QueueResult<T> getRequest();//return and remove
}
