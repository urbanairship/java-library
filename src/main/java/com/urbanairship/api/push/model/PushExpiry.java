/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Optional;
import org.joda.time.DateTime;


/**
 * Optional expiry feature for a Push payload for the Urban Airship API.
 **/

public class PushExpiry extends PushModelObject {

    private final Optional<Long> expirySeconds;
    private final Optional<DateTime> expiryTimeStamp;

    private PushExpiry(Optional<Long> expirySeconds,
                       Optional<DateTime> expiryTimeStamp) {
        this.expirySeconds = expirySeconds;
        this.expiryTimeStamp = expiryTimeStamp;
    }

    /**
     * Get a PushExpiryPayloadBuilder
     * @return PushExpiryPayloadBuilder
     **/
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the expiry (TTL) as a long.  This is optional.
     * @return Optional<<T>ExpirySeconds</T>>
     */
    public Optional<Long> getExpirySeconds() {
        return expirySeconds;
    }

    /**
     * Get the expiry (TTL) as a timestamp.  This is optional.
     * @return Optional<<T>ExpiryTimestamp</T>>
     */
    public Optional<DateTime> getExpiryTimeStamp() {
        return expiryTimeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PushExpiry that = (PushExpiry) o;

        if (expirySeconds != null ? !expirySeconds.equals(that.expirySeconds) : that.expirySeconds != null) {
            return false;
        }

        if (expiryTimeStamp != null ? !expiryTimeStamp.equals(that.expiryTimeStamp) : that.expiryTimeStamp != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (expirySeconds != null ? expirySeconds.hashCode() : 0);
        result = 31 * result + (expiryTimeStamp != null ? expiryTimeStamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushOptionsPayload{" +
                "expirySeconds=" + expirySeconds +
                ", expiryTimeStamp=" + expiryTimeStamp +
                '}';
    }

    public static class Builder {
        private Long expirySeconds = null;
        private DateTime expiryTimeStamp = null;

        private Builder() { }

        /**
         * Set the expiry as a long
         * @param value Long
         * @return Long
         **/
        public Builder setExpirySeconds(long value) {
            this.expirySeconds = value;
            return this;
        }

        /**
         * Set the expiry as a timestamp
         * @param value DateTime
         * @return DateTime
         **/
        public Builder setExpiryTimeStamp(DateTime value) {
            this.expiryTimeStamp = value;
            return this;
        }

        public PushExpiry build() {
            return new PushExpiry(Optional.fromNullable(expirySeconds),
                                  Optional.fromNullable(expiryTimeStamp));
        }
    }


}
