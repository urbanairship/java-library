/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.ISODateTimeFormat;

public final class DateTimeFormats {

    public static final DateTimeFormatter SPACE_FORMATTER = new DateTimeFormatterBuilder().appendLiteral(' ').toFormatter();

    public static final DateTimeFormatter SECONDS_FORMAT = new DateTimeFormatterBuilder()
            .append(ISODateTimeFormat.date())
            .append(SPACE_FORMATTER)
            .append(ISODateTimeFormat.hourMinuteSecond())
            .toFormatter()
            .withZoneUTC();

    public static final DateTimeFormatter MINUTES_FORMAT = new DateTimeFormatterBuilder()
            .append(ISODateTimeFormat.date())
            .append(SPACE_FORMATTER)
            .append(ISODateTimeFormat.hourMinute())
            .toFormatter()
            .withZoneUTC();

    public static final DateTimeFormatter HOURS_FORMAT = new DateTimeFormatterBuilder()
            .append(ISODateTimeFormat.date())
            .append(SPACE_FORMATTER)
            .append(ISODateTimeFormat.hour())
            .toFormatter()
            .withZoneUTC();

    public static final DateTimeFormatter DAYS_FORMAT = ISODateTimeFormat.yearMonthDay().withZoneUTC();

    public static final DateTimeFormatter WEEKS_FORMAT = ISODateTimeFormat.weekyearWeek().withZoneUTC();

    public static final DateTimeFormatter MONTHS_FORMAT = ISODateTimeFormat.yearMonth().withZoneUTC();

    public static final DateTimeFormatter YEARS_FORMAT = ISODateTimeFormat.year().withZoneUTC();

}
