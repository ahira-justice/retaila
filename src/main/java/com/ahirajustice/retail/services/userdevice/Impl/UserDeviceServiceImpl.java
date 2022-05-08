package com.ahirajustice.retail.services.userdevice.Impl;


import com.ahirajustice.retail.properties.AppProperties;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.entities.UserDevice;
import com.ahirajustice.retail.enums.UserDeviceType;
import com.ahirajustice.retail.exceptions.ValidationException;
import com.ahirajustice.retail.repositories.UserDeviceRepository;
import com.ahirajustice.retail.services.userdevice.UserDeviceService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDeviceServiceImpl implements UserDeviceService {

    private final AppProperties appProperties;
    private final UserDeviceRepository userDeviceRepository;

    @Override
    public void authorizeUserDevice(String deviceId, String deviceType, User user) {
        UserDevice device;

        if (!EnumUtils.isValidEnum(UserDeviceType.class, deviceType)) {
            throw new ValidationException("Invalid deviceType");
        }

        long authorizedUserDeviceCount = user.getUserDevices().stream().filter(UserDevice::isAuthorized).count();

        if (authorizedUserDeviceCount >= appProperties.getUserDeviceMaxCount()){
            long pageSize = authorizedUserDeviceCount - (appProperties.getUserDeviceMaxCount() - 1);
            List<UserDevice> userDevicesToDeauthorize = userDeviceRepository.findByUser_IdAndIsAuthorizedTrue(user.getId(), PageRequest.of(0, (int) pageSize, Sort.by("authorizedOn")));

            for (UserDevice userDevice : userDevicesToDeauthorize) {
                removeUserDeviceAuthorization(userDevice);
            }
        }

        Optional<UserDevice> deviceExists = userDeviceRepository.findByDeviceIdAndDeviceType(deviceId, UserDeviceType.valueOf(deviceType));

        if (deviceExists.isPresent()) {
            device = deviceExists.get();

            if (device.getUser().getId() != user.getId()) {
                throw new ValidationException("Device does not belong to user");
            }
        }
        else {
            device = UserDevice.builder()
                .deviceId(deviceId)
                .deviceType(UserDeviceType.valueOf(deviceType))
                .user(user)
                .build();
        }

        device.setAuthorized(true);
        device.setAuthorizedOn(LocalDateTime.now());

        userDeviceRepository.save(device);
    }

    @Override
    public void removeUserDeviceAuthorization(UserDevice device) {
        if (device == null || device.getId() == 0) {
            throw new IllegalArgumentException();
        }

        device.setAuthorized(false);
        device.setAuthorizedOn(null);

        userDeviceRepository.save(device);
    }

    @Override
    public UserDevice verifyUserDeviceExists(String deviceId, UserDeviceType deviceType) {
        Optional<UserDevice> deviceExists = userDeviceRepository.findByDeviceIdAndDeviceType(deviceId,
                deviceType);

        if (!deviceExists.isPresent()) {
            String message = String.format("User device with deviceId: '%s' does not exist", deviceId);
            throw new ValidationException(message);
        }

        return deviceExists.get();
    }

    @Override
    public UserDevice verifyUserDeviceExists(long userDeviceId, UserDeviceType deviceType) {
        Optional<UserDevice> deviceExists = userDeviceRepository.findByIdAndDeviceType(userDeviceId, deviceType);

        if (!deviceExists.isPresent()) {
            String message = String.format("User device with id: '%s' does not exist", userDeviceId);
            throw new ValidationException(message);
        }

        return deviceExists.get();
    }

}
