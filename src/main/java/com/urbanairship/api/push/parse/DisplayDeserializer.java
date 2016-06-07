package com.urbanairship.api.push.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.Display;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class DisplayDeserializer extends JsonDeserializer<Display> {
    private static final FieldParserRegistry<Display, DisplayReader> FIELD_PARSERS =
            new MapFieldParserRegistry<Display, DisplayReader>(
                    ImmutableMap.<String, FieldParser<DisplayReader>>builder()
                            .put("primary_color", new FieldParser<DisplayReader>() {
                                @Override
                                public void parse(DisplayReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readPrimaryColor(jsonParser);
                                }
                            })
                            .put("secondary_color", new FieldParser<DisplayReader>() {
                                @Override
                                public void parse(DisplayReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readSecondaryColor(jsonParser);
                                }
                            })
                            .put("duration", new FieldParser<DisplayReader>() {
                                @Override
                                public void parse(DisplayReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readDuration(jsonParser);
                                }
                            })
                            .put("position", new FieldParser<DisplayReader>() {
                                @Override
                                public void parse(DisplayReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readPosition(jsonParser);
                                }
                            })
                            .build());

    private final StandardObjectDeserializer<Display, ?> deserializer;

    public DisplayDeserializer() {
        deserializer = new StandardObjectDeserializer<Display, DisplayReader>(
                FIELD_PARSERS,
                new Supplier<DisplayReader>() {
                    @Override
                    public DisplayReader get() {
                        return new DisplayReader();
                    }
                }
        );
    }

    @Override
    public Display deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }

}
