package com.ahirajustice.retail.dtos.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
