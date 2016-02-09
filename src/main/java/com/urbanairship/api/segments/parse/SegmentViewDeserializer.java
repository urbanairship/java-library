package com.urbanairship.api.segments.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.segments.model.SegmentView;
import com.urbanairship.api.segments.model.SegmentView;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class SegmentViewDeserializer extends JsonDeserializer<SegmentView> {
    public static final FieldParserRegistry<SegmentView, SegmentViewReader> FIELD_PARSER =
            new MapFieldParserRegistry<SegmentView, SegmentViewReader>(
                    ImmutableMap.<String, FieldParser<SegmentViewReader>>builder()
                            .put("criteria", new FieldParser<SegmentViewReader>() {
                                @Override
                                public void parse(SegmentViewReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readCriteria(jsonParser);
                                }
                            })
                            .put("display_name", new FieldParser<SegmentViewReader>() {
                                @Override
                                public void parse(SegmentViewReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readDisplayName(jsonParser);
                                }
                            })
                            .build()
            );


    private final StandardObjectDeserializer<SegmentView, ?> deserializer;

    public SegmentViewDeserializer() {
        this.deserializer = new StandardObjectDeserializer<SegmentView, SegmentViewReader>(
                FIELD_PARSER,
                new Supplier<SegmentViewReader>() {
                    @Override
                    public SegmentViewReader get() {
                        return new SegmentViewReader();
                    }
                }
        );
    }

    @Override
    public SegmentView deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
