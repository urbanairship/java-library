package com.urbanairship.api.createandsend.model.notification;

import java.util.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

import org.joda.time.DateTime;

import java.util.Objects;

public class CreateAndSendSchedulePayload extends PushModelObject {

    private final DateTime scheduleTime;
    private final CreateAndSendPayload payload;
    private final Optional<String> name;

    private CreateAndSendSchedulePayload(CreateAndSendSchedulePayload.Builder builder) {
        this.scheduleTime = builder.scheduleTime;
        this.payload = builder.payload;
        this.name = Optional.ofNullable(builder.name);
    }

    public static CreateAndSendSchedulePayload.Builder newBuilder() {
        return new CreateAndSendSchedulePayload.Builder();
    }

    /**
     * get the schedule time object for the sending
     *
     * @return DateTime scheduleTime.
     */
    public DateTime getScheduleTime() {
        return scheduleTime;
    }

    /**
     * get the create and send paylod for the sending
     *
     * @return CreateAndSendPayload payload.
     */
    public CreateAndSendPayload getPayload() {
        return payload;
    }

    /**
     * Optional, name for the schedule.
     *
     * @return Optional String name.
     */
    public Optional<String> getName() {
        return name;
    }


    @Override
    public String toString() {
        return "CreateAndSendSchedulePayload{" +
                "scheduleTime=" + scheduleTime +
                ", payload=" + payload +
                ", name=" + name +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateAndSendSchedulePayload)) return false;
        CreateAndSendSchedulePayload that = (CreateAndSendSchedulePayload) o;
        return Objects.equals(getScheduleTime(), that.getScheduleTime()) &&
                Objects.equals(getPayload(), that.getPayload())
                && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScheduleTime(), getPayload(), getName());
    }

    /**
     * CreateAndSendEmail Builder.
     */
    public static class Builder {
        private DateTime scheduleTime;
        private CreateAndSendPayload payload;
        private String name;

        private Builder() {
        }

        /**
         * scheduleTime object.
         *
         * @param scheduleTime DateTime
         * @return CreateAndSendSchedulePayload Builder
         */
        public CreateAndSendSchedulePayload.Builder setScheduleTime(DateTime scheduleTime) {
            this.scheduleTime = scheduleTime;
            return this;
        }

        /**
         * Payload object
         *
         * @param payload Optional Payload
         * @return CreateAndSendSchedulePayload Builder
         */
        public CreateAndSendSchedulePayload.Builder setPayload(CreateAndSendPayload payload) {
            this.payload = payload;
            return this;
        }

        /**
         * Optional Name object
         *
         * @param name Optional Name
         * @return CreateAndSendSchedulePayload Builder
         */
        public CreateAndSendSchedulePayload.Builder setName(String name) {
            this.name = name;
            return this;
        }

        public CreateAndSendSchedulePayload build() {

            Preconditions.checkNotNull(scheduleTime, "Schedule Time must be set.");

            Preconditions.checkNotNull(payload, "Payload must be set.");

            return new CreateAndSendSchedulePayload(this);
        }
    }
}
