package com.urbanairship.api.push.model.notification;

import com.urbanairship.api.push.model.Platform;

public class NotificationPayloadOverrideKey {

    private final Platform platform;
    private final Class<? extends DevicePayloadOverride> clazz;

    public NotificationPayloadOverrideKey(Platform platform, Class<? extends DevicePayloadOverride> clazz) {
        this.platform = platform;
        this.clazz = clazz;
    }

    public Platform getPlatform() {
        return platform;
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
        if (platform != that.platform) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = platform != null ? platform.hashCode() : 0;
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NotificationPayloadOverrideKey{" +
                "platform=" + platform +
                ", clazz=" + clazz +
                '}';
    }
}
