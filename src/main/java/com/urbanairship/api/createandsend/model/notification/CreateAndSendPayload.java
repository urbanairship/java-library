package com.urbanairship.api.createandsend.model.notification;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
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
    private final Optional<ImmutableMap<String, Object>> globalAttributes;

    private CreateAndSendPayload(CreateAndSendAudience audience, Notification notification, Optional<Campaigns> campaigns, Optional<ImmutableMap<String, Object>> globalAttributes) {
        this.audience = audience;
        this.notification = notification;
        this.campaigns = campaigns;
        this.globalAttributes = globalAttributes;
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

    /**
     * Optional, an ImmutableMap representing the globalAttributes.
     *
     * @return Optional ImmutableMap globalAttributes.
     */
    public Optional<ImmutableMap<String, Object>> getGlobalAttributes() {
        return globalAttributes;
    }

    @Override
    public String toString() {
        return "CreateAndSendPayload{" +
                "audience=" + audience +
                ", notification=" + notification +
                ", campaigns=" + campaigns +
                ", globalAttributes=" + globalAttributes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateAndSendPayload)) return false;
        CreateAndSendPayload that = (CreateAndSendPayload) o;
        return Objects.equals(getAudience(), that.getAudience()) &&
                Objects.equals(getNotification(), that.getNotification())
                && Objects.equals(getCampaigns(), that.getCampaigns())
                && Objects.equals(getGlobalAttributes(), that.getGlobalAttributes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAudience(), getNotification(), getCampaigns(), getGlobalAttributes());
    }

    /**
     * CreateAndSendEmail Builder.
     */
    public static class Builder {
        private CreateAndSendAudience audience;
        private Notification notification;
        private Campaigns campaigns;
        private ImmutableMap.Builder<String, Object> globalAttributesBuilder = ImmutableMap.builder();

        private Builder() {
        }

        /**
         * CreateAndSendAudience object.
         *
         * @param audience Optional CreateAndSendAudience
         * @return CreateAndSendPayload Builder
         */
        public Builder setAudience(CreateAndSendAudience audience) {
            this.audience = audience;
            return this;
        }

        /**
         * Notification object
         *
         * @param notification Optional Notification
         * @return CreateAndSendPayload Builder
         */
        public Builder setNotification(Notification notification) {
            this.notification = notification;
            return this;
        }

        /**
         * Optional Notification object
         *
         * @param campaigns Optional campaigns
         * @return CreateAndSendPayload Builder
         */
        public Builder setCampaigns(Campaigns campaigns) {
            this.campaigns = campaigns;
            return this;
        }
        
        /**
         * Optional globalAttributesBuilder ImmutableMap
         *
         * @param k,o String, Object
         * @return CreateAndSendPayload Builder
         */
        public Builder addGlobalAttributes(String k, Object o) {
            this.globalAttributesBuilder.put(k, o);
            return this;
        }


        public CreateAndSendPayload build() {
            ImmutableMap<String, Object> globalAttributes = globalAttributesBuilder.build();

            Preconditions.checkNotNull(notification, "Notification must be set.");

            Preconditions.checkNotNull(audience, "Audience must be set.");

            return new CreateAndSendPayload(audience, notification, Optional.ofNullable(campaigns), Optional.ofNullable(globalAttributes));
        }
    }
}
