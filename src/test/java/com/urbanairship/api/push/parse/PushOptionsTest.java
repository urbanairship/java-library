package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.*;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.google.common.base.Optional;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

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
                .setPlatforms(PlatformData.newBuilder().addPlatform(Platform.IOS).build())
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
                + "\"options\":{\"present\":true}"
                + "}";
        PushPayload push = PushPayload.newBuilder()
                .setAudience(Selectors.all())
                .setPlatforms(PlatformData.newBuilder().addPlatform(Platform.IOS).build())
                .setNotification(Notification.newBuilder().setAlert("wat").build())
                .setPushOptions(PushOptions.newBuilder().build())
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
    public void testParseExpiry() throws Exception {
        String json
                = "{"
                + "\"expirySeconds\":600" /* expire in 10 minutes */
                + "}";
        PushOptions options = PushOptions.newBuilder().setExpiry(PushExpiry.newBuilder().setExpirySeconds(600L).build()).build();
        assertTrue(options.getExpiry().isPresent());
        PushExpiry expiry = options.getExpiry().get();
        assertFalse(expiry.getExpiryTimeStamp().isPresent());
        Long exp = 600L;
        assertEquals(exp, expiry.getExpirySeconds().get());
        String actualJSON = mapper.writeValueAsString(expiry);
        assertEquals(json, actualJSON);
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
    public void testSerializationExpirySeconds() throws Exception {
        PushOptions pushOptions = PushOptions.newBuilder()
                .setExpiry(PushExpiry.newBuilder().setExpirySeconds(3600L).build())
                .build();

        String json = mapper.writeValueAsString(pushOptions);

        String properJson
                = "{"
                + "\"expiry\":{\"expirySeconds\":3600}"
                + "}";

        assertEquals(properJson, json);
    }

    @Test
    public void testSerializationExpiryTimeStamp() throws Exception {
        PushOptions pushOptions = PushOptions.newBuilder()
                .setExpiry(PushExpiry.newBuilder().setExpiryTimeStamp(new DateTime("2014-07-08T12:00:00", DateTimeZone.UTC)).build())
                .build();

        String json = mapper.writeValueAsString(pushOptions);

        String properJson
                = "{"
                + "\"expiry\":{\"expiryTimestamp\":\"2014-07-08T12:00:00\"}"
                + "}";

        assertEquals(properJson, json);
    }


    @Test
    public void testEmptyValidation() throws Exception {
        PushExpiry.newBuilder()
                .build();
    }

    @Test
    public void testDoubleValidation() throws Exception {
        PushExpiry.newBuilder()
                .setExpirySeconds(100)
                .setExpiryTimeStamp(new DateTime())
                .build();
    }

    @Test
    public void testNegativeValidation() throws Exception {
        PushExpiry.newBuilder()
                .setExpirySeconds(-100)
                .build();
    }





}
