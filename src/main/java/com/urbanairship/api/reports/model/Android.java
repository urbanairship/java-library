package com.urbanairship.api.reports.model;

import java.util.Objects;

public final class Android {

    private final int direct;
    private final int influenced;

    private Android(int direct, int influenced) {
        this.direct = direct;
        this.influenced = influenced;
    }

    /**
     * New Android builder.
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
        Android android = (Android) o;
        return direct == android.direct &&
                influenced == android.influenced;
    }

    @Override
    public int hashCode() {
        return Objects.hash(direct, influenced);
    }

    @Override
    public String toString() {
        return "Android{" +
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

        public Android build() { return new Android(direct, influenced); }
    }
}
