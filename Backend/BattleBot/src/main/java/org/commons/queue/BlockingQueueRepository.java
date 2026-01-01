package org.commons.queue;

import lombok.NonNull;
import org.commons.NameId;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

class BlockingQueueRepository<T> implements QueueRepository<T> {
    private final BlockingQueue<NameId> queue = new LinkedBlockingQueue<>();
    private final Map<NameId, T> requestsMap = new ConcurrentHashMap<>();
    private final UUIDService uuidService;

    public BlockingQueueRepository(@NonNull final UUIDService uuidService) {
        this.uuidService = uuidService;
    }

    @Override
    public NameId addRequest(NameId requestUUID,T request) {
        if(requestsMap.containsKey(requestUUID))
            throw new IllegalArgumentException("Request already exists");

        final NameId uuid = uuidService.generateUUID();

        this.requestsMap.put(uuid, request);
        this.queue.add(uuid);

        return uuid;
    }

    @Override
    public boolean containsRequest(NameId requestUUID) {
        return this.queue.contains(requestUUID);
    }

    @Override
    public QueueResult<T> getRequest() {
        try {
            NameId requestName = this.queue.take();
            T request = this.requestsMap.get(requestName);
            this.requestsMap.remove(requestName);

            return new QueueResult<>(requestName, request);
        } catch (InterruptedException e) {
            return null;
        }
    }
}
