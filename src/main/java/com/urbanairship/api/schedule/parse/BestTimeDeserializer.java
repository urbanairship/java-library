package com.urbanairship.api.schedule.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.schedule.model.BestTime;

import java.io.IOException;

public class BestTimeDeserializer extends JsonDeserializer<BestTime> {

    private static final FieldParserRegistry<BestTime, BestTimeReader> FIELD_PARSERS = new MapFieldParserRegistry<BestTime, BestTimeReader>(
            ImmutableMap.<String, FieldParser<BestTimeReader>>builder()
                    .put("send_date", (reader, jsonParser, deserializationContext) -> reader.readSendDate(jsonParser))
                    .build()
    );

    public static final BestTimeDeserializer INSTANCE = new BestTimeDeserializer();

    private final StandardObjectDeserializer<BestTime, ?> deserializer;

    public BestTimeDeserializer() {
        deserializer = new StandardObjectDeserializer<BestTime, BestTimeReader>(
                FIELD_PARSERS,
                () -> new BestTimeReader()
        );
    }

    @Override
    public BestTime deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
