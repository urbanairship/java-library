package com.urbanairship.api.channel.model.web;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.urbanairship.api.push.model.notification.web.WebDevicePayload;

public class Web {

    private final Optional<Subscription> subscription;

    private Web(Optional<Subscription> subscription) {
        this.subscription = subscription;
    }

    public Optional<Subscription> getSubscription() {
        return subscription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Web web = (Web) o;
        return Objects.equal(subscription, web.subscription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(subscription);
    }

    @Override
    public String toString() {
        return "Web{" +
                "subscription=" + subscription +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final static class Builder {

        private Subscription subscription = null;

        private Builder() {
        }

        public Builder setSubscription(Subscription subscription) {
            this.subscription = subscription;
            return this;
        }

        public Web build() {
            return new Web(Optional.fromNullable(subscription));
        }
    }
}
