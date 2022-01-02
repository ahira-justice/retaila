package com.ahirajustice.retail.dtos.usertoken;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyUserTokenRequest {

    @NotNull(message = "username is required")
    private String username;
    @NotNull(message = "tokenType is required")
    private String tokenType;
    @NotNull(message = "token is required")
    private String token;

}
