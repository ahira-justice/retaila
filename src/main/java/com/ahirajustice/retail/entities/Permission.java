package com.ahirajustice.retail.entities;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "permissions")
public class Permission extends BaseEntity {

    @Column(unique = true)
    private String name;
    
    private boolean isSystem;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

    public Permission(String name) {
        this.name = name;
    }

}
