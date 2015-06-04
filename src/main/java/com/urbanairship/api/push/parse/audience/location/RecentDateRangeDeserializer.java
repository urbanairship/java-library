/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.audience.location.RecentDateRange;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class RecentDateRangeDeserializer extends JsonDeserializer<RecentDateRange.Builder> {

    private static final FieldParserRegistry<RecentDateRange.Builder, RecentDateRangeReader> FIELD_PARSERS = new MapFieldParserRegistry<RecentDateRange.Builder, RecentDateRangeReader>(
        ImmutableMap.<String, FieldParser<RecentDateRangeReader>>builder().build(),
        /* Only need the default field parser */
        new FieldParser<RecentDateRangeReader>() {
            @Override
            public void parse(RecentDateRangeReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                reader.readValue(parser);
            }
        });

    private final StandardObjectDeserializer<RecentDateRange.Builder, ?> deserializer;

    public RecentDateRangeDeserializer() {
        deserializer = new StandardObjectDeserializer<RecentDateRange.Builder, RecentDateRangeReader>(
            FIELD_PARSERS,
            new Supplier<RecentDateRangeReader>() {
                @Override
                public RecentDateRangeReader get() {
                    return new RecentDateRangeReader();
                }
            }
            );
    }

    @Override
    public RecentDateRange.Builder deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
