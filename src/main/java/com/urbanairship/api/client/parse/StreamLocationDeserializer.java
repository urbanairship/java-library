/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.RequestErrorDetails;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

/*
Deserializers create a mapping between Jackson and an object. This abstracts all
the boilerplate necessary for Jackson stream parsing, which is essentially what
 we're doing. This will be a lot cleaner when lambda's come down.
 If you're using Intellij, be sure and toggle open the code that's
 been collapsed.
 */
class StreamLocationDeserializer extends JsonDeserializer<RequestErrorDetails.Location> {

    private static final FieldParserRegistry<RequestErrorDetails.Location, StreamLocationReader> FIELD_PARSERS =
            new MapFieldParserRegistry<RequestErrorDetails.Location, StreamLocationReader>(
                    ImmutableMap.<String, FieldParser<StreamLocationReader>>builder()
                            .put("line", (reader, jsonParser, deserializationContext) -> reader.readLine(jsonParser))
                            .put("column", (reader, jsonParser, deserializationContext) -> reader.readColumn(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<RequestErrorDetails.Location, ?> deserializer;

    // See Google Guava for Supplier details
    public StreamLocationDeserializer() {
        deserializer = new StandardObjectDeserializer<RequestErrorDetails.Location, StreamLocationReader>(
                FIELD_PARSERS,
                () -> new StreamLocationReader()
        );
    }

    @Override
    public RequestErrorDetails.Location deserialize(JsonParser parser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }


}
