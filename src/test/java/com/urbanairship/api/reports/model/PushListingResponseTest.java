package com.urbanairship.api.reports.model;

import org.junit.Test;

import java.util.UUID;

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
}
