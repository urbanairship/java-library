package com.urbanairship.api.reports.model;

import com.google.common.base.Optional;
import com.urbanairship.api.reports.Base64ByteArray;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PushDetailResponseTest {

    @Test
    public void testPerPushDetailResponse() {

        UUID pushId = UUID.randomUUID();
        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        Optional<Base64ByteArray> pushBody = Optional.absent();

        PerPushCounts ppc = PerPushCounts.newBuilder()
                .setDirectResponses(1111)
                .setInfluencedResponses(2222)
                .setSends(3333)
                .build();

        PushDetailResponse obj = PushDetailResponse.newBuilder()
                .setAppKey("app_key")
                .setPushID(pushId)
                .setCreated(Optional.of(created))
                .setPushBody(pushBody)
                .setRichDeletions(1234)
                .setRichResponses(2345)
                .setRichSends(3456)
                .setSends(4567)
                .setDirectResponses(5678)
                .setInfluencedResponses(6789)
                .addPlatform(PlatformType.IOS, ppc)
                .build();

        assertNotNull(obj);
        assertEquals("app_key", obj.getAppKey());
        assertEquals(pushId, obj.getPushID());
        assertEquals(created, obj.getCreated().get());
        assertEquals(pushBody, obj.getPushBody());
        assertEquals(1234, obj.getRichDeletions());
        assertEquals(2345, obj.getRichResponses());
        assertEquals(3456, obj.getRichSends());
        assertEquals(4567, obj.getSends());
        assertEquals(5678, obj.getDirectResponses());
        assertEquals(6789, obj.getInfluencedResponses());
    }
}
