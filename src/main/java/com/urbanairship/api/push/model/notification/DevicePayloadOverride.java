/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.DeviceType;

public interface DevicePayloadOverride {
    DeviceType getDeviceType();
    Optional<String> getAlert();
}
