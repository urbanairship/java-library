/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.information.model;


import com.google.common.base.Optional;

public enum DeviceType {

    IOS("ios"),
    ADM("amazon"),
    ANDROID("android");

    private final String identifier;

    private DeviceType(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<DeviceType> find(String identifier) {
        for (DeviceType deviceType : values()) {
            if (deviceType.getIdentifier().equals(identifier)) {
                return Optional.of(deviceType);
            }
        }

        return Optional.absent();
    }

    public String getIdentifier() {
        return identifier;
    }

}