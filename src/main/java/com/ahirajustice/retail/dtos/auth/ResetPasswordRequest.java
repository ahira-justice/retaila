package com.ahirajustice.retail.dtos.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

    private String username;

    private String password;

    private String token;

}
