package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.APIListScheduleResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

// No Unit Tests for this Class
public class APIListScheduleResponseDeserializer extends JsonDeserializer<APIListScheduleResponse> {

    private static final FieldParserRegistry<APIListScheduleResponse, APIListScheduleResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<APIListScheduleResponse, APIListScheduleResponseReader>(
                    ImmutableMap.<String, FieldParser<APIListScheduleResponseReader>>builder()
                            .put("count", new FieldParser<APIListScheduleResponseReader>() {
                                @Override
                                public void parse(APIListScheduleResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readCount(jsonParser);
                                }
                            })
                            .put("total_count", new FieldParser<APIListScheduleResponseReader>() {
                                @Override
                                public void parse(APIListScheduleResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readTotalCount(jsonParser);
                                }
                            })
                            .put("schedules", new FieldParser<APIListScheduleResponseReader>() {
                                @Override
                                public void parse(APIListScheduleResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readListScheduleResponse(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<APIListScheduleResponse, ?> deserializer;

    // See Google Guava for Supplier details
    public APIListScheduleResponseDeserializer(){
        this.deserializer = new StandardObjectDeserializer<APIListScheduleResponse, APIListScheduleResponseReader>(
                FIELD_PARSER,
                new Supplier<APIListScheduleResponseReader>() {
                    @Override
                    public APIListScheduleResponseReader get() {
                        return new APIListScheduleResponseReader();
                    }
                }
        );
    }

    @Override
    public APIListScheduleResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
