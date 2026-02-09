/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayloadResponse;

import java.io.IOException;
import java.util.List;

public final class SchedulePayloadResponseReader implements JsonObjectReader<SchedulePayloadResponse> {

    private final SchedulePayloadResponse.Builder builder;

    public SchedulePayloadResponseReader() {
        this.builder = SchedulePayloadResponse.newBuilder();
    }

    public void readSchedule(JsonParser jsonParser) throws IOException {
        builder.setSchedule(jsonParser.readValueAs(Schedule.class));
    }

    public void readUrl(JsonParser jsonParser) throws IOException {
        builder.setUrl(jsonParser.readValueAs(String.class));
    }

    public void readName(JsonParser jsonParser) throws IOException {
        builder.setName(jsonParser.readValueAs(String.class));
    }

    public void readPaused(JsonParser jsonParser) throws IOException {
        builder.setPaused(jsonParser.readValueAs(Boolean.class));
    }

    public void readPushPayload(JsonParser parser) throws IOException {
        builder.setPushPayload(parser.readValueAs(PushPayload.class));
    }

    public void readPushIds(JsonParser parser) throws IOException {
        List<String> ids = parser.readValueAs(new TypeReference<List<String>>() { });
        builder.addAllPushIds(ids);
    }

    @Override
    public SchedulePayloadResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
