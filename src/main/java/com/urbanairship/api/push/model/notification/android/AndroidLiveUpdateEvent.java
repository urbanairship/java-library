package com.urbanairship.api.push.model.notification.android;

import java.util.Optional;

/**
 * Enum of iosLiveActivityEvent
 */
public enum AndroidLiveUpdateEvent {

    START("start"),
    UPDATE("update"),
    END("end");

    private final String androidLiveUpdateEvent;

    AndroidLiveUpdateEvent(String androidLiveUpdateEvent) {
        this.androidLiveUpdateEvent = androidLiveUpdateEvent;
    }

    public String getAndroidLiveUpdateEvent() {
        return androidLiveUpdateEvent;
    }

    public static Optional<AndroidLiveUpdateEvent> find(String identifier) {
        for (AndroidLiveUpdateEvent androidLiveUpdateEvent : values()) {
            if (androidLiveUpdateEvent.getAndroidLiveUpdateEvent().equals(identifier)) {
                return Optional.of(androidLiveUpdateEvent);
            }
        }

        return Optional.empty();
    }
}
