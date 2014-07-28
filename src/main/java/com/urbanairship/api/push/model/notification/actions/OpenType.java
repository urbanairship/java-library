/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Function;
import com.google.common.base.Optional;

public enum OpenType {

    URL("url"),
    DEEP_LINK("deep_link"),
    LANDING_PAGE("landing_page");

    private final String identifier;

    private OpenType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static Optional<OpenType> find(String id) {
        return fromIdentifierFunction.apply(id);
    }

    public static final Function<String, Optional<OpenType>> fromIdentifierFunction = new Function<String, Optional<OpenType>>() {
        @Override
        public Optional<OpenType> apply(String identifier) {
            for (OpenType deviceType : values()) {
                if (deviceType.getIdentifier().equals(identifier)) {
                    return Optional.of(deviceType);
                }
            }

            return Optional.absent();
        }
    };
}
