package com.urbanairship.api.channel.model.email;

import java.util.Optional;

/**
 * Enum of opt in modes
 */
public enum OptInMode {

    CLASSIC("classic"),
    DOUBLE("double");

    private final String identifier;

    private OptInMode() {
        this(null);
    }

    private OptInMode(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<OptInMode> find(String identifier) {
        for (OptInMode optInLevel : values()) {
            if (optInLevel.getIdentifier().equals(identifier)) {
                return Optional.of(optInLevel);
            }
        }

        return Optional.empty();
    }

    public String getIdentifier() {
        return identifier;
    }
}