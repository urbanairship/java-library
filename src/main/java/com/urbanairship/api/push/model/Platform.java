/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Function;
import com.google.common.base.Optional;

public enum Platform {

    IOS("ios"),
    WNS("wns"),
    MPNS("mpns"),
    ANDROID("android"),
    BLACKBERRY("blackberry"),
    ADM("adm");

    private final String identifier;

    private Platform(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static Optional<Platform> find(String id) {
        return fromIdentifierFunction.apply(id);
    }

    public static final Function<String, Optional<Platform>> fromIdentifierFunction = new Function<String, Optional<Platform>>() {
        @Override
        public Optional<Platform> apply(String identifier) {
            for (Platform platform : values()) {
                if (platform.getIdentifier().equals(identifier)) {
                    return Optional.of(platform);
                }
            }

            return Optional.absent();
        }
    };
}
