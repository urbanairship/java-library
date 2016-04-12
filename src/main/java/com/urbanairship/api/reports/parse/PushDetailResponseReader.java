/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.Base64ByteArray;
import com.urbanairship.api.reports.model.PerPushCounts;
import com.urbanairship.api.reports.model.PlatformType;
import com.urbanairship.api.reports.model.PushDetailResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class PushDetailResponseReader implements JsonObjectReader<PushDetailResponse> {

    private final PushDetailResponse.Builder builder;

    public PushDetailResponseReader() {
        this.builder = PushDetailResponse.newBuilder();
    }

    public void readAppKey(JsonParser jsonParser) throws IOException {
        builder.setAppKey(jsonParser.readValueAs(String.class));
    }

    public void readPushID(JsonParser jsonParser) throws IOException {
        builder.setPushID(jsonParser.readValueAs(UUID.class));
    }

    public void readCreated(JsonParser jsonParser) throws IOException {
        String created = jsonParser.readValueAs(String.class);
        if (created.equals("0")) {
            builder.setCreated(Optional.<DateTime>absent());
        } else {
            builder.setCreated(Optional.of(DateFormats.DATE_PARSER.parseDateTime(created)));
        }
    }

    public void readPushBody(JsonParser jsonParser) throws IOException {
        String pushBody = jsonParser.readValueAs(String.class);

        if (pushBody == null) {
            builder.setPushBody(Optional.<Base64ByteArray>absent());
        } else {
            builder.setPushBody(Optional.fromNullable(new Base64ByteArray(pushBody)));
        }
    }

    public void readRichDeletions(JsonParser jsonParser) throws IOException {
        builder.setRichDeletions(jsonParser.readValueAs(long.class));
    }

    public void readRichResponses(JsonParser jsonParser) throws IOException {
        builder.setRichResponses(jsonParser.readValueAs(long.class));
    }

    public void readRichSends(JsonParser jsonParser) throws IOException {
        builder.setRichSends(jsonParser.readValueAs(long.class));
    }

    public void readSends(JsonParser jsonParser) throws IOException {
        builder.setSends(jsonParser.readValueAs(long.class));
    }

    public void readDirectResponses(JsonParser jsonParser) throws IOException {
        builder.setDirectResponses(jsonParser.readValueAs(long.class));
    }

    public void readInfluencedResponses(JsonParser jsonParser) throws IOException {
        builder.setInfluencedResponses(jsonParser.readValueAs(long.class));
    }

    public void readPlatforms(JsonParser jsonParser) throws IOException {
        Map<String, PerPushCounts> obj = jsonParser.readValueAs(new TypeReference<Map<String, PerPushCounts>>() {
        });

        for (String s : obj.keySet()) {
            PlatformType type = PlatformType.find(s).get();
            builder.addPlatform(type, obj.get(s));
        }
    }

    @Override
    public PushDetailResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
