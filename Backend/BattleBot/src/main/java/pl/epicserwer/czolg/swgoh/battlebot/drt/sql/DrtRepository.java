package pl.epicserwer.czolg.swgoh.battlebot.drt.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DrtRepository extends JpaRepository<DrtEntity, UUID> {
    Optional<DrtEntity> findFirstByUserOrderByDateDesc(DrtUserEntity user);
}
