/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

public final class DeviceType {

    public static final DeviceType ANDROID = new DeviceType("android");
    public static final DeviceType IOS = new DeviceType("ios");
    public static final DeviceType AMAZON = new DeviceType("amazon");
    public static final DeviceType WEB = new DeviceType("web");
    public static final DeviceType WNS = new DeviceType("wns");

    public static final ImmutableSet<DeviceType> TYPES = ImmutableSet.<DeviceType>builder()
            .add(ANDROID)
            .add(IOS)
            .add(AMAZON)
            .add(WEB)
            .add(WNS)
            .build();

    public static DeviceType open(String type) {
        return new DeviceType("open::" + type);
    }

    private final String identifier;

    private DeviceType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static Optional<DeviceType> find(String id) {
        return fromIdentifierFunction.apply(id);
    }

    @Override
    public String toString() {
        return identifier;
    }

    public static final Function<String, Optional<DeviceType>> fromIdentifierFunction = new Function<String, Optional<DeviceType>>() {
        @Override
        public Optional<DeviceType> apply(String identifier) {
            for (DeviceType deviceType : TYPES) {
                if (deviceType.getIdentifier().equals(identifier)) {
                    return Optional.of(deviceType);
                }
            }

            if (identifier.contains("open::")) {
                return Optional.of(new DeviceType(identifier));
            }

            return Optional.absent();
        }
    };
}