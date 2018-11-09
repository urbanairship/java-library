/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public final class DeviceTypeData extends PushModelObject {
    private final Optional<ImmutableSet<DeviceType>> deviceTypes;

    private DeviceTypeData(Optional<ImmutableSet<DeviceType>> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static DeviceTypeData of(DeviceType... deviceTypes) {
        DeviceTypeData.Builder builder = DeviceTypeData.newBuilder();
        for (DeviceType p : deviceTypes) {
            builder.addDeviceType(p);
        }
        return builder.build();
    }

    public Optional<ImmutableSet<DeviceType>> getDeviceTypes() {
        return deviceTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceTypeData that = (DeviceTypeData) o;
        return Objects.equal(deviceTypes, that.deviceTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(deviceTypes);
    }

    @Override
    public String toString() {
        return "DeviceTypeData{" +
            "deviceTypes=" + deviceTypes +
            '}';
    }

    public static class Builder {
        private ImmutableSet.Builder<DeviceType> deviceTypes = null;

        private Builder() { }

        public Builder addDeviceType(DeviceType deviceType) {
            if (deviceTypes == null) {
                deviceTypes = ImmutableSet.builder();
            }
            this.deviceTypes.add(deviceType);
            return this;
        }

        public Builder addAllDeviceTypes(Iterable<DeviceType> deviceTypes) {
            if (this.deviceTypes == null) {
                this.deviceTypes = ImmutableSet.builder();
            }
            this.deviceTypes.addAll(deviceTypes);
            return this;
        }

        public DeviceTypeData build() {
            return new DeviceTypeData(deviceTypes == null
                                    ? Optional.<ImmutableSet<DeviceType>>absent()
                                    : Optional.fromNullable(deviceTypes.build()));
        }
    }
}
