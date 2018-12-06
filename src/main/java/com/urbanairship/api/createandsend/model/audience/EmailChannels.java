package com.urbanairship.api.createandsend.model.audience;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class EmailChannels {
    private final List<EmailChannel> emailChannels;

    private EmailChannels(Builder builder) {
        emailChannels = builder.emailChannels.build();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<EmailChannel> getEmailChannels() {
        return emailChannels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailChannels that = (EmailChannels) o;
        return Objects.equal(emailChannels, that.emailChannels);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(emailChannels);
    }

    @Override
    public String toString() {
        return "EmailChannels{" +
                "emailChannels=" + emailChannels +
                '}';
    }

    public static class Builder {
        private ImmutableList.Builder<EmailChannel> emailChannels = ImmutableList.builder();

        /**
         * Add a email channel to create and send to.
         * @param emailChannel RegisterEmailChannel
         * @return EmailChannels Builder
         */
        public Builder addChannel(EmailChannel emailChannel) {
            this.emailChannels.add(emailChannel);
            return this;
        }

        /**
         * Add multiple email channels to create and send to.
         * @param emailChannels RegisterEmailChannel
         * @return EmailChannels Builder
         */
        public Builder addAllChannels(List<EmailChannel> emailChannels) {
            this.emailChannels.addAll(emailChannels);
            return this;
        }

        public EmailChannels build() {
            return new EmailChannels(this);
        }
    }
}
