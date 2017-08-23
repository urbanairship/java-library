/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.actions.LandingPageContent;
import com.urbanairship.api.push.model.notification.actions.OpenLandingPageWithContentAction;

import java.io.IOException;

public final class LandingPageWithContentSerializer extends JsonSerializer<OpenLandingPageWithContentAction> {
    @Override
    public void serialize(OpenLandingPageWithContentAction value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        try {
            jgen.writeObjectField("type", "landing_page");

            LandingPageContent content = value.getValue();
            jgen.writeFieldName("content");
            jgen.writeStartObject();
            try {
                jgen.writeStringField("body", content.getBody());
                jgen.writeStringField("content_type", content.getContentType());
                if(content.getEncoding().isPresent()) {
                    jgen.writeStringField("content_encoding", content.getEncoding().get() == LandingPageContent.Encoding.UTF8 ?
                            "utf-8" :
                            "base64");
                }
            }
            finally {
                jgen.writeEndObject();
            }
        }
        finally {
            jgen.writeEndObject();
        }
    }
}
