package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.push.model.notification.mpns.MPNSToastData;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import static org.junit.Assert.*;

public class ToastDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testDeserialize() throws Exception {
        String json
            = "{"
            + "\"text1\": \"First bit\","
            + "\"text2\": \"Second bit\","
            + "\"param\": \"/page1.xaml\""
            + "}";

        MPNSToastData parsed = mapper.readValue(json, MPNSToastData.class);
        assertTrue(parsed.getText1().isPresent());
        assertTrue(parsed.getText2().isPresent());
        assertTrue(parsed.getParam().isPresent());
        assertEquals("First bit", parsed.getText1().get());
        assertEquals("Second bit", parsed.getText2().get());
        assertEquals("/page1.xaml", parsed.getParam().get());
    }

    @Test(expected=APIParsingException.class)
    public void testDeserializeEmpty() throws Exception {
        mapper.readValue("{}", MPNSToastData.class);
    }

    @Test(expected=APIParsingException.class)
    public void testDeserializeMissingText() throws Exception {
        String json = "{ \"param\": \"/page1.xaml\" }";
        mapper.readValue(json, MPNSToastData.class);
    }
}
