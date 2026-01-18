package org.commons.stats.guilds;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.commons.AllyCode;
import org.commons.Name;
import org.commons.stats.GameHero;
import org.commons.stats.GearLevel;
import org.commons.stats.Relic;
import org.commons.stats.Star;

import java.util.*;

@Getter
@ToString
public class SortedHeroes {

    public record Player(AllyCode allyCode, Name playerName, Star stars) implements Comparable<Player> {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Player player = (Player) o;
            return Objects.equals(stars, player.stars) && Objects.equals(playerName, player.playerName) && Objects.equals(allyCode, player.allyCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(allyCode, playerName, stars);
        }

        @Override
        public int compareTo(Player o) {
            return stars.compareTo(o.stars());
        }
    }

    private final HashMap<Name, HashMap<Relic, List<Player>>> relicMap;
    private final HashMap<Name, HashMap<GearLevel, List<Player>>> gearMap;
    private final HashMap<Name, List<Player>> playerWithoutHeroMap;


    public static class Builder {
        private final HashMap<Name, HashMap<Relic, List<Player>>> relicMap = new HashMap<>();
        private final HashMap<Name, HashMap<GearLevel, List<Player>>> gearMap = new HashMap<>();
        private final HashMap<Name, List<Player>> playerWithoutHeroMap = new HashMap<>();
        private final List<Player> allPlayers = new ArrayList<>();

        public void add(@NonNull final GuildHero guildHero) {
            final GameHero gameHero = guildHero.getGameHero();
            final Player player = new Player(guildHero.getAllyCode(), guildHero.getName(), gameHero.getStar());
            if (!this.allPlayers.contains(player)) {
                this.allPlayers.add(player);
            }

            this.relicMap.putIfAbsent(gameHero.getName(), new HashMap<>());
            this.gearMap.putIfAbsent(gameHero.getName(), new HashMap<>());
            this.playerWithoutHeroMap.putIfAbsent(gameHero.getName(), new ArrayList<>());

            if (gameHero.getRelic().getStatAsLong() == 0) {
                final HashMap<GearLevel, List<Player>> gearMap = this.gearMap.get(gameHero.getName());
                final List<Player> players = gearMap.getOrDefault(gameHero.getGearLevel(), new ArrayList<>());

                players.add(player);

                gearMap.put(gameHero.getGearLevel(), players);
                this.gearMap.put(gameHero.getName(), gearMap);
                return;
            }

            final HashMap<Relic, List<Player>> relicMap = this.relicMap.get(gameHero.getName());
            final List<Player> players = relicMap.getOrDefault(gameHero.getRelic(), new ArrayList<>());

            players.add(player);

            relicMap.put(gameHero.getRelic(), players);
            this.relicMap.put(gameHero.getName(), relicMap);
        }

        public SortedHeroes build(@NonNull final GuildHeroes guildHeroes) {
            for (Name heroName : this.relicMap.keySet()) {
                final List<Player> allPlayers = new ArrayList<>(this.allPlayers);

                for (List<GuildHero> value : guildHeroes.getGameHeroes().values()) {
                    for (GuildHero guildHero : value) {
                        Player player = new Player(guildHero.getAllyCode(), guildHero.getName(),
                                guildHero.getGameHero().getStar());
                        if (!this.allPlayers.contains(player)) {
                            this.allPlayers.add(player);
                            allPlayers.add(player);
                        }

                        if (guildHero.getGameHero().getName().equals(heroName)) {
                            allPlayers.remove(player);
                        }
                    }
                }

                this.playerWithoutHeroMap.put(heroName, allPlayers);
            }

            for (Map<Relic, List<Player>> innerMap : relicMap.values()) {
                for (List<Player> players : innerMap.values()) {
                    Collections.sort(players);
                }
            }
            for (Map<GearLevel, List<Player>> innerMap : gearMap.values()) {
                for (List<Player> players : innerMap.values()) {
                    Collections.sort(players);
                }
            }
            for (List<Player> value : playerWithoutHeroMap.values()) {
                Collections.sort(value);
            }

            return new SortedHeroes(relicMap, gearMap, playerWithoutHeroMap);
        }
    }

    private SortedHeroes(HashMap<Name, HashMap<Relic, List<Player>>> relicMap, HashMap<Name, HashMap<GearLevel,
            List<Player>>> gearMap, HashMap<Name, List<Player>> playerWithoutHeroMap) {
        this.relicMap = relicMap;
        this.gearMap = gearMap;
        this.playerWithoutHeroMap = playerWithoutHeroMap;
    }
}
