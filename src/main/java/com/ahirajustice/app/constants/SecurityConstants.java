package com.ahirajustice.app.constants;

public class SecurityConstants {

    // Auth
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    // URLs
    public static final String[] EXCLUDE_FROM_AUTH_URLS = new String[] { 
        "/api/auth/login, POST", 
        "/api/users, POST",
        "/api/retail/docs, GET",
        "/api/retail/docs.yaml, GET",
        "/api/retail/**, GET"
    };

}
