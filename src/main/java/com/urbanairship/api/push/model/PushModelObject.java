package com.urbanairship.api.push.model;

import com.urbanairship.api.common.model.APIModelObject;
import com.urbanairship.api.push.parse.PushObjectMapper;

public class PushModelObject extends APIModelObject {
    @Override
    public String toJSON() {
        try {
            return PushObjectMapper.getInstance().writeValueAsString(this);
        } catch ( Exception e ) {
            return toJSON(e);
        }
    }
}
