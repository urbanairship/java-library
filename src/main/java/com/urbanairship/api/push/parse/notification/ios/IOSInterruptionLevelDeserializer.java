package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.ios.IOSInterruptionLevel;

import java.io.IOException;
import java.util.Optional;

public final class IOSInterruptionLevelDeserializer extends JsonDeserializer<IOSInterruptionLevel> {

    @Override
    public IOSInterruptionLevel deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        String iOSInterruptionLevelString = jp.getText();
        Optional<IOSInterruptionLevel> iosInterruptionLevel = IOSInterruptionLevel.find(iOSInterruptionLevelString);

        if (!iosInterruptionLevel.isPresent()) {
            throw new APIParsingException("Unrecognized interruption level " + iOSInterruptionLevelString);
        }

        return iosInterruptionLevel.get();
    }
}
