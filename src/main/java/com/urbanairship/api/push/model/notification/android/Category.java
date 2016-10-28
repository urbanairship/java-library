/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.android;

import com.google.common.base.Optional;

/**
 * Enum of Android categories
 */
public enum Category {

    ALARM("alarm"),
    CALL("call"),
    EMAIL("email"),
    ERR("err"),
    EVENT("event"),
    MSG("msg"),
    PROMO("promo"),
    RECOMMENDATION("recommendation"),
    SERVICE("service"),
    SOCIAL("social"),
    STATUS("status"),
    SYS("sys"),
    TRANSPORT("transport");

    private final String category;

    private Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public static Optional<Category> find(String identifier) {
        for (Category channelType : values()) {
            if (channelType.getCategory().equals(identifier)) {
                return Optional.of(channelType);
            }
        }

        return Optional.absent();
    }
}
