package com.urbanairship.api.push.model.audience.location;

import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.urbanairship.api.push.model.audience.ValueSelector;
import org.apache.commons.lang.ObjectUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import static org.junit.Assert.*;

public class SegmentDefinitionDeserializerTest {

    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testSimpleSegmentDefinition() throws Exception {
        String json
                = "{"
                + "  \"display_name\" : \"My Segment\","
                + "  \"criteria\" : {"
                + "    \"tag\" : \"foo\""
                + "  }"
                + "}";
        SegmentDefinition def = mapper.readValue(json, SegmentDefinition.class);
        assertEquals("My Segment", def.getDisplayName());
        Selector s = def.getCriteria();
        assertNotNull(s);
        assertEquals(SelectorType.TAG, s.getType());
        assertEquals("foo", ((ValueSelector)s).getValue());
    }

    @Test(expected=APIParsingException.class)
    public void testBadCriteria() throws Exception {
        String json
                = "{"
                + "  \"display_name\" : \"My Segment\","
                + "  \"criteria\" : {"
                + "    \"snarf\" : \"foo\""
                + "  }"
                + "}";
        mapper.readValue(json, SegmentDefinition.class);
    }

    @Test(expected=NullPointerException.class)
    public void testMissingCriteria() throws Exception {
        String json
                = "{"
                + "  \"display_name\" : \"My Segment\""
                + "  }"
                + "}";
        mapper.readValue(json, SegmentDefinition.class);
    }



}
