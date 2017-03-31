package com.urbanairship.api.channel.model.web;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

/**
 * Displayed only for Web channels. Describes the non-tag fields from the web subscription submitted in CRA.
 */
public final class WebSettings {

    private final Optional<Subscription> subscription;

    private WebSettings(Optional<Subscription> subscription) {
        this.subscription = subscription;
    }

    /**
     * Get the Subscription object
     *
     * @return Subscription
     */
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
        WebSettings webSettings = (WebSettings) o;
        return Objects.equal(subscription, webSettings.subscription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(subscription);
    }

    @Override
    public String toString() {
        return "WebSettings{" +
                "subscription=" + subscription +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * WebSettings Builder
     */
    public final static class Builder {

        private Subscription subscription = null;

        private Builder() {
        }

        /**
         * Set the subscription object
         * @param subscription Subscription
         * @return Builder
         */
        public Builder setSubscription(Subscription subscription) {
            this.subscription = subscription;
            return this;
        }

        public WebSettings build() {
            return new WebSettings(Optional.fromNullable(subscription));
        }
    }
}
