package com.urbanairship.api.reports.model;

import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PushListingResponseTest {

    @Test
    public void testPushListingResponse() {

        UUID one = UUID.randomUUID();
        UUID two = UUID.randomUUID();

        PushInfoResponse obj = PushInfoResponse.newBuilder()
                .setPushId(one)
                .setDirectResponses(4)
                .setSends(5)
                .setPushType(PushInfoResponse.PushType.UNICAST_PUSH)
                .setPushTime("2013-07-31 21:27:38")
                .setGroupId(two)
                .build();

        PushListingResponse response = PushListingResponse.newBuilder()
                .setNextPage("The Next Page")
                .addPushInfoObject(obj)
                .addPushInfoObject(obj)
                .addPushInfoObject(obj)
                .addPushInfoObject(obj)
                .addPushInfoObject(obj)
                .build();

        assertNotNull(response);
        assertEquals(5, response.getPushInfoList().get().size());
        assertEquals("The Next Page", response.getNextPage().get());
        assertEquals(one, response.getPushInfoList().get().get(2).getPushId());
        assertEquals(two, response.getPushInfoList().get().get(2).getGroupID().get());
    }

    @Test
    public void testErrorAPIPushListingResponse() throws IOException {
        String jsonResponse = "{\n" +
                "    \"ok\": false,\n" +
                "    \"error\": \"error\",\n" +
                "    \"details\": {\n" +
                "        \"error\": \"error\"\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = ReportsObjectMapper.getInstance();
        PushListingResponse response = mapper.readValue(jsonResponse, PushListingResponse.class);
        assertEquals("error", response.getError().get());
        assertEquals("error", response.getErrorDetails().get().getError().get());
        assertEquals(false, response.getOk().get());
    }
}
