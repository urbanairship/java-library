package com.urbanairship.api.createandsend.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.createandsend.model.audience.email.EmailChannel;

import java.io.IOException;

public class CreateAndSendEmailChannelSerializer extends JsonSerializer<EmailChannel> {

    @Override
    public void serialize(EmailChannel payload, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {

        jgen.writeStartObject();
        jgen.writeStringField("ua_address", payload.getUaAddress());

        if (payload.getCommercialOptedIn().isPresent()) {
            jgen.writeStringField("ua_commercial_opted_in", DateFormats.DATE_FORMATTER.print(payload.getCommercialOptedIn().get()));
        } else if (payload.getTransactionalOptedIn().isPresent()) {
            jgen.writeStringField("ua_transactional_opted_in", DateFormats.DATE_FORMATTER.print(payload.getTransactionalOptedIn().get()));
        }
        jgen.writeEndObject();
    }
}
