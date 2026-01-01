package pl.epicserwer.czolg.swgoh.battlebot.stats.guild;

import lombok.NonNull;
import org.commons.AllyCode;
import org.commons.stats.GamePlayer;
import org.commons.stats.Guild;
import org.commons.stats.GuildMember;
import org.commons.stats.guilds.GuildHeroes;
import pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.GuildID;
import pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.parser.StatsParserService;

import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuildHeroesService {
    private final StatsParserService statsParserService;
    private final GuildCalculator guildCalculator = new GuildCalculator();

    public GuildHeroesService(@NonNull final StatsParserService statsParserService) {
        this.statsParserService = statsParserService;
    }

    public GuildHeroes build(@NonNull final String allyCode) throws IllegalArgumentException{
        return this.build(new AllyCode(allyCode));
    }

    public GuildHeroes build(@NonNull final AllyCode allyCode) throws IllegalArgumentException{
        final GamePlayer gamePlayer = this.statsParserService.getPlayerFromAllyCode(allyCode.toString());
        final GuildID guildID = gamePlayer.getGuildID();

        if(!guildID.hasGuild()) throw new IllegalArgumentException("Player has no guild");

        final Guild guild = this.statsParserService.getGuild(guildID.toString());

        final ExecutorService executorService = Executors.newFixedThreadPool(50); // np. 10 wątków
        final List<Future<GamePlayer>> futures = new ArrayList<>();

        for (GuildMember guildMember : guild.getGuildMembers()) {
            futures.add(executorService.submit(() ->
                    statsParserService.getPlayerFromComlinkId(guildMember.playerId().toString())
            ));
        }

        final List<GamePlayer> players = new ArrayList<>();
        for (Future<GamePlayer> future : futures) {
            try {
                players.add(future.get()); //
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();

        return this.guildCalculator.build(players);
    }
}
