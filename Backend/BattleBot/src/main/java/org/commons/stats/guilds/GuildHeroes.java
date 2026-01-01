package org.commons.stats.guilds;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.commons.AllyCode;
import org.commons.Name;
import org.commons.stats.GameHero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@ToString
public class GuildHeroes {
    public static class Builder{
        private final HashMap<Name, List<GuildHero>> gameHeroes = new HashMap<>();

        public void add(@NonNull final Name playerName, @NonNull AllyCode allyCode,@NonNull final GameHero gameHero) {
            gameHeroes.putIfAbsent(gameHero.getName(), new ArrayList<>());

            gameHeroes.get(gameHero.getName()).add(new GuildHero(playerName, allyCode, gameHero));
        }

        public GuildHeroes build() {
            return new GuildHeroes(gameHeroes);
        }
    }

    private final HashMap<Name, List<GuildHero>> gameHeroes;

    private GuildHeroes(@NonNull final HashMap<Name, List<GuildHero>> gameHeroes) {
        this.gameHeroes = gameHeroes;
    }
}
