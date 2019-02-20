package com.urbanairship.api.reports.model;

import java.util.Objects;

public final class DeviceStats {
    private final int direct;
    private final int influenced;

    private DeviceStats(int direct, int influenced) {
        this.direct = direct;
        this.influenced = influenced;
    }

    /**
     * New DeviceStats builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceStats deviceType = (DeviceStats) o;
        return direct == deviceType.direct &&
                influenced == deviceType.influenced;
    }

    @Override
    public int hashCode() {
        return Objects.hash(direct, influenced);
    }

    @Override
    public String toString() {
        return "DeviceStats{" +
                "direct=" + direct +
                ", influenced=" + influenced +
                '}';
    }

    /**
     * Get the Direct quantity.
     *
     * @return int
     */
    public int getDirect() {
        return direct;
    }

    /**
     * Get the influenced quantity.
     *
     * @return int
     */
    public int getInfluenced() {
        return influenced;
    }

    public static class Builder {
        private int direct;
        private int influenced;

        private Builder() {}

        /**
         * Set the Direct quantity.
         *
         * @param value int
         * @return Builder
         */
        public Builder setDirect(int value) {
            this.direct = value;
            return this;
        }

        /**
         * Set the Influenced quantity.
         *
         * @param value int
         * @return Builder
         */
        public Builder setInfluenced(int value) {
            this.influenced = value;
            return this;
        }

        public DeviceStats build() { return new DeviceStats(direct, influenced); }
    }
}
