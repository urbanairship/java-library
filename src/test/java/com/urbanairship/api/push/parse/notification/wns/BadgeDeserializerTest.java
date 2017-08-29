package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.wns.WNSBadgeData;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BadgeDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testValue() throws Exception {
        String json =
                "{" +
                        "  \"value\": 10" +
                        "}";

        WNSBadgeData parsed = mapper.readValue(json, WNSBadgeData.class);
        assertEquals(10, parsed.getValue().get().intValue());
        assertFalse(parsed.getGlyph().isPresent());
    }

    @Test
    public void testGlyph() throws Exception {
        String json =
                "{" +
                        "  \"glyph\": \"new-message\"" +
                        "}";

        WNSBadgeData parsed = mapper.readValue(json, WNSBadgeData.class);
        assertEquals(WNSBadgeData.Glyph.NEW_MESSAGE, parsed.getGlyph().get());
        assertFalse(parsed.getValue().isPresent());
    }


    @Test(expected = APIParsingException.class)
    public void testValidation_Both() throws Exception {
        String json =
                "{" +
                        "  \"value\": 10," +
                        "  \"glyph\": \"none\"" +
                        "}";

        mapper.readValue(json, WNSBadgeData.class);
    }

    @Test(expected = APIParsingException.class)
    public void testValidation_InvalidGlyph() throws Exception {
        String json =
                "{" +
                        "  \"glyph\": \"alkjsaasas\"" +
                        "}";

        mapper.readValue(json, WNSBadgeData.class);
    }

}
