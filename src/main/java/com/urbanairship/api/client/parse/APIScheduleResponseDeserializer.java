/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.model.APIScheduleResponse;
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
public final class APIScheduleResponseDeserializer extends JsonDeserializer<APIScheduleResponse> {

    private static final FieldParserRegistry<APIScheduleResponse, APIScheduleResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<APIScheduleResponse, APIScheduleResponseReader>(
                    ImmutableMap.<String, FieldParser<APIScheduleResponseReader>>builder()
                            .put("ok", new FieldParser<APIScheduleResponseReader>() {
                                @Override
                                public void parse(APIScheduleResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readOk(jsonParser);
                                }
                            })
                            .put("operation_id", new FieldParser<APIScheduleResponseReader>() {
                                @Override
                                public void parse(APIScheduleResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readOperationId(jsonParser);
                                }
                            })
                            .put("schedule_urls", new FieldParser<APIScheduleResponseReader>() {
                                @Override
                                public void parse(APIScheduleResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readScheduleIds(jsonParser);
                                }
                            })
                            .put("schedules", new FieldParser<APIScheduleResponseReader>() {
                                @Override
                                public void parse(APIScheduleResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readSchedulePayloads(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<APIScheduleResponse, ?> deserializer;

    public APIScheduleResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<APIScheduleResponse, APIScheduleResponseReader>(
                FIELD_PARSER,
                new Supplier<APIScheduleResponseReader>() {
                    @Override
                    public APIScheduleResponseReader get() {
                        return new APIScheduleResponseReader();
                    }
                }
        );
    }

    @Override
    public APIScheduleResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}