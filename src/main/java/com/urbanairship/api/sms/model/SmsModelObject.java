package com.urbanairship.api.sms.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.model.APIModelObject;
import com.urbanairship.api.sms.parse.SmsObjectMapper;

public class SmsModelObject extends APIModelObject {
    private final ObjectMapper MAPPER = SmsObjectMapper.getInstance();
    @Override
    public String toJSON() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (Exception e) {
            return toJSON(e);
        }
    }
}
