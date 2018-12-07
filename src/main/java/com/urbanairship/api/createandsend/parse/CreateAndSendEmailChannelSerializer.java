package com.urbanairship.api.createandsend.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.audience.email.EmailChannel;

import java.io.IOException;

public class CreateAndSendEmailChannelSerializer extends JsonSerializer<EmailChannel> {

    @Override
    public void serialize(EmailChannel payload, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {

        jgen.writeStartObject();
        jgen.writeStringField("ua_address", payload.getUaAddress());

        if (payload.getCommercialOptedIn().isPresent()) {
            jgen.writeStringField("ua_commercial_opted_in", String.format("%s-%s-%sT%s:%s:%s",
                    payload.getCommercialOptedIn().get().getYear(),
                    payload.getCommercialOptedIn().get().getMonthOfYear(),
                    payload.getCommercialOptedIn().get().getDayOfMonth(),
                    payload.getCommercialOptedIn().get().getHourOfDay(),
                    payload.getCommercialOptedIn().get().getMinuteOfHour(),
                    payload.getCommercialOptedIn().get().getSecondOfMinute()
            ));
        } else if (payload.getTransactionalOptedIn().isPresent()) {
            jgen.writeStringField("ua_transactional_opted_in", String.format("%s-%s-%sT%s:%s:%s",
                    payload.getTransactionalOptedIn().get().getYear(),
                    payload.getTransactionalOptedIn().get().getMonthOfYear(),
                    payload.getTransactionalOptedIn().get().getDayOfMonth(),
                    payload.getTransactionalOptedIn().get().getHourOfDay(),
                    payload.getTransactionalOptedIn().get().getMinuteOfHour(),
                    payload.getTransactionalOptedIn().get().getSecondOfMinute()
            ));
            }
        jgen.writeEndObject();
    }
}
