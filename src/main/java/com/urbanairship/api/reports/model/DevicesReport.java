package com.urbanairship.api.reports.model;

import com.google.common.collect.ImmutableList;
import java.util.Optional;

import java.util.Objects;

public class DevicesReport {
    private final Optional<String> dateClosed;
    private final Optional<String> dateComputed;
    private final Optional<Integer> totalUniqueDevices;
    private final Optional<ImmutableList<DevicesReportResponse>> counts;

    private DevicesReport(Builder builder) {
        this.dateClosed = Optional.ofNullable(builder.dateClosed);
        this.dateComputed = Optional.ofNullable(builder.dateComputed);
        this.totalUniqueDevices = Optional.ofNullable(builder.totalUniqueDevices);
        this.counts = Optional.ofNullable(builder.counts.build());
    }

    public static Builder newBuilder() { return new Builder(); }

    /**
     * Get the date closed attribute if present for a DevicesReportRequest.
     *
     * @return An optional string
     */
    public Optional<String> getDateClosed() {
        return dateClosed;
    }

    /**
     * Get the date computed attribute if present for a DevicesReportRequest.
     *
     * @return An optional string
     */
    public Optional<String> getDateComputed() {
        return dateComputed;
    }

    /**
     * Get the total unique devices attribute if present for a DevicesReportRequest.
     *
     * @return An optional integer
     */
    public Optional<Integer> getTotalUniqueDevices() {
        return totalUniqueDevices;
    }

    /**
     * Get the list of DevicesReportResponse objects for a DevicesReportRequest
     *
     * @return An optional immutable list of DevicesReportResponse objects
     */
    public Optional<ImmutableList<DevicesReportResponse>> getCounts() {
        return counts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DevicesReport)) return false;
        DevicesReport that = (DevicesReport) o;
        return Objects.equals(dateClosed, that.dateClosed) &&
                Objects.equals(dateComputed, that.dateComputed) &&
                Objects.equals(totalUniqueDevices, that.totalUniqueDevices) &&
                Objects.equals(getCounts(), that.getCounts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateClosed, dateComputed, totalUniqueDevices, getCounts());
    }

    @Override
    public String toString() {
        return "DevicesReport{" +
                "dateClosed=" + dateClosed +
                ", dateComputed=" + dateComputed +
                ", totalUniqueDevices=" + totalUniqueDevices +
                ", counts=" + counts +
                '}';
    }

    public static class Builder {
        private String dateClosed;
        private String dateComputed;
        private Integer totalUniqueDevices;
        private ImmutableList.Builder<DevicesReportResponse> counts = ImmutableList.builder();

        private Builder() {}

        /**
         * Set the date closed
         *
         * @param dateClosed String
         * @return Builder
         */
        public Builder setDateClosed(String dateClosed) {
            this.dateClosed = dateClosed;
            return this;
        }

        /**
         * Set the date computed
         *
         * @param dateComputed String
         * @return Builder
         */
        public Builder setDateComputed(String dateComputed) {
            this.dateComputed = dateComputed;
            return this;
        }

        /**
         * Set the total unique devices
         *
         * @param totalUniqueDevices String
         * @return Builder
         */
        public Builder setTotalUniqueDevices(Integer totalUniqueDevices) {
            this.totalUniqueDevices = totalUniqueDevices;
            return this;
        }

        /**
         * Add a DevicesReportResponse object for a listing
         *
         * @param object DevicesReportResponse
         * @return Builder
         */
        public Builder addDevicesReportResponseObject(DevicesReportResponse object) {
            this.counts.add(object);
            return this;
        }

        /**
         * Build the DevicesReport object
         *
         * @return DevicesReport
         */
        public DevicesReport build() {
            return new DevicesReport(this);
        }
    }
}
