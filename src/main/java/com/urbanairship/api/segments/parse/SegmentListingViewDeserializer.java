package com.urbanairship.api.segments.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.segments.model.SegmentListingView;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class SegmentListingViewDeserializer extends JsonDeserializer<SegmentListingView> {
    public static final FieldParserRegistry<SegmentListingView, SegmentListingViewReader> FIELD_PARSER =
            new MapFieldParserRegistry<SegmentListingView, SegmentListingViewReader>(
                    ImmutableMap.<String, FieldParser<SegmentListingViewReader>>builder()
                            .put("creation_date", new FieldParser<SegmentListingViewReader>() {
                                @Override
                                public void parse(SegmentListingViewReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readCreationDate(jsonParser);
                                }
                            })
                            .put("display_name", new FieldParser<SegmentListingViewReader>() {
                                @Override
                                public void parse(SegmentListingViewReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readDisplayName(jsonParser);
                                }
                            })
                            .put("id", new FieldParser<SegmentListingViewReader>() {
                                @Override
                                public void parse(SegmentListingViewReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readId(jsonParser);
                                }
                            })
                            .put("modification_date", new FieldParser<SegmentListingViewReader>() {
                                @Override
                                public void parse(SegmentListingViewReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readModificationDate(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<SegmentListingView, ?> deserializer;

    public SegmentListingViewDeserializer() {
        this.deserializer = new StandardObjectDeserializer<SegmentListingView, SegmentListingViewReader>(
                FIELD_PARSER,
                new Supplier<SegmentListingViewReader>() {
                    @Override
                    public SegmentListingViewReader get() {
                        return new SegmentListingViewReader();
                    }
                }
        );
    }

    @Override
    public SegmentListingView deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
