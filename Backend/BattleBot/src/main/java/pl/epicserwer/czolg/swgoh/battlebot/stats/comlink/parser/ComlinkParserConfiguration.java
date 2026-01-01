package pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.parser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.ComlinkService;

@Configuration
public class ComlinkParserConfiguration {
    private final ComlinkService comlinkService;

    public ComlinkParserConfiguration(final ComlinkService comlinkService) {
        this.comlinkService = comlinkService;
    }

    @Bean
    public StatsParserService comlinkParserService() {
        return new AppComlinkParserService(comlinkService);
    }
}
