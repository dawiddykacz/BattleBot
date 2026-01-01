package org.commons.queue;

import org.commons.NameId;

public record QueueResult<T>(NameId requestUUID, T request){

}