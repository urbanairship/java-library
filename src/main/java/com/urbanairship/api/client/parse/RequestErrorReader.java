/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;


import com.urbanairship.api.client.RequestError;
import com.urbanairship.api.client.RequestErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

/*
Readers are the part of the deserialization process that actually builds and
return an object.
 */
public final class RequestErrorReader implements JsonObjectReader<RequestError> {

    private final RequestError.Builder builder;

    public RequestErrorReader() {
        this.builder = RequestError.newBuilder();
    }

    public void readOk(JsonParser parser) throws IOException {
        builder.setOk(parser.getBooleanValue());
    }

    public void readOperationId(JsonParser parser) throws IOException {
        builder.setOperationId(parser.readValueAs(String.class));
    }

    public void readError(JsonParser parser) throws IOException {
        builder.setError(parser.readValueAs(String.class));
    }

    public void readErrorCode(JsonParser parser) throws IOException {
        builder.setErrorCode(parser.readValueAs(Number.class));
    }

    public void readDetails(JsonParser parser) throws IOException {
        builder.setDetails(parser.readValueAs(RequestErrorDetails.class));
    }

    @Override
    public RequestError validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
