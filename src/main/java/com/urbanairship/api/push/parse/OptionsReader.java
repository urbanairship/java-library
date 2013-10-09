/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.Options;
import org.codehaus.jackson.JsonParser;
import org.joda.time.DateTime;

import java.io.IOException;

public class OptionsReader implements JsonObjectReader<Options> {

    private final Options.Builder builder;

    public OptionsReader() {
        this.builder = Options.newBuilder();
    }

    public void readExpiry(JsonParser jsonParser) throws IOException {
        // TODO: not sure this is the best way to read expiry, but expiry can be specified as seconds (integer) or a datetime
        try {
            builder.setExpirySeconds(jsonParser.readValueAs(Integer.class));
        } catch (IOException e) {
            builder.setExpiry(jsonParser.readValueAs(DateTime.class));
        }
    }

    @Override
    public Options validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
