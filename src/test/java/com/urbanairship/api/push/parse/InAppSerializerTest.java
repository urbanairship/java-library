package com.urbanairship.api.push.parse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.Display;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.Position;
import com.urbanairship.api.push.model.PushExpiry;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InAppSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testInAppSerializer() throws Exception {
        String displayJson = "{" +
                "\"primary_color\":\"#ffffff\"," +
                "\"secondary_color\":\"#000000\"," +
                "\"duration\":123456," +
                "\"position\":\"top\"" +
                "}";

        String stringJson = "{" +
                "\"alert\":\"test alert\"," +
                "\"display_type\":\"banner\"," +
                "\"expiry\":\"2017-04-15T11:30:00\"," +
                "\"display\":" + displayJson +
                "}";

        Display display = Display.newBuilder()
                .setPrimaryColor("#ffffff")
                .setSecondaryColor("#000000")
                .setDuration(123456)
                .setPosition(Position.TOP)
                .build();

        DateTime timestamp = new DateTime(2017, 4, 15, 11, 30, DateTimeZone.UTC);
        PushExpiry expiry = PushExpiry.newBuilder().setExpiryTimeStamp(timestamp).build();
        InApp inApp = InApp.newBuilder()
                .setAlert("test alert")
                .setDisplay(display)
                .setExpiry(expiry)
                .build();

        String parsedJson = MAPPER.writeValueAsString(inApp);
        assertEquals(stringJson,parsedJson);
    }
}
