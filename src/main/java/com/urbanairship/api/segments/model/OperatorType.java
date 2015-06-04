/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

public enum OperatorType {
    AND("and"), OR("or"), NOT("not");

    private final String identifier;

    OperatorType(String identifier) {
        this.identifier = identifier;
    }

    public static OperatorType getOperatorTypeFromIdentifier(String identifier) {
        for (OperatorType operatorType : values()) {
            if (operatorType.identifier.equals(identifier)) {
                return operatorType;
            }
        }

        return null;
    }

    public String getIdentifier() {
        return identifier;
    }
}
