/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.mpns;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;

public final class MPNSDevicePayload extends PushModelObject implements DevicePayloadOverride {

    private final Optional<MPNSPush> body;
    private final Optional<String> alert;

    private MPNSDevicePayload(Optional<MPNSPush> body, Optional<String> alert) {
        this.body = body;
        this.alert = alert;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.MPNS;
    }

    @Override
    public Optional<String> getAlert() {
        return alert;
    }

    public MPNSPush.Type getType() {
        if (body.isPresent()) {
            return body.get().getType();
        } else {
            return MPNSPush.Type.TOAST;
        }
    }

    public Optional<MPNSPush> getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPNSDevicePayload that = (MPNSDevicePayload)o;
        if (body != null ? !body.equals(that.body) : that.body != null) {
            return false;
        }
        if (alert != null ? !alert.equals(that.alert) : that.alert != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (body != null ? body.hashCode() : 0);
        result = 31 * result + (alert != null ? alert.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private String alert;
        private MPNSPush body;

        private Builder() { }

        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public Builder setBody(MPNSPush body) {
            this.body = body;
            return this;
        }

        public MPNSDevicePayload build() {
            return new MPNSDevicePayload(Optional.fromNullable(body),
                                         Optional.fromNullable(alert));
        }
    }
}
