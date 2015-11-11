package com.urbanairship.api.reports.model;


import com.urbanairship.api.reports.model.PushInfoResponse;
import com.urbanairship.api.reports.model.PushListingResponse;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PushListingResponseTest {

    @Test
    public void testPushListingResponse() {

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
