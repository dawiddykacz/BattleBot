package pl.epicserwer.czolg.swgoh.battlebot.drt.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrtUserRepository extends JpaRepository<DrtUserEntity,String> {
}
