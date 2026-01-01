package org.commons.stats;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.commons.AllyCode;
import org.commons.Name;
import pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.GuildID;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class GamePlayer {
    private final Name playerName;
    private final GuildID guildID;
    private final Level level;
    private final AllyCode allyCode;
    private final List<GameHero> gameHeroes;
}
