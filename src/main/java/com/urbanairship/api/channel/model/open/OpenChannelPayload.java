package com.urbanairship.api.channel.model.open;

import com.google.common.base.Objects;
import com.urbanairship.api.push.model.PushModelObject;

/**
 * Payload used to create open channels.
 */
public class OpenChannelPayload extends PushModelObject {
    private final Channel channel;

    /**
     * Create an open channel payload.
     * @param channel Channel
     */
    public OpenChannelPayload(Channel channel) {
        this.channel = channel;
    }

    /**
     * Get the channel object that is the object used to create or update channels on an open platform.
     * @return
     */
    public Channel getChannel() {
        return channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenChannelPayload that = (OpenChannelPayload) o;
        return Objects.equal(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(channel);
    }

    @Override
    public String toString() {
        return "OpenChannelPayload{" +
                "channel=" + channel +
                '}';
    }
}
