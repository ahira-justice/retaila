package com.ahirajustice.retail.entities;

import com.ahirajustice.retail.enums.UserTokenType;
import lombok.*;

import javax.persistence.*;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserTokenType tokenType;

    @Column(nullable = false)
    private LocalDateTime expiry;

    @Column(nullable = false)
    private boolean isValid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
