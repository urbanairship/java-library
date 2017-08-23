package com.urbanairship.api.push.parse.audience;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.audience.location.AbsoluteDateRange;
import com.urbanairship.api.push.model.audience.location.DateRange;
import com.urbanairship.api.push.model.audience.location.DateRangeUnit;
import com.urbanairship.api.push.model.audience.location.LocationAlias;
import com.urbanairship.api.push.model.audience.location.LocationIdentifier;
import com.urbanairship.api.push.model.audience.location.LocationSelector;
import com.urbanairship.api.push.model.audience.location.PresenceTimeframe;
import com.urbanairship.api.push.model.audience.location.RecentDateRange;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LocationSelectorDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    // -----------------------------------------------------------------------------
    // Absolute date range
    // -----------------------------------------------------------------------------

    @Test
    public void testLocationAbsoluteDate() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"days\" : {"
                + "        \"start\" : \"2012-01-01\","
                + "        \"end\" : \"2012-01-31\""
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertNotNull(location);

        LocationIdentifier id = location.getLocationIdentifier();
        assertNotNull(id);
        assertFalse(id.isAlias());
        assertTrue(id.getId().isPresent());
        assertEquals("test", id.getId().get());

        DateRange dr = location.getDateRange();
        assertNotNull(dr);
        assertTrue(dr instanceof AbsoluteDateRange);
        assertEquals(PresenceTimeframe.ANYTIME, dr.getTimeframe());

        assertEquals(new DateTime(2012, 1, 1, 0, 0, DateTimeZone.UTC), dr.getStart());
        assertEquals(new DateTime(2012, 1, 31, 0, 0, DateTimeZone.UTC), dr.getEnd());
    }

    @Test
    public void testLocationAbsoluteResolutionMinutes() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"minutes\" : {"
                + "        \"start\" : \"2012-01-01T00:00\","
                + "        \"end\" : \"2012-01-01T00:15\""
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertEquals(DateRangeUnit.MINUTES, location.getDateRange().getResolution());
    }

    @Test
    public void testLocationAbsoluteResolutionHours() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"hours\" : {"
                + "        \"start\" : \"2012-01-01T00:00\","
                + "        \"end\" : \"2012-01-01T06:00\""
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertEquals(DateRangeUnit.HOURS, location.getDateRange().getResolution());
    }

    @Test
    public void testLocationAbsoluteResolutionDays() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"days\" : {"
                + "        \"start\" : \"2012-01-01\","
                + "        \"end\" : \"2012-01-01\""
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertEquals(DateRangeUnit.DAYS, location.getDateRange().getResolution());
    }

    @Test
    public void testLocationAbsoluteResolutionWeeks() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"weeks\" : {"
                + "        \"start\" : \"2012-01-01\","
                + "        \"end\" : \"2012-01-14\""
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertEquals(DateRangeUnit.WEEKS, location.getDateRange().getResolution());
    }

    @Test
    public void testLocationAbsoluteResolutionMonths() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"months\" : {"
                + "        \"start\" : \"2012-01-01\","
                + "        \"end\" : \"2012-05-31\""
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertEquals(DateRangeUnit.MONTHS, location.getDateRange().getResolution());
    }

    @Test
    public void testLocationAbsoluteResolutionYears() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"years\" : {"
                + "        \"start\" : \"2012-01-01\","
                + "        \"end\" : \"2013-01-01\""
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertEquals(DateRangeUnit.YEARS, location.getDateRange().getResolution());
    }

    // -----------------------------------------------------------------------------
    // Recent date range
    // -----------------------------------------------------------------------------

    @Test
    public void testLocationRecentDays() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"recent\" : {"
                + "        \"days\" : 2"
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        DateRange dr = location.getDateRange();
        assertNotNull(dr);
        assertTrue(dr instanceof RecentDateRange);
        RecentDateRange rdr = (RecentDateRange) dr;
        assertEquals(DateRangeUnit.DAYS, rdr.getResolution());
        assertEquals(2, rdr.getUnits());
        assertEquals(DateTime.now().withMillis(0),
                rdr.getEnd().withMillis(0));
        assertEquals(DateTime.now().minus(Days.TWO).withMillis(0),
                rdr.getStart().withMillis(0));
    }

    @Test
    public void testLocationRecentHours() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"recent\" : {"
                + "        \"hours\" : 6"
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        DateRange dr = location.getDateRange();
        assertNotNull(dr);
        assertTrue(dr instanceof RecentDateRange);
        RecentDateRange rdr = (RecentDateRange) dr;
        assertEquals(DateRangeUnit.HOURS, rdr.getResolution());
        assertEquals(6, rdr.getUnits());
        assertEquals(DateTime.now().withMillis(0),
                rdr.getEnd().withMillis(0));
        assertEquals(DateTime.now().minus(Hours.SIX).withMillis(0),
                rdr.getStart().withMillis(0));
    }

    @Test
    public void testLocationRecentResolutionMinutes() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"recent\" : {"
                + "        \"minutes\" : 2"
                + "      }"
                + "  }"
                + "}";
        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertEquals(DateRangeUnit.MINUTES, location.getDateRange().getResolution());
    }

    @Test
    public void testLocationRecentResolutionHours() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"recent\" : {"
                + "        \"hours\" : 2"
                + "      }"
                + "  }"
                + "}";
        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertEquals(DateRangeUnit.HOURS, location.getDateRange().getResolution());
    }

    @Test
    public void testLocationRecentResolutionDays() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"recent\" : {"
                + "        \"days\" : 2"
                + "      }"
                + "  }"
                + "}";
        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertEquals(DateRangeUnit.DAYS, location.getDateRange().getResolution());
    }

    @Test
    public void testLocationRecentResolutionWeeks() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"recent\" : {"
                + "        \"weeks\" : 2"
                + "      }"
                + "  }"
                + "}";
        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertEquals(DateRangeUnit.WEEKS, location.getDateRange().getResolution());
    }

    @Test
    public void testLocationRecentResolutionMonths() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"recent\" : {"
                + "        \"months\" : 2"
                + "      }"
                + "  }"
                + "}";
        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertEquals(DateRangeUnit.MONTHS, location.getDateRange().getResolution());
    }

    @Test
    public void testLocationRecentResolutionYears() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"test\","
                + "    \"date\" : {"
                + "      \"recent\" : {"
                + "        \"years\" : 2"
                + "      }"
                + "  }"
                + "}";
        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertEquals(DateRangeUnit.YEARS, location.getDateRange().getResolution());
    }

    // -----------------------------------------------------------------------------
    // Aliases & presence time frame
    // -----------------------------------------------------------------------------

    @Test
    public void testLocationAlias() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"days\" : {"
                + "        \"start\" : \"2012-01-01\","
                + "        \"end\" : \"2012-01-31\""
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        LocationIdentifier id = location.getLocationIdentifier();
        assertNotNull(id);
        assertTrue(id.isAlias());
        assertTrue(id.getAlias().isPresent());
        assertFalse(id.getId().isPresent());

        LocationAlias alias = id.getAlias().get();
        assertEquals("us_state", alias.getType());
        assertEquals("CA", alias.getValue());
    }

    @Test
    public void testLocationAbsoluteLastSeenTrue() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"last_seen\" : true,"
                + "      \"days\" : {"
                + "        \"start\" : \"2012-01-01\","
                + "        \"end\" : \"2012-01-31\""
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertTrue(location.getDateRange() instanceof AbsoluteDateRange);
        assertEquals(PresenceTimeframe.LAST_SEEN, location.getDateRange().getTimeframe());
    }

    @Test
    public void testLocationRecentLastSeenTrue() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"last_seen\" : true,"
                + "      \"recent\" : {"
                + "        \"days\" : 10"
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertTrue(location.getDateRange() instanceof RecentDateRange);
        assertEquals(PresenceTimeframe.LAST_SEEN, location.getDateRange().getTimeframe());
    }

    @Test
    public void testLocationAbsoluteLastSeenFalse() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"last_seen\" : false,"
                + "      \"days\" : {"
                + "        \"start\" : \"2012-01-01\","
                + "        \"end\" : \"2012-01-31\""
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertTrue(location.getDateRange() instanceof AbsoluteDateRange);
        assertEquals(PresenceTimeframe.ANYTIME, location.getDateRange().getTimeframe());
    }

    @Test
    public void testLocationRecentLastSeenFalse() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"last_seen\" : false,"
                + "      \"recent\" : {"
                + "        \"weeks\" : 3"
                + "      }"
                + "  }"
                + "}";

        LocationSelector location = mapper.readValue(json, LocationSelector.class);
        assertTrue(location.getDateRange() instanceof RecentDateRange);
        assertEquals(PresenceTimeframe.ANYTIME, location.getDateRange().getTimeframe());
    }


    // -----------------------------------------------------------------------------
    // Validation
    // -----------------------------------------------------------------------------

    @Test(expected = APIParsingException.class)
    public void testLocationMissingID() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"date\" : {"
                + "      \"days\" : {"
                + "        \"start\" : \"2012-01-01\","
                + "        \"end\" : \"2012-01-31\""
                + "      }"
                + "  }"
                + "}";
        mapper.readValue(json, LocationSelector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testLocationMissingDate() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"id\" : \"foo\""
                + "  }"
                + "}";
        mapper.readValue(json, LocationSelector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testAbsoluteMissingStart() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"days\" : {"
                + "        \"end\" : \"2012-01-31\""
                + "      }"
                + "  }"
                + "}";
        mapper.readValue(json, LocationSelector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testAbsoluteBadDate() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"days\" : {"
                + "        \"start\" : \"2012-01-FOO\","
                + "        \"end\" : \"2012-01-31\""
                + "      }"
                + "  }"
                + "}";
        mapper.readValue(json, LocationSelector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testAbsoluteMissingEnd() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"days\" : {"
                + "        \"start\" : \"2012-01-31\""
                + "      }"
                + "  }"
                + "}";
        mapper.readValue(json, LocationSelector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testAbsoluteInvalidResolution() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"frobs\" : {"
                + "        \"start\" : \"2012-01-31\","
                + "        \"end\" : \"2012-02-24\""
                + "      }"
                + "  }"
                + "}";
        mapper.readValue(json, LocationSelector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testRecentMissingValue() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"recent\" : {"
                + "      }"
                + "  }"
                + "}";
        mapper.readValue(json, LocationSelector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testRecentMissingBadUnits() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"recent\" : {"
                + "        \"frobs\" : 10"
                + "      }"
                + "  }"
                + "}";
        mapper.readValue(json, LocationSelector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testRecentNotANumber() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"recent\" : {"
                + "        \"months\" : \"thirty seven\""
                + "      }"
                + "  }"
                + "}";
        mapper.readValue(json, LocationSelector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testRecentInvalidResolution() throws Exception {
        String json = "{"
                + "  \"location\" : {"
                + "    \"us_state\" : \"CA\","
                + "    \"date\" : {"
                + "      \"recent\" : {"
                + "        \"frobs\" : 12"
                + "      }"
                + "  }"
                + "}";
        mapper.readValue(json, LocationSelector.class);
    }
}
