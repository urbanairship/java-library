package com.urbanairship.api.channel.model.web;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class Subscription {

    private final Optional<String> p256dh;
    private final Optional<String> auth;

    private Subscription(Optional<String> p256dh, Optional<String> auth) {
        this.p256dh = p256dh;
        this.auth = auth;
    }

    /**
     * Get the p256dh String
     *
     * @return p256dh String
     */
    public Optional<String> getP256dh() {
        return p256dh;
    }

    /**
     * Get the auth String
     *
     * @return auth String
     */
    public Optional<String> getAuth() {
        return auth;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Subscription that = (Subscription) o;
        return Objects.equal(p256dh, that.p256dh) &&
                Objects.equal(auth, that.auth);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(p256dh, auth);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "p256dh=" + p256dh +
                ", auth=" + auth +
                '}';
    }

    public final static class Builder {
        private String p256dh = null;
        private String auth = null;

        private Builder() {
        }

        public Builder setP256dh(String p256dh) {
            this.p256dh = p256dh;
            return this;
        }

        public Builder setAuth(String auth) {
            this.auth = auth;
            return this;
        }

        public Subscription build() {
            return new Subscription(
                    Optional.fromNullable(p256dh),
                    Optional.fromNullable(auth)
            );
        }
    }
}
