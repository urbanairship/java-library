/*
 * Copyright 2013 Urban Airship and Contributors
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
public class StreamLocationReader implements JsonObjectReader<APIErrorDetails.Location> {

    private final APIErrorDetails.Location.Builder builder;

    public StreamLocationReader(){
        this.builder = APIErrorDetails.Location.newBuilder();
    }

    public void readLine(JsonParser parser) throws IOException {
        builder.setLine(parser.readValueAs(Number.class));
    }

    public void readColumn(JsonParser parser) throws IOException {
        builder.setColumn(parser.readValueAs(Number.class));
    }

    public APIErrorDetails.Location validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception ex){
            // TODO log errors
            throw new APIParsingException(ex.getMessage());
        }
    }
}
