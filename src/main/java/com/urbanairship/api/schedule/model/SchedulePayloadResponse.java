/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.urbanairship.api.push.model.PushPayload;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class SchedulePayloadResponse {

    private final Schedule schedule;
    private final Optional<String> url;
    private final Optional<String> name;
    private final PushPayload pushPayload;
    private Set<String> pushIds;

    private SchedulePayloadResponse(Schedule schedule, String url, String name, PushPayload pushPayload, Set<String> pushIds) {
        this.schedule = schedule;
        this.url = Optional.fromNullable(url);
        this.name = Optional.fromNullable(name);
        this.pushPayload = pushPayload;
        this.pushIds = pushIds;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Optional<String> getUrl () {
        return url;
    }

    public Optional<String> getName () {
        return name;
    }

    public PushPayload getPushPayload() {
        return pushPayload;
    }

    public Set<String> getPushIds() {
        return pushIds;
    }

    // yes, we want this to be mutable. push ids are set on the model object after the request payload is deserialized
    public void addPushId(String pushId) {
        pushIds.add(pushId);
    }

    public void addAllPushIds(Collection<String> pushIds) {
        this.pushIds.addAll(pushIds);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "SchedulePayloadResponse{" +
                "schedule=" + schedule +
                ", url=" + url +
                ", name=" + name +
                ", pushPayload=" + pushPayload +
                ", pushIds=" + pushIds +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(schedule, url, name, pushPayload, pushIds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SchedulePayloadResponse other = (SchedulePayloadResponse) obj;
        return Objects.equal(this.schedule, other.schedule)
                && Objects.equal(this.url, other.url)
                && Objects.equal(this.name, other.name)
                && Objects.equal(this.pushPayload, other.pushPayload)
                && Objects.equal(this.pushIds, other.pushIds);
    }

    public static class Builder {
        private Schedule schedule = null;
        private String url = null;
        private String name = null;
        private PushPayload pushPayload = null;
        private Set<String> pushIds = Sets.newHashSet();

        private Builder() { }

        public Builder setSchedule(Schedule schedule) {
            this.schedule = schedule;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPushPayload(PushPayload pushPayload) {
            this.pushPayload = pushPayload;
            return this;
        }

        public Builder addPushId(String pushId) {
            this.pushIds.add(pushId);
            return this;
        }

        public Builder addAllPushIds(Collection<String> pushIds) {
            this.pushIds.addAll(pushIds);
            return this;
        }

        public SchedulePayloadResponse build() {
            Preconditions.checkNotNull(schedule, "'schedule' must be set");
            Preconditions.checkNotNull(pushPayload, "'pushPayload' must be set");
            if(name != null) {
                Preconditions.checkArgument(StringUtils.isNotBlank(name), "'name' must be a non-blank string");
            }

            return new SchedulePayloadResponse(schedule, url, name, pushPayload, pushIds);
        }
    }
}
