/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.APIListSchedulesResponse;
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
class APIListSchedulesResponseDeserializer extends JsonDeserializer<APIListSchedulesResponse> {

    private static final FieldParserRegistry<APIListSchedulesResponse, APIListSchedulesResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<APIListSchedulesResponse, APIListSchedulesResponseReader>(
                    ImmutableMap.<String, FieldParser<APIListSchedulesResponseReader>>builder()
                                .put("ok", new FieldParser<APIListSchedulesResponseReader>() {
                                    @Override
                                    public void parse(APIListSchedulesResponseReader reader,
                                                      JsonParser jsonParser,
                                                      DeserializationContext deserializationContext) throws IOException {
                                        reader.readOk(jsonParser);
                                    }
                                })
                                .put("count", new FieldParser<APIListSchedulesResponseReader>() {
                                    @Override
                                    public void parse(APIListSchedulesResponseReader reader,
                                                      JsonParser jsonParser,
                                                      DeserializationContext deserializationContext) throws IOException {
                                        reader.readCount(jsonParser);
                                    }
                                })
                                .put("total_count", new FieldParser<APIListSchedulesResponseReader>() {
                                    @Override
                                    public void parse(APIListSchedulesResponseReader reader,
                                                      JsonParser jsonParser,
                                                      DeserializationContext deserializationContext) throws IOException {
                                        reader.readTotalCount(jsonParser);
                                    }
                                })
                                .put("next_page", new FieldParser<APIListSchedulesResponseReader>() {
                                    @Override
                                    public void parse(APIListSchedulesResponseReader reader,
                                                      JsonParser jsonParser,
                                                      DeserializationContext deserializationContext) throws IOException {
                                        reader.readNextPage(jsonParser);
                                    }
                                })
                                .put("schedules", new FieldParser<APIListSchedulesResponseReader>() {
                                    @Override
                                    public void parse(APIListSchedulesResponseReader reader,
                                                      JsonParser jsonParser,
                                                      DeserializationContext deserializationContext) throws IOException {
                                        reader.readSchedules(jsonParser);
                                    }
                                })
                                .build()
            );

    private final StandardObjectDeserializer<APIListSchedulesResponse, ?> deserializer;

    // See Google Guava for Supplier details
    public APIListSchedulesResponseDeserializer(){
        this.deserializer = new StandardObjectDeserializer<APIListSchedulesResponse, APIListSchedulesResponseReader>(
            FIELD_PARSER,
            new Supplier<APIListSchedulesResponseReader>() {
                @Override
                public APIListSchedulesResponseReader get() {
                    return new APIListSchedulesResponseReader();
                }
            }
        );
    }

    @Override
    public APIListSchedulesResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
