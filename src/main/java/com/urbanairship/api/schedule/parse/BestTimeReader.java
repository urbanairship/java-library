package com.urbanairship.api.schedule.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.schedule.model.BestTime;
import org.joda.time.DateTime;

import java.io.IOException;

public class BestTimeReader implements JsonObjectReader<BestTime> {

    private final BestTime.Builder builder;

    public BestTimeReader() {
        this.builder = BestTime.newBuilder();
    }

    public void readSendDate(JsonParser jsonParser) throws IOException {
        builder.setSendDate(jsonParser.readValueAs(DateTime.class));
    }

    @Override
    public BestTime validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
