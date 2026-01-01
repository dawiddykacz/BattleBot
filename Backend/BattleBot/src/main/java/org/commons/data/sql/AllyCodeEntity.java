package org.commons.data.sql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Entity
@Table(name = "ally_codes")
class AllyCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(name = "allyCode",nullable = false)
    private String allyCode;

    @Setter
    @ManyToOne
    @JoinColumn(name = "discord_user_id", nullable = false)
    private DiscordUserEntity discordUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllyCodeEntity that = (AllyCodeEntity) o;
        return Objects.equals(allyCode, that.allyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(allyCode);
    }
}
