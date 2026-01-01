package pl.epicserwer.czolg.swgoh.battlebot.stats.guild;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.parser.StatsParserService;

@Configuration
public class GuildHeroesConfiguration {
    private StatsParserService statsParserService;

    @Autowired
    public GuildHeroesConfiguration(StatsParserService statsParserService) {
        this.statsParserService = statsParserService;
    }

    @Bean
    public GuildHeroesService guildHeroesService() {
        return new GuildHeroesService(this.statsParserService);
    }
}
