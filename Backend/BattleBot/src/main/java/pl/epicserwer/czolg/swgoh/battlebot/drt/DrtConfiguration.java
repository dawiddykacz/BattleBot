package pl.epicserwer.czolg.swgoh.battlebot.drt;

import org.commons.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.epicserwer.czolg.swgoh.battlebot.drt.sql.DrtRepository;
import pl.epicserwer.czolg.swgoh.battlebot.drt.sql.DrtUserRepository;
import pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.parser.StatsParserService;

@Configuration
public class DrtConfiguration {
    private final UserService userService;
    private final DrtRepository drtRepository;
    private final DrtUserRepository drtUserRepository;
    private final StatsParserService statsParserService;

    @Autowired
    public DrtConfiguration(UserService userService, DrtRepository drtRepository, DrtUserRepository drtUserRepository, StatsParserService statsParserService) {
        this.userService = userService;
        this.drtRepository = drtRepository;
        this.drtUserRepository = drtUserRepository;
        this.statsParserService = statsParserService;
    }

    @Bean
    public DrtService drtService() {
        return new DrtCommonService(userService, drtRepository, drtUserRepository, statsParserService);
    }
}
