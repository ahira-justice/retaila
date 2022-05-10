package com.ahirajustice.retail.viewmodels.client;

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
public class ClientViewModel {

    private String identifier;
    private String name;
    private String adminEmail;
    private boolean isActive;

}
