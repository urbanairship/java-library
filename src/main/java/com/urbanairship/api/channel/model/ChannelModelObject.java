package com.urbanairship.api.channel.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.common.model.APIModelObject;

public class ChannelModelObject extends APIModelObject {
    private final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();
    @Override
    public String toJSON() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (Exception e) {
            return toJSON(e);
        }
    }
}
