package org.commons.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.commons.Name;
import org.commons.NameId;

@AllArgsConstructor
@Getter
@ToString
public class GameHero {
    private final NameId id;
    private final Name name;
    private final Level level;
    private final GearLevel gearLevel;
    private final Relic relic;
}
