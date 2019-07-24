package com.urbanairship.api.reports.model;

import java.util.Objects;
import java.util.Optional;

public final class DeviceTypeStats {
    private final Optional<Integer> optedIn;
    private final Optional<Integer> optedOut;
    private final Optional<Integer> uninstalled;
    private final Optional<Integer> uniqueDevices;

    private DeviceTypeStats(Builder builder) {
        this.optedIn = Optional.ofNullable(builder.optedIn);
        this.optedOut = Optional.ofNullable(builder.optedOut);
        this.uninstalled = Optional.ofNullable(builder.uninstalled);
        this.uniqueDevices = Optional.ofNullable(builder.uniqueDevices);
    }

    /**
     * New DeviceTypeStats builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the optedIn quantity.
     *
     * @return int
     */
    public Optional<Integer> getOptedIn() {
        return optedIn;
    }

    /**
     * Get the optedOut quantity.
     *
     * @return int
     */
    public Optional<Integer> getOptedOut() {
        return optedOut;
    }

    /**
     * Get the uninstalled quantity.
     *
     * @return int
     */
    public Optional<Integer> getUninstalled() {
        return uninstalled;
    }

    /**
     * Get the unique devices quantity.
     *
     * @return int
     */
    public Optional<Integer> getUniqueDevices() {
        return uniqueDevices;
    }

    @Override
    public int hashCode() {
        return Objects.hash(optedIn, optedOut, uninstalled, uniqueDevices);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DeviceTypeStats other = (DeviceTypeStats) obj;
        return Objects.equals(this.optedIn, other.optedIn)
                && Objects.equals(this.optedOut, other.optedOut)
                && Objects.equals(this.uninstalled, other.uninstalled)
                && Objects.equals(this.uniqueDevices, other.uniqueDevices);
    }

    @Override
    public String toString() {
        return "DeviceTypeStats{" +
                "optedIn=" + optedIn +
                ", optedOut=" + optedOut +
                ", uninstalled=" + uninstalled +
                ", uniqueDevices=" + uniqueDevices +
                '}';
    }

    public static class Builder {
        private int optedIn;
        private int optedOut;
        private int uninstalled;
        private int uniqueDevices;

        private Builder() {}

        /**
         * Set the optedIn quantity.
         *
         * @param value int
         * @return Builder
         */
        public Builder setOptedIn(int value) {
            this.optedIn = value;
            return this;
        }

        /**
         * Set the optedOut quantity.
         *
         * @param value int
         * @return Builder
         */
        public Builder setOptedOut(int value) {
            this.optedOut = value;
            return this;
        }

        /**
         * Set the uninstalled quantity.
         *
         * @param value int
         * @return Builder
         */
        public Builder setUninstalled(int value) {
            this.uninstalled = value;
            return this;
        }

        /**
         * Set the uniqueDevices quantity.
         *
         * @param value int
         * @return Builder
         */
        public Builder setUniqueDevices(int value) {
            this.uniqueDevices = value;
            return this;
        }

        public DeviceTypeStats build() {
            return new DeviceTypeStats(this);
        }
    }
}
