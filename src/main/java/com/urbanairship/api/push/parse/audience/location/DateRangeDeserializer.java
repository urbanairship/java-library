/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.audience.location.DateRange;
import com.urbanairship.api.push.model.audience.location.DateRangeUnit;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class DateRangeDeserializer extends JsonDeserializer<DateRange> {

    private static final FieldParserRegistry<DateRange, DateRangeReader> FIELD_PARSERS = new MapFieldParserRegistry<DateRange, DateRangeReader>(
        ImmutableMap.<String, FieldParser<DateRangeReader>>builder()
        .put("recent", new FieldParser<DateRangeReader>() {
                @Override
                public void parse(DateRangeReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                    reader.readRecent(parser);
                }
            })
        .put("last_seen", new FieldParser<DateRangeReader>() {
                @Override
                public void parse(DateRangeReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                    reader.readTimeframe(parser);
                }
            })
        .build(),
        /* Default field parser */
        new FieldParser<DateRangeReader>() {
            @Override
            public void parse(DateRangeReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                String field = parser.getCurrentName();
                DateRangeUnit resolution = DateRangeUnit.getUnitForIdentifier(field);
                if (resolution != null) {
                    reader.readAbsolute(parser);
                    reader.setResolution(resolution);
                }
            }
        });

    private final StandardObjectDeserializer<DateRange, ?> deserializer;

    public DateRangeDeserializer() {
        deserializer = new StandardObjectDeserializer<DateRange, DateRangeReader>(
            FIELD_PARSERS,
            new Supplier<DateRangeReader>() {
                @Override
                public DateRangeReader get() {
                    return new DateRangeReader();
                }
            }
            );
    }

    @Override
        public DateRange deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
