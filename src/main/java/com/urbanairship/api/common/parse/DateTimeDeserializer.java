/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;

import java.io.IOException;

public class DateTimeDeserializer extends JsonDeserializer<DateTime> {

    @Override
    public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String dateString = jp.getText();
            try {
                return DateFormats.DATE_PARSER.parseDateTime(dateString);
            }
            catch (Exception e) {
                throw new APIParsingException(String.format("Date string %s is not in accepted ISO 8601 UTC date format", dateString));
            }
    }
}
