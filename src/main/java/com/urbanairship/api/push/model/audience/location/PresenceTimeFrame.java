package com.urbanairship.api.push.model.audience.location;

/**
 * Denotes whether the time value is a last seen time, where a match only
 * occurs if the device was last seen at the location, or whether the location
 * is an anytime location, where a match occurs if the device was present in
 * the location at any time.
 */
public enum PresenceTimeFrame {
    LAST_SEEN,
    ANYTIME
}
