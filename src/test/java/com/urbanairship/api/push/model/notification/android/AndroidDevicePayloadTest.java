package com.urbanairship.api.push.model.notification.android;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.HashMap;
import com.urbanairship.api.push.model.PushExpiry;

public class AndroidDevicePayloadTest {

    @Test (expected = Exception.class)
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

}

