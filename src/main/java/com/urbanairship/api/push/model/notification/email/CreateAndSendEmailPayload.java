package com.urbanairship.api.push.model.notification.email;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.Campaigns;
import com.urbanairship.api.push.model.audience.CreateAndSendAudience;
import com.urbanairship.api.push.model.notification.Notification;

import java.util.Objects;

public class CreateAndSendEmailPayload {

    private final Optional<CreateAndSendAudience> audience;
    private final Optional<Notification> notification;
    private final Optional<Campaigns> campaigns;


    private CreateAndSendEmailPayload(com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload.Builder builder) {
        this.audience = Optional.fromNullable(builder.audience);
        this.notification = Optional.fromNullable(builder.notification);
        this.campaigns = Optional.fromNullable(builder.campaigns);

    }

    public static com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload.Builder newBuilder() {
        return new com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload.Builder();
    }


    /**
     * Optional, get the audience object for the payload
     *
     * @return Optional CreateAndSendAudience audience.
     */
    public Optional<CreateAndSendAudience> getAudience() {
        return audience;
    }

    /**
     * Optional, an object representing the notification.
     *
     * @return Optional Notification notification.
     */
    public Optional<Notification> getNotification() {
        return notification;
    }

    /**
     * Optional, an object representing the campaign.
     *
     * @return Optional Campaigns campaigns.
     */
    public Optional<Campaigns> getCampaigns() {
        return campaigns;
    }


    @Override
    public String toString() {
        return "CreateAndSendEmailPayload{" +
                "audience=" + audience +
                ", notification=" + notification +
                ", campaigns=" + campaigns +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload)) return false;
        com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload that = (com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload) o;
        return Objects.equals(getAudience(), that.getAudience()) &&
                Objects.equals(getNotification(), that.getNotification())
                && Objects.equals(getCampaigns(), that.getCampaigns());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAudience(), getNotification(), getCampaigns());
    }

    /**
     * CreateAndSendEmail Builder.
     */
    public static class Builder {
        private CreateAndSendAudience audience;
        private Notification notification;
        private Campaigns campaigns;

        private Builder() {
        }

        /**
         * Optional, CreateAndSendAudience object.
         *
         * @param audience Optional CreateAndSendAudience
         * @return CreateAndSendEmailPayload Builder
         */
        public com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload.Builder setAudience(CreateAndSendAudience audience) {
            this.audience = audience;
            return this;
        }

        /**
         * Optional Notification object
         *
         * @param notification Optional Notification
         * @return CreateAndSendEmailPayload Builder
         */
        public com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload.Builder setNotification(Notification notification) {
            this.notification = notification;
            return this;
        }

        /**
         * Optional Notification object
         *
         * @param campaigns Optional campaigns
         * @return CreateAndSendEmailPayload Builder
         */
        public com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload.Builder setCampaigns(Campaigns campaigns) {
            this.campaigns = campaigns;
            return this;
        }

        public com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload build() {

            Preconditions.checkNotNull(notification, "Notification must be set.");

            Preconditions.checkNotNull(audience, "Audience must be set.");


            return new com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload(this);
        }
    }
}
