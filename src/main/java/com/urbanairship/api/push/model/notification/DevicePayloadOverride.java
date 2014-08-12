package com.urbanairship.api.push.model.notification;

import com.urbanairship.api.push.model.DeviceType;
import com.google.common.base.Optional;

public interface DevicePayloadOverride {
    DeviceType getDeviceType();
    Optional<String> getAlert();
}
