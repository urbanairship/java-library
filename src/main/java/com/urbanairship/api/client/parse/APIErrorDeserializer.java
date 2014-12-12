/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.APIError;
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
public final class APIErrorDeserializer extends JsonDeserializer<APIError> {

    private static final FieldParserRegistry<APIError, APIErrorReader> FIELD_PARSERS =
            new MapFieldParserRegistry<APIError, APIErrorReader>(
                    ImmutableMap.<String, FieldParser<APIErrorReader>>builder()
                            .put("operation_id", new FieldParser<APIErrorReader>() {
                                @Override
                                public void parse(APIErrorReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readOperationId(jsonParser);
                                }
                            })
                            .put("error", new FieldParser<APIErrorReader>() {
                                @Override
                                public void parse(APIErrorReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readError(jsonParser);
                                }
                            })
                            .put("error_code", new FieldParser<APIErrorReader>() {
                                @Override
                                public void parse(APIErrorReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readErrorCode(jsonParser);
                                }
                            })
                            .put("details", new FieldParser<APIErrorReader>() {
                                @Override
                                public void parse(APIErrorReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readDetails(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<APIError, ?> deserializer;

    // See Google Guava for Supplier details
    public APIErrorDeserializer() {
        deserializer = new StandardObjectDeserializer<APIError, APIErrorReader>(
                FIELD_PARSERS,
                new Supplier<APIErrorReader>() {
                    @Override
                    public APIErrorReader get() {
                        return new APIErrorReader();
                    }
                }
        );
    }

    public APIError deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
