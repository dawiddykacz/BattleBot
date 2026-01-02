package pl.epicserwer.czolg.swgoh.battlebot.stats.guild.filter.heroes;

import lombok.NonNull;
import org.commons.AllyCode;
import org.commons.Name;
import org.commons.stats.guilds.GuildHeroes;
import org.commons.stats.guilds.SortedHeroes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.epicserwer.czolg.swgoh.battlebot.stats.guild.GuildHeroesService;

import java.util.List;

class GuildHeroCommonFilterService implements GuildHeroFilterService{
    private final GuildHeroesService guildHeroesService;
    private final GuildHeroFilter guildHeroFilter;
    private final Logger logger;

    public GuildHeroCommonFilterService(GuildHeroesService guildHeroesService, GuildHeroFilter guildHeroFilter) {
        this.guildHeroesService = guildHeroesService;
        this.guildHeroFilter = guildHeroFilter;

        this.logger = LoggerFactory.getLogger(getClass());
    }

    public SortedHeroes getHeroesByRarity(@NonNull final AllyCode allyCode,@NonNull final List<Name> heroNames){
        logger.info("Sorting heroes by Rarity from {}",allyCode);
        GuildHeroes guildHeroes = guildHeroesService.build(allyCode);
        logger.info("Sorting heroes by Rarity from {} - sorting",allyCode);
        final SortedHeroes sortedHeroes = guildHeroFilter.calculate(guildHeroes, heroNames);
        logger.info("Sorting heroes by Rarity from {} - completed",allyCode);
        return sortedHeroes;
    }
}
