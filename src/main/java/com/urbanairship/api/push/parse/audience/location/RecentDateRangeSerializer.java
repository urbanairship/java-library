/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.audience.location.PresenceTimeframe;
import com.urbanairship.api.push.model.audience.location.RecentDateRange;

import java.io.IOException;

public class RecentDateRangeSerializer extends JsonSerializer<RecentDateRange> {
    @Override
    public void serialize(RecentDateRange range, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        if (range.getTimeframe() == PresenceTimeframe.LAST_SEEN) {
            jgen.writeBooleanField("last_seen", true);
        }
        jgen.writeObjectFieldStart("recent");
        jgen.writeNumberField(range.getResolution().getIdentifier(),
                              range.getUnits());
        jgen.writeEndObject();
        jgen.writeEndObject();
    }
}
