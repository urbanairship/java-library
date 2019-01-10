package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.*;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PushOptionsTest {

    private ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testOptionsNotPresent() throws Exception {
        String properJson
                = "{"
                + "\"audience\":\"ALL\","
                + "\"device_types\":[\"ios\"],"
                + "\"notification\":{\"alert\":\"wat\"}"
                + "}";
        PushPayload pushPayload = PushPayload.newBuilder()
                .setAudience(Selectors.all())
                .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
                .setNotification(Notification.newBuilder().setAlert("wat").build())
                .build();

        assertFalse(pushPayload.getPushOptions().isPresent());

        String json = mapper.writeValueAsString(pushPayload);
        assertEquals(properJson, json);
    }

    @Test
    public void testOptionsPresent() throws Exception {
        String properJson
            = "{"
            + "\"audience\":\"ALL\","
            + "\"device_types\":[\"ios\"],"
            + "\"notification\":{\"alert\":\"wat\"},"
            + "\"options\":{}"
            + "}";
        PushPayload push = PushPayload.newBuilder()
            .setAudience(Selectors.all())
            .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
            .setNotification(Notification.newBuilder().setAlert("wat").build())
            .setPushOptions(PushOptions.newBuilder().build())
            .build();
        String json = mapper.writeValueAsString(push);

        assertTrue(push.getPushOptions().isPresent());
        assertEquals(properJson, json);
    }

    @Test
    public void testOptionsNoThrottlePresent() throws Exception {
        String properJson
            = "{"
            + "\"audience\":\"ALL\","
            + "\"device_types\":[\"ios\"],"
            + "\"notification\":{\"alert\":\"wat\"},"
            + "\"options\":{\"no_throttle\":true}"
            + "}";
        PushPayload push = PushPayload.newBuilder()
            .setAudience(Selectors.all())
            .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
            .setNotification(Notification.newBuilder().setAlert("wat").build())
            .setPushOptions(PushOptions.newBuilder().setNoThrottle(PushNoThrottle.newBuilder().setValue(true).build()).build())
            .build();
        String json = mapper.writeValueAsString(push);

        assertTrue(push.getPushOptions().isPresent());
        assertEquals(properJson, json);
    }

    @Test
    public void testOptionsAllPresent() throws Exception {
        String properJson
            = "{"
            + "\"audience\":\"ALL\","
            + "\"device_types\":[\"ios\"],"
            + "\"notification\":{\"alert\":\"wat\"},"
            + "\"options\":{\"expiry\":300,"
            + "\"no_throttle\":true}"
            + "}";
        PushPayload push = PushPayload.newBuilder()
            .setAudience(Selectors.all())
            .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
            .setNotification(Notification.newBuilder().setAlert("wat").build())
            .setPushOptions(PushOptions.newBuilder().setExpiry(PushExpiry.newBuilder().setExpirySeconds(300).build()).setNoThrottle(PushNoThrottle.newBuilder().setValue(true).build()).build())
            .build();
        String json = mapper.writeValueAsString(push);

        assertTrue(push.getPushOptions().isPresent());
        assertEquals(properJson, json);
    }


    @Test
    public void testEmptyOptions() throws Exception {
        String properJson
                = "{"
                + "}";
        PushOptions options = PushOptions.newBuilder().build();
        String json = mapper.writeValueAsString(options);

        assertFalse(options.getExpiry().isPresent());
        assertEquals(json, properJson);
    }

    @Test
    public void testParseNoThrottleRoundTrip() throws Exception {
        PushNoThrottle pushNoThrottle1 = PushNoThrottle.newBuilder().setValue(true).build();
        String json = mapper.writeValueAsString(pushNoThrottle1);
        PushNoThrottle pushNoThrottle2 = mapper.readValue(json, PushNoThrottle.class);
        assertEquals(pushNoThrottle1, pushNoThrottle2);
    }


    @Test
    public void testParsingNoThrottle() throws Exception {
        String json
            = "{"
            + "\"no_throttle\":true"
            + "}";

        PushOptions options = mapper.readValue(json, PushOptions.class);

        assertTrue(options.getNoThrottle().isPresent());
        PushNoThrottle noThrottle = options.getNoThrottle().get();
        assertEquals(true, noThrottle.getValue().get());
    }

    @Test
    public void testParseExpiryRoundTrip() throws Exception {
        PushExpiry expiry1 = PushExpiry.newBuilder().setExpirySeconds(600).build();
        String json = mapper.writeValueAsString(expiry1);
        PushExpiry expiry2 = mapper.readValue(json, PushExpiry.class);
        assertEquals(expiry1, expiry2);
    }



    /* Equality */
    @Test
    public void testTimeStampEquality() throws Exception {
        PushExpiry e1 = PushExpiry.newBuilder().setExpiryTimeStamp(new DateTime("2014-07-08T12:00:00", DateTimeZone.UTC)).build();
        PushExpiry e2 = PushExpiry.newBuilder().setExpiryTimeStamp(new DateTime("2014-07-08T12:00:00", DateTimeZone.UTC)).build();
        PushExpiry e3 = PushExpiry.newBuilder().setExpiryTimeStamp(new DateTime("2014-07-08T14:00:00", DateTimeZone.UTC)).build();
        assertEquals(e1, e1);
        assertEquals(e1, e2);
        assertEquals(e2, e1);
        assertTrue(e1.equals(e2));
        assertTrue(e2.equals(e1));
        assertFalse(e1.equals(e3));
        assertFalse(e3.equals(e1));
    }


    @Test
    public void testSecondsEquality() throws Exception {
        PushExpiry e1 = PushExpiry.newBuilder().setExpirySeconds(100).build();
        PushExpiry e2 = PushExpiry.newBuilder().setExpirySeconds(100).build();
        PushExpiry e3 = PushExpiry.newBuilder().setExpirySeconds(10).build();
        assertEquals(e1, e1);
        assertEquals(e1, e2);
        assertEquals(e2, e1);
        assertTrue(e1.equals(e2));
        assertTrue(e2.equals(e1));
        assertFalse(e1.equals(e3));
        assertFalse(e3.equals(e1));
    }


    @Test
    public void testParsingExpirySeconds() throws Exception {
        String json
                = "{"
                + "\"expiry\":3600"
                + "}";

        PushOptions options = mapper.readValue(json, PushOptions.class);
        Integer seconds = 3600;

        assertTrue(options.getExpiry().isPresent());
        PushExpiry expiry = options.getExpiry().get();
        assertEquals(seconds, expiry.getExpirySeconds().get());
    }

    @Test
    public void testParsingExpiryTimeStamp() throws Exception {
        String json
                = "{"
                + "\"expiry\":\"2014-07-08T12:00:00\""
                + "}";

        PushOptions options = mapper.readValue(json, PushOptions.class);

        assertTrue(options.getExpiry().isPresent());
        PushExpiry expiry = options.getExpiry().get();
        assertEquals(new DateTime(2014, 7, 8, 12, 0, 0, DateTimeZone.UTC), expiry.getExpiryTimeStamp().get());
    }

    @Test(expected=APIParsingException.class)
    public void testParseValidation() throws Exception {
        String json
            = "{"
            + "  \"expiry\" : -10"
            + "}";
        mapper.readValue(json, PushOptions.class);
    }

    @Test(expected=APIParsingException.class)
    public void testEmptyValidation() throws Exception {
        PushExpiry.newBuilder()
                .build();
    }

    @Test(expected=APIParsingException.class)
    public void testDoubleValidation() throws Exception {
        PushExpiry.newBuilder()
                .setExpirySeconds(100)
                .setExpiryTimeStamp(new DateTime())
                .build();
    }

    @Test(expected=APIParsingException.class)
    public void testNegativeValidation() throws Exception {
        PushExpiry.newBuilder()
                .setExpirySeconds(-100)
                .build();
    }


}
