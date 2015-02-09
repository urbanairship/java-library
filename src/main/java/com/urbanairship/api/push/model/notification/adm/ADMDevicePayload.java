/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.adm;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.Interactive;

import java.util.Map;

public final class ADMDevicePayload extends PushModelObject implements DevicePayloadOverride {
    private final Optional<String> alert;
    private final Optional<String> consolidationKey;
    private final Optional<Integer> expiresAfter;
    private final Optional<ImmutableMap<String, String>> extra;
    private final Optional<Interactive> interactive;

    private ADMDevicePayload(Optional<String> alert,
                             Optional<String> consolidationKey,
                             Optional<Integer> expiresAfter,
                             Optional<ImmutableMap<String, String>> extra,
                             Optional<Interactive> interactive) {
        this.alert = alert;
        this.consolidationKey = consolidationKey;
        this.expiresAfter = expiresAfter;
        this.extra = extra;
        this.interactive = interactive;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.AMAZON;
    }

    @Override
    public Optional<String> getAlert() {
        return alert;
    }

    public Optional<String> getConsolidationKey() {
        return consolidationKey;
    }

    public Optional<Integer> getExpiresAfter() {
        return expiresAfter;
    }

    public Optional<ImmutableMap<String, String>> getExtra() {
        return extra;
    }

    public Optional<Interactive> getInteractive() {
        return interactive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ADMDevicePayload that = (ADMDevicePayload)o;
        if (alert != null ? !alert.equals(that.alert) : that.alert != null) {
            return false;
        }
        if (consolidationKey != null ? !consolidationKey.equals(that.consolidationKey) : that.consolidationKey != null) {
            return false;
        }
        if (expiresAfter != null ? !expiresAfter.equals(that.expiresAfter) : that.expiresAfter != null) {
            return false;
        }
        if (extra != null ? !extra.equals(that.extra) : that.extra != null) {
            return false;
        }
        if (interactive != null ? !interactive.equals(that.interactive) : that.extra != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (alert != null ? alert.hashCode() : 0);
        result = 31 * result + (consolidationKey != null ? consolidationKey.hashCode() : 0);
        result = 31 * result + (expiresAfter != null ? expiresAfter.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        result = 31 * result + (interactive != null ? interactive.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private String alert = null;
        private String consolidationKey = null;
        private Integer expiresAfter = null;
        private ImmutableMap.Builder<String, String> extra = null;
        private Interactive interactive = null;

        private Builder() { }

        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public Builder setConsolidationKey(String consolidationKey) {
            this.consolidationKey = consolidationKey;
            return this;
        }

        public Builder setExpiresAfter(int value) {
            this.expiresAfter = value;
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

        public Builder setInteractive(Interactive value) {
            this.interactive = value;
            return this;
        }

        public ADMDevicePayload build() {
            return new ADMDevicePayload(Optional.fromNullable(alert),
                                        Optional.fromNullable(consolidationKey),
                                        Optional.fromNullable(expiresAfter),
                                        extra == null ? Optional.<ImmutableMap<String,String>>absent() : Optional.fromNullable(extra.build()),
                                        Optional.fromNullable(interactive));
        }
    }
}
