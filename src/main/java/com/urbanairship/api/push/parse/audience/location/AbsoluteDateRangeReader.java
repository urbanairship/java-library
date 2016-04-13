/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.audience.location.AbsoluteDateRange;
import org.codehaus.jackson.JsonParser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class AbsoluteDateRangeReader implements JsonObjectReader<AbsoluteDateRange.Builder> {

    private AbsoluteDateRange.Builder builder = AbsoluteDateRange.newBuilder();

    private static final DateTimeFormatter[] FORMATS = new DateTimeFormatter[] { DateFormats.DATE_PARSER,
            DateFormats.SECONDS_FORMAT,
            DateFormats.MINUTES_FORMAT,
            DateFormats.HOURS_FORMAT,
            DateFormats.DAYS_FORMAT,
            DateFormats.WEEKS_FORMAT,
            DateFormats.MONTHS_FORMAT,
            DateFormats.YEARS_FORMAT };

    AbsoluteDateRangeReader() { }

    public void readStart(JsonParser parser) throws IOException {
        builder.setStart(readDateTime(parser));
    }

    public void readEnd(JsonParser parser) throws IOException {
        builder.setEnd(readDateTime(parser));
    }

    private DateTime readDateTime(JsonParser parser) throws IOException {
        String dateString = parser.getText();
        DateTime value;
        for (DateTimeFormatter format : FORMATS) {
            try {
                value = format.parseDateTime(dateString);
                if (value != null) {
                    return value;
                }
            }
            catch (Exception e) {
            }
        }
        throw new APIParsingException(String.format("Time period specifier '%s' is not a valid date time.", dateString));
    }

    @Override
    public AbsoluteDateRange.Builder validateAndBuild() throws IOException {
        return builder;
    }
}
