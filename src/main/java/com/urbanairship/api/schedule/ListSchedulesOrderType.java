package com.urbanairship.api.schedule;

/**
 * A class of ordering options for Schedules API listing responses.
 */
public enum ListSchedulesOrderType {

    ASC("asc"),
    DESC("desc");

    private String key;

    ListSchedulesOrderType(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
