package com.urbanairship.api.push.parse.notification.adm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.OpenExternalURLAction;
import com.urbanairship.api.push.model.notification.actions.ShareAction;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.push.model.notification.adm.ADMFields;
import com.urbanairship.api.push.model.notification.adm.ADMTemplate;
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

        mapper.writeValueAsString(bigTextStyle);

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

    @Test
    public void templatePushRoundTripTest() throws IOException {
        ADMFields admFields = ADMFields.newBuilder()
                .setTitle("Shoe sale on {{level}} floor!")
                .setAlert("All the shoes are on sale {{name}}!")
                .setSummary("Don't miss out!")
                .setIcon("shoes")
                .setIconColor("{{iconColor}}")
                .build();

        ADMTemplate admTemplate = ADMTemplate.newBuilder()
                .setFields(admFields)
                .build();

        ADMDevicePayload admDevicePayload = ADMDevicePayload.newBuilder()
                .setTemplate(admTemplate)
                .build();


        String expectedStr = "{\n" +
                "      \"template\": {\n" +
                "         \"fields\": {\n" +
                "            \"title\": \"Shoe sale on {{level}} floor!\",\n" +
                "            \"alert\": \"All the shoes are on sale {{name}}!\",\n" +
                "            \"summary\": \"Don't miss out!\",\n" +
                "            \"icon\": \"shoes\",\n" +
                "            \"icon_color\": \"{{iconColor}}\"\n" +
                "         }\n" +
                "      }\n" +
                "   }";

        String actualStr = mapper.writeValueAsString(admDevicePayload);

        ADMDevicePayload roundTripPayload = mapper.readValue(actualStr, ADMDevicePayload.class);

        JsonNode expectedJson = mapper.readTree(expectedStr);
        JsonNode actualJson = mapper.readTree(actualStr);

        assertEquals(expectedJson, actualJson);
        assertEquals(admDevicePayload, roundTripPayload);
    }
}