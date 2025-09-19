package com.urbanairship.api.channel.model.email;

import java.util.Optional;

/**
 * Enum of tracking opt in/out levels for email channels.
 */
public enum TrackingOptInLevel {

    OPEN_TRACKING_OPTED_IN("open_tracking_opted_in"),
    OPEN_TRACKING_OPTED_OUT("open_tracking_opted_out"),
    CLICK_TRACKING_OPTED_IN("click_tracking_opted_in"),
    CLICK_TRACKING_OPTED_OUT("click_tracking_opted_out");

    private final String identifier;

    private TrackingOptInLevel() {
        this(null);
    }

    private TrackingOptInLevel(String identifier) {
        this.identifier = identifier;
    }

    public static Optional<TrackingOptInLevel> find(String identifier) {
        for (TrackingOptInLevel level : values()) {
            if (level.getIdentifier().equals(identifier)) {
                return Optional.of(level);
            }
        }

        return Optional.empty();
    }

    public String getIdentifier() {
        return identifier;
    }
}
