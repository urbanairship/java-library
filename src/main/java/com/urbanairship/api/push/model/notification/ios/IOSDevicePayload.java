/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.ios;

import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * IOSDevicePayload for iOS specific push messages.
 */
public final class IOSDevicePayload extends PushModelObject implements DevicePayloadOverride {

    private final Optional<IOSAlertData> alert;
    private final Optional<ImmutableMap<String, String>> extra;
    private final Optional<String> sound;
    private final Optional<IOSBadgeData> badge;
    private final Optional<Boolean> contentAvailable;

    private IOSDevicePayload(Optional<IOSAlertData> alert,
                             Optional<String> sound,
                             Optional<IOSBadgeData> badge,
                             Optional<Boolean> contentAvailable,
                             Optional<ImmutableMap<String, String>> extra) {
        this.alert = alert;
        this.sound = sound;
        this.badge = badge;
        this.contentAvailable = contentAvailable;
        this.extra = extra;
    }

    /**
     * Get an IOSPayloadBuilder
     * @return IOSPayloadBuilder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the deviceType.
     * @return deviceType
     */
    @Override
    public DeviceType getDeviceType() {
        return DeviceType.IOS;
    }

    /**
     * Get the alert if present.
     * @return alert
     */
    @Override
    public Optional<String> getAlert() {
        return alert.isPresent() ? alert.get().getBody() : Optional.<String>absent();
    }

    /**
     * Get the IOSAlertData
     * @return IOSAlertData
     */
    public Optional<IOSAlertData> getAlertData() {
        return alert;
    }

    /**
     * Get the sound file name
     * @return sound file name
     */
    public Optional<String> getSound() {
        return sound;
    }

    /**
     * Get IOSBadgeData
     * @return IOSBadgeData
     */
    public Optional<IOSBadgeData> getBadge() {
        return badge;
    }

    /**
     * Get the content available boolean value
     * @return content available
     */
    public Optional<Boolean> getContentAvailable() {
        return contentAvailable;
    }

    /**
     * Get a Map of the extra key value pairs
     * @return key value pairs
     */
    public Optional<ImmutableMap<String, String>> getExtra() {
        return extra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IOSDevicePayload that = (IOSDevicePayload)o;
        if (alert != null ? !alert.equals(that.alert) : that.alert != null) {
            return false;
        }
        if (extra != null ? !extra.equals(that.extra) : that.extra != null) {
            return false;
        }
        if (sound != null ? !sound.equals(that.sound) : that.sound != null) {
            return false;
        }
        if (badge != null ? !badge.equals(that.badge) : that.badge != null) {
            return false;
        }
        if (contentAvailable != null ? !contentAvailable.equals(that.contentAvailable) : that.contentAvailable != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (alert != null ? alert.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        result = 31 * result + (sound != null ? sound.hashCode() : 0);
        result = 31 * result + (badge != null ? badge.hashCode() : 0);
        result = 31 * result + (contentAvailable != null ? contentAvailable.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IOSDevicePayload{" +
                "alert=" + alert +
                ", extra=" + extra +
                ", sound=" + sound +
                ", badge=" + badge +
                ", contentAvailable=" + contentAvailable +
                '}';
    }

    public static class Builder {
        private IOSAlertData alert = null;
        private String sound = null;
        private IOSBadgeData badge = null;
        private Boolean contentAvailable = null;
        private ImmutableMap.Builder<String, String> extra = null;

        private Builder() { }

        /**
         * Create an IOSAlertData object with the given alert string. This is a
         * shortcut for setting an alert data object when no additional iOS
         * APNS payload options are needed.
         *
         * @param alert String alert
         * @return Builder
         */
        public Builder setAlert(String alert) {
            this.alert = IOSAlertData.newBuilder()
                .setBody(alert)
                .build();
            return this;
        }

        /**
         * Set the IOSAlertData object.
         * @param alert IOSAlertData
         * @return Builder
         */
        public Builder setAlert(IOSAlertData alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Set the filename for the sound. A matching sound file that meets
         * Apple requirements needs to reside on the device.
         * @param sound Sound file name
         * @return Builder
         */
        public Builder setSound(String sound) {
            this.sound = sound;
            return this;
        }

        /**
         * Set the badge data.
         * @param badge IOSBadgeData
         * @return Builder
         */
        public Builder setBadge(IOSBadgeData badge) {
            this.badge = badge;
            return this;
        }

        /**
         * Set the flag indicating content availability.
         * @param value Boolean for content availability.
         * @return Builder
         */
        public Builder setContentAvailable(boolean value) {
            this.contentAvailable = value;
            return this;
        }

        /**
         * Add an extra key value pair to the notification payload. Maximum
         * payload is 256 bytes.
         * @param key String key
         * @param value String value
         * @return Builder
         */
        public Builder addExtraEntry(String key, String value) {
            if (extra == null) {
                extra = ImmutableMap.builder();
            }
            this.extra.put(key, value);
            return this;
        }

        /**
         * Add key value pairs to payload. Maximum payload is 256 bytes.
         * @param entries Map of key value pairs
         * @return Builder.
         */
        public Builder addAllExtraEntries(Map<String, String> entries) {
            if (extra == null) {
                extra = ImmutableMap.builder();
            }
            this.extra.putAll(entries);
            return this;
        }

        /**
         * Build IOSDevicePayload
         * @return IOSDevicePayload
         */
        public IOSDevicePayload build() {
            // Yes, empty payloads are valid (for Passes)
            return new IOSDevicePayload(Optional.fromNullable(alert),
                                        Optional.fromNullable(sound),
                                        Optional.fromNullable(badge),
                                        Optional.fromNullable(contentAvailable),
                                        extra == null ? Optional.<ImmutableMap<String,String>>absent() : Optional.fromNullable(extra.build()));

        }
    }
}
