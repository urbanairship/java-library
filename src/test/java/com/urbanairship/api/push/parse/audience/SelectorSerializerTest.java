package com.urbanairship.api.push.parse.audience;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.audience.sms.SmsSelector;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SelectorSerializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testSmsSender() throws IOException {
        String expected = "{\"sms_sender\" : \"123\"}";
        String actual = MAPPER.writeValueAsString(Selectors.smsBroadcast("123"));

        JsonNode expectedJson = MAPPER.readTree(expected);
        JsonNode actualJson = MAPPER.readTree(actual);

        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testSmsSelector() throws IOException {
        String expected = "{\"sms_id\":{\"msisdn\":\"15552243311\",\"sender\":\"12345\"}}";
        String actual = MAPPER.writeValueAsString(SmsSelector.newBuilder()
                .setMsisdn("15552243311")
                .setSender("12345")
                .build());

        JsonNode expectedJson = MAPPER.readTree(expected);
        JsonNode actualJson = MAPPER.readTree(actual);

        assertEquals(expectedJson, actualJson);
    }
}
