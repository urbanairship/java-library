package com.urbanairship.api.common.parse;

import com.urbanairship.api.common.model.APIErrorDetails;
import static com.urbanairship.api.common.model.APIConstants.Field;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class APIErrorDetailsSerializer extends JsonSerializer<APIErrorDetails> {

    @Override
    public void serialize(APIErrorDetails value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField(Field.ERROR, value.getError());
        if (value.getPath().isPresent()) {
            jgen.writeStringField(Field.ERROR_PATH, value.getPath().get());
        }
        if (value.getLocation().isPresent()) {
            jgen.writeObjectFieldStart(Field.ERROR_LOCATION);
            jgen.writeNumberField(Field.ERROR_LINE, value.getLocation().get().getLine());
            jgen.writeNumberField(Field.ERROR_COLUMN, value.getLocation().get().getColumn());
            jgen.writeEndObject();
        }
        jgen.writeEndObject();
    }
}
