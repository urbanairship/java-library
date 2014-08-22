/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.APIListSchedulesResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/*
Readers are the part of the deserialization process that actually builds and
return an object.
 */
public class APIListSchedulesResponseReader implements JsonObjectReader <APIListSchedulesResponse> {

    private final APIListSchedulesResponse.Builder builder;

    public APIListSchedulesResponseReader(){
        this.builder = APIListSchedulesResponse.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.readValueAs(Boolean.class));
    }

    public void readCount(JsonParser jsonParser) throws IOException {
        builder.setCount(jsonParser.readValueAs(Integer.class));
    }

    public void readTotalCount(JsonParser jsonParser) throws IOException {
        builder.setTotalCount(jsonParser.readValueAs(Integer.class));
    }

    public void readNextPage(JsonParser jsonParser) throws IOException {
        builder.setNextPage(jsonParser.readValueAs(String.class));
    }

    public void readSchedules(JsonParser jsonParser) throws IOException {
        List<Map> list = jsonParser.readValueAs(new TypeReference<List<Map>>() {
        });

        builder.setSchedules(list);
    }

    @Override
    public APIListSchedulesResponse validateAndBuild() throws IOException {
        try{
            return builder.build();
        }
        catch (Exception ex){
            throw new APIParsingException(ex.getMessage());
        }
    }
}
