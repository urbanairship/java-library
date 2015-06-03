package com.urbanairship.api.location.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.location.model.Location;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class LocationDeserializer extends JsonDeserializer<Location> {

    public static final LocationDeserializer INSTANCE = new LocationDeserializer();
    private static final FieldParserRegistry<Location, LocationReader> FIELD_PARSERS = new MapFieldParserRegistry<Location, LocationReader>(
            ImmutableMap.<String, FieldParser<LocationReader>>builder()
                    .put("bounds", new FieldParser<LocationReader>() {
                        @Override
                        public void parse(LocationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readBounds(jsonParser);
                        }
                    })
                    .put("centroid", new FieldParser<LocationReader>() {
                        @Override
                        public void parse(LocationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readCentroid(jsonParser);
                        }
                    })
                    .put("id", new FieldParser<LocationReader>() {
                        @Override
                        public void parse(LocationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readId(jsonParser);
                        }
                    })
                    .put("properties", new FieldParser<LocationReader>() {
                        @Override
                        public void parse(LocationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPropertyJSONNode(jsonParser);
                        }
                    })
                    .put("type", new FieldParser<LocationReader>() {
                        @Override
                        public void parse(LocationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readType(jsonParser);
                        }
                    })
                    .build()
    );
    private final StandardObjectDeserializer<Location, ?> deserializer;

    public LocationDeserializer() {
        deserializer = new StandardObjectDeserializer<Location, LocationReader>(
                FIELD_PARSERS,
                new Supplier<LocationReader>() {
                    @Override
                    public LocationReader get() {
                        return new LocationReader();
                    }
                }
        );
    }


    @Override
    public Location deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        try {
            return deserializer.deserialize(parser, deserializationContext);
        } catch (Exception e) {
            throw APIParsingException.raise(String.format("Error parsing location object. %s", e.getMessage()), parser);
        }
    }
}
