package com.urbanairship.api.schedule.model;

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

    private final Schedule schedule;
    private final Optional<String> name;
    private final PushPayload pushPayload;

    private SchedulePayload(Schedule schedule, String name, PushPayload pushPayload) {
        this.schedule = schedule;
        this.name = Optional.fromNullable(name);
        this.pushPayload = pushPayload;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchedulePayload schedulePayload = (SchedulePayload) o;


        if (schedule != null ? !schedule.equals(schedulePayload.schedule) : schedulePayload.schedule != null) {
            return false;
        }
        if (name != null ? !name.equals(schedulePayload.name) : schedulePayload.name != null) {
            return false;
        }

        if (pushPayload != null ? !pushPayload.equals(schedulePayload.pushPayload) : schedulePayload.pushPayload != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = schedule != null ? schedule.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pushPayload != null ? pushPayload.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SchedulePayload{" +
                "schedule='" + schedule + '\'' +
                ", name='" + name + '\'' +
                ", pushPayload='" + pushPayload + '\'' +
                '}';
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
        private Schedule schedule = null;
        private String name = null;
        private PushPayload pushPayload = null;

        private Builder() { }

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

            return new SchedulePayload(schedule, name, pushPayload);
        }
    }
}
