package com.urbanairship.api.push.model.notification.wns;

import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.google.common.base.Optional;

public final class WNSDevicePayload extends PushModelObject implements DevicePayloadOverride {

    private Optional<WNSPush> body;
    private Optional<String> alert;

    private WNSDevicePayload(Optional<WNSPush> body, Optional<String> alert) {
        this.body = body;
        this.alert = alert;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public DeviceType getPlatform() {
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
            return new WNSDevicePayload(Optional.fromNullable(body),
                                        Optional.fromNullable(alert));
        }
    }
}
