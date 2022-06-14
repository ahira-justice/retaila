package com.ahirajustice.retaila.services.userdevice;


import com.ahirajustice.retaila.entities.User;
import com.ahirajustice.retaila.entities.UserDevice;
import com.ahirajustice.retaila.enums.UserDeviceType;

public interface UserDeviceService {

    void authorizeUserDevice(String deviceId, String deviceType, User user);

    void removeUserDeviceAuthorization(UserDevice device);

    UserDevice verifyUserDeviceExists(String deviceId, UserDeviceType deviceType);

    UserDevice verifyUserDeviceExists(long id, UserDeviceType deviceType);

}
