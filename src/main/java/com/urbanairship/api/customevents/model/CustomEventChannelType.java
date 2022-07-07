package com.urbanairship.api.customevents.model;

import java.util.Optional;

public enum CustomEventChannelType {
    IOS_CHANNEL("ios_channel"),
    ANDROID_CHANNEL("android_channel"),
    AMAZON_CHANNEL("amazon_channel");

    private final String identifier;

    private CustomEventChannelType() {
        this(null);
    }

    private CustomEventChannelType(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<CustomEventChannelType> find(String identifier) {
        for (CustomEventChannelType customEventChannelType : values()) {
            if (customEventChannelType.getIdentifier().equals(identifier)) {
                return Optional.of(customEventChannelType);
            }
        }

        return Optional.empty();
    }

    public String getIdentifier() {
        return identifier;
    }
}
