package com.ahirajustice.retail.entities;

import com.ahirajustice.retail.enums.UserTokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_tokens")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "token", "tokenType" }) })
public class UserToken extends BaseEntity {

    @Column(nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserTokenType tokenType;

    @Column(nullable = false)
    private LocalDateTime expiry;

    @Column(nullable = false)
    private boolean isValid;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

}
