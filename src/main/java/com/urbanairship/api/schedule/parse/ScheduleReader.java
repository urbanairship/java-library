/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.schedule.model.Schedule;
import org.joda.time.DateTime;

import java.io.IOException;

public final class ScheduleReader implements JsonObjectReader<Schedule> {

    private final Schedule.Builder builder;

    public ScheduleReader() {
        this.builder = Schedule.newBuilder();
    }

    public void readScheduledTime(JsonParser jsonParser) throws IOException {
        String time = jsonParser.readValuesAs(DateTime.class).toString();
        builder.setScheduledTimestamp(jsonParser.readValueAs(DateTime.class));
    }

    @Override
    public Schedule validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
