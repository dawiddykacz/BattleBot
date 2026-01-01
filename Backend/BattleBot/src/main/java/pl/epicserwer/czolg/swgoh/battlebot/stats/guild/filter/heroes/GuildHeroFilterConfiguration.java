package pl.epicserwer.czolg.swgoh.battlebot.stats.guild.filter.heroes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.epicserwer.czolg.swgoh.battlebot.stats.guild.GuildHeroesService;

@Configuration
public class GuildHeroFilterConfiguration {
    private final GuildHeroesService guildHeroesService;
    private final GuildHeroFilter guildHeroFilter;

    public GuildHeroFilterConfiguration(GuildHeroesService guildHeroesService) {
        this.guildHeroesService = guildHeroesService;
        this.guildHeroFilter = new GuildHeroCommonFilter();
    }

    @Bean
    public GuildHeroFilterService guildHeroFilterService() {
        return new GuildHeroCommonFilterService(guildHeroesService,guildHeroFilter);
    }
}
