/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.common.model.APIModelObject;

public class APIResponseModelObject extends APIModelObject {

    @Override
    public String toJSON() {
        try {
            return APIResponseObjectMapper.getInstance().writeValueAsString(this);
        } catch ( Exception e ) {
            return toJSON(e);
        }
    }
}
