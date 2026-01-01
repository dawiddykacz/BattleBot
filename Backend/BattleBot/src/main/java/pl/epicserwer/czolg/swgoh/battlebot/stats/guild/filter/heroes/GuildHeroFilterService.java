package pl.epicserwer.czolg.swgoh.battlebot.stats.guild.filter.heroes;

import lombok.NonNull;
import org.commons.AllyCode;
import org.commons.Name;
import org.commons.stats.guilds.SortedHeroes;

import java.util.List;

public interface GuildHeroFilterService {
    SortedHeroes getHeroesByRarity(@NonNull final AllyCode allyCode, @NonNull final List<Name> heroNames);
}
