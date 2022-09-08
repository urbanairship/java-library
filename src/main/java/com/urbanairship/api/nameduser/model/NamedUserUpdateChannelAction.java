package com.urbanairship.api.nameduser.model;

import java.util.Optional;

public enum NamedUserUpdateChannelAction {
    ASSOCIATE("associate"),
    DISASSOCIATE("disassociate");

    private final String identifier;

    private NamedUserUpdateChannelAction(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<NamedUserUpdateChannelAction> find(String identifier) {
        for (NamedUserUpdateChannelAction namedUserUpdateChannelAction : values()) {
            if (namedUserUpdateChannelAction.getIdentifier().equals(identifier)) {
                return Optional.of(namedUserUpdateChannelAction);
            }
        }

        return Optional.empty();
    }

    public String getIdentifier() {
        return identifier;
    }
}