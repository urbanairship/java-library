/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.segments.model.Predicate;
import com.urbanairship.api.segments.model.TagPredicateBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class PredicateDeserializerTest {

    private static ObjectMapper mapper;

    @BeforeClass
    public static void setUp() throws Exception {
        mapper = APIResponseObjectMapper.getInstance();
    }

    @Test
    public void testValidPredicate() throws Exception {
        String json = "{\"tag\": \"blah\"}";
        Predicate expected = TagPredicateBuilder.newInstance().setTag("blah").build();

        Assert.assertEquals(expected, parse(json));
    }

    @Test
    public void testValidTagPredicateWithClass() throws Exception {
        String json = "{\"tag\": \"blah\", \"tag_class\": \"auto\"}";
        Predicate expected = TagPredicateBuilder.newInstance().setTag("blah").setTagClass("auto").build();

        Assert.assertEquals(expected, parse(json));
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidPredicateKeyUnknown() throws Exception {
        parse("{\"constraint\": \"cake\"}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidPredicateEmptyObject() throws Exception {
        parse("{}");
    }

    @Test(expected = InvalidAudienceSegmentException.class)
    public void testInvalidPredicateMultipleKeys() throws Exception {
        parse("{\"tag\": \"fun\", \"cash\": \"money\"}");
    }

    private Predicate parse(String json) throws IOException {
        return mapper.readValue(json, Predicate.class);
    }
}
