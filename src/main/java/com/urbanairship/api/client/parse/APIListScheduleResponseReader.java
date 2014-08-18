package com.urbanairship.api.client.parse;


import com.urbanairship.api.client.APIListScheduleResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

// No Unit Tests for this Class
public class APIListScheduleResponseReader implements JsonObjectReader<APIListScheduleResponse> {

    private final APIListScheduleResponse.Builder builder;

    public APIListScheduleResponseReader() { this.builder = APIListScheduleResponse.newBuilder(); }

    public void readCount(JsonParser jsonParser) throws IOException { builder.setCount(jsonParser.readValueAs(Number.class).intValue()); }
    public void readTotalCount(JsonParser jsonParser) throws IOException { builder.setTotalCount(jsonParser.readValueAs(Number.class).intValue()); }
    public void readNextPage(JsonParser jsonParser) throws IOException { builder.setNextPage(jsonParser.readValueAs(String.class)); }
    public void readListScheduleResponse(JsonParser jsonParser) throws IOException { builder.addAllSchedule((List<SchedulePayload>) jsonParser.readValueAs(new TypeReference<List<SchedulePayload>>() { })); }

    @Override
    public APIListScheduleResponse validateAndBuild() throws IOException {
        try{
            return builder.build();
        }
        catch (Exception ex){
            throw new APIParsingException(ex.getMessage());
        }
    }
}
