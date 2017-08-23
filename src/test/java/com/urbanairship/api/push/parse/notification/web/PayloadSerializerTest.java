package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.notification.web.WebDevicePayload;
import com.urbanairship.api.push.model.notification.web.WebIcon;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PayloadSerializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testFullWebPayload() throws Exception {
        WebIcon webIcon = WebIcon.newBuilder()
                .setUrl("https://i.ytimg.com/vi/PNgykntrIzE/maxresdefault.jpg")
                .build();

        WebDevicePayload webPayload = WebDevicePayload.newBuilder()
                .setAlert("WebSettings specific alert")
                .setTitle("WebSettings title")
                .addExtraEntry("extrakey", "extravalue")
                .setWebIcon(webIcon)
                .build();

        String expected = "{" +
                    "\"alert\":\"WebSettings specific alert\"," +
                    "\"extra\":{\"extrakey\":\"extravalue\"}," +
                    "\"icon\":{\"url\":\"https://i.ytimg.com/vi/PNgykntrIzE/maxresdefault.jpg\"}," +
                    "\"title\":\"WebSettings title\"" +
                "}";

        String parsedJson = MAPPER.writeValueAsString(webPayload);
        WebDevicePayload roundTripWebPayload = MAPPER.readValue(parsedJson, WebDevicePayload.class);

        JsonNode jsonFromObject = MAPPER.readTree(parsedJson);
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromObject, jsonFromString);
        assertEquals(roundTripWebPayload, webPayload);
    }
}
