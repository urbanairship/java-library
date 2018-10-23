package com.urbanairship.api.channel.model.email;

import com.google.common.base.Optional;

/**
 * Enum of opt in levels
 */
public enum OptInLevel {

    COMMERCIAL("commercial"),
    TRANSACTIONAL("transactional"),
    NONE("none");

    private final String identifier;

    private OptInLevel() {
        this(null);
    }

    private OptInLevel(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<OptInLevel> find(String identifier) {
        for (OptInLevel optInLevel : values()) {
            if (optInLevel.getIdentifier().equals(identifier)) {
                return Optional.of(optInLevel);
            }
        }

        return Optional.absent();
    }

    public String getIdentifier() {
        return identifier;
    }

}

