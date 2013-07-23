/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification;

import com.urbanairship.api.push.model.Platform;
import com.google.common.base.Optional;

public interface DevicePayloadOverride {
    Platform getPlatform();
    Optional<String> getAlert();
}
