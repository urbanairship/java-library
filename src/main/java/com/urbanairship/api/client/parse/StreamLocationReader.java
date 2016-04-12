/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.RequestErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

/*
Readers are the part of the deserialization process that actually builds and
return an object.
 */
public class StreamLocationReader implements JsonObjectReader<RequestErrorDetails.Location> {

    private final RequestErrorDetails.Location.Builder builder;

    public StreamLocationReader() {
        this.builder = RequestErrorDetails.Location.newBuilder();
    }

    public void readLine(JsonParser parser) throws IOException {
        builder.setLine(parser.readValueAs(Number.class));
    }

    public void readColumn(JsonParser parser) throws IOException {
        builder.setColumn(parser.readValueAs(Number.class));
    }

    public RequestErrorDetails.Location validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            // TODO log errors
            throw new APIParsingException(ex.getMessage());
        }
    }
}
