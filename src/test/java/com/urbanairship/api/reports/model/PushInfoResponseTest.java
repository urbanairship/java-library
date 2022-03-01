package com.urbanairship.api.reports.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PushInfoResponseTest {

    @Test
    public void testPushInfoResponseBuilder() {

        UUID one = UUID.randomUUID();
        UUID two = UUID.randomUUID();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime("2013-07-31 21:27:38");

        PushInfoResponse obj = PushInfoResponse.newBuilder()
                .setPushId(one)
                .setDirectResponses(4)
                .setSends(5)
                .setPushType(PushInfoResponse.PushType.UNICAST_PUSH)
                .setPushTime("2013-07-31 21:27:38")
                .setGroupId(two)
                .build();

        assertNotNull(obj);
        assertEquals(one, obj.getPushId());
        assertEquals(4, obj.getDirectResponses());
        assertEquals(5, obj.getSends());
        assertEquals(PushInfoResponse.PushType.UNICAST_PUSH, obj.getPushType());
        assertEquals(dt, obj.getPushTime());
        assertTrue(obj.getGroupID().isPresent());
        assertEquals(two, obj.getGroupID().get());

    }

    @Test
    public void testPushInfoResponseBuilderNoGroupID() {

        UUID one = UUID.randomUUID();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime("2013-07-31 21:27:38");

        PushInfoResponse obj = PushInfoResponse.newBuilder()
                .setPushId(one)
                .setSends(5)
                .setPushType(PushInfoResponse.PushType.UNICAST_PUSH)
                .setPushTime("2013-07-31 21:27:38")
                .build();

        assertNotNull(obj);
        assertEquals(one, obj.getPushId());
        assertEquals(5, obj.getSends());
        assertEquals(PushInfoResponse.PushType.UNICAST_PUSH, obj.getPushType());
        assertEquals(dt, obj.getPushTime());
        assertFalse(obj.getGroupID().isPresent());

    }

    @Test
    public void testErrorAPIPushInfoResponse() throws IOException {
        String jsonResponse = "{\n" +
                "    \"ok\": false,\n" +
                "    \"error\": \"error\",\n" +
                "    \"details\": {\n" +
                "        \"error\": \"error\"\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = ReportsObjectMapper.getInstance();
        PushInfoResponse response = mapper.readValue(jsonResponse, PushInfoResponse.class);
        assertEquals("error", response.getError().get());
        assertEquals("error", response.getErrorDetails().get().getError().get());
        assertEquals(false, response.getOk());
    }
}
