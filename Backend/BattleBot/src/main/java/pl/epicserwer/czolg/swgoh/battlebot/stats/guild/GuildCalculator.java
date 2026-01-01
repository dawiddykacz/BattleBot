package pl.epicserwer.czolg.swgoh.battlebot.stats.guild;

import lombok.NonNull;
import org.commons.AllyCode;
import org.commons.Name;
import org.commons.stats.GameHero;
import org.commons.stats.GamePlayer;
import org.commons.stats.guilds.GuildHeroes;

import java.util.List;

public class GuildCalculator {

    public GuildHeroes build( @NonNull final List<GamePlayer> gamePlayers){
        GuildHeroes.Builder builder = new GuildHeroes.Builder();

        for(GamePlayer gamePlayer : gamePlayers){
            for (GameHero gameHero : gamePlayer.getGameHeroes()) {
                builder.add(gamePlayer.getPlayerName(),gamePlayer.getAllyCode(),gameHero);
            }
        }

        return builder.build();
    }

}
