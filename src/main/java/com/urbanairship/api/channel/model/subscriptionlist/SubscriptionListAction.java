package com.urbanairship.api.channel.model.subscriptionlist;

import com.google.common.base.Optional;

public enum SubscriptionListAction {
    SUBSCRIBE("subscribe"),
    UNSUBSCRIBE("unsubscribe");

    private final String identifier;

    private SubscriptionListAction(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<SubscriptionListAction> find(String identifier) {
        for (SubscriptionListAction subscriptionListAction : values()) {
            if (subscriptionListAction.getIdentifier().equals(identifier)) {
                return Optional.of(subscriptionListAction);
            }
        }

        return Optional.absent();
    }

    public String getIdentifier() {
        return identifier;
    }
}
