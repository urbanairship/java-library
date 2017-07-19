/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Function;
import com.google.common.base.Optional;

public enum DeviceType {

    IOS("ios"),
    WNS("wns"),
    ANDROID("android"),
    AMAZON("amazon"),
    WEB("web");

    public static DeviceType first() {
        return IOS;
    }

    public static DeviceType last() {
        return WEB;
    }

    private final String identifier;

    DeviceType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static Optional<DeviceType> find(String id) {
        return fromIdentifierFunction.apply(id);
    }

    public static final Function<String, Optional<DeviceType>> fromIdentifierFunction = new Function<String, Optional<DeviceType>>() {
        @Override
        public Optional<DeviceType> apply(String identifier) {
            for (DeviceType deviceType : values()) {
                if (deviceType.getIdentifier().equals(identifier)) {
                    return Optional.of(deviceType);
                }
            }

            return Optional.absent();
        }
    };
}
