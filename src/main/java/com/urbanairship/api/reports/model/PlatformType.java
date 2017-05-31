/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Optional;

public enum PlatformType {

    IOS("ios"),
    AMAZON("amazon"),
    ANDROID("android"),
    WEB("web"),
    ALL("all"),
    VARIANT("variant");

    private final String identifier;

    private PlatformType(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<PlatformType> find(String identifier) {
        for (PlatformType deviceType : values()) {
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
