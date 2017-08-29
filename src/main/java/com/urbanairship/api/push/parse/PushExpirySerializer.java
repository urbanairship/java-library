/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.push.model.PushExpiry;

import java.io.IOException;

public class PushExpirySerializer extends JsonSerializer<PushExpiry> {

    @Override
    public void serialize(PushExpiry payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (payload.getExpiryTimeStamp().isPresent()) {
            jgen.writeString(DateFormats.DATE_FORMATTER.print(payload.getExpiryTimeStamp().get()));
        }
        if (payload.getExpirySeconds().isPresent()) {
            jgen.writeNumber(payload.getExpirySeconds().get());
        }
    }
}
