package com.ahirajustice.retaila.viewmodels.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String accessToken;
    private String tokenType;

}