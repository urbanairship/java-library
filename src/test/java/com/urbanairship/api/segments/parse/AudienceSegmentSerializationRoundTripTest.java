/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.push.model.audience.location.DateRangeUnit;
import com.urbanairship.api.segments.model.AudienceSegment;
import com.urbanairship.api.segments.model.DateRange;
import com.urbanairship.api.segments.model.DateTimeFormats;
import com.urbanairship.api.segments.model.LocationAlias;
import com.urbanairship.api.segments.model.LocationIdentifier;
import com.urbanairship.api.segments.model.LocationPredicate;
import com.urbanairship.api.segments.model.Operator;
import com.urbanairship.api.segments.model.OperatorType;
import com.urbanairship.api.segments.model.PresenceTimeframe;
import com.urbanairship.api.segments.model.RecentDateRange;
import com.urbanairship.api.segments.model.TagPredicate;
import com.urbanairship.api.segments.model.TagPredicateBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class AudienceSegmentSerializationRoundTripTest {

    private static ObjectMapper mapper = APIResponseObjectMapper.getInstance();

    @Test
    public void testRoundTrip() throws Exception {
        DateTime end = new DateTime(new Date());
        String endString = DateTimeFormats.DAYS_FORMAT.print(end);
        DateTime start = end.minusDays(5);
        String startString = DateTimeFormats.DAYS_FORMAT.print(start);

        Operator op = Operator.newBuilder(OperatorType.AND)
                .addPredicate(new LocationPredicate(new LocationIdentifier(LocationAlias.newBuilder()
                        .setAliasType("us_state")
                        .setAliasValue("OR")
                        .build()),
                        new DateRange(DateRangeUnit.DAYS, startString, endString), PresenceTimeframe.ANYTIME))
                .addPredicate(new LocationPredicate(
                        new LocationIdentifier(LocationAlias.newBuilder()
                                .setAliasType("us_congressional_district")
                                .setAliasValue("1234")
                                .build()),
                        new RecentDateRange(DateRangeUnit.MONTHS, 3), PresenceTimeframe.ANYTIME))
                .addOperator(Operator.newBuilder(OperatorType.OR)
                        .addPredicate(buildTagPredicate("tag1"))
                        .addPredicate(buildTagPredicate("tag2"))
                        .build())
                .build();

        AudienceSegment segment = AudienceSegment.newBuilder()
                .setDisplayName("Test display name")
                .setRootOperator(op)
                .build();

        String serialized = mapper.writeValueAsString(segment);

        Assert.assertEquals(segment, mapper.readValue(serialized, AudienceSegment.class));
    }

    @Test
    public void testRoundTripWithCount() throws Exception {
        DateTime end = new DateTime(new Date());
        String endString = DateTimeFormats.DAYS_FORMAT.print(end);
        DateTime start = end.minusDays(5);
        String startString = DateTimeFormats.DAYS_FORMAT.print(start);

        Operator op = Operator.newBuilder(OperatorType.AND)
                .addPredicate(new LocationPredicate(new LocationIdentifier(LocationAlias.newBuilder()
                        .setAliasType("us_state")
                        .setAliasValue("OR")
                        .build()),
                        new DateRange(DateRangeUnit.DAYS, startString, endString), PresenceTimeframe.ANYTIME))
                .addPredicate(new LocationPredicate(
                        new LocationIdentifier(LocationAlias.newBuilder()
                                .setAliasType("us_congressional_district")
                                .setAliasValue("1234")
                                .build()),
                        new RecentDateRange(DateRangeUnit.MONTHS, 3), PresenceTimeframe.ANYTIME))
                .addOperator(Operator.newBuilder(OperatorType.OR)
                        .addPredicate(buildTagPredicate("tag1"))
                        .addPredicate(buildTagPredicate("tag2"))
                        .build())
                .build();

        AudienceSegment segment = AudienceSegment.newBuilder()
                .setDisplayName("Test display name")
                .setRootOperator(op)
                .setCount(12L)
                .build();

        String serialized = mapper.writeValueAsString(segment);

        Assert.assertEquals(segment, mapper.readValue(serialized, AudienceSegment.class));
    }

    @Test
    public void testRoundTripWithCountAndTagClass() throws Exception {
        DateTime end = new DateTime(new Date());
        String endString = DateTimeFormats.DAYS_FORMAT.print(end);
        DateTime start = end.minusDays(5);
        String startString = DateTimeFormats.DAYS_FORMAT.print(start);

        Operator op = Operator.newBuilder(OperatorType.AND)
                .addPredicate(new LocationPredicate(new LocationIdentifier(LocationAlias.newBuilder()
                        .setAliasType("us_state")
                        .setAliasValue("OR")
                        .build()),
                        new DateRange(DateRangeUnit.DAYS, startString, endString), PresenceTimeframe.ANYTIME))
                .addPredicate(new LocationPredicate(
                        new LocationIdentifier(LocationAlias.newBuilder()
                                .setAliasType("us_congressional_district")
                                .setAliasValue("1234")
                                .build()),
                        new RecentDateRange(DateRangeUnit.MONTHS, 3), PresenceTimeframe.ANYTIME))
                .addOperator(Operator.newBuilder(OperatorType.OR)
                        .addPredicate(buildTagPredicate("tag1"))
                        .addPredicate(buildTagPredicate("tag2", "class2"))
                        .build())
                .build();

        AudienceSegment segment = AudienceSegment.newBuilder()
                .setDisplayName("Test display name")
                .setRootOperator(op)
                .setCount(12L)
                .build();

        String serialized = mapper.writeValueAsString(segment);

        Assert.assertEquals(segment, mapper.readValue(serialized, AudienceSegment.class));
    }

    @Test
    public void testRoundTripWithNotOperator() throws Exception {
        DateTime end = new DateTime(new Date());
        String endString = DateTimeFormats.DAYS_FORMAT.print(end);
        DateTime start = end.minusDays(5);
        String startString = DateTimeFormats.DAYS_FORMAT.print(start);

        Operator op = Operator.newBuilder(OperatorType.AND)
                .addPredicate(new LocationPredicate(new LocationIdentifier(LocationAlias.newBuilder()
                        .setAliasType("us_state")
                        .setAliasValue("OR")
                        .build()),
                        new DateRange(DateRangeUnit.DAYS, startString, endString), PresenceTimeframe.ANYTIME))
                .addPredicate(new LocationPredicate(new LocationIdentifier(LocationAlias.newBuilder()
                        .setAliasType("us_congressional_district")
                        .setAliasValue("1234")
                        .build()),
                        new RecentDateRange(DateRangeUnit.MONTHS, 3), PresenceTimeframe.ANYTIME))
                .addOperator(Operator.newBuilder(OperatorType.OR)
                        .addPredicate(buildTagPredicate("tag1"))
                        .addPredicate(buildTagPredicate("tag2"))
                        .build())
                .addOperator(Operator.newBuilder(OperatorType.NOT)
                                .addPredicate(buildTagPredicate("not-tag", "fooClass"))
                                .build()
                )
                .addOperator(Operator.newBuilder(OperatorType.NOT)
                                .addOperator(Operator.newBuilder(OperatorType.AND)
                                                .addPredicate(
                                                        new LocationPredicate(new LocationIdentifier("blah"), new DateRange(DateRangeUnit.MONTHS, "2011-05", "2012-02"),
                                                                PresenceTimeframe.ANYTIME))
                                                .addPredicate(buildTagPredicate("woot"))
                                                .build()
                                )
                                .build()
                )
                .build();

        AudienceSegment segment = AudienceSegment.newBuilder()
                .setDisplayName("Test display name")
                .setRootOperator(op)
                .build();

        String serialized = mapper.writeValueAsString(segment);

        Assert.assertEquals(segment, mapper.readValue(serialized, AudienceSegment.class));
    }

    private TagPredicate buildTagPredicate(String tag) {
        return TagPredicateBuilder.newInstance().setTag(tag).build();
    }

    private TagPredicate buildTagPredicate(String tag, String tagClass) {
        return TagPredicateBuilder.newInstance().setTag(tag).setTagClass(tagClass).build();
    }

    @Test
    public void testRoundTripWithConstraintAsPredicate() throws Exception {
        AudienceSegment segment = AudienceSegment.newBuilder()
                .setDisplayName("Test display name")
                .setRootPredicate(buildTagPredicate("root-tag"))
                .build();

        String serialized = mapper.writeValueAsString(segment);

        Assert.assertEquals(segment, mapper.readValue(serialized, AudienceSegment.class));
    }
}
