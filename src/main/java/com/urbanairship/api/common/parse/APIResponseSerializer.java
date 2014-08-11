package com.urbanairship.api.common.parse;

import com.urbanairship.api.common.model.APIResponse;
import com.urbanairship.api.common.model.APIError;
import static com.urbanairship.api.common.model.APIConstants.Field;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class APIResponseSerializer extends JsonSerializer<APIResponse> {

    @Override
    public void serialize(APIResponse value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        boolean ok = ! value.getError().isPresent();
        jgen.writeBooleanField(Field.OK, ok);

        if (value.getError().isPresent()) {
            APIError error = value.getError().get();
            jgen.writeStringField(Field.ERROR, error.getError());
            jgen.writeNumberField(Field.ERROR_CODE, error.getErrorCode());
            if (error.getDetails().isPresent()) {
                jgen.writeObjectField(Field.ERROR_DETAILS, error.getDetails().get());
            }
        }

        if (value.getData().isPresent()) {
            Map<String, Object> data = value.getData().get();
            for (String key : data.keySet()) {
                jgen.writeObjectField(key, data.get(key));
            }
        }

        jgen.writeEndObject();
    }
}
