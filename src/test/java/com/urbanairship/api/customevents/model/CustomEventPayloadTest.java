package com.urbanairship.api.customevents.model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomEventPayloadTest {

    @Test
    public void testBuilder() {
        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .build();

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("category", "mens shoes");
        properties.put("id", "pid-11046546");
        properties.put("description", "sky high");
        properties.put("brand", "victory");

        CustomEventBody customEventBody = CustomEventBody.newBuilder()
                .setName("purchased")
                .setValue(new BigDecimal(120.49))
                .setTransaction("886f53d4-3e0f-46d7-930e-c2792dac6e0a")
                .setInteractionId("your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234")
                .setInteractionType("url")
                .addAllPropertyEntries(properties)
                .setSessionId("22404b07-3f8f-4e42-a4ff-a996c18fa9f1")
                .build();

        // The date and time when the event occurred.
        DateTime occurred = new DateTime(2016, 5, 2, 2, 31, 22, DateTimeZone.UTC);

        CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
                .setOccurred(occurred)
                .setCustomEventUser(customEventUser)
                .setCustomEventBody(customEventBody)
                .build();

        assertTrue(customEventPayload.getCustomEventBody().getProperties().isPresent());
        assertEquals(4, customEventPayload.getCustomEventBody().getProperties().get().size());
        assertTrue(customEventPayload.getCustomEventBody().getProperties().get().containsKey("description"));
        assertEquals("victory", customEventPayload.getCustomEventBody().getProperties().get().get("brand"));

        assertEquals("purchased", customEventPayload.getCustomEventBody().getName());
        assertEquals(new BigDecimal(120.49), customEventPayload.getCustomEventBody().getValue().get());
        assertEquals("886f53d4-3e0f-46d7-930e-c2792dac6e0a", customEventPayload.getCustomEventBody().getTransaction().get());
        assertEquals("your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234", customEventPayload.getCustomEventBody().getInteractionId().get());
        assertEquals("url", customEventPayload.getCustomEventBody().getInteractionType().get());
        assertEquals("22404b07-3f8f-4e42-a4ff-a996c18fa9f1", customEventPayload.getCustomEventBody().getSessionId());

        assertEquals("e393d28e-23b2-4a22-9ace-dc539a5b07a8", customEventPayload.getCustomEventUser().getChannel().get());
        assertEquals(CustomEventChannelType.ANDROID_CHANNEL, customEventPayload.getCustomEventUser().getChannelType().get());

        assertEquals(occurred, customEventPayload.getOccurred());
    }
}
