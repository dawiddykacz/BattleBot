package pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.NonNull;
import org.commons.AllyCode;
import org.commons.Amount;
import org.commons.Name;
import org.commons.NameId;
import org.commons.stats.*;
import org.commons.stats.GameHero;
import pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.ComlinkService;
import pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.GuildID;

import java.util.ArrayList;
import java.util.List;

class AppComlinkParserService implements StatsParserService {
    private final ComlinkService comlinkService;

    public AppComlinkParserService(@NonNull final ComlinkService comlinkService) {
        this.comlinkService = comlinkService;
    }

    public boolean isGuildExists(@NonNull final String guildId) {
        try {
            final JsonElement json = comlinkService.getGuild(guildId);
            return json != null;
        } catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    public boolean isPlayerExistsFromAllyCode(@NonNull final String allyCode) {
        return this.isPlayerExists(allyCode, null);
    }

    public boolean isPlayerExistsFromComlinkId(@NonNull final String comlinkId) {
        return this.isPlayerExists(null, comlinkId);
    }

    private boolean isPlayerExists(final String allyCode, final String heroId) {
        try {
            final JsonElement json = comlinkService.getPlayer(allyCode, heroId);
            return json != null;
        } catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    public GamePlayer getPlayerFromAllyCode(@NonNull final String allyCode) throws IllegalArgumentException {
        return this.getPlayer(allyCode, null);
    }

    public GamePlayer getPlayerFromComlinkId(@NonNull final String comlinkId) throws IllegalArgumentException {
        return this.getPlayer(null, comlinkId);
    }

    private GamePlayer getPlayer(final String allyCode, final String heroId) throws IllegalArgumentException {
        final JsonElement jsonElement = this.comlinkService.getPlayer(allyCode, heroId);
        if (jsonElement == null) {
            throw new IllegalArgumentException("No such player");
        }

        final List<GameHero> heroes = new ArrayList<>();
        Name name = new Name();
        AllyCode allyCode1 = null;
        Level level = new Level();
        GuildID guildId = null;

        if (jsonElement.isJsonObject()) {
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("name") && jsonObject.get("name").isJsonPrimitive()) {
                name = new Name(jsonObject.get("name").getAsString());
            }
            if (jsonObject.has("allyCode") && jsonObject.get("allyCode").isJsonPrimitive()) {
                allyCode1 = new AllyCode(jsonObject.get("allyCode").getAsString());
            }
            if (jsonObject.has("level") && jsonObject.get("level").isJsonPrimitive()) {
                level = new Level(jsonObject.get("level").getAsInt());
            }
            if (jsonObject.has("guildId") && jsonObject.get("guildId").isJsonPrimitive()) {
                guildId = new GuildID(jsonObject.get("guildId").getAsString());
            }

            if (jsonObject.has("rosterUnit") && jsonObject.get("rosterUnit").isJsonArray()) {
                final JsonArray units = jsonObject.getAsJsonArray("rosterUnit");

                for (JsonElement unit : units) {
                    if (unit.isJsonObject()) {
                        final JsonObject unitObject = unit.getAsJsonObject();

                        try {
                            final GameHero gameHero = this.getHero(unitObject);
                            if (gameHero != null) heroes.add(gameHero);

                        } catch (Exception ignored) {

                        }
                    }
                }
            }
        }

        return new GamePlayer(name, guildId, level,allyCode1, heroes);
    }

    public Guild getGuild(@NonNull final String guildId) throws IllegalArgumentException {
        final JsonElement jsonElement = this.comlinkService.getGuild(guildId);
        if (jsonElement == null) throw new IllegalArgumentException("Invalid guild ID: " + guildId);


        final List<GuildMember> guildMembers = new ArrayList<>();

        if (jsonElement.isJsonObject()) {
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("guild") && jsonObject.get("guild").isJsonObject()) {
                final JsonObject guildJson = jsonObject.getAsJsonObject("guild");

                if (guildJson.has("member") && guildJson.get("member").isJsonArray()) {
                    final JsonArray membersJson = guildJson.getAsJsonArray("member");

                    for (JsonElement element : membersJson) {
                        if (element.isJsonObject()) {
                            final JsonObject memberJson = element.getAsJsonObject();

                            if (memberJson.has("playerId") && memberJson.get("playerId").isJsonPrimitive()) {
                                final String playerId = memberJson.get("playerId").getAsString();
                                if (playerId == null || playerId.isEmpty()) continue;

                                int drt = 0;
                                if (memberJson.has("memberContribution") && memberJson.get("memberContribution").isJsonArray()) {
                                    JsonArray contributionsJson = memberJson.getAsJsonArray("memberContribution");
                                    for (JsonElement contribution : contributionsJson) {
                                        if (contribution.isJsonObject()) {
                                            final JsonObject contributionJson = contribution.getAsJsonObject();
                                            final JsonElement jsonElement1 = contributionJson.get("type");
                                            if (jsonElement1 != null && jsonElement1.isJsonPrimitive() &&
                                                    jsonElement1.getAsString().equals("CONTRIBUTION_TYPE_TRIBUTE")) {
                                                drt = contributionJson.get("currentValue").getAsInt();
                                            }
                                        }
                                    }
                                }
                                guildMembers.add(new GuildMember(new NameId(playerId), new Amount(drt)));
                            }
                        }
                    }
                }
            }
        }

        return new Guild(guildMembers);
    }

    private GameHero getHero(@NonNull final JsonObject heroJson) throws IllegalArgumentException {
        NameId id = new NameId(heroJson.get("id").getAsString());
        Name name = new Name();
        Level level = new Level();
        GearLevel gearLevel = new GearLevel();
        Relic relic = new Relic();

        if (heroJson.has("definitionId") && heroJson.get("definitionId").isJsonPrimitive()) {
            name = new Name(heroJson.get("definitionId").getAsString().split(":")[0].toLowerCase());
        }
        if (heroJson.has("currentLevel") && heroJson.get("currentLevel").isJsonPrimitive()) {
            level = new Level(heroJson.get("currentLevel").getAsInt());
        }

        if (heroJson.has("relic") && heroJson.get("relic").isJsonObject()) {
            JsonObject relicJson = heroJson.getAsJsonObject("relic");
            if (relicJson.has("currentTier") && relicJson.get("currentTier").isJsonPrimitive()) {
                int currentTier = relicJson.get("currentTier").getAsInt() - 2;
                if (currentTier < 0) currentTier = 0;

                relic = new Relic(currentTier);
            }
        }
        if (heroJson.has("currentTier") && heroJson.get("currentTier").isJsonPrimitive()) {
            gearLevel = new GearLevel(heroJson.get("currentTier").getAsInt());
        }


        return new GameHero(id, name, level, gearLevel, relic);
    }
}
