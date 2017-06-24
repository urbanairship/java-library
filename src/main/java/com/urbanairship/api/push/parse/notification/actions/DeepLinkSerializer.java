/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.actions.DeepLinkAction;

import java.io.IOException;

public final class DeepLinkSerializer extends JsonSerializer<DeepLinkAction> {
    @Override
    public void serialize(DeepLinkAction value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        try {
            jgen.writeObjectField("type", "deep_link");
            jgen.writeObjectField("content", value.getLink());

            if (value.getFallbackUrl().isPresent()) {
                jgen.writeStringField("fallback_url", value.getFallbackUrl().get());
            }
        }
        finally {
            jgen.writeEndObject();
        }
    }
}
