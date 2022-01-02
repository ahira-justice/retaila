package com.ahirajustice.retail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${app.config.accesstokenexpireminutes}")
    public int ACCESS_TOKEN_EXPIRE_MINUTES;

    @Value("${app.config.secretkey}")
    public String SECRET_KEY;

    @Value("${app.config.superuser.email}")
    public String SUPERUSER_EMAIL;

    @Value("${app.config.superuser.firstname}")
    public String SUPERUSER_FIRST_NAME;

    @Value("${app.config.superuser.lastname}")
    public String SUPERUSER_LAST_NAME;

    @Value("${app.config.superuser.password}")
    public String SUPERUSER_PASSWORD;

    @Value("${app.config.usertoken.length}")
    public int USER_TOKEN_LENGTH;

    @Value("${app.config.usertoken.keyspace}")
    public String USER_TOKEN_KEYSPACE;
}
