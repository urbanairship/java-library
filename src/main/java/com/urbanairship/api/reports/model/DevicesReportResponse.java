package com.urbanairship.api.reports.model;

import com.google.common.collect.ImmutableMap;
import java.util.Objects;
import java.util.Optional;

public final class DevicesReportResponse {
    private Optional<ImmutableMap<String, DeviceTypeStats>> deviceStatsMap;

    private DevicesReportResponse() { this(Optional.empty()); }

    private DevicesReportResponse(Optional<ImmutableMap<String, DeviceTypeStats>> deviceStatsMap) {
        this.deviceStatsMap = deviceStatsMap;
    }

    /**
     * Get the map of devices and platform statistics associated with each device type.
     *
     * @return A Map of device names and their associated platform statistics
     */
    public Optional<ImmutableMap<String, DeviceTypeStats>> getDeviceTypeStatsMap() {
        return deviceStatsMap;
    }

    public static Builder newBuilder() { return new Builder(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevicesReportResponse that = (DevicesReportResponse) o;
        return Objects.equals(deviceStatsMap, that.deviceStatsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceStatsMap);
    }

    @Override
    public String toString() {
        return "DevicesReportResponse{" +
                "deviceStatsMap=" + deviceStatsMap +
                '}';
    }

    public static class Builder {
        private ImmutableMap.Builder<String, DeviceTypeStats> deviceStatsMap = ImmutableMap.builder();

        private Builder() {}

        /**
         * Add a mapping of device type and device statistics for listing
         *
         * @param value String, object DeviceTypeStats
         * @return Builder
         */
        public Builder addDeviceTypeStatsMapping(String value, DeviceTypeStats object) {
            this.deviceStatsMap.put(value, object);
            return this;
        }

        public DevicesReportResponse build() {
            return new DevicesReportResponse(Optional.ofNullable(deviceStatsMap.build()));
        }
    }
}
