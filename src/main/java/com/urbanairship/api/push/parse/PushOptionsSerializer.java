/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.PushOptions;

import java.io.IOException;

public class PushOptionsSerializer extends JsonSerializer<PushOptions> {
    @Override
    public void serialize(PushOptions payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (payload.getExpiry().isPresent()) {
            jgen.writeObjectField("expiry", payload.getExpiry().get());
        }
        if (payload.getNoThrottle().isPresent()) {
            jgen.writeBooleanField("no_throttle", payload.getNoThrottle().get());
        }
        if (payload.getPersonalization().isPresent()) {
            jgen.writeBooleanField("personalization", payload.getPersonalization().get());
        }
        if (payload.getRedactPayload().isPresent()) {
            jgen.writeBooleanField("redact_payload", payload.getRedactPayload().get());
        }
        if (payload.getBypassHoldoutGroups().isPresent()) {
            jgen.writeBooleanField("bypass_holdout_groups", payload.getBypassHoldoutGroups().get());
        }
        if (payload.getBypassFrequencyLimits().isPresent()) {
            jgen.writeBooleanField("bypass_frequency_limits", payload.getBypassFrequencyLimits().get());
        }

        jgen.writeEndObject();
    }
}
