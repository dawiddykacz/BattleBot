package pl.epicserwer.czolg.swgoh.battlebot.filter;

import lombok.NonNull;
import org.commons.Name;
import org.commons.NameId;
import org.commons.queue.ChooseQueueRepository;
import org.commons.queue.QueueRepository;
import org.commons.queue.QueueResult;
import org.commons.queue.RequestStatus;
import org.commons.stats.guilds.SortedHeroes;
import org.commons.user.UserService;
import pl.epicserwer.czolg.swgoh.battlebot.controllers.v1.dto.FilterHeroesByStatsResponseDto;
import pl.epicserwer.czolg.swgoh.battlebot.stats.guild.filter.heroes.GuildHeroFilterService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class AppFilterService implements FilterService {
    private final List<NameId> processing = new ArrayList<>();
    private final HashMap<NameId, FilterHeroesByStatsResponseDto> results = new HashMap<>();
    private final QueueRepository<FilterRequest> queueRepository;

    private final UserService userService;
    private final GuildHeroFilterService guildHeroFilterService;

    public AppFilterService(@NonNull final UserService userService, @NonNull final GuildHeroFilterService guildHeroFilterService){
        this.userService = userService;
        this.guildHeroFilterService = guildHeroFilterService;

        this.queueRepository = new ChooseQueueRepository<FilterRequest>()
                .choose(ChooseQueueRepository.QUEUE_TYPE.BLOCKING);
        this.runThread();
    }

    //return request uuid
    public String addFilterRequest(final String discordId, @NonNull final String[] heroNameArray){
        final NameId nameId = new NameId(discordId);
        List<Name> heroNames = new ArrayList<>();
        for(String heroName : heroNameArray){
            heroNames.add(new Name(heroName.toLowerCase()));
        }

        final FilterRequest filterRequest = new FilterRequest(nameId,this.userService.getAllyCode(nameId),heroNames);
        return this.queueRepository.addRequest(nameId, filterRequest).toString();
    }

    public RequestStatus getStatus(@NonNull final String requestId){
        final NameId nameId = new NameId(requestId);
        if(this.queueRepository.containsRequest(nameId)) return RequestStatus.REQUESTED;
        if(this.processing.contains(nameId)) return RequestStatus.PROCESSING;
        if(this.results.containsKey(nameId)) return RequestStatus.COMPLETED;

        return RequestStatus.NOT_FOUND;
    }

    public FilterHeroesByStatsResponseDto getResult(@NonNull final String requestId){
        final NameId nameId = new NameId(requestId);
        if(this.getStatus(requestId) != RequestStatus.COMPLETED)
            throw new IllegalStateException(nameId+" is not completed or not found");

        final FilterHeroesByStatsResponseDto filterResults = this.results.get(nameId);
        this.results.remove(nameId);
        return filterResults;
    }

    private void runThread(){

        final Thread thread = new Thread(() -> {
            while(true){

                final QueueResult<FilterRequest> queueResult = this.queueRepository.getRequest();
                if(queueResult == null) continue;

                final FilterRequest filterRequest = queueResult.request();
                this.processing.add(queueResult.requestUUID());

                final SortedHeroes sortedHeroes = this.guildHeroFilterService.getHeroesByRarity(
                        filterRequest.allyCode(),filterRequest.heroNames()
                );

                this.results.put(queueResult.requestUUID(),new FilterHeroesByStatsResponseDto(sortedHeroes ));

                this.processing.remove(queueResult.requestUUID());
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
