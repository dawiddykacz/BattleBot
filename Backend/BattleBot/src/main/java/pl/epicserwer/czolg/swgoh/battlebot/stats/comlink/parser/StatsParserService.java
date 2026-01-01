package pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.parser;

import lombok.NonNull;
import org.commons.stats.GamePlayer;
import org.commons.stats.Guild;

public interface StatsParserService {
    boolean isGuildExists(@NonNull final String guildId);
    Guild getGuild(@NonNull final String guildId);

    GamePlayer getPlayerFromComlinkId(@NonNull final String comlinkId) throws IllegalArgumentException;
    GamePlayer getPlayerFromAllyCode(@NonNull final String allyCode) throws IllegalArgumentException;
    boolean isPlayerExistsFromComlinkId(@NonNull final String comlinkId);
    boolean isPlayerExistsFromAllyCode(@NonNull final String allyCode);
}
