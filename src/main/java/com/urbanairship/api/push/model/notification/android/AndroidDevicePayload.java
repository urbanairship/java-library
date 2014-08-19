/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.android;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;

import java.util.Map;

public final class AndroidDevicePayload extends PushModelObject implements DevicePayloadOverride {

    private final Optional<String> alert;
    private final Optional<String> collapseKey;
    private final Optional<PushExpiry> timeToLive;
    private final Optional<Boolean> delayWhileIdle;
    private final Optional<ImmutableMap<String, String>> extra;

    private AndroidDevicePayload(Optional<String> alert,
                                 Optional<String> collapseKey,
                                 Optional<PushExpiry> timeToLive,
                                 Optional<Boolean> delayWhileIdle,
                                 Optional<ImmutableMap<String, String>> extra) {
        this.alert = alert;
        this.collapseKey = collapseKey;
        this.timeToLive = timeToLive;
        this.delayWhileIdle = delayWhileIdle;
        this.extra = extra;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.ANDROID;
    }

    @Override
    public Optional<String> getAlert() {
        return alert;
    }


    public Optional<String> getCollapseKey() {
        return collapseKey;
    }

    public Optional<PushExpiry> getTimeToLive() {
        return timeToLive;
    }

    public Optional<Boolean> getDelayWhileIdle() {
        return delayWhileIdle;
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

        AndroidDevicePayload that = (AndroidDevicePayload)o;
        if (alert != null ? !alert.equals(that.alert) : that.alert != null) {
            return false;
        }
        if (collapseKey != null ? !collapseKey.equals(that.collapseKey) : that.collapseKey != null) {
            return false;
        }
        if (timeToLive != null ? !timeToLive.equals(that.timeToLive) : that.timeToLive != null) {
            return false;
        }
        if (delayWhileIdle != null ? !delayWhileIdle.equals(that.delayWhileIdle) : that.delayWhileIdle != null) {
            return false;
        }
        if (extra != null ? !extra.equals(that.extra) : that.extra != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (alert != null ? alert.hashCode() : 0);
        result = 31 * result + (collapseKey != null ? collapseKey.hashCode() : 0);
        result = 31 * result + (timeToLive != null ? timeToLive.hashCode() : 0);
        result = 31 * result + (delayWhileIdle != null ? delayWhileIdle.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private String alert = null;
        private String collapseKey = null;
        private PushExpiry timeToLive = null;
        private Boolean delayWhileIdle = null;
        private ImmutableMap.Builder<String, String> extra = null;

        private Builder() { }

        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public Builder setCollapseKey(String collapseKey) {
            this.collapseKey = collapseKey;
            return this;
        }

        public Builder setTimeToLive(PushExpiry value) {
            this.timeToLive = value;
            return this;
        }

        public Builder setDelayWhileIdle(boolean value) {
            this.delayWhileIdle = value;
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

        public AndroidDevicePayload build() {
            return new AndroidDevicePayload(Optional.fromNullable(alert),
                                            Optional.fromNullable(collapseKey),
                                            Optional.fromNullable(timeToLive),
                                            Optional.fromNullable(delayWhileIdle),
                                            extra == null ? Optional.<ImmutableMap<String,String>>absent() : Optional.fromNullable(extra.build()));
        }
    }
}
