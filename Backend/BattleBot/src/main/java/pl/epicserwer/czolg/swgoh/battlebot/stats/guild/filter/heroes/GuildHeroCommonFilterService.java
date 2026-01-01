package pl.epicserwer.czolg.swgoh.battlebot.stats.guild.filter.heroes;

import lombok.NonNull;
import org.commons.AllyCode;
import org.commons.Name;
import org.commons.stats.guilds.GuildHeroes;
import org.commons.stats.guilds.SortedHeroes;
import pl.epicserwer.czolg.swgoh.battlebot.stats.guild.GuildHeroesService;

import java.util.List;

class GuildHeroCommonFilterService implements GuildHeroFilterService{
    private final GuildHeroesService guildHeroesService;
    private final GuildHeroFilter guildHeroFilter;

    public GuildHeroCommonFilterService(GuildHeroesService guildHeroesService, GuildHeroFilter guildHeroFilter) {
        this.guildHeroesService = guildHeroesService;
        this.guildHeroFilter = guildHeroFilter;
    }

    public SortedHeroes getHeroesByRarity(@NonNull final AllyCode allyCode,@NonNull final List<Name> heroNames){
        GuildHeroes guildHeroes = guildHeroesService.build(allyCode);
        return guildHeroFilter.calculate(guildHeroes, heroNames);
    }
}
