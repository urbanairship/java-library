/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.push.model.audience.location.DateRangeUnit;
import com.urbanairship.api.segments.model.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class LocationPredicateDeserializerTest {

    private static ObjectMapper mapper;

    @BeforeClass
    public static void setUp() throws Exception {
        mapper = APIResponseObjectMapper.getInstance();
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testMissingDateRangeSpecifier() throws Exception {
        // Valid id value
        String json = "{\"id\": \"woot\"}";
         parse(json);
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidLocationExtraField() throws Exception {
        parse("{\"id\": \"test\", \"date\": {\"recent\": {\"days\": 3}}, \"key\": \"value\"}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidLocationNoFields() throws Exception {
        parse("{}");
    }

    @Test
    public void testValidMinutesDateRange() throws Exception {
        String json = "{\"id\": \"blah\", \"date\": {\"minutes\": {\"start\": \"2012-10-15 12:30\", \"end\": \"2012-10-15 12:59\"}}}";
        LocationPredicate expected = new LocationPredicate(new LocationIdentifier("blah"),
                new DateRange(DateRangeUnit.MINUTES, "2012-10-15 12:30", "2012-10-15 12:59"), PresenceTimeframe.ANYTIME);

        Assert.assertEquals(expected, parse(json));
    }

    @Test
    public void testValidMinutesDateRangeWithLastSeen() throws Exception {
        String json = "{\"id\": \"blah\", \"date\": {\"minutes\": {\"start\": \"2012-10-15 12:30\", \"end\": \"2012-10-15 12:59\"}, \"last_seen\": true}}";
        LocationPredicate expected = new LocationPredicate(new LocationIdentifier("blah"),
                new DateRange(DateRangeUnit.MINUTES, "2012-10-15 12:30", "2012-10-15 12:59"), PresenceTimeframe.LAST_SEEN);

        Assert.assertEquals(expected, parse(json));
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidLastSeenValue() throws Exception {
        String json = "{\"id\": \"blah\", \"date\": {\"minutes\": {\"start\": \"2012-10-15 12:30\", \"end\": \"2012-10-15 12:59\"}, \"last_seen\": \"yes\"}}";
        parse(json);
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testDateRangeWithLastSeenNotInsideDateSpecifier() throws Exception {
        String json = "{\"id\": \"blah\", \"date\": {\"minutes\": {\"start\": \"2012-10-15 12:30\", \"end\": \"2012-10-15 12:59\"}}, \"last_seen\": true}";
        LocationPredicate expected = new LocationPredicate(new LocationIdentifier("blah"),
                new DateRange(DateRangeUnit.MINUTES, "2012-10-15 12:30", "2012-10-15 12:59"), PresenceTimeframe.LAST_SEEN);

        Assert.assertEquals(expected, parse(json));
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidMinutesDateRangeBadFormat() throws Exception {
        parse("{\"id\": \"blah\", \"date\": {\"minutes\": {\"start\": \"2012-10-15 12:30:00\", \"end\": \"2012-10-15 12:59\"}}}");
    }

    @Test
    public void testValidHoursDateRange() throws Exception {
        String json = "{\"id\": \"blah\", \"date\": {\"hours\": {\"start\": \"2012-10-15 12\", \"end\": \"2012-10-15 14\"}}}";
        LocationPredicate expected = new LocationPredicate(new LocationIdentifier("blah"),
                new DateRange(DateRangeUnit.HOURS, "2012-10-15 12", "2012-10-15 14"), PresenceTimeframe.ANYTIME);

        Assert.assertEquals(expected, parse(json));
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidHoursDateRangeBadFormat() throws Exception {
        parse("{\"id\": \"blah\", \"date\": {\"hours\": {\"start\": \"2012-10-15 \", \"end\": \"2012-10-15 14\"}}}");
    }

    @Test
    public void testValidDaysDateRange() throws Exception {
        String json = "{\"id\": \"blah\", \"date\": {\"days\": {\"start\": \"2012-10-15\", \"end\": \"2012-10-16\"}}}";
        LocationPredicate expected = new LocationPredicate(new LocationIdentifier("blah"),
                new DateRange(DateRangeUnit.DAYS, "2012-10-15", "2012-10-16"), PresenceTimeframe.ANYTIME);

        Assert.assertEquals(expected, parse(json));
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidDaysDateRangeBadFormat() throws Exception {
        parse("{\"id\": \"blah\", \"date\": {\"days\": {\"start\": \"2012-10-15 10:18\", \"end\": \"2012-10-16\"}}}");
    }

    @Test
    public void testValidWeeksDateRange() throws Exception {
        String json = "{\"id\": \"blah\", \"date\": {\"weeks\": {\"start\": \"2012-W02\", \"end\": \"2012-W10\"}}}";
        LocationPredicate expected = new LocationPredicate(new LocationIdentifier("blah"),
                new DateRange(DateRangeUnit.WEEKS, "2012-W02", "2012-W10"), PresenceTimeframe.ANYTIME);

        Assert.assertEquals(expected, parse(json));
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidWeeksDateRangeBadFormat() throws Exception {
        parse("{\"id\": \"blah\", \"date\": {\"weeks\": {\"start\": \"2012-W02\", \"end\": \"2012 W10\"}}}");
    }

    @Test
    public void testValidMonthsDateRange() throws Exception {
        String json = "{\"id\": \"blah\", \"date\": {\"months\": {\"start\": \"2012-01\", \"end\": \"2012-03\"}}}";
        LocationPredicate expected = new LocationPredicate(new LocationIdentifier("blah"),
                new DateRange(DateRangeUnit.MONTHS, "2012-01", "2012-03"), PresenceTimeframe.ANYTIME);

        Assert.assertEquals(expected, parse(json));
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidMonthsDateRangeBadFormat() throws Exception {
        parse("{\"id\": \"blah\", \"date\": {\"months\": {\"start\": \"2012-01\", \"end\": \"201203\"}}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidDateRangeNonStringValues() throws Exception {
        parse("{\"id\": \"test\", \"date\": {\"days\": {\"start\": 99999999, \"end\": 9999999999}}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidDateRangeMissingStart() throws Exception {
        parse("{\"id\": \"test\", \"date\": {\"minutes\": {\"end\": \"2012-01-01 12:00\"}}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidDateRangeMispelling() throws Exception {
        parse("{\"id\": \"test\", \"date\": {\"minutes\": {\"start\": \"2012-01-01 12:00\", \"ends\": \"2012-01-01 12:00\"}}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidDateRangeNotProperObject() throws Exception {
        parse("{\"id\": \"test\", \"date\": \"2012-01-01 12:00:00\"}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidDateRangeUnknownUnit() throws Exception {
        parse("{\"id\": \"blah\", \"date\": {\"decades\": {\"start\": \"2012-01\", \"end\": \"2012-03\"}}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidDateRangeExtraFieldInRange() throws Exception {
        parse("{\"id\": \"blah\", \"date\": {\"months\": {\"start\": \"2012-01\", \"end\": \"2012-03\", \"key\": \"value\"}}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidDateRangeExtraFieldInUnits() throws Exception {
        parse("{\"id\": \"blah\", \"date\": {\"months\": {\"start\": \"2012-01\", \"end\": \"2012-03\"}, \"key\": \"value\"}}");
    }

    @Test
    public void testValidRecentRangeMinutes() throws Exception {
        String json = "{\"id\": \"test\", \"date\": {\"recent\": {\"minutes\": 30}}}";
        LocationPredicate expected = new LocationPredicate(new LocationIdentifier("test"),
                new RecentDateRange(DateRangeUnit.MINUTES, 30), PresenceTimeframe.ANYTIME);

        Assert.assertEquals(expected, parse(json));
    }

    @Test
    public void testValidRecentRangeHours() throws Exception {
        String json = "{\"id\": \"test\", \"date\": {\"recent\": {\"hours\": 3}}}";
        LocationPredicate expected = new LocationPredicate(new LocationIdentifier("test"),
                new RecentDateRange(DateRangeUnit.HOURS, 3), PresenceTimeframe.ANYTIME);

        Assert.assertEquals(expected, parse(json));
    }

    @Test
    public void testValidRecentRangeDays() throws Exception {
        String json = "{\"id\": \"test\", \"date\": {\"recent\": {\"days\": 30}}}";
        LocationPredicate expected = new LocationPredicate(new LocationIdentifier("test"),
                new RecentDateRange(DateRangeUnit.DAYS, 30), PresenceTimeframe.ANYTIME);

        Assert.assertEquals(expected, parse(json));
    }

    @Test
    public void testValidRecentRangeWeeks() throws Exception {
        String json = "{\"id\": \"test\", \"date\": {\"recent\": {\"weeks\": 30}}}";
        LocationPredicate expected = new LocationPredicate(new LocationIdentifier("test"),
                new RecentDateRange(DateRangeUnit.WEEKS, 30), PresenceTimeframe.ANYTIME);

        Assert.assertEquals(expected, parse(json));
    }

    @Test
    public void testValidRecentRangeMonths() throws Exception {
        String json = "{\"id\": \"test\", \"date\": {\"recent\": {\"months\": 3}}}";
        LocationPredicate expected = new LocationPredicate(new LocationIdentifier("test"),
                new RecentDateRange(DateRangeUnit.MONTHS, 3), PresenceTimeframe.ANYTIME);

        Assert.assertEquals(expected, parse(json));
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidRecentRangeNotNumeric() throws Exception {
        parse("{\"id\": \"test\", \"date\": {\"recent\": {\"months\": \"3\"}}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidRecentRangeUnknownUnit() throws Exception {
        parse("{\"id\": \"test\", \"date\": {\"recent\": {\"years\": \"3\"}}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidRecentRangeMalformed() throws Exception {
        parse("{\"id\": \"test\", \"date\": {\"recent\": {\"start\": \"3\", \"end\": 7}}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidRecentRangeNotObject() throws Exception {
        parse("{\"id\": \"test\", \"date\": {\"recent\": 3}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidRecentExtraKeyInUnits() throws Exception {
        parse("{\"id\": \"test\", \"date\": {\"recent\": {\"days\": 3, \"key\": \"value\"}}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidRecentExtraKey() throws Exception {
        parse("{\"id\": \"test\", \"date\": {\"recent\": {\"days\": 3}, \"key\": \"value\"}}");
    }

    private LocationPredicate parse(String json) throws IOException {
        return mapper.readValue(json, LocationPredicate.class);
    }

}
