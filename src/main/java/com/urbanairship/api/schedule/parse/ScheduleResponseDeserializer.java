/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.schedule.model.ScheduleResponse;
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
public final class ScheduleResponseDeserializer extends JsonDeserializer<ScheduleResponse> {

    private static final FieldParserRegistry<ScheduleResponse, ScheduleResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<ScheduleResponse, ScheduleResponseReader>(
                    ImmutableMap.<String, FieldParser<ScheduleResponseReader>>builder()
                            .put("ok", new FieldParser<ScheduleResponseReader>() {
                                @Override
                                public void parse(ScheduleResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readOk(jsonParser);
                                }
                            })
                            .put("operation_id", new FieldParser<ScheduleResponseReader>() {
                                @Override
                                public void parse(ScheduleResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readOperationId(jsonParser);
                                }
                            })
                            .put("schedule_urls", new FieldParser<ScheduleResponseReader>() {
                                @Override
                                public void parse(ScheduleResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readScheduleUrls(jsonParser);
                                }
                            })
                            .put("schedule_ids", new FieldParser<ScheduleResponseReader>() {
                                @Override
                                public void parse(ScheduleResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readScheduleIds(jsonParser);
                                }
                            })
                            .put("schedules", new FieldParser<ScheduleResponseReader>() {
                                @Override
                                public void parse(ScheduleResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readSchedulePayloads(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<ScheduleResponse, ?> deserializer;

    public ScheduleResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<ScheduleResponse, ScheduleResponseReader>(
                FIELD_PARSER,
                new Supplier<ScheduleResponseReader>() {
                    @Override
                    public ScheduleResponseReader get() {
                        return new ScheduleResponseReader();
                    }
                }
        );
    }

    @Override
    public ScheduleResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
