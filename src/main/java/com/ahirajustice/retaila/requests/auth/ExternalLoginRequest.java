package com.ahirajustice.retaila.requests.auth;

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
public class ExternalLoginRequest {

    private String username;
    private String firstName;
    private String lastName;
    private int expires;

}
