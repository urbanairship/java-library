/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.ISODateTimeFormat;

import static org.joda.time.format.ISODateTimeFormat.date;

public class DateFormats {

    private static final DateTimeParser SPACE_PARSER = new DateTimeFormatterBuilder().appendLiteral(' ').toParser();
    private static final DateTimeParser T_PARSER = new DateTimeFormatterBuilder().appendLiteral('T').toParser();

    public static final DateTimeFormatter DATE_PARSER = new DateTimeFormatterBuilder()
            .append(date())
            .append(null, new DateTimeParser[] {SPACE_PARSER, T_PARSER})
            .append(ISODateTimeFormat.hourMinuteSecond())
            .toFormatter()
            .withZoneUTC();

    public static final DateTimeFormatter DATE_FORMATTER = ISODateTimeFormat.dateHourMinuteSecond().withZoneUTC();

    public static final DateTimeFormatter SPACE_FORMATTER = new DateTimeFormatterBuilder().appendLiteral(' ').toFormatter();

    public static final DateTimeFormatter SECONDS_FORMAT = new DateTimeFormatterBuilder()
            .append(date())
            .append(null, new DateTimeParser[] {SPACE_PARSER, T_PARSER})
            .append(ISODateTimeFormat.hourMinuteSecond())
            .toFormatter()
            .withZoneUTC();

    public static final DateTimeFormatter MINUTES_FORMAT = new DateTimeFormatterBuilder()
            .append(date())
            .append(null, new DateTimeParser[] {SPACE_PARSER, T_PARSER})
            .append(ISODateTimeFormat.hourMinute())
            .toFormatter()
            .withZoneUTC();

    public static final DateTimeFormatter HOURS_FORMAT = new DateTimeFormatterBuilder()
            .append(date())
            .append(null, new DateTimeParser[] {SPACE_PARSER, T_PARSER})
            .append(ISODateTimeFormat.hour())
            .toFormatter()
            .withZoneUTC();

    public static final DateTimeFormatter DAYS_FORMAT = ISODateTimeFormat.yearMonthDay().withZoneUTC();

    public static final DateTimeFormatter WEEKS_FORMAT = ISODateTimeFormat.weekyearWeek().withZoneUTC();

    public static final DateTimeFormatter MONTHS_FORMAT = ISODateTimeFormat.yearMonth().withZoneUTC();

    public static final DateTimeFormatter YEARS_FORMAT = ISODateTimeFormat.year().withZoneUTC();

    private DateFormats() { }
}
