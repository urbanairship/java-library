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

public class OperatorDeserializerTest {

    private static ObjectMapper mapper;

    @BeforeClass
    public static void setUp() throws Exception {
        mapper = APIResponseObjectMapper.getInstance();
    }

    @Test
    public void testSimpleOperators() throws Exception {
        String json =
            "{\"or\": [" +
                "{\"tag\": \"tag1\"}," +
                "{\"tag\": \"tag2\"}," +
                "{\"tag\": \"tag3\"}" +
            "]}";

        Operator expected = Operator.newBuilder(OperatorType.OR)
            .addPredicate(buildTagPredicate("tag1"))
            .addPredicate(buildTagPredicate("tag2"))
            .addPredicate(buildTagPredicate("tag3"))
            .build();

        Assert.assertEquals(expected, parse(json));

        json =
            "{\"and\": [" +
                "{\"tag\": \"tag1\"}," +
                "{\"tag\": \"tag2\"}," +
                "{\"tag\": \"tag3\"}" +
            "]}";

        expected = Operator.newBuilder(OperatorType.AND)
            .addPredicate(buildTagPredicate("tag1"))
            .addPredicate(buildTagPredicate("tag2"))
            .addPredicate(buildTagPredicate("tag3"))
            .build();

        Assert.assertEquals(expected, parse(json));

        json =
            "{\"not\": {\"and\" : [" +
                "{\"tag\": \"tag1\"}," +
                "{\"tag\": \"tag2\"}," +
                "{\"tag\": \"tag3\"}" +
            "]}}";

        expected = Operator.newBuilder(OperatorType.NOT)
            .addOperator(Operator.newBuilder(OperatorType.AND)
                .addPredicate(buildTagPredicate("tag1"))
                .addPredicate(buildTagPredicate("tag2"))
                .addPredicate(buildTagPredicate("tag3"))
                .build()
            )
            .build();

        Assert.assertEquals(expected, parse(json));
    }

    private TagPredicate buildTagPredicate(String tag) {
        return TagPredicateBuilder.newInstance().setTag(tag).build();
    }

    @Test
    public void testNestedOperators() throws Exception {
        String json =
            "{\"or\": [" +
                "{\"and\": [" +
                    "{\"tag\": \"tag1\"}," +
                    "{\"tag\": \"tag2\"}," +
                    "{\"tag\": \"tag3\"}" +
                "]}," +
                "{\"location\": {\"id\": \"blah\", \"date\": {\"recent\": {\"days\": 3}}}}" +
            "]}";

        Operator expected = Operator.newBuilder(OperatorType.OR)
            .addOperator(Operator.newBuilder(OperatorType.AND)
                    .addPredicate(buildTagPredicate("tag1"))
                    .addPredicate(buildTagPredicate("tag2"))
                    .addPredicate(buildTagPredicate("tag3"))
                    .build()
            )
            .addPredicate(new LocationPredicate(new LocationIdentifier("blah"), new RecentDateRange(
                    DateRangeUnit.DAYS, 3), PresenceTimeframe.ANYTIME))
            .build();

        Assert.assertEquals(expected, parse(json));
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidOperatorNoChildren() throws Exception {
        parse("{\"or\": []}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidOperatorInvalidKey() throws Exception {
        parse("{\"blah\": [{\"tag\": \"tag1\"}]");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidOperatorExtraKeys() throws Exception {
        parse("{\"or\": [{\"tag\": \"tagit\"}], \"key\": \"value\"}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidOperatorChildrenNotArray() throws Exception {
        parse("{\"or\": {\"values\": [{\"tag\": \"tag1\"}]}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidOperatorChildrenNotObjects() throws Exception {
        parse("{\"and\": [[{\"tag\": \"tag1\"}]]");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidOperatorEmptyObject() throws Exception {
        parse("{}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testOperatorsMaxDepthExceeded() throws Exception {
        String json =
            "{\"and\" : [" +
                "{\"or\": [" +
                    "{\"and\": [" +
                        "{\"not\": {\"or\": [" +
                            "{\"and\": [" +
                                "{\"not\": {\"or\": [" +
                                    "{\"and\": [" +
                                        "{\"not\": {\"or\": [" +
                                            "{\"and\": [" +
                                                "{\"tag\": \"bleh\"}" +
                                            "]}" +
                                        "]}}" +
                                    "]}" +
                                "]}}" +
                            "]}" +
                        "]}}" +
                    "]}" +
                "]}" +
            "]}";

        parse(json);
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testDoubleNegativeInvalid() throws Exception {
        String json =
            "{\"not\": {\"not\" : {" +
                "{\"tag\": \"tag1\"}," +
            "}}}";

        parse(json);
    }

    private Operator parse(String json) throws IOException {
        return mapper.readValue(json, Operator.class);
    }
}
