/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public class Options extends PushModelObject {

    private final DateTime expiry;
    private final Long expirySeconds;

    // TODO local, global, etc

    private Options(DateTime expiry) {
        this.expiry = expiry;
        this.expirySeconds = null;
    }

    private Options(Long expirySeconds) {
        this.expiry = null;
        this.expirySeconds = expirySeconds;
    }

    /**
     * Get the expiry DateTime
     * @return DateTime
     */
    public DateTime getExpiry() {
        return expiry;
    }

    /**
     * Get the expiry seconds.
     * @return Long
     */
    public Long getExpirySeconds() {
        return expirySeconds;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Options that = (Options) o;

        if (expiry != null ? !expiry.equals(that.expiry) : that.expiry != null) {
            return false;
        }
        if (expirySeconds != null ? !expirySeconds.equals(that.expirySeconds) : that.expirySeconds != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = expiry != null ? expiry.hashCode() : 0;
        result = 31 * result + (expirySeconds != null ? expirySeconds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Options{" +
                "expiry=" + expiry +
                ", expirySeconds=" + expirySeconds +
                '}';
    }


    /**
     * Get new Options Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Build an Options object. Will fail if any of the following preconditions are not met.
     * <pre>
     * 1. One of expiry or expirySeconds must be set. Both cannot be null.
     * 2. Exactly one of expiry or expirySeconds can be set. Both cannot be not null.
     * 3. When setting expirySeconds the value must be zero or more.
     * </pre>
     *
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @return Options
     */
    public static class Builder {
        private DateTime expiry = null;
        private Long expirySeconds = null;

        private Builder() { }

        /**
         * Set the DateTime for expiry. This will be converted to UTC by the server.
         * @param expiry Delivery expiration.
         * @return Builder
         */
        public Builder setExpiry(DateTime expiry) {
            this.expiry = expiry;
            return this;
        }

        /**
         * Set the seconds from now for expiry.
         * @param expirySeconds Delivery expiration in seconds from now. Must be zero or more.
         * @return Builder
         */
        public Builder setExpirySeconds(Long expirySeconds) {
            this.expirySeconds = expirySeconds;
            return this;
        }

        /**
         * Build the Options object.
         * @return Options
         */
        public Options build() {
            Preconditions.checkArgument(!(expiry == null && expirySeconds == null),
                    "One of 'expiry' or 'expirySeconds' must be set.");

            Preconditions.checkArgument(!(expiry != null && expirySeconds != null),
                    "Exactly one of 'expiry' or 'expirySeconds' can be set.");

            Preconditions.checkArgument(!(expirySeconds != null && expirySeconds < 0),
                    "'expirySeconds' must be zero or more.");

            if (expiry != null) {
                return new Options(expiry);
            }

            return new Options(expirySeconds);
        }
    }
}
