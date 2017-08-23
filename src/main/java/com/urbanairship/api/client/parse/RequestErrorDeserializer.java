/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.RequestError;
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
public final class RequestErrorDeserializer extends JsonDeserializer<RequestError> {

    private static final FieldParserRegistry<RequestError, RequestErrorReader> FIELD_PARSERS =
            new MapFieldParserRegistry<RequestError, RequestErrorReader>(
                    ImmutableMap.<String, FieldParser<RequestErrorReader>>builder()
                            .put("ok", new FieldParser<RequestErrorReader>() {
                                @Override
                                public void parse(RequestErrorReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readOk(jsonParser);
                                }
                            })
                            .put("operation_id", new FieldParser<RequestErrorReader>() {
                                @Override
                                public void parse(RequestErrorReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readOperationId(jsonParser);
                                }
                            })
                            .put("error", new FieldParser<RequestErrorReader>() {
                                @Override
                                public void parse(RequestErrorReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readError(jsonParser);
                                }
                            })
                            .put("error_code", new FieldParser<RequestErrorReader>() {
                                @Override
                                public void parse(RequestErrorReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readErrorCode(jsonParser);
                                }
                            })
                            .put("details", new FieldParser<RequestErrorReader>() {
                                @Override
                                public void parse(RequestErrorReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readDetails(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<RequestError, ?> deserializer;

    // See Google Guava for Supplier details
    public RequestErrorDeserializer() {
        deserializer = new StandardObjectDeserializer<RequestError, RequestErrorReader>(
                FIELD_PARSERS,
                new Supplier<RequestErrorReader>() {
                    @Override
                    public RequestErrorReader get() {
                        return new RequestErrorReader();
                    }
                }
        );
    }

    public RequestError deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
