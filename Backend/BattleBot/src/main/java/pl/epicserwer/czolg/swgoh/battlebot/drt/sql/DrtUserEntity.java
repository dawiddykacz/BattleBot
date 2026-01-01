package pl.epicserwer.czolg.swgoh.battlebot.drt.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "drt_user")
@Getter
@Setter
@ToString
public class DrtUserEntity {
    @Id
    private String allyCode;

    @Column(nullable = false)
    private String guild;

    @Column(nullable = false)
    private String name;
}
