package com.urbanairship.api.nameduser.model;

import java.util.Arrays;
import java.util.Optional;

public enum NamedUserScopeType {

    APP("app"),
    WEB("web"),
    EMAIL("email"),
    SMS("sms");

    private final String identifier;

    private NamedUserScopeType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static Optional<NamedUserScopeType> find (String identifier) {
        for (NamedUserScopeType scopeType : values()) {
            if (scopeType.getIdentifier().equals(identifier)) {
                return Optional.of(scopeType);
            }
        }
        return Arrays.stream(values())
                .filter(scopeType -> scopeType.getIdentifier().equals(identifier))
                .findFirst();
    }
}
