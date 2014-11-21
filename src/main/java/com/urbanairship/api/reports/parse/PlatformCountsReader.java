/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.PerPushCounts;
import com.urbanairship.api.reports.model.PlatformCounts;
import com.urbanairship.api.reports.model.PlatformType;
import com.urbanairship.api.reports.model.RichPerPushCounts;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.util.Map;

public class PlatformCountsReader implements JsonObjectReader<PlatformCounts> {

    private final PlatformCounts.Builder builder;

    public PlatformCountsReader() {
        this.builder = PlatformCounts.newBuilder();
    }

    public void readPlatformCounts(JsonParser jsonParser) throws IOException {
        Map<String, PerPushCounts> obj = jsonParser.readValueAs(new TypeReference<Map<String, PerPushCounts>>() { });

        for (String s : obj.keySet()) {
            PlatformType type = PlatformType.find(s).get();
            builder.addPlatform(type, obj.get(s));
        }
    }

    public void readRichPlatformCounts(JsonParser jsonParser) throws IOException {
        Map<String, RichPerPushCounts> obj = jsonParser.readValueAs(new TypeReference<Map<String, RichPerPushCounts>>() { });

        for (String s : obj.keySet()) {
            PlatformType type = PlatformType.find(s).get();
            builder.addRichPlatform(type, obj.get(s));
        }
    }

    public void readTime(JsonParser jsonParser) throws IOException {
        String created = jsonParser.readValueAs(String.class);
        String pattern = "yyyy-MM-dd HH:mm:ss";
        builder.setTime(DateTime.parse(created, DateTimeFormat.forPattern(pattern)));
    }

    @Override
    public PlatformCounts validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
