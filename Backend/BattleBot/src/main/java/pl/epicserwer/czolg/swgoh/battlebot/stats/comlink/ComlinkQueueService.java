package pl.epicserwer.czolg.swgoh.battlebot.stats.comlink;

import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.NonNull;
import org.commons.Key;
import org.commons.Url;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

class ComlinkQueueService {
    private static final int LIMIT = 20;
    private final BlockingQueue<CallableTask> queue = new LinkedBlockingQueue<>();

    private final ComlinkApi comlinkApi;

    public ComlinkQueueService(@NonNull final Key accessKey, @NonNull final Key secretKey, @NonNull final Url apiUrl){
        this.comlinkApi = new ComlinkApi(accessKey, secretKey, apiUrl);

        Thread worker = new Thread(this::run);
        worker.setDaemon(true);
        worker.start();
    }

    public JsonElement add(@NonNull final ComlinkRequest comlinkRequest){
        final CompletableFuture<JsonElement> future = new CompletableFuture<>();
        final CallableTask callableTask = new CallableTask(comlinkRequest,future);
        try {
            queue.put(callableTask);
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } catch (ExecutionException e) {
            return null;
        }
    }


    private void run(){
        long interval = 1000L / LIMIT;
        while (true) {
            try {
                final CallableTask task = this.queue.take();
                final JsonElement jsonElement = this.comlinkApi.sendRequest(task.getComlinkRequest());
                if(jsonElement != null){
                    task.getResponse().complete(jsonElement);
                }else {
                    task.addTry();
                    if(task.isFailed()){
                        task.getResponse().complete(null);
                    }else {
                        this.queue.put(task);
                    }
                }

                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static class CallableTask{
        @Getter
        private final ComlinkRequest comlinkRequest;
        @Getter
        private final CompletableFuture<JsonElement> response;
        private final long startTime = System.currentTimeMillis();

        private int tries = 0;

        public CallableTask(@NonNull final ComlinkRequest comlinkRequest,
                            @NonNull final CompletableFuture<JsonElement> response) {
            this.comlinkRequest = comlinkRequest;
            this.response = response;
        }

        public void addTry(){
            this.tries ++;
        }

        public boolean isFailed(){
            return this.tries >= 3 && System.currentTimeMillis() - this.startTime > 1_100;
        }
    }
}
