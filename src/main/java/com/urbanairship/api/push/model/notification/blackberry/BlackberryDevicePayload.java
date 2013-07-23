/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.blackberry;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;

public final class BlackberryDevicePayload extends PushModelObject implements DevicePayloadOverride {

    private final Optional<String> alert;
    private final Optional<String> body;
    private final Optional<String> contentType;

    private BlackberryDevicePayload(Optional<String> alert,
                                    Optional<String> body,
                                    Optional<String> contentType) {
        this.alert = alert;
        this.body = body;
        this.contentType = contentType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public Platform getPlatform() {
        return Platform.BLACKBERRY;
    }

    @Override
    public Optional<String> getAlert() {
        return alert;
    }


    public Optional<String> getBody() {
        return body;
    }

    public Optional<String> getContentType() {
        return contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlackberryDevicePayload that = (BlackberryDevicePayload)o;
        if (alert != null ? !alert.equals(that.alert) : that.alert != null) {
            return false;
        }
        if (body != null ? !body.equals(that.body) : that.body != null) {
            return false;
        }
        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (alert != null ? alert.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private String alert = null;
        private String body = null;
        private String contentType = null;

        private Builder() { }

        public Builder setAlert(String value) {
            this.alert = value;
            return this;
        }

        public Builder setBody(String value) {
            this.body = value;
            return this;
        }

        public Builder setContentType(String value) {
            this.contentType = value;
            return this;
        }

        public BlackberryDevicePayload build() {

            return new BlackberryDevicePayload(Optional.fromNullable(alert),
                                               Optional.fromNullable(body),
                                               Optional.fromNullable(contentType));
        }
    }
}
