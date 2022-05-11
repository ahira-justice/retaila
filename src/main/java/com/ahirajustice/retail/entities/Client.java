package com.ahirajustice.retail.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clients")
public class Client extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String identifier;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String secret;

    @Column(nullable = false)
    private String adminEmail;

    @Column(nullable = false)
    private boolean isActive;

}
