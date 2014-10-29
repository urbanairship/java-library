package com.urbanairship.api.channel.registration.model;


import com.google.common.base.Optional;

public enum DeviceType {

    IOS("ios"),
    ADM("amazon"),
    ANDROID("android");

    private final String identifier;

    private DeviceType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static Optional<DeviceType> find(String identifier) {
        for (DeviceType deviceType : values()) {
            if (deviceType.getIdentifier().equals(identifier)) {
                return Optional.of(deviceType);
            }
        }

        return Optional.absent();
    }

}