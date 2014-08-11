package com.urbanairship.api.push.model;

import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.SelectorType;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Set;

public final class PushPayload extends PushModelObject {

    private final Selector audience;
    private final Optional<Notification> notification;
    private final Optional<RichPushMessage> message;
    private final PlatformData platforms;
    private final Optional<PushOptions> options;

    public static Builder newBuilder() {
        return new Builder();
    }

    private PushPayload(Selector audience,
                        Optional<Notification> notification,
                        Optional<RichPushMessage> message,
                        PlatformData platforms,
                        PushOptions options) {
        this.audience = audience;
        this.notification = notification;
        this.message = message;
        this.platforms = platforms;
        this.options = Optional.fromNullable(options);
    }

    public Selector getAudience() {
        return audience;
    }

    public Optional<Notification> getNotification() {
        return notification;
    }

    public Optional<RichPushMessage> getMessage() {
        return message;
    }

    public PlatformData getPlatforms() {
        return platforms;
    }

    public Optional<PushOptions> getOptions() {
        return options;
    }

    public boolean isBroadcast() {
        return audience.getType().equals(SelectorType.ALL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PushPayload that = (PushPayload) o;

        if (audience != null ? !audience.equals(that.audience) : that.audience != null) {
            return false;
        }
        if (notification != null ? !notification.equals(that.notification) : that.notification != null) {
            return false;
        }
        if (message != null ? !message.equals(that.message) : that.message != null) {
            return false;
        }
        if (platforms != null ? !platforms.equals(that.platforms) : that.platforms != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (audience != null ? audience.hashCode() : 0);
        result = 31 * result + (notification != null ? notification.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (platforms != null ? platforms.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushPayload{" +
            "audience=" + audience +
            ", notification=" + notification +
            ", message=" + message +
            ", platforms=" + platforms +
            '}';
    }

    public static class Builder {
        private PlatformData platforms = null;
        private Selector audience = null;
        private Notification notification = null;
        private RichPushMessage message = null;
        private PushOptions options = null;

        private Builder() { }

        public Builder setAudience(Selector value) {
            this.audience = value;
            return this;
        }

        public Builder setNotification(Notification notification) {
            this.notification = notification;
            return this;
        }

        public Builder setMessage(RichPushMessage message) {
            this.message = message;
            return this;
        }

        public Builder setOptions(PushOptions options) {
            this.options = options;
            return this;
        }

        public Builder setPlatforms(PlatformData platforms) {
            this.platforms = platforms;
            return this;
        }

        public PushPayload build() {
            Preconditions.checkArgument(!(notification == null && message == null), "At least one of 'notification' or 'message' must be set.");
            Preconditions.checkNotNull(audience, "'audience' must be set");
            Preconditions.checkNotNull(platforms, "'device_types' must be set");

            return new PushPayload(audience,
                                   Optional.fromNullable(notification),
                                   Optional.fromNullable(message),
                                   platforms,
                                   options);
        }
    }
}
