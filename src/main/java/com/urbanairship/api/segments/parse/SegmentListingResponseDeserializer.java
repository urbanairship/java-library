package com.urbanairship.api.segments.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.segments.model.SegmentListingResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class SegmentListingResponseDeserializer extends JsonDeserializer<SegmentListingResponse> {
    public static final FieldParserRegistry<SegmentListingResponse, SegmentListingResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<SegmentListingResponse, SegmentListingResponseReader>(
                    ImmutableMap.<String, FieldParser<SegmentListingResponseReader>>builder()
                            .put("next_page", new FieldParser<SegmentListingResponseReader>() {
                                @Override
                                public void parse(SegmentListingResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readNextPage(jsonParser);
                                }
                            })
                            .put("segments", new FieldParser<SegmentListingResponseReader>() {
                                @Override
                                public void parse(SegmentListingResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readSegments(jsonParser);
                                }
                            })
                            .build()
    );

    private final StandardObjectDeserializer<SegmentListingResponse, ?> deserializer;

    public SegmentListingResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<SegmentListingResponse, SegmentListingResponseReader>(
                FIELD_PARSER,
                new Supplier<SegmentListingResponseReader>() {
                    @Override
                    public SegmentListingResponseReader get() {
                        return new SegmentListingResponseReader();
                    }
                }
        );
    }

    @Override
    public SegmentListingResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
