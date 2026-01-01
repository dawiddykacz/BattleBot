package org.commons.user;

import org.commons.data.DataUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfiguration {
    private final DataUserService dataUserService;

    @Autowired
    public UserServiceConfiguration(final DataUserService dataUserService) {
        this.dataUserService = dataUserService;
    }

    @Bean
    public UserService userService() {
        return new UserCommonService(this.dataUserService);
    }
}
