package pl.epicserwer.czolg.swgoh.battlebot.stats.test;

import org.commons.AllyCode;
import org.commons.Name;
import org.commons.stats.guilds.SortedHeroes;
import org.commons.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.epicserwer.czolg.swgoh.battlebot.drt.DrtService;
import pl.epicserwer.czolg.swgoh.battlebot.stats.guild.GuildHeroesService;
import pl.epicserwer.czolg.swgoh.battlebot.stats.guild.filter.heroes.GuildHeroFilterService;

import java.util.List;

@Service
public class MM {
   private final DrtService drtService;
   private final UserService userService;
   private final GuildHeroesService guildHeroesService;
   private final GuildHeroFilterService guildHeroFilterService;

   @Autowired
   public MM(DrtService drtService, UserService userService, GuildHeroesService guildHeroesService, GuildHeroFilterService guildHeroFilterService) {
       this.drtService = drtService;
       this.userService = userService;
       this.guildHeroesService = guildHeroesService;
       this.guildHeroFilterService = guildHeroFilterService;
   }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
       //864927446
        long l = System.currentTimeMillis();
        final String g = "f_Thgi18TriIdV1cEkEphA";
        String ally = "864927446";
        //userService.registerUser(new NameId("dd"),new AllyCode(ally));

        //this.drtService.a();

    }
}
