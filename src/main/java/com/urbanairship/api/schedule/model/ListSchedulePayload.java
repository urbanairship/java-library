/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.PushModelObject;

/**
 * Class representing a schedule list payload.
 */
public class ListSchedulePayload extends PushModelObject {

    private final Optional<String> start;

    private final Optional<Integer> limit;

    private ListSchedulePayload(String start, Integer limit) {
        this.start = Optional.fromNullable(start);
        this.limit = Optional.fromNullable(limit);
    }

    /**
     * Get the start - ID of the starting element for paginating results. This is optional.
     * @return Optional<<T>String</T>>
     */
    public Optional<String> getStart() {
        return start;
    }

    /**
     * Get the limit - maximum number of elements to return. This is optional.
     * @return Optional<<T>Integer</T>>
     */
    public Optional<Integer> getLimit() {
        return limit;
    }

    /**
     * SchedulePayload Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * SchedulePayload Builder
     */
    public static class Builder {
        private String start = null;
        private Integer limit = null;

        private Builder() { }

        /**
         * Set the start
         * @param start ID of the starting element for paginating results
         * @return Builder
         */
        public Builder setStart(String start) {
            this.start = start;
            return this;
        }

        /**
         * Set the limit.
         * @param limit maximum number of elements to return
         * @return Builder
         */
        public Builder setLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        /**
         * Build a SchedulePayload
         * @return SchedulePayload
         */
        public ListSchedulePayload build() {
            return new ListSchedulePayload(start, limit);
        }
    }
}
