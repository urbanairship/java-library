package com.urbanairship.api.push.model.notification.ios;

import java.util.Optional;

/**
 * Enum of iosLiveActivityEvent
 */
public enum IOSLiveActivityEvent {

    START("start"),
    UPDATE("update"),
    END("end");

    private final String iosLiveActivityEvent;

    IOSLiveActivityEvent(String iosLiveActivityEvent) {
        this.iosLiveActivityEvent = iosLiveActivityEvent;
    }

    public String getIosLiveActivityEvent() {
        return iosLiveActivityEvent;
    }

    public static Optional<IOSLiveActivityEvent> find(String identifier) {
        for (IOSLiveActivityEvent iosLiveActivityEvent : values()) {
            if (iosLiveActivityEvent.getIosLiveActivityEvent().equals(identifier)) {
                return Optional.of(iosLiveActivityEvent);
            }
        }

        return Optional.empty();
    }
}
