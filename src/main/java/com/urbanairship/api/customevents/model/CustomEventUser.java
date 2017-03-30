package com.urbanairship.api.customevents.model;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

public class CustomEventUser extends PushModelObject {

    private final CustomEventChannelType channelType;
    private final String channel;

    private CustomEventUser(Builder builder) {
        this.channelType = builder.channelType;
        this.channel = builder.channel;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public CustomEventChannelType getChannelType() {
        return channelType;
    }

    public String getChannel() {
        return channel;
    }

    public static class Builder {
        private String channel = null;
        private CustomEventChannelType channelType = null;

        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder setCustomEventChannelType(CustomEventChannelType channelType) {
            this.channelType = channelType;
            return this;
        }

        public CustomEventUser build() {
            Preconditions.checkNotNull(channelType, "'channelType' must not be null");
            Preconditions.checkNotNull(channel, "'channel' must not be null");

            return new CustomEventUser(this);
        }
    }
}
