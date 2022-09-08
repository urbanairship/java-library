/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification;

import com.urbanairship.api.push.model.DeviceType;

public interface DevicePayloadOverride {
    DeviceType getDeviceType();
    Object getAlert();
}
