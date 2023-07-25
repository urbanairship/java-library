/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.BooleanFieldDeserializer;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.PushOptions;

import java.io.IOException;

public class PushOptionsReader implements JsonObjectReader<PushOptions> {

    private final PushOptions.Builder builder;

    public PushOptionsReader() {
        this.builder = PushOptions.newBuilder();
    }

    public void readExpiry(JsonParser parser) throws IOException {
        builder.setExpiry(parser.readValueAs(PushExpiry.class));
    }

    public void readNoThrottle(JsonParser parser) throws IOException {
        builder.setNoThrottle(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "no_throttle"));
    }

    public void readPersonalization(JsonParser parser) throws IOException {
        builder.setPersonalization(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "personalization"));
    }

    public void readRedactPayload(JsonParser parser) throws IOException {
        builder.setRedactPayload(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "personalization"));
    }

    public void readBypassHoldoutGroups(JsonParser parser) throws IOException {
        builder.setBypassHoldoutGroups(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "personalization"));
    }

    public void readBypassFrequencyLimits(JsonParser parser) throws IOException {
        builder.setBypassFrequencyLimits(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "bypass_frequency_limits"));
    }

    @Override
    public PushOptions validateAndBuild() throws IOException {
        return builder.build();
    }
}
