package com.urbanairship.api.schedule.model;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushPayload;

import java.util.Optional;

public class SchedulePayload extends ScheduleModelObject {
    private final PushPayload pushPayload;
    private final Schedule schedule;
    private final Optional<String> name;

    private SchedulePayload(Builder builder) {
        this.pushPayload = builder.pushPayload;
        this.schedule = builder.schedule;
        this.name = Optional.ofNullable(builder.name);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public PushPayload getPushPayload() {
        return pushPayload;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Optional<String> getName() {
        return name;
    }

    public static class Builder {
        private PushPayload pushPayload;
        private Schedule schedule;
        private String name;

        public Builder setPushPayload(PushPayload pushPayload) {
            this.pushPayload = pushPayload;
            return this;
        }

        public Builder setSchedule(Schedule schedule) {
            this.schedule = schedule;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public SchedulePayload build() {
            Preconditions.checkNotNull(schedule, "'schedule' must be set");
            Preconditions.checkNotNull(pushPayload, "'push payload' must be set");

            return new SchedulePayload(this);
        }
    }
}