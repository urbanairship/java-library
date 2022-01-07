package com.urbanairship.api.nameduser.model;

import java.util.Optional;

public enum NamedUserUpdateDeviceType {

    IOS_CHANNEL("ios"),
    ANDROID_CHANNEL("android"),
    AMAZON_CHANNEL("amazon"),
    WEB_CHANNEL("web"),
    SMS("sms"),
    EMAIL_ADDRESS("email_address"),
    OPEN("open");

    private final String identifier;

    private NamedUserUpdateDeviceType(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<NamedUserUpdateDeviceType> find (String identifier) {
        for (NamedUserUpdateDeviceType audienceType : values()) {
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