package com.ahirajustice.retaila.repositories;


import com.ahirajustice.retaila.entities.UserDevice;
import com.ahirajustice.retaila.enums.UserDeviceType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    List<UserDevice> findByUser_IdAndIsAuthorizedTrue(long userId, Pageable pageable);

    Optional<UserDevice> findByDeviceIdAndDeviceType(String deviceId, UserDeviceType deviceType);

    Optional<UserDevice> findByIdAndDeviceType(long id, UserDeviceType deviceType);

}
