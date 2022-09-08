package com.urbanairship.api.channel.model.email;

import java.util.Optional;

/**
 * Enum of opt in levels
 */
public enum OptInLevel {

    EMAIL_COMMERCIAL_OPTED_IN("commercial_opted_in"),
    EMAIL_COMMERCIAL_OPTED_OUT("commercial_opted_out"),
    EMAIL_TRANSACTIONAL_OPTED_IN("transactional_opted_in"),
    EMAIL_TRANSACTIONAL_OPTED_OUT("transactional_opted_out"),
    CREATE_AND_SEND_COMMERCIAL_OPTED_IN("ua_commercial_opted_in"),
    CREATE_AND_SEND_TRANSACTIONAL_OPTED_IN("ua_transactional_opted_in"),
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

        return Optional.empty();
    }

    public String getIdentifier() {
        return identifier;
    }
}