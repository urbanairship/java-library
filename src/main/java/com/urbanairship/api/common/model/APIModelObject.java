/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.common.model;

import com.urbanairship.api.common.parse.CommonObjectMapper;
import java.io.IOException;

public abstract class APIModelObject {
    public String toJSON() {
        try {
            return CommonObjectMapper.getInstance().writeValueAsString(this);
        } catch ( IOException e) {
            return toJSON(e);
        }
    }

    protected static String toJSON(Exception e) {
        StringBuilder sb =  new StringBuilder()
            .append("{ \"exception\" : \"")
            .append(e.getClass().getName())
            .append("\", \"message\" : \"")
            .append(e.getMessage())
            .append("\" }");
        return sb.toString();
    }
}
