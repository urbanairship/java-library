package com.urbanairship.api.channel.model.email;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;
import java.util.Objects;

/**
 * Represents the payload to be used for registering or updating an email channel.
 */
public class UninstallEmailChannel extends PushModelObject {

    private final String emailAddress;

    private UninstallEmailChannel(Builder builder) {
        this.emailAddress = builder.email_address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    /**
         * New UninstallEmailChannel builder.
         * @return Builder
         */
        public static Builder newBuilder () {
            return new Builder();
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UninstallEmailChannel)) return false;
        UninstallEmailChannel that = (UninstallEmailChannel) o;
        return Objects.equals(getEmailAddress(), that.getEmailAddress());
    }

    @Override
    public String toString() {
        return "UninstallEmailChannel{" +
                "emailAddress='" + emailAddress + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress);
    }

    /**
     * Create UninstallEmailChannel Builder
     */
    public final static class Builder {
        private String email_address;

        private Builder() {}

        /**
         * Set the email_address
         * @param email_address String
         * Used _ notation to conform with previously written code
         * @return RegisterEmailChannel Builder
         */
        public Builder setEmailAddress(String email_address) {
            this.email_address = email_address;
            return this;
        }

        public UninstallEmailChannel build() {
            Preconditions.checkNotNull(email_address, "'email_address' cannot be null.");

            return new UninstallEmailChannel(this);
        }
    }
}