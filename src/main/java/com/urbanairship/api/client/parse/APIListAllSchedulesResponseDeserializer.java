/*
 * Copyright 2014 Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.model.APIListAllSchedulesResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class APIListAllSchedulesResponseDeserializer extends JsonDeserializer<APIListAllSchedulesResponse> {

    private static final FieldParserRegistry<APIListAllSchedulesResponse, APIListAllSchedulesResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<APIListAllSchedulesResponse, APIListAllSchedulesResponseReader>(
                    ImmutableMap.<String, FieldParser<APIListAllSchedulesResponseReader>>builder()
                            .put("count", new FieldParser<APIListAllSchedulesResponseReader>() {
                                @Override
                                public void parse(APIListAllSchedulesResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readCount(jsonParser);
                                }
                            })
                            .put("total_count", new FieldParser<APIListAllSchedulesResponseReader>() {
                                @Override
                                public void parse(APIListAllSchedulesResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readTotalCount(jsonParser);
                                }
                            })
                            .put("next_page", new FieldParser<APIListAllSchedulesResponseReader>() {
                                @Override
                                public void parse(APIListAllSchedulesResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readNextPage(jsonParser);
                                }
                            })
                            .put("schedules", new FieldParser<APIListAllSchedulesResponseReader>() {
                                @Override
                                public void parse(APIListAllSchedulesResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readListScheduleResponse(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<APIListAllSchedulesResponse, ?> deserializer;

    public APIListAllSchedulesResponseDeserializer(){
        this.deserializer = new StandardObjectDeserializer<APIListAllSchedulesResponse, APIListAllSchedulesResponseReader>(
                FIELD_PARSER,
                new Supplier<APIListAllSchedulesResponseReader>() {
                    @Override
                    public APIListAllSchedulesResponseReader get() {
                        return new APIListAllSchedulesResponseReader();
                    }
                }
        );
    }

    @Override
    public APIListAllSchedulesResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
