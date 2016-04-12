/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;


import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.RequestErrorDetails;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

/*
Deserializers create a mapping between Jackson and an object. This abstracts all
the boilerplate necessary for Jackson stream parsing, which is essentially what
 we're doing. This will be a lot cleaner when lambda's come down.
 If you're using Intellij, be sure and toggle open the code that's
 been collapsed.
 */
public final class RequestErrorDetailsDeserializer extends JsonDeserializer<RequestErrorDetails> {

    private static final FieldParserRegistry<RequestErrorDetails, RequestErrorDetailsReader> FIELD_PARSERS =
            new MapFieldParserRegistry<RequestErrorDetails, RequestErrorDetailsReader>(
                    ImmutableMap.<String, FieldParser<RequestErrorDetailsReader>>builder()
                            .put("path", new FieldParser<RequestErrorDetailsReader>() {
                                @Override
                                public void parse(RequestErrorDetailsReader reader, JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readPath(jsonParser);
                                }
                            })
                            .put("error", new FieldParser<RequestErrorDetailsReader>() {
                                @Override
                                public void parse(RequestErrorDetailsReader reader, JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readError(jsonParser);
                                }
                            })
                            .put("location", new FieldParser<RequestErrorDetailsReader>() {
                                @Override
                                public void parse(RequestErrorDetailsReader reader, JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readLocation(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<RequestErrorDetails, ?> deserializer;

    // See Google Guava for Supplier details
    public RequestErrorDetailsDeserializer() {
        deserializer = new StandardObjectDeserializer<RequestErrorDetails, RequestErrorDetailsReader>(
                FIELD_PARSERS,
                new Supplier<RequestErrorDetailsReader>() {
                    @Override
                    public RequestErrorDetailsReader get() {
                        return new RequestErrorDetailsReader();
                    }
                }
        );
    }

    public RequestErrorDetails deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
