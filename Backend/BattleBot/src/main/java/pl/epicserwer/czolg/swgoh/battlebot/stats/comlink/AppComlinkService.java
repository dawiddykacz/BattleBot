package pl.epicserwer.czolg.swgoh.battlebot.stats.comlink;

import com.google.gson.JsonElement;
import org.commons.AllyCode;
import org.commons.Key;
import org.commons.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AppComlinkService implements ComlinkService {
    private final ComlinkQueueService queueService;
    private Logger logger;

    public AppComlinkService(Key accessKey, Key secretKey, Url apiUrl) {
        this.queueService = new ComlinkQueueService(accessKey, secretKey, apiUrl);

        this.logger = LoggerFactory.getLogger(getClass());
    }

    public JsonElement getGuild(String guildId) throws IllegalArgumentException{
        return this.queueService.add(new GuildRequest(new GuildID(guildId)));
    }

    public JsonElement getPlayer(String allyCode,String comlinkPlayerId) throws IllegalArgumentException{
        return this.queueService.add(getPlayerRequest(allyCode,comlinkPlayerId));
    }

    private PlayerRequest getPlayerRequest(String allyCode,String comlinkPlayerId) throws IllegalArgumentException{
        if(allyCode != null) return PlayerRequest.from(new AllyCode(allyCode));
        if(comlinkPlayerId != null) return PlayerRequest.from(new ComlinkPlayerId(comlinkPlayerId));

        throw new IllegalArgumentException("ally code or comlink player id must be not null") ;
    }
}
