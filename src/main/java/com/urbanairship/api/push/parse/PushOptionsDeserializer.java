/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.PushOptions;

import java.io.IOException;

public class PushOptionsDeserializer extends JsonDeserializer<PushOptions> {

    private static final FieldParserRegistry<PushOptions, PushOptionsReader> FIELD_PARSERS = new MapFieldParserRegistry<PushOptions, PushOptionsReader>(ImmutableMap.<String, FieldParser<PushOptionsReader>>builder()
        .put("expiry", (reader, parser, context) -> reader.readExpiry(parser))
        .put("no_throttle", (reader, parser, context) -> reader.readNoThrottle(parser))
        .put("personalization", (reader, parser, context) -> reader.readPersonalization(parser))
        .put("redact_payload", (reader, parser, context) -> reader.readRedactPayload(parser))
        .put("bypass_holdout_groups", (reader, parser, context) -> reader.readBypassHoldoutGroups(parser))
        .put("bypass_frequency_limits", (reader, parser, context) -> reader.readBypassFrequencyLimits(parser))
            .build()
    );

    private final StandardObjectDeserializer<PushOptions, ?> deserializer;

    public PushOptionsDeserializer() {
        deserializer = new StandardObjectDeserializer<PushOptions, PushOptionsReader>(
                FIELD_PARSERS,
                () -> new PushOptionsReader()
        );
    }

    @Override
    public PushOptions deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
