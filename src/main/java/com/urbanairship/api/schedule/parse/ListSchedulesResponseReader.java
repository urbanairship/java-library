/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.parse;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;
import com.urbanairship.api.schedule.model.SchedulePayload;

import java.io.IOException;
import java.util.List;

public final class ListSchedulesResponseReader implements JsonObjectReader<ListAllSchedulesResponse> {

    private final ListAllSchedulesResponse.Builder builder;

    public ListSchedulesResponseReader() {
        this.builder = ListAllSchedulesResponse.newBuilder();
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
    public ListAllSchedulesResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
