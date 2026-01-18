package pl.epicserwer.czolg.swgoh.battlebot.controllers.v1.dto;

import lombok.*;
import org.commons.Name;
import org.commons.stats.GearLevel;
import org.commons.stats.Relic;
import org.commons.stats.guilds.SortedHeroes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
public class FilterHeroesByStatsResponseDto {
    public record Player(String name,Long starLevel) {
    }

    public record Hero(HashMap<Long, List<Player>> relicMap,
                       HashMap<Long, List<Player>> gearMap,
                       List<Player> playerWithoutHeroMap){

    }
    private final HashMap<String, Hero> heroesMap = new HashMap<>();

    public FilterHeroesByStatsResponseDto(@NonNull final SortedHeroes sortedHeroes) {
        final HashMap<Name, HashMap<Relic, List<SortedHeroes.Player>>> relicMap = sortedHeroes.getRelicMap();
        final HashMap<Name, HashMap<GearLevel, List<SortedHeroes.Player>>> gearMap = sortedHeroes.getGearMap();
        final HashMap<Name,List<SortedHeroes.Player>> playerWithoutHeroMap = sortedHeroes.getPlayerWithoutHeroMap();

        final HashMap<String, HashMap<Long, List<Player>>> relicMap2 = new HashMap<>();
        final HashMap<String, HashMap<Long, List<Player>>> gearMap2 = new HashMap<>();
        final HashMap<String, List<Player>> playerWithoutHeroMap2 = new HashMap<>();

        for (Name name : relicMap.keySet()) {
            relicMap2.putIfAbsent(name.toString(), new HashMap<>());
            gearMap2.putIfAbsent(name.toString(), new HashMap<>());
            playerWithoutHeroMap2.putIfAbsent(name.toString(), new ArrayList<>());

            for (Relic relic : relicMap.get(name).keySet()) {
                relicMap2.get(name.toString()).putIfAbsent(relic.getStatAsLong(),new ArrayList<>());
                for (SortedHeroes.Player player : relicMap.get(name).get(relic)) {
                    relicMap2.get(name.toString()).get(relic.getStatAsLong()).add(toPlayer(player));
                }
            }
            for (GearLevel gear : gearMap.get(name).keySet()) {
                gearMap2.get(name.toString()).putIfAbsent(gear.getStatAsLong(),new ArrayList<>());
                for (SortedHeroes.Player player : gearMap.get(name).get(gear)) {
                    gearMap2.get(name.toString()).get(gear.getStatAsLong()).add(toPlayer(player));
                }
            }

            for (SortedHeroes.Player player : playerWithoutHeroMap.get(name)) {
                playerWithoutHeroMap2.get(name.toString()).add(toPlayer(player));
            }
        }

        for (String heroName : relicMap2.keySet()) {
            this.heroesMap.put(heroName,new Hero(relicMap2.get(heroName),
                    gearMap2.get(heroName),playerWithoutHeroMap2.get(heroName)));
        }
    }

    private Player toPlayer(@NonNull final SortedHeroes.Player sortedPlayer) {
        return new Player(sortedPlayer.playerName().toString(),sortedPlayer.stars().getStatAsLong());
    }
}

