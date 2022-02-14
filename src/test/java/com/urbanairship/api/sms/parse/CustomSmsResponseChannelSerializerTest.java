package com.urbanairship.api.sms.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.sms.model.CustomSmsResponseMmsPayload;
import com.urbanairship.api.sms.model.CustomSmsResponseSmsPayload;
import com.urbanairship.api.sms.model.MmsSlides;

import org.junit.Test;

import java.io.IOException;

public class CustomSmsResponseChannelSerializerTest {
    private static final ObjectMapper MAPPER = SmsObjectMapper.getInstance();

    @Test
    public void testCustomSmsResponseSmsPayload() throws IOException {

            CustomSmsResponseSmsPayload CustomSmsResponseSmsPayload = com.urbanairship.api.sms.model.CustomSmsResponseSmsPayload.newBuilder()
                    .setAlert("Your balance is $1234.56")
                    .setMobileOriginatedId("28883743-4868-4083-ab5d-77ac4542531a")
                    .setShortenLinks(true)
                    .build();

            String parsedJson = MAPPER.writeValueAsString(CustomSmsResponseSmsPayload);
            String jsonString = String.format("{\"mobile_originated_id\":\"28883743-4868-4083-ab5d-77ac4542531a\",\"sms\":{\"alert\":\"Your balance is $1234.56\",\"shorten_links\":true}}");

            JsonNode actual = MAPPER.readTree(parsedJson);

            JsonNode expected = MAPPER.readTree(jsonString);

            org.junit.Assert.assertEquals(expected, actual);

    }

    @Test
    public void testCustomSmsResponseMmsPayload() throws IOException {

        CustomSmsResponseMmsPayload CustomSmsResponseMmsPayload = com.urbanairship.api.sms.model.CustomSmsResponseMmsPayload.newBuilder()
                .setFallbackText("Your balance is $1234.56")
                .setMobileOriginatedId("28883743-4868-4083-ab5d-77ac4542531a")
                .setSlides(MmsSlides.newBuilder()
                        .setMediaUrl("https://example.com/cat/pics/12345678.gif")
                        .setText("test")
                        .setMediaContentLength(12345)
                        .setMediaContentType("image/gif")
                        .build())
                .build();

            String parsedJson = MAPPER.writeValueAsString(CustomSmsResponseMmsPayload);
            String jsonString = String.format("{\"mobile_originated_id\":\"28883743-4868-4083-ab5d-77ac4542531a\",\"mms\":{\"fallback_text\":\"Your balance is $1234.56\",\"slides\":[{\"text\":\"test\",\"media\":{\"url\":\"https://example.com/cat/pics/12345678.gif\",\"content_type\":\"image/gif\",\"content_length\":12345}}]}}");

            JsonNode actual = MAPPER.readTree(parsedJson);
            JsonNode expected = MAPPER.readTree(jsonString);

            org.junit.Assert.assertEquals(expected, actual);
    }
}
