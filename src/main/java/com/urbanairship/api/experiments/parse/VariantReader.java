/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.experiments.model.Variant;
import com.urbanairship.api.experiments.model.VariantPushPayload;
import com.urbanairship.api.schedule.model.Schedule;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;
import java.math.BigDecimal;

public class VariantReader implements JsonObjectReader<Variant> {

    private final Variant.Builder builder;

    public VariantReader() {
        this.builder = Variant.newBuilder();
    }

    public void readName(JsonParser jsonParser) throws IOException {
        builder.setName(jsonParser.readValueAs(String.class));
    }

    public void readDescription(JsonParser jsonParser) throws IOException {
        builder.setDescription(jsonParser.readValueAs(String.class));
    }

    public void readSchedule(JsonParser jsonParser) throws IOException {
        builder.setSchedule(jsonParser.readValueAs(Schedule.class));
    }

    public void readWeight(JsonParser jsonParser) throws IOException {
        builder.setWeight(jsonParser.readValueAs(BigDecimal.class));
    }

    public void readVariantPushPayload(JsonParser parser) throws IOException {
        builder.setPushPayload(parser.readValueAs(VariantPushPayload.class));
    }

    @Override
    public Variant validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
