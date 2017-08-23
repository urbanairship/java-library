package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.urbanairship.api.common.parse.CommonObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class DateTimeBasicSerializationTest {

    private static final ObjectMapper mapper = CommonObjectMapper.getInstance();

    @Test
    public void testRoundTrip() throws Exception {
        DateTime date = new DateTime(2012, 4, 5, 14, 34, 25, DateTimeZone.UTC);

        Set<DateTime> set = Sets.newHashSet(date);

        String json = mapper.writeValueAsString(set);
        Set<DateTime> parsed = mapper.readValue(json, new TypeReference<Set<DateTime>>() {
        });

        assertEquals(set, parsed);
    }
}
