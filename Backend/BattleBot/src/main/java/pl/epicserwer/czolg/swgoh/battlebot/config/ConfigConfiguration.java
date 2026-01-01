package pl.epicserwer.czolg.swgoh.battlebot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigConfiguration {
    @Bean
    public ConfigService configService() {
        return new SpringConfigService();
    }
}
