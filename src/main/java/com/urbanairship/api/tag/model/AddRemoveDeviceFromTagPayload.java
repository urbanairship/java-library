package com.urbanairship.api.tag.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

public final class AddRemoveDeviceFromTagPayload extends PushModelObject {

    private final Optional<AddRemoveSet> ios_channels;
    private final Optional<AddRemoveSet> device_tokens;
    private final Optional<AddRemoveSet> device_pins;
    private final Optional<AddRemoveSet> apids;

    public static Builder newBuilder() {
        return new Builder();
    }

    private AddRemoveDeviceFromTagPayload(Optional<AddRemoveSet> ios_channels,
                                          Optional<AddRemoveSet> device_tokens,
                                          Optional<AddRemoveSet> device_pins,
                                          Optional<AddRemoveSet> apids) {
        this.ios_channels = ios_channels;
        this.device_tokens = device_tokens;
        this.device_pins = device_pins;
        this.apids = apids;
    }

    public Optional<AddRemoveSet> getIOSChannels() {
        return ios_channels;
    }

    public Optional<AddRemoveSet> getDeviceTokens() {
        return device_tokens;
    }

    public Optional<AddRemoveSet> getDevicePins() {
        return device_pins;
    }

    public Optional<AddRemoveSet> getApids() {
        return apids;
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
            Preconditions.checkArgument(!(ios_channels == null && device_tokens == null && device_pins == null && apids == null), "At least one of ios_channels, device_tokens, device_pins, or apids must be set");

            return new AddRemoveDeviceFromTagPayload(Optional.fromNullable(ios_channels), Optional.fromNullable(device_tokens), Optional.fromNullable(device_pins), Optional.fromNullable(apids));
        }
    }
}
