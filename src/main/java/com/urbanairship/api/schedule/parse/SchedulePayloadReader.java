package com.urbanairship.api.schedule.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class SchedulePayloadReader implements JsonObjectReader<SchedulePayload> {

    private final SchedulePayload.Builder builder;

    public SchedulePayloadReader() {
        this.builder = SchedulePayload.newBuilder();
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

    public void readPushPayload(JsonParser parser) throws IOException {
        builder.setPushPayload(parser.readValueAs(PushPayload.class));
    }

    public void readPushIds(JsonParser parser) throws IOException {
        builder.addAllPushIds( (List<String>) parser.readValueAs(new TypeReference<List<String>>() { }));
    }

    @Override
    public SchedulePayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
