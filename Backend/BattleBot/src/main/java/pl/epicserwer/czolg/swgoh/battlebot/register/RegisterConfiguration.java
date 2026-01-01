package pl.epicserwer.czolg.swgoh.battlebot.register;

import org.commons.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.parser.StatsParserService;

@Configuration
public class RegisterConfiguration {
    private final UserService userService;
    private final StatsParserService statsParserService;

    @Autowired
    public RegisterConfiguration(UserService userService, StatsParserService statsParserService) {
        this.userService = userService;
        this.statsParserService = statsParserService;
    }

    @Bean
    public AppRegisterService appRegisterService() {
        return new AppRegisterService(userService,statsParserService);
    }
}
