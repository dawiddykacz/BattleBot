package pl.epicserwer.czolg.swgoh.battlebot.controllers.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.epicserwer.czolg.swgoh.battlebot.controllers.v1.dto.register.RegisterResult;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class RegisterAllyCodeResponseDto {
    private final RegisterResult result;
}
