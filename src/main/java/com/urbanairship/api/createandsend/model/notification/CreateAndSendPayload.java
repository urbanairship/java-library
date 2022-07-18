package com.urbanairship.api.createandsend.model.notification;

import com.google.common.base.Preconditions;
import com.urbanairship.api.createandsend.model.audience.CreateAndSendAudience;
import com.urbanairship.api.push.model.Campaigns;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.Notification;

import java.util.Objects;
import java.util.Optional;

public class CreateAndSendPayload extends PushModelObject {

    private final CreateAndSendAudience audience;
    private final Notification notification;
    private final Optional<Campaigns> campaigns;

    private CreateAndSendPayload(CreateAndSendPayload.Builder builder) {
        this.audience = builder.audience;
        this.notification = builder.notification;
        this.campaigns = Optional.ofNullable(builder.campaigns);
    }

    public static CreateAndSendPayload.Builder newBuilder() {
        return new CreateAndSendPayload.Builder();
    }

    /**
     * get the audience object for the payload
     *
     * @return Optional CreateAndSendAudience audience.
     */
    public CreateAndSendAudience getAudience() {
        return audience;
    }

    /**
     * an object representing the notification.
     *
     * @return Optional Notification notification.
     */
    public Notification getNotification() {
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
        return "CreateAndSendPayload{" +
                "audience=" + audience +
                ", notification=" + notification +
                ", campaigns=" + campaigns +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateAndSendPayload)) return false;
        CreateAndSendPayload that = (CreateAndSendPayload) o;
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
         * CreateAndSendAudience object.
         *
         * @param audience Optional CreateAndSendAudience
         * @return CreateAndSendPayload Builder
         */
        public CreateAndSendPayload.Builder setAudience(CreateAndSendAudience audience) {
            this.audience = audience;
            return this;
        }

        /**
         * Notification object
         *
         * @param notification Optional Notification
         * @return CreateAndSendPayload Builder
         */
        public CreateAndSendPayload.Builder setNotification(Notification notification) {
            this.notification = notification;
            return this;
        }

        /**
         * Optional Notification object
         *
         * @param campaigns Optional campaigns
         * @return CreateAndSendPayload Builder
         */
        public CreateAndSendPayload.Builder setCampaigns(Campaigns campaigns) {
            this.campaigns = campaigns;
            return this;
        }

        public CreateAndSendPayload build() {

            Preconditions.checkNotNull(notification, "Notification must be set.");

            Preconditions.checkNotNull(audience, "Audience must be set.");

            return new CreateAndSendPayload(this);
        }
    }
}
