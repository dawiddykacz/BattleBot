package pl.epicserwer.czolg.swgoh.battlebot.controllers.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class RegisterAllyCodeDto {
    private String discordId;
    private String allyCode;
}
