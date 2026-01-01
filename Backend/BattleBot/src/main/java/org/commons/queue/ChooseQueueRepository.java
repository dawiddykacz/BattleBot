package org.commons.queue;

public class ChooseQueueRepository<T> {
    public enum QUEUE_TYPE{
        BLOCKING
    }

    public QueueRepository<T> choose(QUEUE_TYPE type){
        final UUIDService uuidService = new UUIDService();

        return new BlockingQueueRepository<>(uuidService);
    }
}
