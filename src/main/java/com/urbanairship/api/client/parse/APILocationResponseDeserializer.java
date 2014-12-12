package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.model.APILocationResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class APILocationResponseDeserializer extends JsonDeserializer<APILocationResponse> {

    private static final FieldParserRegistry<APILocationResponse, APILocationResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<APILocationResponse, APILocationResponseReader>(
                    ImmutableMap.<String, FieldParser<APILocationResponseReader>>builder()
                            .put("features", new FieldParser<APILocationResponseReader>() {
                                @Override
                                public void parse(APILocationResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readFeatures(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<APILocationResponse, ?> deserializer;

    public APILocationResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<APILocationResponse, APILocationResponseReader>(
                FIELD_PARSER,
                new Supplier<APILocationResponseReader>() {
                    @Override
                    public APILocationResponseReader get() {
                        return new APILocationResponseReader();
                    }
                }
        );
    }

    @Override
    public APILocationResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
