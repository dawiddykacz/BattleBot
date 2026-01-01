package pl.epicserwer.czolg.swgoh.battlebot.stats.comlink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.epicserwer.czolg.swgoh.battlebot.config.ConfigService;

@Configuration
public class ComlinkConfiguration {
    private final ConfigService configService;

    @Autowired
    public ComlinkConfiguration(final ConfigService configService) {
        this.configService = configService;
    }

    @Bean
    public ComlinkService comlinkService() {
        return new AppComlinkService(this.configService.getComlinkAccessKey(),
                this.configService.getComlinkSecretKey(),this.configService.getComlinkUrl());
    }
}
