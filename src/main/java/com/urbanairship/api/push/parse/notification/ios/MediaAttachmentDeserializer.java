/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.MediaAttachment;

import java.io.IOException;

public class MediaAttachmentDeserializer extends JsonDeserializer<MediaAttachment> {

    private static final FieldParserRegistry<MediaAttachment, MediaAttachmentReader> FIELD_PARSER = new MapFieldParserRegistry<MediaAttachment, MediaAttachmentReader>(
            ImmutableMap.<String, FieldParser<MediaAttachmentReader>>builder()
            .put("options", (reader, json, context) -> reader.readOptions(json, context))
            .put("url", (reader, json, context) -> reader.readUrl(json))
            .put("content", (reader, json, context) -> reader.readContent(json, context))
            .build()
    );

    private final StandardObjectDeserializer<MediaAttachment, ?> deserializer;

    public MediaAttachmentDeserializer() {
        deserializer = new StandardObjectDeserializer<MediaAttachment, MediaAttachmentReader>(
                FIELD_PARSER,
                () -> new MediaAttachmentReader()
        );
    }

    @Override
    public MediaAttachment deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return deserializer.deserialize(jp, ctxt);
    }
}
