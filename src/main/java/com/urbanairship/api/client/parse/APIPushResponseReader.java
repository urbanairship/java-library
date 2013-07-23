/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.APIPushResponse;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

/*
Readers are the part of the deserialization process that actually builds and
return an object.
 */
public class APIPushResponseReader implements JsonObjectReader<APIPushResponse>{

    private final APIPushResponse.Builder builder;

    public APIPushResponseReader(){
        this.builder = APIPushResponse.newBuilder();
    }

    public void readOperationId(JsonParser jsonParser) throws IOException {
        builder.setOperationId(jsonParser.readValueAs(String.class));
    }

    public void readPushIds(JsonParser jsonParser) throws IOException {
        List<String> list =
                jsonParser.readValueAs(new TypeReference<List<String>>(){});
        builder.setPushIds(list);
    }

    @Override
    public APIPushResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e){
            throw new APIParsingException(e.getMessage());
        }
    }
}
