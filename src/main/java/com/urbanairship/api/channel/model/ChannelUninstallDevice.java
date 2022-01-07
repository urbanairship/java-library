package com.urbanairship.api.channel.model;

import com.google.common.base.Objects;

/**
 * Uninstall Typed Channel constructor.
 *
 */
public class ChannelUninstallDevice {
    private String channelId;
    private ChannelUninstallDeviceType type;

    public ChannelUninstallDevice(String channelId, ChannelUninstallDeviceType type) {
        this.channelId = channelId;
        this.type = type;
    }

    public String getChannelId(){
        return channelId;
    }

    public ChannelUninstallDeviceType getChannelType(){
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelUninstallDevice that = (ChannelUninstallDevice) o;
        return Objects.equal(channelId, that.channelId) && 
                Objects.equal(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(channelId, type);
    }

    @Override
    public String toString() {
        return "ChannelUninstallDevice{" +
                "channelId=" + channelId +
                "type=" + type +
                '}';
    }
}