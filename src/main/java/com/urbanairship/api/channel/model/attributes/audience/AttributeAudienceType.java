package com.urbanairship.api.channel.model.attributes.audience;

import java.util.Optional;

public enum AttributeAudienceType {
    IOS_CHANNEL("ios_channel"),
    ANDROID_CHANNEL("android_channel"),
    AMAZON_CHANNEL("amazon_channel"),
    WEB_CHANNEL("web_channel"),
    CHANNEL("channel"),
    EMAIL_ADDRESS("email_address"),
    NAMED_USER_ID("named_user_id");

    private final String identifier;

    private AttributeAudienceType(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<AttributeAudienceType> find (String identifier) {
        for (AttributeAudienceType audienceType : values()) {
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
