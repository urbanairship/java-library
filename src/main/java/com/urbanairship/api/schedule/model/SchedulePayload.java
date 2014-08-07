/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;

import com.google.common.base.Objects;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.PushPayload;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;

/**
 * Class representing a schedule payload. When the schedule fires, the
 * PushPayload will be sent.
 */
public class SchedulePayload extends PushModelObject {

    private final Optional<String> url;
    private final Schedule schedule;
    private final Optional<String> name;
    private final PushPayload pushPayload;

    private SchedulePayload(String url, Schedule schedule, String name, PushPayload pushPayload) {
        this.url = Optional.fromNullable(url);
        this.schedule = schedule;
        this.name = Optional.fromNullable(name);
        this.pushPayload = pushPayload;
    }

    /**
     * Get the url
     * @return url
     */
    public Optional<String> getUrl() {
        return url;
    }

    /**
     * Get the schedule
     * @return Schedule
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Get the schedule name. This is optional.
     * @return Optional<<T>String</T>>
     */
    public Optional<String> getName () {
        return name;
    }

    /**
     * Get the push payload
     * @return PushPayload
     */
    public PushPayload getPushPayload() {
        return pushPayload;
    }


    /**
     * SchedulePayload Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url, schedule, name, pushPayload);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SchedulePayload other = (SchedulePayload) obj;
        return Objects.equal(this.url, other.url) && Objects.equal(this.schedule, other.schedule) && Objects.equal(this.name, other.name) && Objects.equal(this.pushPayload, other.pushPayload);
    }

    @Override
    public String toString() {
        return "SchedulePayload{" +
                "url=" + url +
                ", schedule=" + schedule +
                ", name=" + name +
                ", pushPayload=" + pushPayload +
                '}';
    }

    /**
     * SchedulePayload Builder
     */
    public static class Builder {
        private String url = null;
        private Schedule schedule = null;
        private String name = null;
        private PushPayload pushPayload = null;

        private Builder() { }

        /**
         * Set the schedule for this payload.
         * @param url String
         * @return Builder
         */
        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * Set the schedule for this payload.
         * @param schedule Schedule
         * @return Builder
         */
        public Builder setSchedule(Schedule schedule) {
            this.schedule = schedule;
            return this;
        }

        /**
         * Set the schedule name.
         * @param name name of schedule
         * @return Builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the push payload that will be delivered when the schedule fires.
         * @param pushPayload Payload to send
         * @return Builder
         */
        public Builder setPushPayload(PushPayload pushPayload) {
            this.pushPayload = pushPayload;
            return this;
        }

        /**
         * Build a SchedulePayload
         * @return SchedulePayload
         */
        public SchedulePayload build() {
            Preconditions.checkNotNull(schedule, "'schedule' must be set");
            Preconditions.checkNotNull(pushPayload, "'audience' must be set");
            if(name != null) {
                Preconditions.checkArgument(StringUtils.isNotBlank(name), "'name' must be a non-blank string");
            }

            return new SchedulePayload(url, schedule, name, pushPayload);
        }
    }
}
