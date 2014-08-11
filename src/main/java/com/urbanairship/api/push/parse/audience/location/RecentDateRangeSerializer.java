package com.urbanairship.api.push.parse.audience.location;

import com.urbanairship.api.push.model.audience.location.RecentDateRange;
import com.urbanairship.api.push.model.audience.location.PresenceTimeframe;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
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
