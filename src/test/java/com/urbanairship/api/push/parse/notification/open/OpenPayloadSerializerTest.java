package com.urbanairship.api.push.parse.notification.open;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AddTagAction;
import com.urbanairship.api.push.model.notification.actions.RemoveTagAction;
import com.urbanairship.api.push.model.notification.actions.TagActionData;
import com.urbanairship.api.push.model.notification.open.OpenPayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OpenPayloadSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testOpenPayload() throws Exception {
        DeviceType deviceTypeEmail = DeviceType.open("email");
        DeviceType deviceTypeSms = DeviceType.open("sms");

        AddTagAction butterTag = new AddTagAction(TagActionData.single("butter"));
        RemoveTagAction coolTag = new RemoveTagAction(TagActionData.single("cool"));

        ImmutableMap<String, Actions> buttonActions = ImmutableMap.<String,Actions>builder()
                .put("yes", Actions.newBuilder().addTags(butterTag).build())
                .put("no", Actions.newBuilder().removeTags(coolTag).build())
                .build();

        Interactive interactive = Interactive.newBuilder()
                .setType("ua_yes_no_foreground")
                .setButtonActions(buttonActions)
                .build();

        ImmutableMap<String, String> extras = ImmutableMap.<String, String>builder()
                .put("key", "value")
                .put("second_key", "second_value")
                .build();

        OpenPayload openPayloadEmail = OpenPayload.newBuilder()
                .setAlert("alert")
                .setExtras(extras)
                .setInteractive(interactive)
                .setTitle("title")
                .setMediaAttachment("example.com")
                .setSummary("summary")
                .setDeviceType(deviceTypeEmail)
                .build();

        OpenPayload openPayloadSms = OpenPayload.newBuilder()
                .setAlert("sms alert")
                .setTitle("sms title")
                .setDeviceType(deviceTypeSms)
                .build();

        PushPayload payload = PushPayload.newBuilder()
                .setNotification(Notifications.notification(openPayloadEmail, openPayloadSms))
                .setDeviceTypes(DeviceTypeData.of(deviceTypeEmail, deviceTypeSms))
                .setAudience(Selectors.tag("tag1"))
                .build();

        String json = "{\n" +
                "    \"audience\": {\n" +
                "        \"tag\": \"tag1\"\n" +
                "    },\n" +
                "    \"device_types\": [\n" +
                "        \"open::email\",\n" +
                "        \"open::sms\"\n" +
                "    ],\n" +
                "    \"notification\": {\n" +
                "        \"open::sms\": {\n" +
                "            \"alert\": \"sms alert\",\n" +
                "            \"title\": \"sms title\"\n" +
                "        },\n" +
                "        \"open::email\": {\n" +
                "            \"alert\": \"alert\",\n" +
                "            \"extra\": {\n" +
                "                \"key\": \"value\",\n" +
                "                \"second_key\": \"second_value\"\n" +
                "            },\n" +
                "            \"interactive\": {\n" +
                "                \"type\": \"ua_yes_no_foreground\",\n" +
                "                \"button_actions\": {\n" +
                "                    \"yes\": {\n" +
                "                        \"add_tag\": \"butter\"\n" +
                "                    },\n" +
                "                    \"no\": {\n" +
                "                        \"remove_tag\": \"cool\"\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"media_attachment\": \"example.com\",\n" +
                "            \"title\": \"title\",\n" +
                "            \"summary\": \"summary\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        String parsedJson = MAPPER.writeValueAsString(payload);

        JsonNode actual = MAPPER.readTree(parsedJson);
        JsonNode expected = MAPPER.readTree(json);

        assertEquals(expected, actual);
    }
}
