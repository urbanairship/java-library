package com.urbanairship.api.reports.model;

import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

import java.util.Map;
import java.util.Objects;

public final class Response {
    public DateTime date;
    private ImmutableMap<String, DeviceStats> deviceStatsMap;

    private Response() { this(null, null); }

    private Response(DateTime date, ImmutableMap<String, DeviceStats> deviceStatsMap) {
        this.date = date;
        this.deviceStatsMap = deviceStatsMap;
    }

    public static Builder newBuilder() { return new Builder(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(date, response.date) &&
                Objects.equals(deviceStatsMap, response.deviceStatsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, deviceStatsMap);
    }

    @Override
    public String toString() {
        return "Response{" +
                "date=" + date +
                ", deviceStatsMap=" + deviceStatsMap +
                '}';
    }

    /**
     * Get the time interval represented by the Response object.
     *
     * @return DateTime
     */
    public DateTime getDate() {
        return date;
    }

    /**
     * Get the map of devices and platform statistics associated with each device type.
     *
     * @return A Map of device names and their associated platform statistics
     */
    public Map<String, DeviceStats> getDeviceStatsMap() {
        return deviceStatsMap;
    }

    public static class Builder {

        public DateTime date;
        private ImmutableMap.Builder<String, DeviceStats> deviceStatsMap = ImmutableMap.builder();

        private Builder() {}

        /**
         * Set the date object for listing
         *
         * @param date DateTIme
         * @return Builder
         */
        public Builder setDate(DateTime date) {
            this.date = date;
            return this;
        }

        /**
         * Add a mapping of device type and device statistics for listing
         *
         * @param value String, object DeviceStats
         * @return Builder
         */
        public Builder addDeviceStatsMapping(String value, DeviceStats object) {
            this.deviceStatsMap.put(value, object);
            return this;
        }

        /**
         * Add all mappings of device type and device statistics for listing
         *
         * @param deviceStats Map of device type and it's associated statistics
         * @return Builder
         */
        public Builder addDeviceStatsMapping(ImmutableMap<String, DeviceStats> deviceStats) {
            if (!deviceStats.isEmpty()) {
                this.deviceStatsMap.putAll(deviceStats);
            }
            return this;
        }

        public Response build() {
            return new Response(date, deviceStatsMap.build());
        }

    }


}
