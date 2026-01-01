package org.commons.data.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscordUserRepository extends JpaRepository<DiscordUserEntity,Long> {
    Optional<DiscordUserEntity> findByDiscordId(String discordId);
    boolean existsByDiscordId(String discordId);
}
