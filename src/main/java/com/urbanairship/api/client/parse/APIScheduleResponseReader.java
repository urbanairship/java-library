package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.APIScheduleResponse;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

/*
Readers are the part of the deserialization process that actually builds and
return an object.
 */
public class APIScheduleResponseReader implements JsonObjectReader <APIScheduleResponse> {

    private final APIScheduleResponse.Builder builder;

    public APIScheduleResponseReader (){
        this.builder = APIScheduleResponse.newBuilder();
    }

    public void readOperationId(JsonParser jsonParser) throws IOException {
        builder.setOperationId(jsonParser.readValueAs(String.class));
    }

    public void readScheduleIds(JsonParser jsonParser) throws IOException {
        List<String> list =
                jsonParser.readValueAs(new TypeReference<List<String>>(){});
        builder.setScheduleUrls(list);
    }

    @Override
    public APIScheduleResponse validateAndBuild() throws IOException {
        try{
            return builder.build();
        }
        catch (Exception ex){
            throw new APIParsingException(ex.getMessage());
        }
    }
}
