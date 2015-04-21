/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.List;

public final class IOSAlertData extends PushModelObject {

    private final Optional<String> body;
    private final Optional<String> actionLocKey;
    private final Optional<String> locKey;
    private final Optional<List<String>> locArgs;
    private final Optional<String> launchImage;

    private IOSAlertData(Optional<String> body,
                         Optional<String> actionLocKey,
                         Optional<String> locKey,
                         Optional<List<String>> locArgs,
                         Optional<String> launchImage) {
        this.body = body;
        this.actionLocKey = actionLocKey;
        this.locKey = locKey;
        this.locArgs = locArgs;
        this.launchImage = launchImage;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isCompound() {
        return actionLocKey.isPresent()
            || locKey.isPresent()
            || locArgs.isPresent()
            || launchImage.isPresent();
    }

    public Optional<String> getBody() {
        return body;
    }

    public Optional<String> getActionLocKey() {
        return actionLocKey;
    }

    public Optional<String> getLocKey() {
        return locKey;
    }

    public Optional<List<String>> getLocArgs() {
        return locArgs;
    }

    public Optional<String> getLaunchImage() {
        return launchImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IOSAlertData that = (IOSAlertData)o;
        if (body != null ? !body.equals(that.body) : that.body != null) {
            return false;
        }
        if (actionLocKey != null ? !actionLocKey.equals(that.actionLocKey) : that.actionLocKey != null) {
            return false;
        }
        if (locKey != null ? !locKey.equals(that.locKey) : that.locKey != null) {
            return false;
        }
        if (locArgs != null ? !locArgs.equals(that.locArgs) : that.locArgs != null) {
            return false;
        }
        if (launchImage != null ? !launchImage.equals(that.launchImage) : that.launchImage != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (body != null ? body.hashCode() : 0);
        result = 31 * result + (actionLocKey != null ? actionLocKey.hashCode() : 0);
        result = 31 * result + (locKey != null ? locKey.hashCode() : 0);
        result = 31 * result + (locArgs != null ? locArgs.hashCode() : 0);
        result = 31 * result + (launchImage != null ? launchImage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IOSAlertData{" +
                "body=" + body +
                ", actionLocKey=" + actionLocKey +
                ", locKey=" + locKey +
                ", locArgs=" + locArgs +
                ", launchImage=" + launchImage +
                '}';
    }

    public static class Builder {
        private String body;
        private String actionLocKey;
        private String locKey;
        private List<String> locArgs;
        private String launchImage;

        private Builder() { }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setActionLocKey(String value) {
            this.actionLocKey = value;
            return this;
        }

        public Builder setLocKey(String value) {
            this.locKey = value;
            return this;
        }

        public Builder setLocArgs(List<String> value) {
            this.locArgs = value;
            return this;
        }

        public Builder setLaunchImage(String value) {
            this.launchImage = value;
            return this;
        }

        public IOSAlertData build() {
            return new IOSAlertData(Optional.fromNullable(body),
                                    Optional.fromNullable(actionLocKey),
                                    Optional.fromNullable(locKey),
                                    Optional.fromNullable(locArgs),
                                    Optional.fromNullable(launchImage));
        }
    }
}
