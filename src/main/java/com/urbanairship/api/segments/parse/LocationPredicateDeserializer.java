/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.urbanairship.api.push.model.audience.location.DateRangeUnit;
import com.urbanairship.api.segments.model.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class LocationPredicateDeserializer extends JsonDeserializer<LocationPredicate> {

    public static final LocationPredicateDeserializer INSTANCE = new LocationPredicateDeserializer(
            LocationIdentifierDeserializer.INSTANCE);

    private static final String INVALID_LOCATION = "Location predicates must contain a payload with a single location identifier and a date range specifier";
    private static final String INVALID_DATE_SPECIFIER = "Date specifiers for location predicates must either be a recent specifier or a specific range";
    private static final String INVALID_DATE_RANGE_UNIT = "Valid date range units for location predicates are minutes, hours, days, weeks, or months";
    private static final String INVALID_DATE_RANGE = "Date ranges must consist of an object with a start and end key whose values are of the appropriate ISO 8601 format for the specified unit";
    private static final String INVALID_RECENT_RANGE = "Recent date ranges must consist of a unit key and an integer number for quantity";

    private static final RangeDeserializer RANGE_DESERIALIZER = new RangeDeserializer();
    private static final DateRangeDeserializer DATE_RANGE_DESERIALIZER = new DateRangeDeserializer(RANGE_DESERIALIZER);
    private static final RecentDateRangeDeserializer RECENT_DATE_RANGE_DESERIALIZER = new RecentDateRangeDeserializer();

    private final LocationIdentifierDeserializer locationIdentifierDeserializer;

    private LocationPredicateDeserializer(LocationIdentifierDeserializer locationIdentifierDeserializer) {
        this.locationIdentifierDeserializer = locationIdentifierDeserializer;
    }

    private static DateRangeUnit parseDateRangeUnit(JsonParser jp) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.START_OBJECT) {
            token = jp.nextToken();
        }

        if (token != JsonToken.FIELD_NAME) {
            throw new InvalidAudienceSegmentException(INVALID_DATE_RANGE_UNIT);
        }

        DateRangeUnit unit = DateRangeUnit.getUnitForIdentifier(jp.getCurrentName());
        if (unit == null) {
            throw new InvalidAudienceSegmentException(INVALID_DATE_RANGE_UNIT);
        }

        return unit;
    }

    @Override
    public LocationPredicate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token != JsonToken.START_OBJECT) {
            throw new InvalidAudienceSegmentException(INVALID_LOCATION);
        }

        token = jp.nextToken();

        DateSpecifier dateSpecifier = null;
        LocationIdentifier identifier = null;
        int keyCount = 0;
        while (token != JsonToken.END_OBJECT) {
            keyCount++;
            if (keyCount > 2 || token != JsonToken.FIELD_NAME) {
                throw new InvalidAudienceSegmentException(INVALID_LOCATION);
            }

            String key = jp.getCurrentName();
            if ("date".equals(key)) {
                jp.nextToken();
                dateSpecifier = parseDateSpecifier(jp, ctxt);
            } else {
                identifier = locationIdentifierDeserializer.deserialize(jp, ctxt);
            }

            token = jp.nextToken();
        }

        if (identifier == null || dateSpecifier == null) {
            throw new InvalidAudienceSegmentException(INVALID_LOCATION);
        }

        jp.nextToken();
        if (dateSpecifier.getDateValue().isSimpleDateRange()) {
            return new LocationPredicate(identifier, dateSpecifier.getDateValue().getDateRange(), dateSpecifier.getPresenceTimeframe());
        }
        return new LocationPredicate(identifier, dateSpecifier.getDateValue().getRecentDateRange(), dateSpecifier.getPresenceTimeframe());
    }

    private DateSpecifier parseDateSpecifier(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.START_OBJECT) {
            token = jp.nextToken();
        }

        PresenceTimeframe presenceTimeframe = PresenceTimeframe.ANYTIME;
        DateValue dateValue = null;
        while (token != JsonToken.END_OBJECT) {
            if (token != JsonToken.FIELD_NAME) {
                throw new InvalidAudienceSegmentException(INVALID_DATE_SPECIFIER);
            }

            String key = jp.getCurrentName();
            dateValue = parseDateValue(key, jp, ctxt);

            token = jp.nextToken();
        }

        return new DateSpecifier(presenceTimeframe, dateValue);
    }

    private DateValue parseDateValue(String key, JsonParser jp, DeserializationContext ctxt) throws IOException {
        DateValue dateValue;
        if ("recent".equals(key)) {
            jp.nextToken();
            dateValue = new DateValue(RECENT_DATE_RANGE_DESERIALIZER.deserialize(jp, ctxt));
            if (jp.nextToken() != JsonToken.END_OBJECT) {
                throw new InvalidAudienceSegmentException(INVALID_DATE_SPECIFIER);
            }
        } else {
            dateValue = new DateValue(DATE_RANGE_DESERIALIZER.deserialize(jp, ctxt));
        }

        return dateValue;
    }

    private static class RecentDateRangeDeserializer extends JsonDeserializer<RecentDateRange> {

        @Override
        public RecentDateRange deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            DateRangeUnit unit = parseDateRangeUnit(jp);

            JsonToken token = jp.nextToken();
            if (token != JsonToken.VALUE_NUMBER_INT) {
                throw new InvalidAudienceSegmentException(INVALID_RECENT_RANGE);
            }

            // TODO: validate that the number isn't too big for the recent range?
            return new RecentDateRange(unit, jp.getIntValue());
        }
    }

    private static class DateRangeDeserializer extends JsonDeserializer<DateRange> {

        private final RangeDeserializer rangeDeserializer;

        private DateRangeDeserializer(RangeDeserializer rangeDeserializer) {
            this.rangeDeserializer = rangeDeserializer;
        }

        @Override
        public DateRange deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            DateRangeUnit unit = parseDateRangeUnit(jp);

            jp.nextToken();
            Range range = rangeDeserializer.deserialize(jp, ctxt);

            DateTimeFormatter formatter = unit.getFormatter();
            try {
                formatter.parseDateTime(range.getRangeStart());
                formatter.parseDateTime(range.getRangeEnd());
            } catch (Exception e) {
                throw new InvalidAudienceSegmentException(INVALID_DATE_RANGE);
            }

            return new DateRange(unit, range.getRangeStart(), range.getRangeEnd());
        }
    }

    private static class RangeDeserializer extends JsonDeserializer<Range> {

        @Override
        public Range deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonToken token = jp.getCurrentToken();
            if (token == JsonToken.START_OBJECT) {
                token = jp.nextToken();
            }

            String rangeStart = null;
            String rangeEnd = null;

            int keyCount = 0;
            while (token != JsonToken.END_OBJECT) {
                keyCount++;
                if (keyCount > 2 || token != JsonToken.FIELD_NAME) {
                    throw new InvalidAudienceSegmentException(INVALID_DATE_RANGE);
                }

                String key = jp.getCurrentName();
                jp.nextToken();
                if ("start".equals(key)) {
                    rangeStart = parseString(jp);
                } else if ("end".equals(key)) {
                    rangeEnd = parseString(jp);
                }

                token = jp.nextToken();
            }

            if (rangeStart == null || rangeEnd == null) {
                throw new InvalidAudienceSegmentException(INVALID_DATE_RANGE);
            }

            return new Range(rangeStart, rangeEnd);
        }

        private String parseString(JsonParser jp) throws IOException {
            // TODO: validate the date based on the unit...
            if (jp.getCurrentToken() != JsonToken.VALUE_STRING) {
                throw new InvalidAudienceSegmentException(INVALID_DATE_RANGE);
            }

            return jp.getText();
        }
    }

    private static class Range {

        private final String rangeStart;
        private final String rangeEnd;

        private Range(String rangeStart, String rangeEnd) {
            this.rangeStart = rangeStart;
            this.rangeEnd = rangeEnd;
        }

        public String getRangeStart() {
            return rangeStart;
        }

        public String getRangeEnd() {
            return rangeEnd;
        }
    }

    private static class DateSpecifier {

        private final PresenceTimeframe presenceTimeframe;
        private final DateValue dateValue;

        private DateSpecifier(PresenceTimeframe presenceTimeframe, DateValue dateValue) {
            this.presenceTimeframe = presenceTimeframe;
            this.dateValue = dateValue;
        }

        public PresenceTimeframe getPresenceTimeframe() {
            return presenceTimeframe;
        }

        public DateValue getDateValue() {
            return dateValue;
        }
    }

    private static class DateValue {

        private final DateRange dateRange;
        private final RecentDateRange recentDateRange;

        private DateValue(DateRange dateRange) {
            this.dateRange = dateRange;
            this.recentDateRange = null;
        }

        private DateValue(RecentDateRange recentDateRange) {
            this.recentDateRange = recentDateRange;
            this.dateRange = null;
        }

        public boolean isSimpleDateRange() {
            return dateRange != null;
        }

        public DateRange getDateRange() {
            return dateRange;
        }

        public boolean isRecentDateRange() {
            return recentDateRange != null;
        }

        public RecentDateRange getRecentDateRange() {
            return recentDateRange;
        }
    }
}
