package com.urbanairship.api.push.parse;

import com.google.common.base.Optional;
import com.urbanairship.api.client.UrbanAirshipClient;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.Display;
import com.urbanairship.api.push.model.Position;
import org.codehaus.jackson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DisplayReader implements JsonObjectReader<Display> {
    private static final Logger log = LoggerFactory.getLogger(UrbanAirshipClient.class);

    private final Display.Builder builder;

    public DisplayReader() {
        this.builder = Display.newBuilder();
    }

    public void readPrimaryColor(JsonParser parser) throws IOException {
        builder.setPrimaryColor(parser.readValueAs(String.class));
    }

    public void readSecondaryColor(JsonParser parser) throws IOException {
        builder.setSecondaryColor(parser.readValueAs(String.class));
    }

    public void readDuration(JsonParser parser) throws IOException {
        builder.setDuration(parser.readValueAs(Integer.class));
    }

    public void readPosition(JsonParser parser) throws IOException {
        String positionString = parser.getText();
        Optional<Position> positionOpt = Position.find(positionString);

        if (!positionOpt.isPresent()) {
            log.error("Unrecognized position " + positionString);
            return;
        }

        builder.setPosition(positionOpt.get());
    }

    @Override
    public Display validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
