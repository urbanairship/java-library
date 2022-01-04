package com.urbanairship.api.channel.model;

import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * Represents a Channel Uninstall payload for the Airship API.
 */
public class ChannelUninstallPayload extends ChannelModelObject {

    private final Set<ChannelUninstallDevice> channels;
    
    private ChannelUninstallPayload(Set<ChannelUninstallDevice> channels) {
        this.channels = channels;
    }

    /**
     * ChannelUninstallPayload Builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the list of channels to uninstall.
     *
     * @return Set of ChannelUninstallDevice
     */
    public Set<ChannelUninstallDevice> getChannels() {
        return channels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelUninstallPayload that = (ChannelUninstallPayload) o;
        return Objects.equal(channels, that.channels);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(channels);
    }

    @Override
    public String toString() {
        return "ChannelUninstallDevice{" +
                "channels=" + channels +
                '}';
    }

    public static class Builder {
        ImmutableSet.Builder<ChannelUninstallDevice> channelsBuilder = ImmutableSet.builder();

        /**
         * Set the Set of ChannelUninstallDevice.
         *
         * @param channels Set of ChannelUninstallDevice
         * @return Builder
         */
        public Builder setChannels(Set<ChannelUninstallDevice> channels) {
            channelsBuilder.addAll(channels);
            return this;
        }

        public ChannelUninstallPayload build() {
            ImmutableSet<ChannelUninstallDevice> channels = channelsBuilder.build();

            Preconditions.checkArgument(channels.size() > 0, "At least one channel must be set.");
            Preconditions.checkArgument(channels.size() <= 1000, "Maximum of 1000 channels per payload.");

            return new ChannelUninstallPayload(channels);
        }
    }
}
