package com.urbanairship.api.push.parse.localization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.localization.Localization;

import java.io.IOException;

public class LocalizationSerializer extends JsonSerializer<Localization> {
    @Override
    public void serialize(Localization localization, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (localization.getLanguage().isPresent()) {
            jgen.writeStringField("language", localization.getLanguage().get());
        }
        if (localization.getCountry().isPresent()) {
            jgen.writeStringField("country", localization.getCountry().get());
        }
        if (localization.getNotification().isPresent()) {
            jgen.writeObjectField("notification", localization.getNotification().get());
        }
        if (localization.getRichPushMessage().isPresent()) {
            jgen.writeObjectField("message", localization.getRichPushMessage().get());
        }
        if (localization.getInApp().isPresent()) {
            jgen.writeObjectField("in_app", localization.getInApp().get());
        }

        jgen.writeEndObject();
    }
}
