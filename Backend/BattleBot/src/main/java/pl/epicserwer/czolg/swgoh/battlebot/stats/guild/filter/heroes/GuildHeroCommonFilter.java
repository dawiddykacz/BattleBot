package pl.epicserwer.czolg.swgoh.battlebot.stats.guild.filter.heroes;

import lombok.NonNull;
import org.commons.Name;
import org.commons.stats.guilds.GuildHero;
import org.commons.stats.guilds.GuildHeroes;
import org.commons.stats.guilds.SortedHeroes;

import java.util.HashMap;
import java.util.List;

class GuildHeroCommonFilter implements GuildHeroFilter {

    public SortedHeroes calculate(@NonNull final GuildHeroes guildHeroes, List<Name> heroNames){
        SortedHeroes.Builder builder = new SortedHeroes.Builder();

        final HashMap<Name, List<GuildHero>> heroMap = guildHeroes.getGameHeroes();
        heroMap.forEach((name, heroes) -> {
           if(contains(heroNames,name)){
               heroes.forEach(builder::add);
           }
        });

        return builder.build(guildHeroes);
    }

    private boolean contains(List<Name> heroNames, Name name){
        for (Name heroName : heroNames) {
            if(name.contains(heroName)){
                return true;
            }
        }
        return false;
    }
}
