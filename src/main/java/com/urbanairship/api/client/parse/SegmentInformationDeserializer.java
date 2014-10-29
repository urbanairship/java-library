package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.model.SegmentInformation;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class SegmentInformationDeserializer extends JsonDeserializer<SegmentInformation> {

    private static final FieldParserRegistry<SegmentInformation, SegmentInformationReader> FIELD_PARSER =
            new MapFieldParserRegistry<SegmentInformation, SegmentInformationReader>(
                    ImmutableMap.<String, FieldParser<SegmentInformationReader>>builder()
                            .put("creation_date", new FieldParser<SegmentInformationReader>() {
                                @Override
                                public void parse(SegmentInformationReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readCreationDate(jsonParser);
                                }
                            })
                            .put("display_name", new FieldParser<SegmentInformationReader>() {
                                @Override
                                public void parse(SegmentInformationReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readDisplayName(jsonParser);
                                }
                            })
                            .put("id", new FieldParser<SegmentInformationReader>() {
                                @Override
                                public void parse(SegmentInformationReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readId(jsonParser);
                                }
                            })
                            .put("modification_date", new FieldParser<SegmentInformationReader>() {
                                @Override
                                public void parse(SegmentInformationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readModificationDate(jsonParser);
                                }
                            })
                            .build()
            );
    private final StandardObjectDeserializer<SegmentInformation, ?> deserializer;

    public SegmentInformationDeserializer(){
        this.deserializer = new StandardObjectDeserializer<SegmentInformation, SegmentInformationReader>(
                FIELD_PARSER,
                new Supplier<SegmentInformationReader>() {
                    @Override
                    public SegmentInformationReader get() {
                        return new SegmentInformationReader();
                    }
                }
        );
    }
    @Override
    public SegmentInformation deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
