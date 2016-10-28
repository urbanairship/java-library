/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.location.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.location.model.LocationResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class LocationResponseDeserializer extends JsonDeserializer<LocationResponse> {

    private static final FieldParserRegistry<LocationResponse, LocationResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<LocationResponse, LocationResponseReader>(
                    ImmutableMap.<String, FieldParser<LocationResponseReader>>builder()
                            .put("features", new FieldParser<LocationResponseReader>() {
                                @Override
                                public void parse(LocationResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readFeatures(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<LocationResponse, ?> deserializer;

    public LocationResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<LocationResponse, LocationResponseReader>(
                FIELD_PARSER,
                new Supplier<LocationResponseReader>() {
                    @Override
                    public LocationResponseReader get() {
                        return new LocationResponseReader();
                    }
                }
        );
    }

    @Override
    public LocationResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
