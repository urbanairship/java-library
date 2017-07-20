/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.urbanairship.api.push.model.notification.android.BigTextStyle;
import com.urbanairship.api.push.model.notification.android.Style;

import java.io.IOException;

public class BigTextStyleSerializer extends JsonSerializer<BigTextStyle> {

    @Override
    public void serialize(BigTextStyle style, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        //jgen.writeStartObject();

        jgen.writeStringField("type", Style.Type.BIG_TEXT.getStyleType());
        jgen.writeStringField("big_text", style.getContent());
        if (style.getTitle().isPresent()) {
            jgen.writeStringField("title", style.getTitle().get());
        }
        if (style.getSummary().isPresent()) {
            jgen.writeStringField("summary", style.getSummary().get());
        }

        //jgen.writeEndObject();
    }


    @Override
    public void serializeWithType(BigTextStyle style, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSerializer) throws IOException {
        typeSerializer.writeTypePrefixForObject(style, jgen);
        serialize(style, jgen, provider);
        typeSerializer.writeTypeSuffixForObject(style, jgen);
    }
}
