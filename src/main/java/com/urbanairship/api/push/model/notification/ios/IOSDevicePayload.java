package com.urbanairship.api.push.model.notification.ios;

import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

public final class IOSDevicePayload extends PushModelObject implements DevicePayloadOverride {

    private Optional<IOSAlertData> alert;
    private Optional<ImmutableMap<String, String>> extra;
    private Optional<String> sound;
    private Optional<IOSBadgeData> badge;
    private Optional<Boolean> contentAvailable;

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

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public Platform getPlatform() {
        return Platform.IOS;
    }

    @Override
    public Optional<String> getAlert() {
        return alert.isPresent() ? alert.get().getBody() : Optional.<String>absent();
    }

    public Optional<IOSAlertData> getAlertData() {
        return alert;
    }

    public Optional<String> getSound() {
        return sound;
    }

    public Optional<IOSBadgeData> getBadge() {
        return badge;
    }

    public Optional<Boolean> getContentAvailable() {
        return contentAvailable;
    }

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

        public Builder setAlert(String alert) {
            this.alert = IOSAlertData.newBuilder()
                .setBody(alert)
                .build();
            return this;
        }

        public Builder setAlert(IOSAlertData alert) {
            this.alert = alert;
            return this;
        }

        public Builder setSound(String sound) {
            this.sound = sound;
            return this;
        }

        public Builder setBadge(IOSBadgeData badge) {
            this.badge = badge;
            return this;
        }

        public Builder setContentAvailable(boolean value) {
            this.contentAvailable = value;
            return this;
        }

        public Builder addExtraEntry(String key, String value) {
            if (extra == null) {
                extra = ImmutableMap.builder();
            }
            this.extra.put(key, value);
            return this;
        }

        public Builder addAllExtraEntries(Map<String, String> entries) {
            if (extra == null) {
                extra = ImmutableMap.builder();
            }
            this.extra.putAll(entries);
            return this;
        }

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
