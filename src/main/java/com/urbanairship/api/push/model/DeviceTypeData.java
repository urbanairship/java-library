/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public final class DeviceTypeData extends PushModelObject {
    private final boolean all;
    private final Optional<ImmutableSet<DeviceType>> deviceTypes;

    private DeviceTypeData(boolean all, Optional<ImmutableSet<DeviceType>> deviceTypes) {
        this.all = all;
        this.deviceTypes = deviceTypes;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static DeviceTypeData all() {
        return DeviceTypeData.newBuilder()
            .setAll(true)
            .build();
    }

    public static DeviceTypeData of(DeviceType... deviceTypes) {
        DeviceTypeData.Builder builder = DeviceTypeData.newBuilder();
        for (DeviceType p : deviceTypes) {
            builder.addDeviceType(p);
        }
        return builder.build();
    }

    public boolean isAll() {
        return all;
    }

    public Optional<ImmutableSet<DeviceType>> getDeviceTypes() {
        return deviceTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeviceTypeData that = (DeviceTypeData) o;

        if (all != that.all) {
            return false;
        }
        if (deviceTypes != null ? !deviceTypes.equals(that.deviceTypes) : that.deviceTypes != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (deviceTypes != null ? deviceTypes.hashCode() : 0);
        result = 31 * result + (all ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeviceTypeData{" +
            "all=" + all +
            ", deviceTypes=" + deviceTypes +
            '}';
    }

    public static class Builder {

        private boolean all = false;
        private ImmutableSet.Builder<DeviceType> deviceTypes = null;

        private Builder() { }

        public Builder setAll(boolean value) {
            all = value;
            return this;
        }

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
            Preconditions.checkArgument(!(all && (deviceTypes != null)), "'device_types' cannot be both 'all' and a list of device types.");
            return new DeviceTypeData(all,
                                    deviceTypes == null
                                    ? Optional.<ImmutableSet<DeviceType>>absent()
                                    : Optional.fromNullable(deviceTypes.build()));
        }
    }
}
