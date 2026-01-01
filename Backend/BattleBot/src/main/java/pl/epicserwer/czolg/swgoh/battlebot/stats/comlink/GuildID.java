package pl.epicserwer.czolg.swgoh.battlebot.stats.comlink;

public class GuildID {
    private final String id;

    public GuildID(final String id) {
        this.id = id;
    }

    public boolean hasGuild(){
        return id != null && !id.isEmpty();
    }

    @Override
    public String toString() {
        return this.id;
    }
}
