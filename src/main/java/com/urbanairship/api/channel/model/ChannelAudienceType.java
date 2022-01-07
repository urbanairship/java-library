package com.urbanairship.api.channel.model;

import java.util.Optional;

public enum ChannelAudienceType {
    IOS_CHANNEL("ios_channel"),
    ANDROID_CHANNEL("android_channel"),
    AMAZON_CHANNEL("amazon_channel"),
    WEB_CHANNEL("web_channel"),
    CHANNEL("channel"),
    EMAIL_ADDRESS("email_address"),
    NAMED_USER_ID("named_user_id");

    private final String identifier;

    private ChannelAudienceType(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<ChannelAudienceType> find (String identifier) {
        for (ChannelAudienceType audienceType : values()) {
            if (audienceType.getIdentifier().equals(identifier)) {
                return Optional.ofNullable(audienceType);
            }
        }

        return Optional.empty();
    }

    public String getIdentifier() {
        return identifier;
    }
}
