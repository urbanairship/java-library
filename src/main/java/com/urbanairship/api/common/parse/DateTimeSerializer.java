/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;

import java.io.IOException;

public class DateTimeSerializer extends JsonSerializer<DateTime> {

    @Override
    public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(DateFormats.DATE_FORMATTER.print(value));
    }
}
