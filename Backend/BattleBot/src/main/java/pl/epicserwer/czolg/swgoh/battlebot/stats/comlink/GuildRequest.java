package pl.epicserwer.czolg.swgoh.battlebot.stats.comlink;


import lombok.NonNull;
import lombok.ToString;

@ToString
class GuildRequest extends ComlinkRequest {
    private final GuildID guildID;

    public GuildRequest(@NonNull final GuildID guildID) {
        super(Method.POST,"guild");

        this.guildID = guildID;
    }

    @Override
    public String getJsonBodyAsString(){
        return "{\"payload\":{\"guildId\":\""+this.guildID+"\"," +
                "\"includeRecentGuildActivityInfo\":true},\"enums\":true}";
    }
}
