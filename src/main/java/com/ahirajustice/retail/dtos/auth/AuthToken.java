package com.ahirajustice.retail.dtos.auth;

import java.util.Date;

public class AuthToken {

    private String username;
    private Date expiry;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

}
