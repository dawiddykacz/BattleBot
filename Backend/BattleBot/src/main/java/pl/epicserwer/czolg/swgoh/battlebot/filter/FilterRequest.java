package pl.epicserwer.czolg.swgoh.battlebot.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.commons.AllyCode;
import org.commons.Name;
import org.commons.NameId;

import java.util.List;

record FilterRequest(NameId discordId, AllyCode allyCode, List<Name> heroNames) {
}
