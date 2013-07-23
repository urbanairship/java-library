/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience.location;

import com.urbanairship.api.common.parse.DateFormats;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.joda.time.Hours;
import org.joda.time.Days;
import org.joda.time.Weeks;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.ReadablePeriod;
import org.joda.time.format.DateTimeFormatter;

public enum DateRangeUnit {

    MINUTES("minutes", DateFormats.MINUTES_FORMAT),
    HOURS("hours", DateFormats.HOURS_FORMAT),
    DAYS("days", DateFormats.DAYS_FORMAT),
    WEEKS("weeks", DateFormats.WEEKS_FORMAT),
    MONTHS("months", DateFormats.MONTHS_FORMAT),
    YEARS("years", DateFormats.YEARS_FORMAT);

    private final String identifier;
    private final DateTimeFormatter formatter;

    private DateRangeUnit(String identifier, DateTimeFormatter formatter) {
        this.identifier = identifier;
        this.formatter = formatter;
    }

    public String getIdentifier() {
        return identifier;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public static DateRangeUnit getUnitForIdentifier(String identifier) {
        for (DateRangeUnit dateRangeUnit : values()) {
            if (dateRangeUnit.identifier.equals(identifier)) {
                return dateRangeUnit;
            }
        }

        return null;
    }

}
