package com.urbanairship.api.push.parse;

import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.push.model.PushExpiry;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import java.util.Map;
import java.io.IOException;

public class PushExpirySerializer extends JsonSerializer<PushExpiry> {
    @Override
    public void serialize(PushExpiry payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (payload.getExpiryTimeStamp().isPresent()) {
            jgen.writeObjectField("expiryTimestamp", payload.getExpiryTimeStamp().get());
        }
        if (payload.getExpirySeconds().isPresent()) {
            jgen.writeObjectField("expirySeconds", payload.getExpirySeconds().get());
        }

        jgen.writeEndObject();
    }
}
