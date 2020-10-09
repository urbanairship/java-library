package com.urbanairship.api.channel.model.attributes;

import com.google.common.base.Optional;

public enum AttributeAction {
    SET("set"),
    REMOVE("remove");

    private final String identifier;

    private AttributeAction(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<AttributeAction> find(String identifier) {
        for (AttributeAction attributeAction : values()) {
            if (attributeAction.getIdentifier().equals(identifier)) {
                return Optional.of(attributeAction);
            }
        }

        return Optional.absent();
    }

    public String getIdentifier() {
        return identifier;
    }
}
