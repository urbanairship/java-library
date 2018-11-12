/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import java.util.Objects;

public final class DeviceType {

    public static final DeviceType AMAZON = new DeviceType(PlatformType.NATIVE, "amazon");
    public static final DeviceType ANDROID = new DeviceType(PlatformType.NATIVE, "android");
    public static final DeviceType IOS = new DeviceType(PlatformType.NATIVE, "ios");
    public static final DeviceType WEB = new DeviceType(PlatformType.NATIVE, "web");
    public static final DeviceType WNS = new DeviceType(PlatformType.NATIVE, "wns");
    public static final DeviceType EMAIL = new DeviceType(PlatformType.NATIVE, "email");

    public static final ImmutableSet<DeviceType> TYPES = ImmutableSet.<DeviceType>builder()
            .add(AMAZON)
            .add(ANDROID)
            .add(IOS)
            .add(WEB)
            .add(WNS)
            .add(EMAIL)
            .build();

    private enum PlatformType {
        NATIVE,
        OPEN
    }

    private static final String OPEN_PLATFORM_NAMESPACE = "open::";

    public static DeviceType open(String platformName) {
        return new DeviceType(PlatformType.OPEN, OPEN_PLATFORM_NAMESPACE + platformName);
    }

    private final String identifier;
    private final PlatformType platformType;

    private DeviceType(PlatformType platformType, String identifier) {
        this.platformType = platformType;
        this.identifier = identifier;
    }

    public boolean isOpenPlatform() {
        return platformType.equals(PlatformType.OPEN);
    }

    public String getIdentifier() {
        return identifier;
    }

    public static Optional<DeviceType> find(String identifier) {
        for (DeviceType deviceType : TYPES) {
            if (deviceType.getIdentifier().equals(identifier)) {
                return Optional.of(deviceType);
            }
        }

        if (identifier.contains(OPEN_PLATFORM_NAMESPACE)) {
            return Optional.of(new DeviceType(PlatformType.OPEN, identifier));
        } else {
            return Optional.absent();
        }
    }

    @Override
    public String toString() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceType that = (DeviceType) o;
        return Objects.equals(identifier, that.identifier) && platformType == that.platformType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, platformType);
    }

}
