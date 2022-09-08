/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.model;

import java.util.Optional;

/**
 * Enum of channel types
 */
public enum ChannelType {

    IOS("ios"),
    ADM("amazon"),
    ANDROID("android"),
    WEB("web"),
    OPEN("open"),
    EMAIL("email"),
    SMS("sms");

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

        return Optional.empty();
    }

    public String getIdentifier() {
        return identifier;
    }

}
