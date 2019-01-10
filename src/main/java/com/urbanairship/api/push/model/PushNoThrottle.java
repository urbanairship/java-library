/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;


/**
 * Optional no_throttle feature for a Push payload for the Urban Airship API.
 **/

public class PushNoThrottle extends PushModelObject {

    private final Optional<Boolean> value;


    private PushNoThrottle(Optional<Boolean> value) {
        this.value = value;
    }

    /**
     * Get a PushNoThrottlePayloadBuilder
     * @return PushNoThrottlePayloadBuilder
     **/
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the no_throttle value as Booleand.  This is optional.
     * @return Optional<<T>value</T>>
     */
    public Optional<Boolean> getValue() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PushNoThrottle that = (PushNoThrottle) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "PushNoThrottle{"
            + "value=" + value
            + '}';
    }


    public static class Builder {
        private Boolean value = null;

        private Builder() { }

        /**
         * Set the value as a boolean
         * @param value Boolean
         * @return Builder
         **/
        public Builder setValue(boolean value) {
            this.value = value;
            return this;
        }


        public PushNoThrottle build() {
            if (value == null) {
                throw new APIParsingException("Value can not be null");
            }
            return new PushNoThrottle(Optional.fromNullable(value));
        }
    }



}
