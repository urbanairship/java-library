package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.push.model.notification.mpns.MPNSToastData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class MPNSToastSerializer extends JsonSerializer<MPNSToastData> {
    @Override
    public void serialize(MPNSToastData toast, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (toast.getText1().isPresent()) {
            jgen.writeStringField("text1", toast.getText1().get());
        }
        if (toast.getText2().isPresent()) {
            jgen.writeStringField("text2", toast.getText2().get());
        }
        if (toast.getParam().isPresent()) {
            jgen.writeStringField("params", toast.getParam().get());
        }

        jgen.writeEndObject();
    }
}
