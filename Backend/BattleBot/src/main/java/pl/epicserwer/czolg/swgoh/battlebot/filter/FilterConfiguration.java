package pl.epicserwer.czolg.swgoh.battlebot.filter;

import lombok.NonNull;
import org.commons.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.epicserwer.czolg.swgoh.battlebot.stats.guild.filter.heroes.GuildHeroFilterService;

@Configuration
public class FilterConfiguration {
    private final UserService userService;
    private final GuildHeroFilterService guildHeroFilterService;

    @Autowired
    public FilterConfiguration(@NonNull final UserService userService, @NonNull final GuildHeroFilterService guildHeroFilterService){
        this.userService = userService;
        this.guildHeroFilterService = guildHeroFilterService;
    }

    @Bean
    public FilterService filterService(){
        return new AppFilterService(userService, guildHeroFilterService);
    }
}
