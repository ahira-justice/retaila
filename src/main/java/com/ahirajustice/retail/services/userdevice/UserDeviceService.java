package com.ahirajustice.retail.services.userdevice;


import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.entities.UserDevice;
import com.ahirajustice.retail.enums.UserDeviceType;

public interface UserDeviceService {

    void authorizeUserDevice(String deviceId, String deviceType, User user);

    void removeUserDeviceAuthorization(UserDevice device);

    UserDevice verifyUserDeviceExists(String deviceId, UserDeviceType deviceType);

    UserDevice verifyUserDeviceExists(long id, UserDeviceType deviceType);

}
