package com.urbanairship.api.channel.parse.email;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.audience.CreateAndSendAudience;

import java.io.IOException;

public class CreateAndSendEmailChannelSerializer extends JsonSerializer<CreateAndSendAudience> {
    @Override
    public void serialize(CreateAndSendAudience createAndSendAudience, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

    }
}
