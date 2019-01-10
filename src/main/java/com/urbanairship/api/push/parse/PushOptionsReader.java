/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.PushNoThrottle;
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
        builder.setNoThrottle(parser.readValueAs(PushNoThrottle.class));
    }

    @Override
    public PushOptions validateAndBuild() throws IOException {
        return builder.build();
    }
}
