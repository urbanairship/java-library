/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.APIErrorDetails;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.APIParsingException;
import org.codehaus.jackson.JsonParser;


import java.io.IOException;

/*
Readers are the part of the deserialization process that actually builds and
return an object.
 */
public final class APIErrorDetailsReader implements JsonObjectReader<APIErrorDetails>{

    private final APIErrorDetails.Builder builder;

    public APIErrorDetailsReader(){
        this.builder = APIErrorDetails.newBuilder();
    }

    public void readPath(JsonParser parser) throws IOException {
        builder.setPath(parser.readValueAs(String.class));
    }

    public void readError(JsonParser parser) throws IOException {
        builder.setError(parser.readValueAs(String.class));
    }

    public void readLocation(JsonParser parser) throws IOException {
        builder.setLocation(parser.readValueAs(APIErrorDetails.Location.class));
    }

    @Override
    public APIErrorDetails validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception ex){
            throw new APIParsingException(ex.getMessage());
        }
    }
}
