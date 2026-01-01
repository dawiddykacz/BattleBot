package pl.epicserwer.czolg.swgoh.battlebot.stats.comlink;

import com.google.gson.JsonElement;

public interface ComlinkService {
    JsonElement getGuild(String guildId) throws IllegalArgumentException;
    JsonElement getPlayer(String allyCode,String comlinkPlayerId) throws IllegalArgumentException;
}
