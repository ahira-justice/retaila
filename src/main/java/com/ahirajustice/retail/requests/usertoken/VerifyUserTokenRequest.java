package com.ahirajustice.retail.requests.usertoken;

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

    private String username;

    private String tokenType;

    private String token;

}
