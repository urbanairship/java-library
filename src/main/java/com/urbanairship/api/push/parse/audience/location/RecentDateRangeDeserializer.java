/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.audience.location.RecentDateRange;

import java.io.IOException;

public class RecentDateRangeDeserializer extends JsonDeserializer<RecentDateRange.Builder> {

    private static final FieldParserRegistry<RecentDateRange.Builder, RecentDateRangeReader> FIELD_PARSERS = new MapFieldParserRegistry<RecentDateRange.Builder, RecentDateRangeReader>(
        ImmutableMap.<String, FieldParser<RecentDateRangeReader>>builder().build(),
        /* Only need the default field parser */
            (reader, parser, context) -> reader.readValue(parser));

    private final StandardObjectDeserializer<RecentDateRange.Builder, ?> deserializer;

    public RecentDateRangeDeserializer() {
        deserializer = new StandardObjectDeserializer<RecentDateRange.Builder, RecentDateRangeReader>(
            FIELD_PARSERS,
                () -> new RecentDateRangeReader()
        );
    }

    @Override
    public RecentDateRange.Builder deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
