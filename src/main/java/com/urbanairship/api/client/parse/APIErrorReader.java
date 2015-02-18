/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;


import com.urbanairship.api.client.APIError;
import com.urbanairship.api.client.APIErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

/*
Readers are the part of the deserialization process that actually builds and
return an object.
 */
public final class APIErrorReader implements JsonObjectReader<APIError> {

    private final APIError.Builder builder;

    public APIErrorReader() {
        this.builder = APIError.newBuilder();
    }

    public void readOk(JsonParser parser) throws IOException {
        builder.setOk(parser.readValueAs(Boolean.class));
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
        builder.setDetails(parser.readValueAs(APIErrorDetails.class));
    }

    @Override
    public APIError validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
