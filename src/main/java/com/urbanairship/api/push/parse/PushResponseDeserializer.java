/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.PushResponse;

import java.io.IOException;

/*
Deserializers create a mapping between Jackson and an object. This abstracts all
the boilerplate necessary for Jackson stream parsing, which is essentially what
 we're doing. This will be a lot cleaner when lambda's come down.
 If you're using Intellij, be sure and toggle open the code that's
 been collapsed.
 */
public final class PushResponseDeserializer extends JsonDeserializer<PushResponse> {

    private static final FieldParserRegistry<PushResponse, PushResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<PushResponse, PushResponseReader>(
                    ImmutableMap.<String, FieldParser<PushResponseReader>>builder()
                            .put("operation_id", (reader, jsonParser, deserializationContext) -> reader.readOperationId(jsonParser))
                            .put("push_ids", (reader, jsonParser, deserializationContext) -> reader.readPushIds(jsonParser))
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("message_ids", (reader, jsonParser, deserializationContext) -> reader.readMessageIds(jsonParser))
                            .put("content_urls", (reader, jsonParser, deserializationContext) -> reader.readContentUrls(jsonParser))
                            .put("localized_ids", (reader, jsonParser, deserializationContext) -> reader.readLocalizedIds(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<PushResponse, ?> deserializer;

    // See Google Guava for Supplier details
    public PushResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<>(
                FIELD_PARSERS,
                () -> new PushResponseReader()
        );
    }

    @Override
    public PushResponse deserialize(JsonParser parser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
