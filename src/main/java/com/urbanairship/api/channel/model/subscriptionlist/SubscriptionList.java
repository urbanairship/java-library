package com.urbanairship.api.channel.model.subscriptionlist;

import com.google.common.base.Preconditions;

/**
 * Object that represents individual subscription list.
 */
public class SubscriptionList {
    private final SubscriptionListAction action;
    private final String listId;


    private SubscriptionList(Builder builder) {
        this.action = builder.action;
        this.listId = builder.listId;
    }

    /**
     * New subscription list builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the subscription list action.
     *
     * @return String action
     */
    public SubscriptionListAction getAction() {
        return action;
    }

    /**
     * Get the subscription list key.
     *
     * @return String key
     */
    public String getListId() {
        return listId;
    }

    /**
     * Create subscription list Builder
     */
    public static class Builder {
        SubscriptionListAction action;
        String listId;

        /**
         * Indicates that you want to subscribe or unsubscribe a list on the audience.
         *
         * @param action String
         * @return Builder
         */
        public Builder setAction(SubscriptionListAction action) {
            this.action = action;
            return this;
        }

        /**
         * The listId you want to set.
         *
         * @param listId String
         * @return Builder
         */
        public Builder setListId(String listId) {
            this.listId = listId;
            return this;
        }

        public SubscriptionList build() {
            Preconditions.checkNotNull(action, "Action must be set.");
            Preconditions.checkNotNull(listId, "ListId must be set.");

            return new SubscriptionList(this);
        }
    }
}
