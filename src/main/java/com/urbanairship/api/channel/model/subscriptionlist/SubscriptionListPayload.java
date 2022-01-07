package com.urbanairship.api.channel.model.subscriptionlist;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.channel.model.ChannelAudience;
import com.urbanairship.api.channel.model.ChannelModelObject;

/**
 * Represents a Channel Subscription List payload for the Airship API.
 */
public class SubscriptionListPayload extends ChannelModelObject {
    private final ChannelAudience audience;
    ImmutableList<SubscriptionList> subscriptionList;

    private SubscriptionListPayload(ChannelAudience audience, ImmutableList<SubscriptionList> subscriptionList) {
        this.audience = audience;
        this.subscriptionList = subscriptionList;
    }

    /**
     * SubscriptionListPayload Builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the audience that determines the set of channels to target.
     *
     * @return ChannelAudience audience
     */
    public ChannelAudience getAudience() {
        return audience;
    }

    /**
     * Get the Subscription list to subscribe or unsubscribe for the audience.
     *
     * @return ImmutableList of SubscriptionList subscriptionList
     */
    public ImmutableList<SubscriptionList> getSubscriptionList() {
        return subscriptionList;
    }

    public static class Builder {
        ChannelAudience audience;
        ImmutableList.Builder<SubscriptionList> subscriptionListBuilder = ImmutableList.builder();

        /**
         * Set the audience that determines the set of channels to target.
         *
         * @param audience ChannelAudience
         * @return Builder
         */
        public Builder setAudience(ChannelAudience audience) {
            this.audience = audience;
            return this;
        }

        /**
         * Subscribe or unsubscribe a subscription list, for the audience.
         *
         * @param subscriptionList SubscriptionList
         * @return Builder
         */
        public Builder addSubscriptionList(SubscriptionList subscriptionList) {
            subscriptionListBuilder.add(subscriptionList);
            return this;
        }

        public SubscriptionListPayload build() {
            ImmutableList<SubscriptionList> subscriptionList = subscriptionListBuilder.build();
            Preconditions.checkNotNull(audience, "Audience must be set.");
            Preconditions.checkArgument(subscriptionList.size() > 0, "Subscription List must be added to SubscriptionListPayload.");

            return new SubscriptionListPayload(audience, subscriptionList);
        }
    }
}
