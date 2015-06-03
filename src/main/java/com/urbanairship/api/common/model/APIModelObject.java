/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
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
        return "{ \"exception\" : \"" + e.getClass().getName() + "\", \"message\" : \"" + e.getMessage() + "\" }";

    }
}
