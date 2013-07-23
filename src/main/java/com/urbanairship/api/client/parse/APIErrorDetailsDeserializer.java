/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;


import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.APIErrorDetails;
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
class APIErrorDetailsDeserializer extends JsonDeserializer<APIErrorDetails>{

    private static final FieldParserRegistry<APIErrorDetails, APIErrorDetailsReader> FIELD_PARSERS =
            new MapFieldParserRegistry<APIErrorDetails, APIErrorDetailsReader>(
                    ImmutableMap.<String, FieldParser< APIErrorDetailsReader >> builder()
                    .put("path", new FieldParser<APIErrorDetailsReader>() {
                        @Override
                        public void parse(APIErrorDetailsReader reader, JsonParser jsonParser,
                                          DeserializationContext deserializationContext) throws IOException{
                            reader.readPath(jsonParser);
                        }
                    })
                    .put("error", new FieldParser<APIErrorDetailsReader>() {
                        @Override
                        public void parse(APIErrorDetailsReader reader, JsonParser jsonParser,
                                          DeserializationContext deserializationContext) throws IOException {
                            reader.readError(jsonParser);
                        }
                    })
                    .put("location", new FieldParser<APIErrorDetailsReader>() {
                        @Override
                        public void parse(APIErrorDetailsReader reader, JsonParser jsonParser,
                                          DeserializationContext deserializationContext) throws IOException {
                            reader.readLocation(jsonParser);
                        }
                    })
            .build()
            );

    private final StandardObjectDeserializer<APIErrorDetails, ?> deserializer;

    // See Google Guava for Supplier details
    public APIErrorDetailsDeserializer(){
        deserializer = new StandardObjectDeserializer<APIErrorDetails, APIErrorDetailsReader>(
                FIELD_PARSERS,
                new Supplier<APIErrorDetailsReader>() {
                    @Override
                    public APIErrorDetailsReader get() {
                        return new APIErrorDetailsReader();
                    }
                }
        );
    }

    public APIErrorDetails deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
