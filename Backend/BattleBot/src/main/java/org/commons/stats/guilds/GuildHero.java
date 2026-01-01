package org.commons.stats.guilds;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.commons.AllyCode;
import org.commons.Name;
import org.commons.NameId;
import org.commons.stats.GameHero;

import java.util.Objects;

@AllArgsConstructor
@Getter
@ToString
public class GuildHero {
    private final Name name;
    private final AllyCode allyCode;
    private final GameHero gameHero;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuildHero guildHero = (GuildHero) o;
        if(gameHero == null || guildHero.gameHero == null) return false;
        return Objects.equals(gameHero.getName(), guildHero.gameHero.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gameHero);
    }
}
