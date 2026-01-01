package pl.epicserwer.czolg.swgoh.battlebot.stats.guild.filter.heroes;

import lombok.NonNull;
import org.commons.Name;
import org.commons.stats.guilds.GuildHeroes;
import org.commons.stats.guilds.SortedHeroes;

import java.util.List;

interface GuildHeroFilter {
    SortedHeroes calculate(@NonNull final GuildHeroes guildHeroes, List<Name> heroNames);
}
