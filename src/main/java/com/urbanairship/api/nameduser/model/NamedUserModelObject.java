package com.urbanairship.api.nameduser.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.model.APIModelObject;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;

public class NamedUserModelObject extends APIModelObject {
    private final ObjectMapper MAPPER = NamedUserObjectMapper.getInstance();

    @Override
    public String toJSON() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (Exception e) {
            return toJSON(e);
        }
    }
}
