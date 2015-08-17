package com.urbanairship.api.schedule;

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
