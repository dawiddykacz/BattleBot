package pl.epicserwer.czolg.swgoh.battlebot.stats.comlink;

import lombok.NonNull;
import lombok.ToString;
import org.commons.AllyCode;

@ToString
class PlayerRequest extends ComlinkRequest{
    public static PlayerRequest from(@NonNull final AllyCode allyCode) {
        return new PlayerRequest("allyCode",allyCode.toString());
    }
    public static PlayerRequest from(@NonNull final ComlinkPlayerId complinkPlayerId) {
        return new PlayerRequest("playerId",complinkPlayerId.toString());
    }

    private final String key;
    private final String value;

    private PlayerRequest(@NonNull final String key,@NonNull final String value) {
        super(Method.POST,"player");

        this.key = key;
        this.value = value;
    }


    @Override
    public String getJsonBodyAsString(){
        return "{\"payload\":{\""+key+"\":\""+value+"\"},\"enums\":false}";
    }
}
