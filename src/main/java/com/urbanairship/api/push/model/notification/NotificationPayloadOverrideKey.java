/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification;

import com.urbanairship.api.push.model.DeviceType;

public class NotificationPayloadOverrideKey {

    private final DeviceType deviceType;
    private final Class<? extends DevicePayloadOverride> clazz;

    public NotificationPayloadOverrideKey(DeviceType deviceType, Class<? extends DevicePayloadOverride> clazz) {
        this.deviceType = deviceType;
        this.clazz = clazz;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public Class<? extends DevicePayloadOverride> getOverrideClass() {
        return clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificationPayloadOverrideKey that = (NotificationPayloadOverrideKey) o;

        if (clazz != null ? !clazz.equals(that.clazz) : that.clazz != null) {
            return false;
        }
        if (deviceType != that.deviceType) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = deviceType != null ? deviceType.hashCode() : 0;
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NotificationPayloadOverrideKey{" +
                "deviceType=" + deviceType +
                ", clazz=" + clazz +
                '}';
    }
}
