package com.urbanairship.api.push.parse.notification;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.ShareAction;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InteractiveSerializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testSerializationRoundTrip() throws IOException {
        Interactive interactive = Interactive.newBuilder()
            .setType("ua_yes_no_foreground")
            .setButtonActions(
                ImmutableMap.of(
                   "yes",
                    Actions.newBuilder()
                        .setShare(new ShareAction("foo"))
                        .build()))
            .build();

        String json = mapper.writeValueAsString(interactive);

        Interactive interactive1 = mapper.readValue(json, Interactive.class);
        assertEquals(interactive, interactive1);
    }

    @Test
    public void testSerializationNoButtonActions() throws IOException {
        Interactive interactive = Interactive.newBuilder()
            .setType("ua_yes_no_foreground")
            .build();
        assertTrue(interactive.getButtonActions().isEmpty());

        String json = mapper.writeValueAsString(interactive);

        Interactive interactive1 = mapper.readValue(json, Interactive.class);
        assertTrue(interactive1.getButtonActions().isEmpty());
        assertEquals(interactive, interactive1);
    }
}
