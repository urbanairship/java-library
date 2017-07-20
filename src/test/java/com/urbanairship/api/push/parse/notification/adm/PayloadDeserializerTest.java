package com.urbanairship.api.push.parse.notification.adm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.ShareAction;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PayloadDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testAlertRoundtrip() throws IOException {
        ADMDevicePayload override = ADMDevicePayload.newBuilder()
            .setAlert("Some Title")
            .build();
        String json = mapper.writeValueAsString(override);
        ADMDevicePayload override2 = mapper.readValue(json, ADMDevicePayload.class);
        assertEquals(override, override2);
    }

    @Test
    public void testInteractiveRoundtrip() throws IOException {
        Interactive interactive = Interactive.newBuilder()
            .setType("ua_yes_no_foreground")
            .setButtonActions(
                ImmutableMap.of(
                    "yes",
                    Actions.newBuilder()
                        .setShare(new ShareAction("foo"))
                        .build()))
            .build();
        ADMDevicePayload override = ADMDevicePayload.newBuilder()
            .setAlert("Some Title")
            .setInteractive(interactive)
            .build();
        String json = mapper.writeValueAsString(override);
        ADMDevicePayload override2 = mapper.readValue(json, ADMDevicePayload.class);
        assertEquals(override, override2);
    }
}

