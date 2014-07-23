/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSBinding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class WNSBindingSerializer extends JsonSerializer<WNSBinding> {
    @Override
    public void serialize(WNSBinding binding, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("template", binding.getTemplate());

        if (binding.getVersion().isPresent()) {
            jgen.writeNumberField("version", binding.getVersion().get());
        }

        if (binding.getFallback().isPresent()) {
            jgen.writeStringField("fallback", binding.getFallback().get());
        }

        if (binding.getLang().isPresent()) {
            jgen.writeStringField("lang", binding.getLang().get());
        }

        if (binding.getBaseUri().isPresent()) {
            jgen.writeStringField("base_uri", binding.getBaseUri().get());
        }

        if (binding.getAddImageQuery().isPresent()) {
            jgen.writeBooleanField("add_image_query", binding.getAddImageQuery().get());
        }

        if (binding.getImageCount() > 0) {
            jgen.writeArrayFieldStart("image");
            for (String text : binding.getImages().get()) {
                jgen.writeString(text);
            }
            jgen.writeEndArray();
        }

        if (binding.getTextCount() > 0) {
            jgen.writeArrayFieldStart("text");
            for (String text : binding.getText().get()) {
                jgen.writeString(text);
            }
            jgen.writeEndArray();
        }

        jgen.writeEndObject();
    }
}
