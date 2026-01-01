package pl.epicserwer.czolg.swgoh.battlebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"pl.epicserwer.czolg.swgoh.battlebot", "org.commons"})
@EntityScan(basePackages = {"pl.epicserwer.czolg.swgoh.battlebot", "org.commons"})
@EnableJpaRepositories(basePackages = {"pl.epicserwer.czolg.swgoh.battlebot", "org.commons"})
public class BattleBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleBotApplication.class, args);
	}

}
