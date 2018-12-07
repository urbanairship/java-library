package com.urbanairship.api.createandsend.parse.notification.email;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.notification.email.VariableDetail;

import java.io.IOException;

public class VariableDetailSerializer extends JsonSerializer<VariableDetail> {
    @Override
    public void serialize(VariableDetail variableDetail, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (variableDetail.getDefaultValue().isPresent()) {
            jgen.writeStringField("defaul_value", variableDetail.getDefaultValue().get());
        }

        if (variableDetail.getKey().isPresent()) {
            jgen.writeStringField("key", variableDetail.getKey().get());
        }

        jgen.writeEndObject();
    }
}
