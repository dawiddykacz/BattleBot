package org.commons.data;

import org.commons.data.sql.AllyCodeRepository;
import org.commons.data.sql.DiscordUserRepository;
import org.commons.data.sql.SQLDataUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataUserConfiguration {
    private final DiscordUserRepository discordUserRepository;
    private final AllyCodeRepository allyCodeRepository;

    @Autowired
    public DataUserConfiguration(DiscordUserRepository discordUserRepository, AllyCodeRepository allyCodeRepository) {
        this.discordUserRepository = discordUserRepository;
        this.allyCodeRepository = allyCodeRepository;
    }

    @Bean
    public SQLDataUserService sqlDataUserService(){
        return new SQLDataUserService(this.discordUserRepository, this.allyCodeRepository);
    }
}
