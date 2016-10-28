/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.model;

import com.google.common.base.Optional;

/**
 * Enum of channel types
 */
public enum ChannelType {

    IOS("ios"),
    ADM("amazon"),
    ANDROID("android");

    private final String identifier;

    private ChannelType() {
        this(null);
    }

    private ChannelType(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<ChannelType> find(String identifier) {
        for (ChannelType channelType : values()) {
            if (channelType.getIdentifier().equals(identifier)) {
                return Optional.of(channelType);
            }
        }

        return Optional.absent();
    }

    public String getIdentifier() {
        return identifier;
    }

}
