package pl.epicserwer.czolg.swgoh.battlebot.drt.sql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "drt_data")
@Getter
@Setter
@ToString
public class DrtEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ally_code", referencedColumnName = "allyCode")
    private DrtUserEntity user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int drt;
}
