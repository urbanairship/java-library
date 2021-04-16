package com.urbanairship.api.push.model.notification.android;

import com.urbanairship.api.push.model.PushExpiry;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AndroidDevicePayloadTest {

    @Test(expected = Exception.class)
    public void testNullExtraValues1() {
        Map<String, String> values = new HashMap<String, String>();
        values.put("this", null);
        AndroidDevicePayload.newBuilder()
                .addAllExtraEntries(values)
                .build();
    }

    @Test(expected = Exception.class)
    public void testNullExtraValues2() {
        AndroidDevicePayload.newBuilder()
                .addExtraEntry("foo", null)
                .build();
    }

    @Test(expected = Exception.class)
    public void testNullExtraKey() {
        AndroidDevicePayload.newBuilder()
                .addExtraEntry(null, "1")
                .build();
    }

    @Test
    public void testTimeToLiveBuild() {
        AndroidDevicePayload.newBuilder()
                .setTimeToLive(PushExpiry.newBuilder().setExpirySeconds(600).build())
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectTemplateConfiguration() {
        AndroidFields androidFields = AndroidFields.newBuilder()
                .setAlert("alert field")
                .build();

        AndroidTemplate androidTemplate = AndroidTemplate.newBuilder()
                .setTemplateId("templateId")
                .setFields(androidFields)
                .build();

    }

}

