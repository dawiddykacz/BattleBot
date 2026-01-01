package pl.epicserwer.czolg.swgoh.battlebot.register;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RegisterResult {
    private final boolean success;
    private final String message;
}
