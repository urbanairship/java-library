package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Optional;

/**
 * Enum of iOS Interruption levels
 */
public enum IOSInterruptionLevel {

    PASSIVE("passive"),
    ACTIVE("active"),
    TIME_SENSITIVE("time-sensitive"),
    CRITICAL("critical");

    private final String interruptionLevel;

    private IOSInterruptionLevel(String interruptionLevel) {
        this.interruptionLevel = interruptionLevel;
    }

    public String getInterruptionLevel() {
        return interruptionLevel;
    }

    public static Optional<IOSInterruptionLevel> find(String identifier) {
        for (IOSInterruptionLevel interruptionLevel : values()) {
            if (interruptionLevel.getInterruptionLevel().equals(identifier)) {
                return Optional.of(interruptionLevel);
            }
        }

        return Optional.absent();
    }
}
