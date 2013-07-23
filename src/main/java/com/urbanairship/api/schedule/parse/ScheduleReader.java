package com.urbanairship.api.schedule.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.schedule.model.Schedule;
import org.codehaus.jackson.JsonParser;
import org.joda.time.DateTime;

import java.io.IOException;

public class ScheduleReader implements JsonObjectReader<Schedule> {

    private final Schedule.Builder builder;

    public ScheduleReader() {
        this.builder = Schedule.newBuilder();
    }

    public void readScheduledTime(JsonParser jsonParser) throws IOException {
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
