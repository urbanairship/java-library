/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;


import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.push.model.audience.location.DateRangeUnit;
import com.urbanairship.api.segments.model.AudienceSegment;
import com.urbanairship.api.segments.model.LocationIdentifier;
import com.urbanairship.api.segments.model.LocationPredicate;
import com.urbanairship.api.segments.model.Operator;
import com.urbanairship.api.segments.model.OperatorType;
import com.urbanairship.api.segments.model.PresenceTimeframe;
import com.urbanairship.api.segments.model.RecentDateRange;
import com.urbanairship.api.segments.model.TagPredicate;
import com.urbanairship.api.segments.model.TagPredicateBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class AudienceSegmentDeserializerTest {

    private static ObjectMapper mapper;

    @BeforeClass
    public static void setUp() throws Exception {
        mapper = APIResponseObjectMapper.getInstance();
    }

    @Test
    public void testParseAudienceSegmentWithOperatorCriteria() throws Exception {
        String json =
                "{" +
                        "\"display_name\": \"displayit\"," +
                        "\"criteria\": {\"or\": [" +
                        "{\"and\": [" +
                        "{\"tag\": \"tag1\"}," +
                        "{\"tag\": \"tag2\"}," +
                        "{\"tag\": \"tag3\"}" +
                        "]}," +
                        "{\"location\": {\"id\": \"blah\", \"date\": {\"recent\": {\"days\": 3}}}}" +
                        "]}" +
                        "}";

        Operator op = Operator.newBuilder(OperatorType.OR)
                .addOperator(Operator.newBuilder(OperatorType.AND)
                        .addPredicate(buildTagPredicate("tag1"))
                        .addPredicate(buildTagPredicate("tag2"))
                        .addPredicate(buildTagPredicate("tag3"))
                        .build())
                .addPredicate(new LocationPredicate(new LocationIdentifier("blah"),
                        new RecentDateRange(DateRangeUnit.DAYS, 3), PresenceTimeframe.ANYTIME))
                .build();

        AudienceSegment expected = AudienceSegment.newBuilder()
                .setDisplayName("displayit")
                .setRootOperator(op)
                .build();

        Assert.assertEquals(expected, parse(json));
    }

    private TagPredicate buildTagPredicate(String tag) {
        return TagPredicateBuilder.newInstance().setTag(tag).build();
    }

    private TagPredicate buildTagPredicate(String tag, String tagClass) {
        return TagPredicateBuilder.newInstance().setTagClass(tagClass).setTag(tag).build();
    }

    @Test
    public void testParseAudienceSegmentWithTagCriteria() throws Exception {
        String json =
                "{" +
                        "\"display_name\": \"displayit\"," +
                        "\"criteria\": {\"tag\": \"tag3\"}" +
                        "}";

        AudienceSegment expected = AudienceSegment.newBuilder()
                .setDisplayName("displayit")
                .setRootPredicate(buildTagPredicate("tag3"))
                .build();

        Assert.assertEquals(expected, parse(json));
    }

    @Test
    public void testParseAudienceSegmentWithLocationCriteria() throws Exception {
        String json =
                "{" +
                        "\"display_name\": \"displayit\"," +
                        "\"criteria\": {\"location\": {\"id\": \"blah\", \"date\": {\"recent\": {\"days\": 3}}}}" +
                        "}";

        AudienceSegment expected = AudienceSegment.newBuilder()
                .setDisplayName("displayit")
                .setRootPredicate(new LocationPredicate(new LocationIdentifier("blah"), new RecentDateRange(DateRangeUnit.DAYS, 3), PresenceTimeframe.ANYTIME))
                .build();

        Assert.assertEquals(expected, parse(json));
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidSegmentEmpty() throws Exception {
        parse("{}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidSegmentMissingDisplayName() throws Exception {
        parse("{\"criteria\": {\"tag\": \"blejh\"}}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidSegmentMissingCriteria() throws Exception {
        parse("{\"display_name\": \"name\"}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidSegmentExtraKey() throws Exception {
        parse("{\"display_name\": \"name\", \"criteria\": {\"tag\": \"blejh\"}, \"key\": \"value\"}");
    }

    @Test
    public void testSegmentWithIdIsValidAndIdIsIgnored() throws Exception {
        String json =
                "{" +
                        "\"id\":\"ignore_me_please\"," +
                        "\"display_name\": \"displayit\"," +
                        "\"criteria\": {\"tag\": \"tag3\"}" +
                        "}";

        AudienceSegment result = parse(json);

        AudienceSegment expected = AudienceSegment.newBuilder()
                .setDisplayName("displayit")
                .setRootPredicate(buildTagPredicate("tag3"))
                .build();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSegmentWithTagClassesAndLocation() throws Exception {
        String json = "{\n" +
                "        \"display_name\": \"Foo Segment\",\n" +
                "        \"criteria\": {\n" +
                "                \"and\" : [\n" +
                "                    {\n" +
                "                        \"or\": [\n" +
                "                                {\n" +
                "                                    \"and\": [\n" +
                "                                            {\"tag\": \"tag1\"},\n" +
                "                                            {\"tag\": \"tag2\"},\n" +
                "                                            {\"tag\": \"tag3\"}\n" +
                "                                        ]\n" +
                "                                },\n" +
                "                                {\n" +
                "                                    \"and\": [\n" +
                "                                            {\"tag\": \"tag1\", \"tag_class\" : \"USER\"},\n" +
                "                                            {\"tag\": \"tag2\", \"tag_class\" : \"USER\"},\n" +
                "                                            {\"tag\": \"tag3\", \"tag_class\" : \"USER\"}\n" +
                "                                        ]\n" +
                "                                }\n" +
                "                        ]\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"location\": {\n" +
                "                            \"id\": \"FooCity\",\n" +
                "                            \"date\": {\n" +
                "                                \"recent\": {\"days\": 3}\n" +
                "                                }\n" +
                "                        }\n" +
                "                    }\n" +
                "                ]\n" +
                "        }\n" +
                "    }";

        AudienceSegment result = parse(json);
        Operator op = Operator.newBuilder(OperatorType.AND)
                .addOperator(Operator.newBuilder(OperatorType.OR)
                        .addOperator(Operator.newBuilder(OperatorType.AND)
                                .addPredicate(buildTagPredicate("tag1"))
                                .addPredicate(buildTagPredicate("tag2"))
                                .addPredicate(buildTagPredicate("tag3")).build())
                        .addOperator(Operator.newBuilder(OperatorType.AND)
                                .addPredicate(buildTagPredicate("tag1", "USER"))
                                .addPredicate(buildTagPredicate("tag2", "USER"))
                                .addPredicate(buildTagPredicate("tag3", "USER")).build())
                        .build())
                .addPredicate(new LocationPredicate(new LocationIdentifier("FooCity"),
                        new RecentDateRange(DateRangeUnit.DAYS, 3), PresenceTimeframe.ANYTIME))
                .build();

        AudienceSegment expected = AudienceSegment.newBuilder()
                .setDisplayName("Foo Segment")
                .setRootOperator(op)
                .build();
        Assert.assertEquals(expected, result);
    }

    private AudienceSegment parse(String json) throws IOException {
        return mapper.readValue(json, AudienceSegment.class);
    }
}
