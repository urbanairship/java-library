/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.PushNoThrottle;

import java.io.IOException;

public class PushNoThrottleSerializer extends JsonSerializer<PushNoThrottle> {

    @Override
    public void serialize(PushNoThrottle payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (payload.getValue().isPresent()) {
            jgen.writeBoolean(payload.getValue().get());
        }

    }
}
