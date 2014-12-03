/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.tag.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

public final class AddRemoveDeviceFromTagPayload extends PushModelObject {

    private final Optional<AddRemoveSet> iosChannels;
    private final Optional<AddRemoveSet> deviceTokens;
    private final Optional<AddRemoveSet> devicePins;
    private final Optional<AddRemoveSet> apids;

    public static Builder newBuilder() {
        return new Builder();
    }

    private AddRemoveDeviceFromTagPayload(Optional<AddRemoveSet> iosChannels,
                                          Optional<AddRemoveSet> deviceTokens,
                                          Optional<AddRemoveSet> devicePins,
                                          Optional<AddRemoveSet> apids) {
        this.iosChannels = iosChannels;
        this.deviceTokens = deviceTokens;
        this.devicePins = devicePins;
        this.apids = apids;
    }

    public Optional<AddRemoveSet> getIOSChannels() {
        return iosChannels;
    }

    public Optional<AddRemoveSet> getDeviceTokens() {
        return deviceTokens;
    }

    public Optional<AddRemoveSet> getDevicePins() {
        return devicePins;
    }

    public Optional<AddRemoveSet> getApids() {
        return apids;
    }

    @Override
    public String toString() {
        return "AddRemoveDeviceFromTagPayload{" +
                "iosChannels=" + iosChannels +
                ", deviceTokens=" + deviceTokens +
                ", devicePins=" + devicePins +
                ", apids=" + apids +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(iosChannels, deviceTokens, devicePins, apids);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AddRemoveDeviceFromTagPayload other = (AddRemoveDeviceFromTagPayload) obj;
        return Objects.equal(this.iosChannels, other.iosChannels) && Objects.equal(this.deviceTokens, other.deviceTokens) && Objects.equal(this.devicePins, other.devicePins) && Objects.equal(this.apids, other.apids);
    }


    public static class Builder {
        private AddRemoveSet ios_channels = null;
        private AddRemoveSet device_tokens = null;
        private AddRemoveSet device_pins = null;
        private AddRemoveSet apids = null;

        private Builder() { }

        public Builder setIOSChannels(AddRemoveSet value) {
            this.ios_channels = value;
            return this;
        }

        public Builder setDeviceTokens(AddRemoveSet value) {
            this.device_tokens = value;
            return this;
        }

        public Builder setDevicePins(AddRemoveSet value) {
            this.device_pins = value;
            return this;
        }

        public Builder setApids(AddRemoveSet value) {
            this.apids = value;
            return this;
        }

        public AddRemoveDeviceFromTagPayload build() {
            Preconditions.checkArgument(!(ios_channels == null && device_tokens == null && device_pins == null && apids == null), "At least one of iosChannels, deviceTokens, devicePins, or apids must be set");

            return new AddRemoveDeviceFromTagPayload(Optional.fromNullable(ios_channels), Optional.fromNullable(device_tokens), Optional.fromNullable(device_pins), Optional.fromNullable(apids));
        }
    }
}
