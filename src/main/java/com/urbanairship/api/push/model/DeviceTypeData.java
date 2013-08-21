/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * Represents the different deviceTypes available.
 */
public final class DeviceTypeData extends PushModelObject {
    private final boolean all;
    private final Optional<ImmutableSet<DeviceType>> deviceTypes;

    private DeviceTypeData(boolean all, Optional<ImmutableSet<DeviceType>> deviceTypes) {
        this.all = all;
        this.deviceTypes = deviceTypes;
    }

    /**
     * New DeviceTypeData builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Factory method for creating a DeviceTypeData object that sends to all
     * available messaging deviceTypes.
     * @return DeviceTypeData
     */
    public static DeviceTypeData all() {
        return DeviceTypeData.newBuilder()
            .setAll(true)
            .build();
    }

    /**
     * Factory method for creating a DeviceTypeData object with all the deviceTypes
     * that were passed in
     * @param deviceTypes DeviceType to send to.
     * @return DeviceTypeData
     */
    public static DeviceTypeData of(DeviceType... deviceTypes) {
        DeviceTypeData.Builder builder = DeviceTypeData.newBuilder();
        for (DeviceType p : deviceTypes) {
            builder.addDeviceType(p);
        }
        return builder.build();
    }

    /**
     * Returns true if set for all deviceTypes.
     * @return is all deviceTypes
     */
    public boolean isAll() {
        return all;
    }

    /**
     * Return a set of all deviceTypes this object is targeting.
     * @return deviceTypes
     */
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

    /**
     * DeviceTypeData Builder
     */
    public static class Builder {

        private boolean all = false;
        private ImmutableSet.Builder<DeviceType> deviceTypes = null;

        private Builder() { }

        /**
         * New DeviceTypeData builder set for all deviceTypes.
         * @param value true or false for set all
         * @return Builder
         */
        public Builder setAll(boolean value) {
            all = value;
            return this;
        }

        /**
         * Add a deviceType to send to.
         * @param deviceType DeviceType
         * @return Builder
         */
        public Builder addDeviceType(DeviceType deviceType) {
            if (deviceTypes == null) {
                deviceTypes = ImmutableSet.builder();
            }
            this.deviceTypes.add(deviceType);
            return this;
        }

        /**
         * Add all deviceTypes from the iterable.
         * @param deviceTypes DeviceType iterable.
         * @return Builder
         */
        public Builder addAllDeviceTypes(Iterable<DeviceType> deviceTypes) {
            if (this.deviceTypes == null) {
                this.deviceTypes = ImmutableSet.builder();
            }
            this.deviceTypes.addAll(deviceTypes);
            return this;
        }

        /**
         * Build a DeviceTypeData object.
         * @return DeviceTypeData
         */
        public DeviceTypeData build() {
            Preconditions.checkArgument(!(all && (deviceTypes != null)), "'device_types' cannot be both 'all' and a list of device types.");
            return new DeviceTypeData(all,
                                    deviceTypes == null
                                    ? Optional.<ImmutableSet<DeviceType>>absent()
                                    : Optional.fromNullable(deviceTypes.build()));
        }
    }
}
