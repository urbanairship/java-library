/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;


import com.urbanairship.api.client.model.APIListAllSchedulesResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public final class APIListAllSchedulesResponseReader implements JsonObjectReader<APIListAllSchedulesResponse> {

    private final APIListAllSchedulesResponse.Builder builder;

    public APIListAllSchedulesResponseReader() {
        this.builder = APIListAllSchedulesResponse.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.getBooleanValue());
    }

    public void readCount(JsonParser jsonParser) throws IOException {
        builder.setCount(jsonParser.readValueAs(Number.class).intValue());
    }

    public void readTotalCount(JsonParser jsonParser) throws IOException {
        builder.setTotalCount(jsonParser.readValueAs(Number.class).intValue());
    }

    public void readNextPage(JsonParser jsonParser) throws IOException {
        builder.setNextPage(jsonParser.readValueAs(String.class));
    }

    public void readListScheduleResponse(JsonParser jsonParser) throws IOException {
        builder.addAllSchedule((List<SchedulePayload>) jsonParser.readValueAs(new TypeReference<List<SchedulePayload>>() {
        }));
    }

    @Override
    public APIListAllSchedulesResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
