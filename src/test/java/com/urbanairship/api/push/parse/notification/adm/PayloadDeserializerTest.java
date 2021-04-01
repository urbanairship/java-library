package com.urbanairship.api.push.parse.notification.adm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.OpenExternalURLAction;
import com.urbanairship.api.push.model.notification.actions.ShareAction;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.push.model.notification.android.BigPictureStyle;
import com.urbanairship.api.push.model.notification.android.BigTextStyle;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

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

    @Test
    public void roundTripTesting() throws IOException {
        Interactive interactive = Interactive.newBuilder()
                .setType("ua_yes_no_foreground")
                .setButtonActions(
                        ImmutableMap.of(
                                "yes",
                                Actions.newBuilder()
                                        .setShare(new ShareAction("foo"))
                                        .build()))
                .build();

        URI uri = URI.create("http://www.urbanairship.com");

        OpenExternalURLAction urlAction = new OpenExternalURLAction(uri);

        Actions actions = Actions.newBuilder()
                .setOpen(urlAction)
                .build();

        BigTextStyle bigTextStyle = BigTextStyle.newBuilder()
                .setContent("content")
                .setSummary("summary")
                .setTitle("title")
                .build();

        String bigTextStr = mapper.writeValueAsString(bigTextStyle);

        ADMDevicePayload admDevicePayload = ADMDevicePayload.newBuilder()
                .setAlert("Some Title")
                .setInteractive(interactive)
                .setActions(actions)
                .setSummary("summary")
                .setTitle("title")
                .setSound("sound")
                .setStyle(bigTextStyle)
                .setNotificationTag("notificationTag")
                .setNotificationChannel("notificationChannel")
                .setIconColor("#000000")
                .setIcon("icon")
                .build();

        String payloadStr = mapper.writeValueAsString(admDevicePayload);

        ADMDevicePayload roundTripPayload = mapper.readValue(payloadStr, ADMDevicePayload.class);

        assertEquals(admDevicePayload, roundTripPayload);
    }
}