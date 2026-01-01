package org.commons.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Guild {
    private final List<GuildMember> guildMembers;
    private final int allDrt;

    public Guild(final List<GuildMember> guildMembers) {
        this.guildMembers = guildMembers;

        int allDrt = 0;
        for (final GuildMember guildMember : guildMembers) {
            allDrt += guildMember.drt().getAmountAsInt();
        }
        this.allDrt = allDrt;
    }
}
