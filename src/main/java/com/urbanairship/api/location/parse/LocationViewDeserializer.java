/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.location.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.location.model.LocationView;

import java.io.IOException;

public class LocationViewDeserializer extends JsonDeserializer<LocationView> {

    public static final LocationViewDeserializer INSTANCE = new LocationViewDeserializer();
    private static final FieldParserRegistry<LocationView, LocationViewReader> FIELD_PARSERS = new MapFieldParserRegistry<LocationView, LocationViewReader>(
            ImmutableMap.<String, FieldParser<LocationViewReader>>builder()
                    .put("bounds", new FieldParser<LocationViewReader>() {
                        @Override
                        public void parse(LocationViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readBounds(jsonParser);
                        }
                    })
                    .put("centroid", new FieldParser<LocationViewReader>() {
                        @Override
                        public void parse(LocationViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readCentroid(jsonParser);
                        }
                    })
                    .put("id", new FieldParser<LocationViewReader>() {
                        @Override
                        public void parse(LocationViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readId(jsonParser);
                        }
                    })
                    .put("properties", new FieldParser<LocationViewReader>() {
                        @Override
                        public void parse(LocationViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPropertyJSONNode(jsonParser);
                        }
                    })
                    .put("type", new FieldParser<LocationViewReader>() {
                        @Override
                        public void parse(LocationViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readType(jsonParser);
                        }
                    })
                    .build()
    );
    private final StandardObjectDeserializer<LocationView, ?> deserializer;

    public LocationViewDeserializer() {
        deserializer = new StandardObjectDeserializer<LocationView, LocationViewReader>(
                FIELD_PARSERS,
                new Supplier<LocationViewReader>() {
                    @Override
                    public LocationViewReader get() {
                        return new LocationViewReader();
                    }
                }
        );
    }


    @Override
    public LocationView deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        try {
            return deserializer.deserialize(parser, deserializationContext);
        } catch (Exception e) {
            throw APIParsingException.raise(String.format("Error parsing location object. %s", e.getMessage()), parser);
        }
    }
}
