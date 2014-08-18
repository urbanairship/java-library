/*
 * Copyright 2014 Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;


import com.urbanairship.api.client.APIListTagsResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class APIListTagsResponseReader implements JsonObjectReader<APIListTagsResponse> {

    private final APIListTagsResponse.Builder builder;

    public APIListTagsResponseReader() { this.builder = APIListTagsResponse.newBuilder(); }

    public void readTags(JsonParser jsonParser) throws IOException { builder.allAllTags((List<String>) jsonParser.readValueAs(new TypeReference<List<String>>() {})); }

    @Override
    public APIListTagsResponse validateAndBuild() throws IOException {
        try{
            return builder.build();
        }
        catch (Exception ex){
            throw new APIParsingException(ex.getMessage());
        }
    }


}
