package com.ahirajustice.retail.dtos.auth;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    
    private String username;
    private Date expiry;

}
