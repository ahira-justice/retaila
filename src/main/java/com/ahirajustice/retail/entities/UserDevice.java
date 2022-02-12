package com.ahirajustice.retail.entities;


import com.ahirajustice.retail.enums.UserDeviceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Entity(name = "user_devices")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "deviceId", "deviceType" }) })
public class UserDevice extends BaseEntity{

    @Column(nullable = false)
    private String deviceId;

    @Column(nullable = false)
    private boolean isAuthorized;

    private LocalDateTime authorizedOn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserDeviceType deviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}