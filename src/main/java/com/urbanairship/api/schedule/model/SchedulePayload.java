/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.PushPayload;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SchedulePayload extends PushModelObject {

    private final Schedule schedule;
    private final Optional<String> url;
    private final Optional<String> name;
    private final PushPayload pushPayload;
    private Set<String> pushIds;

    private SchedulePayload(Schedule schedule, String url, String name, PushPayload pushPayload, Set<String> pushIds) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o)  { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        SchedulePayload that = (SchedulePayload) o;

        if (name != null ? !name.equals(that.name) : that.name != null) { return false; }
        if (pushIds != null ? !pushIds.equals(that.pushIds) : that.pushIds != null) { return false; }
        if (!pushPayload.equals(that.pushPayload)) { return false; }
        if (!schedule.equals(that.schedule)) { return false; }
        if (url != null ? !url.equals(that.url) : that.url != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = schedule.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + pushPayload.hashCode();
        result = 31 * result + (pushIds != null ? pushIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SchedulePayload{" +
                "schedule=" + schedule +
                ", url=" + url +
                ", name=" + name +
                ", pushPayload=" + pushPayload +
                ", pushIds=" + pushIds +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Schedule schedule = null;
        private String url = null;
        private String name = null;
        private PushPayload pushPayload = null;
        private HashSet<String > pushIds = Sets.newHashSet();

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

        public SchedulePayload build() {
            Preconditions.checkNotNull(schedule, "'schedule' must be set");
            Preconditions.checkNotNull(pushPayload, "'audience' must be set");
            if(name != null) {
                Preconditions.checkArgument(StringUtils.isNotBlank(name), "'name' must be a non-blank string");
            }

            return new SchedulePayload(schedule, url, name, pushPayload, pushIds);
        }
    }
}