/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.audience.location.AbsoluteDateRange;
import com.urbanairship.api.push.model.audience.location.PresenceTimeframe;

import java.io.IOException;

public class AbsoluteDateRangeSerializer extends JsonSerializer<AbsoluteDateRange> {
    @Override
    public void serialize(AbsoluteDateRange range, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        if (range.getTimeframe() == PresenceTimeframe.LAST_SEEN) {
            jgen.writeBooleanField("last_seen", true);
        }
        jgen.writeObjectFieldStart(range.getResolution().getIdentifier());
        jgen.writeObjectField("start", range.getStart());
        jgen.writeObjectField("end", range.getEnd());
        jgen.writeEndObject();
        jgen.writeEndObject();
    }
}
