package com.urbanairship.api.reports.model;

import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;
import java.util.Objects;
import java.util.Optional;

public final class ResponseReportResponse {
    private Optional<DateTime> date;
    private Optional<ImmutableMap<String, DeviceStats>> deviceStatsMap;

    private ResponseReportResponse() { this(null, null); }

    private ResponseReportResponse(Optional<DateTime> date, Optional<ImmutableMap<String, DeviceStats>> deviceStatsMap) {
        this.date = date;
        this.deviceStatsMap = deviceStatsMap;
    }

    public static Builder newBuilder() { return new Builder(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseReportResponse that = (ResponseReportResponse) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(deviceStatsMap, that.deviceStatsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, deviceStatsMap);
    }

    @Override
    public String toString() {
        return "ResponseReportResponse{" +
                "date=" + date +
                ", deviceStatsMap=" + deviceStatsMap +
                '}';
    }

    /**
     * Get the time interval represented by the Response object.
     *
     * @return DateTime
     */
    public Optional<DateTime> getDate() {
        return date;
    }

    /**
     * Get the map of devices and platform statistics associated with each device type.
     *
     * @return A Map of device names and their associated platform statistics
     */
    public Optional<ImmutableMap<String, DeviceStats>> getDeviceStatsMap() {
        return deviceStatsMap;
    }

    public static class Builder {
        private DateTime date = null;
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

        public ResponseReportResponse build() {
            return new ResponseReportResponse(Optional.ofNullable(date), Optional.ofNullable(deviceStatsMap.build()));
        }
    }
}
