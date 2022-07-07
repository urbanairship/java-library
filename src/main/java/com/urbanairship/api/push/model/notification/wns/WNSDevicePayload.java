/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.wns;

import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;

import java.util.Optional;

public final class WNSDevicePayload extends PushModelObject implements DevicePayloadOverride {

    private final Optional<WNSPush> body;
    private final Optional<String> alert;

    private WNSDevicePayload(Optional<WNSPush> body, Optional<String> alert) {
        this.body = body;
        this.alert = alert;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.WNS;
    }

    @Override
    public Optional<String> getAlert() {
        return alert;
    }

    public WNSPush.Type getType() {
        if (body.isPresent()) {
            return body.get().getType();
        } else {
            return WNSPush.Type.TOAST;
        }
    }

    public Optional<WNSPush> getBody() {
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

        WNSDevicePayload that = (WNSDevicePayload)o;
        if (body != null ? !body.equals(that.body) : that.body != null) {
            return false;
        }

        return !(alert != null ? !alert.equals(that.alert) : that.alert != null);
    }

    @Override
    public int hashCode() {
        int result = (body != null ? body.hashCode() : 0);
        result = 31 * result + (alert != null ? alert.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private String alert;
        private WNSPush body;

        private Builder() { }

        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public Builder setBody(WNSPush body) {
            this.body = body;
            return this;
        }

        public WNSDevicePayload build() {
            return new WNSDevicePayload(Optional.ofNullable(body),
                                        Optional.ofNullable(alert));
        }
    }
}
