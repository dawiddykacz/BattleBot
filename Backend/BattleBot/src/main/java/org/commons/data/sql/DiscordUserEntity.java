package org.commons.data.sql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name = "discord_users")
class DiscordUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(name = "discord_id",nullable = false,unique = true)
    private String discordId;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "primary_ally_code_id", referencedColumnName = "id")
    private AllyCodeEntity allyCode;

    @OneToMany(mappedBy = "discordUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AllyCodeEntity> allyCodes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscordUserEntity that = (DiscordUserEntity) o;
        return Objects.equals(discordId, that.discordId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(discordId);
    }
}
