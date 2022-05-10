package com.ahirajustice.retail.requests.user;

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
public class UserCreateRequest {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
