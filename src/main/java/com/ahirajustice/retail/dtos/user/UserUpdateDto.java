package com.ahirajustice.retail.dtos.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    private String email;
    private String firstName;
    private String lastName;

}
