package com.urbanairship.api.reports.model;

import java.util.Objects;
import java.util.Optional;

public final class DeviceStats {
    private final Optional<Integer> direct;
    private final Optional<Integer> influenced;

    private DeviceStats(Optional<Integer> direct, Optional<Integer> influenced) {
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
        DeviceStats that = (DeviceStats) o;
        return Objects.equals(direct, that.direct) &&
                Objects.equals(influenced, that.influenced);
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
    public Optional<Integer> getDirect() {
        return direct;
    }

    /**
     * Get the influenced quantity.
     *
     * @return int
     */
    public Optional<Integer> getInfluenced() {
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

        public DeviceStats build() { return new DeviceStats(Optional.ofNullable(direct), Optional.ofNullable(influenced)); }
    }
}
