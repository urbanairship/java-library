/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.parse.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.channel.model.ios.IosSettings;
import com.urbanairship.api.channel.model.ios.QuietTime;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;

import java.io.IOException;

public final class IosSettingsReader implements JsonObjectReader<IosSettings> {

    private final IosSettings.Builder builder;

    public IosSettingsReader() {
        this.builder = IosSettings.newBuilder();
    }

    public void readBadge(JsonParser jsonParser) throws IOException {
        builder.setBadge(jsonParser.getIntValue());
    }

    public void readQuietTime(JsonParser jsonParser) throws IOException {
        builder.setQuietTime(jsonParser.readValueAs(QuietTime.class));
    }

    public void readTimeZone(JsonParser jsonParser) throws IOException {
        builder.setTimeZone(jsonParser.readValueAs(String.class));
    }

    @Override
    public IosSettings validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
