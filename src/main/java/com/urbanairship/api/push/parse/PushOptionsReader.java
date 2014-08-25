package com.urbanairship.api.push.parse;

import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.PushOptions;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class PushOptionsReader implements JsonObjectReader<PushOptions> {

    private final PushOptions.Builder builder;

    public PushOptionsReader() {
        this.builder = PushOptions.newBuilder();
    }

    @Override
    public PushOptions validateAndBuild() throws IOException {
        return builder.build();
    }
}
