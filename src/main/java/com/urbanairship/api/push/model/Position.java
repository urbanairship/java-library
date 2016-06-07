/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Optional;

/**
 * Enum for specifying the notification position.
 */
public enum Position {
    TOP("top"),
    BOTTOM("bottom");

    private final String type;

    private Position(String type) {
        this.type = type;
    }

    public static Optional<Position> find(String type) {
        for (Position position : values()) {
            if (position.getType().equals(type)) {
                return Optional.of(position);
            }
        }

        return Optional.absent();
    }

    public String getType() {
        return type;
    }
}