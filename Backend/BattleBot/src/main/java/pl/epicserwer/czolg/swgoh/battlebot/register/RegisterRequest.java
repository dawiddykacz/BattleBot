package pl.epicserwer.czolg.swgoh.battlebot.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.commons.AllyCode;
import org.commons.NameId;

@Getter
@AllArgsConstructor
@ToString
class RegisterRequest {
    private NameId discordId;
    private AllyCode allyCode;
}
