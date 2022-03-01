package com.urbanairship.api.reports.model;

import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Optional;
import java.util.Objects;

public class DevicesReport {
    private final Optional<String> dateClosed;
    private final Optional<String> dateComputed;
    private final Optional<Integer> totalUniqueDevices;
    private final Optional<ImmutableList<DevicesReportResponse>> counts;
    private final boolean ok;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private DevicesReport(Builder builder) {
        this.dateClosed = Optional.ofNullable(builder.dateClosed);
        this.dateComputed = Optional.ofNullable(builder.dateComputed);
        this.totalUniqueDevices = Optional.ofNullable(builder.totalUniqueDevices);
        this.counts = Optional.ofNullable(builder.counts.build());
        this.ok = builder.ok;
        this.error = Optional.ofNullable(builder.error);
        this.errorDetails = Optional.ofNullable(builder.errorDetails);
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

    /**
     * Get the OK status as a boolean
     *
     * @return boolean
     */
    public boolean getOk() {
        return ok;
    }

    /**
     * Get the error if present
     *
     * @return An Optional String
     */
    public Optional<String> getError() {
        return error;
    }

    /**
     * Get the error details if present
     *
     * @return An Optional String
     */
    public Optional<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DevicesReport)) return false;
        DevicesReport that = (DevicesReport) o;
        return Objects.equals(dateClosed, that.dateClosed) &&
                Objects.equals(dateComputed, that.dateComputed) &&
                Objects.equals(totalUniqueDevices, that.totalUniqueDevices) &&
                Objects.equals(getCounts(), that.getCounts()) &&
                Objects.equals(ok, that.ok) &&
                Objects.equals(error, that.error) &&
                Objects.equals(errorDetails, that.errorDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateClosed, dateComputed, totalUniqueDevices, getCounts(), ok, error, errorDetails);
    }

    @Override
    public String toString() {
        return "DevicesReport{" +
                "dateClosed=" + dateClosed +
                ", dateComputed=" + dateComputed +
                ", totalUniqueDevices=" + totalUniqueDevices +
                ", counts=" + counts +
                ", ok=" + ok +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    public static class Builder {
        private String dateClosed;
        private String dateComputed;
        private Integer totalUniqueDevices;
        private ImmutableList.Builder<DevicesReportResponse> counts = ImmutableList.builder();
        private boolean ok;
        private String error;
        private ErrorDetails errorDetails;
        
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
         * Set the ok status
         *
         * @param value boolean
         * @return Builder
         */
        public Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        /**
         * Set the error
         *
         * @param error String
         * @return Builder
         */
        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        /**
         * Set the errorDetails
         *
         * @param errorDetails String
         * @return Builder
         */
        public Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
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
