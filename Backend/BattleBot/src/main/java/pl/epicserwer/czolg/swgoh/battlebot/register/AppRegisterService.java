package pl.epicserwer.czolg.swgoh.battlebot.register;

import lombok.NonNull;
import org.commons.AllyCode;
import org.commons.NameId;
import org.commons.queue.ChooseQueueRepository;
import org.commons.queue.QueueRepository;
import org.commons.queue.QueueResult;
import org.commons.queue.RequestStatus;
import org.commons.user.UserService;
import pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.parser.StatsParserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppRegisterService {
    private final List<NameId> processing = new ArrayList<>();
    private HashMap<NameId,RegisterResult> results = new HashMap<>();

    private final QueueRepository<RegisterRequest> queueRepository;
    private final UserService userService;
    private final StatsParserService statsParserService;

    public AppRegisterService(@NonNull final UserService userService,@NonNull final StatsParserService statsParserService){
        this.userService = userService;
        this.statsParserService = statsParserService;

        this.queueRepository = new ChooseQueueRepository<RegisterRequest>()
                .choose(ChooseQueueRepository.QUEUE_TYPE.BLOCKING);
        this.runThread();
    }

    //return request uuid
    public String addRegisterRequest(final String discordId, @NonNull final String allyCode){
        final NameId nameId = new NameId(discordId);
        final RegisterRequest registerRequest = new RegisterRequest(nameId,new AllyCode(allyCode));
        return this.queueRepository.addRequest(nameId, registerRequest).toString();
    }

    public RequestStatus getStatus(@NonNull final String requestId){
        final NameId nameId = new NameId(requestId);
        if(this.queueRepository.containsRequest(nameId)) return RequestStatus.REQUESTED;
        if(this.processing.contains(nameId)) return RequestStatus.PROCESSING;
        if(this.results.containsKey(nameId)) return RequestStatus.COMPLETED;

        return RequestStatus.NOT_FOUND;
    }

    public RegisterResult getResult(@NonNull final String requestId){
        final NameId nameId = new NameId(requestId);
        if(this.getStatus(requestId) != RequestStatus.COMPLETED)
            throw new IllegalStateException(nameId+" is not completed or not found");

        final RegisterResult registerResult = this.results.get(nameId);
        this.results.remove(nameId);
        return registerResult;
    }

    private void runThread(){
        final Thread thread = new Thread(() -> {
            while(true){
                final QueueResult<RegisterRequest> queueResult = this.queueRepository.getRequest();
                if(queueResult == null) continue;

                final RegisterRequest registerRequest = queueResult.request();
                this.processing.add(queueResult.requestUUID());
                try {
                    if(!this.statsParserService.isPlayerExistsFromAllyCode(registerRequest.getAllyCode().toString())){
                        throw new IllegalArgumentException("AllyCode "+registerRequest.getAllyCode().toString()+" is not found");
                    }

                    this.userService.registerUser(registerRequest.getDiscordId(), registerRequest.getAllyCode());
                    this.results.put(queueResult.requestUUID(),
                            new RegisterResult(true,"User successfully registered"));
                }catch (final IllegalArgumentException exception){
                    this.results.put(queueResult.requestUUID(),
                            new RegisterResult(false,exception.getMessage()));
                }catch (final Exception exception){
                    exception.printStackTrace();
                    continue;
                }

                this.processing.remove(queueResult.requestUUID());
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
