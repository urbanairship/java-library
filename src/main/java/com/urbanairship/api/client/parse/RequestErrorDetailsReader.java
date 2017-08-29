/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.client.RequestErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;

import java.io.IOException;

/*
Readers are the part of the deserialization process that actually builds and
return an object.
 */
public final class RequestErrorDetailsReader implements JsonObjectReader<RequestErrorDetails> {

    private final RequestErrorDetails.Builder builder;

    public RequestErrorDetailsReader() {
        this.builder = RequestErrorDetails.newBuilder();
    }

    public void readPath(JsonParser parser) throws IOException {
        builder.setPath(parser.readValueAs(String.class));
    }

    public void readError(JsonParser parser) throws IOException {
        builder.setError(parser.readValueAs(String.class));
    }

    public void readLocation(JsonParser parser) throws IOException {
        builder.setLocation(parser.readValueAs(RequestErrorDetails.Location.class));
    }

    @Override
    public RequestErrorDetails validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
