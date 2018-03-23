package com.urbanairship.api.push.model.notification.open;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OpenPayloadTest {

    @Test
    public void testBuilder() {
        DeviceType deviceTypeEmail = DeviceType.open("email");

        ImmutableMap<String, String> extras = ImmutableMap.<String, String>builder()
                .put("key", "value")
                .put("second_key", "second_value")
                .build();

        OpenPayload openPayloadEmail = OpenPayload.newBuilder()
                .setAlert("alert")
                .setExtras(extras)
                .setTitle("title")
                .setMediaAttachment("example.com")
                .setSummary("summary")
                .setDeviceType(deviceTypeEmail)
                .build();

        assertTrue(openPayloadEmail.getExtras().isPresent());

        assertEquals(openPayloadEmail.getExtras().get().get("key"), "value");
        assertEquals(openPayloadEmail.getExtras().get().get("second_key"), "second_value");
        assertEquals(openPayloadEmail.getAlert().get(), "alert");
        assertEquals(openPayloadEmail.getTitle().get(), "title");
        assertEquals(openPayloadEmail.getSummary().get(), "summary");
        assertEquals(openPayloadEmail.getDeviceType(), deviceTypeEmail);
    }
}
