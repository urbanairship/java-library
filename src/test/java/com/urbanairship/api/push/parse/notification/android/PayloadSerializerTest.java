package com.urbanairship.api.push.parse.notification.android;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AddTagAction;
import com.urbanairship.api.push.model.notification.actions.OpenExternalURLAction;
import com.urbanairship.api.push.model.notification.actions.RemoveTagAction;
import com.urbanairship.api.push.model.notification.actions.TagActionData;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.model.notification.android.AndroidLiveUpdate;
import com.urbanairship.api.push.model.notification.android.AndroidLiveUpdateEvent;
import com.urbanairship.api.push.model.notification.android.BigTextStyle;
import com.urbanairship.api.push.model.notification.android.Category;
import com.urbanairship.api.push.model.notification.android.PublicNotification;
import com.urbanairship.api.push.model.notification.android.Wearable;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

public class PayloadSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();


    @Test
    public void testFullAndroidPayload() throws Exception {
        PublicNotification publicNotification = PublicNotification.newBuilder()
                .setAlert("Hello!")
                .setTitle("A greeting")
                .setSummary("A summary")
                .build();

        BigTextStyle bigTextStyle = BigTextStyle.newBuilder()
                .setContent("big text")
                .setTitle("big text title")
                .setSummary("big text summary")
                .build();

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

        Wearable wearable = Wearable.newBuilder()
                .setBackgroundImage("https://yolo.pizza.biz/")
                .setInteractive(interactive)
                .addExtraPage("Title1", "An alert")
                .addExtraPage("Title2", "An alert again")
                .build();

        PushExpiry expiry = PushExpiry.newBuilder()
                .setExpirySeconds(12345)
                .build();

        URI uri = URI.create("http://www.urbanairship.com");

        OpenExternalURLAction externalURLAction = new OpenExternalURLAction(uri);

        Actions actions = Actions.newBuilder()
                .setOpen(externalURLAction)
                .build();

        AndroidDevicePayload payload = AndroidDevicePayload.newBuilder()
                .setAlert("Hi")
                .setCategory(Category.ALARM)
                .setCollapseKey("blah")
                .setNotificationChannel("channel1")
                .setNotificationTag("nt1")
                .setDelayWhileIdle(true)
                .setDeliveryPriority("high")
                .setInteractive(interactive)
                .setLocalOnly(true)
                .setPriority(1)
                .setPublicNotification(publicNotification)
                .setSound("cowbell.mp3")
                .setIcon("icon.xml")
                .setIconColor("#012345")
                .setStyle(bigTextStyle)
                .setSummary("A summary")
                .setTimeToLive(expiry)
                .setTitle("A title")
                .setVisibility(1)
                .setWearable(wearable)
                .setActions(actions)
                .build();

        String json = "{\n" +
                "  \"alert\": \"Hi\",\n" +
                "  \"collapse_key\": \"blah\",\n" +
                "  \"notification_channel\": \"channel1\",\n" +
                "  \"notification_tag\": \"nt1\",\n" +
                "  \"time_to_live\": 12345,\n" +
                "  \"delivery_priority\": \"high\",\n" +
                "  \"delay_while_idle\": true,\n" +
                "  \"interactive\": {\n" +
                "    \"type\": \"ua_yes_no_foreground\",\n" +
                "    \"button_actions\": {\n" +
                "      \"yes\": {\n" +
                "        \"add_tag\": \"butter\"\n" +
                "      },\n" +
                "      \"no\": {\n" +
                "        \"remove_tag\": \"cool\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"title\": \"A title\",\n" +
                "  \"local_only\": true,\n" +
                "  \"wearable\": {\n" +
                "    \"background_image\": \"https://yolo.pizza.biz/\",\n" +
                "    \"interactive\": {\n" +
                "      \"type\": \"ua_yes_no_foreground\",\n" +
                "      \"button_actions\": {\n" +
                "        \"yes\": {\n" +
                "          \"add_tag\": \"butter\"\n" +
                "        },\n" +
                "        \"no\": {\n" +
                "          \"remove_tag\": \"cool\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"extra_pages\": [\n" +
                "      {\n" +
                "        \"title\": \"Title1\",\n" +
                "        \"alert\": \"An alert\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"title\": \"Title2\",\n" +
                "        \"alert\": \"An alert again\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"summary\": \"A summary\",\n" +
                "  \"sound\": \"cowbell.mp3\",\n" +
                "  \"icon\": \"icon.xml\",\n" +
                "  \"icon_color\": \"#012345\",\n" +
                "  \"category\": \"alarm\",\n" +
                "  \"priority\": 1,\n" +
                "  \"style\": {\n" +
                "    \"type\": \"big_text\",\n" +
                "    \"big_text\": \"big text\",\n" +
                "    \"title\": \"big text title\",\n" +
                "    \"summary\": \"big text summary\"\n" +
                "  },\n" +
                "  \"time_to_live\": 12345,\n" +
                "  \"visibility\": 1,\n" +
                "  \"public_notification\": {\n" +
                "    \"summary\": \"A summary\",\n" +
                "    \"alert\": \"Hello!\",\n" +
                "    \"title\": \"A greeting\"\n" +
                "  },\n" +
                "  \"actions\": {\n" +
                "    \"open\": {\n" +
                "      \"type\": \"url\",\n" +
                "      \"content\": \"http://www.urbanairship.com\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        String parsedJson = MAPPER.writeValueAsString(payload);

        AndroidDevicePayload roundTripPayload = MAPPER.readValue(parsedJson, AndroidDevicePayload.class);

        JsonNode expected = MAPPER.readTree(json);
        JsonNode actual = MAPPER.readTree(parsedJson);

        assertEquals(expected, actual);
        assertEquals(payload, roundTripPayload);
    }

    @Test
    public void testLiveUpdate() throws Exception {
        AndroidLiveUpdate androidLiveUpdate = AndroidLiveUpdate.newBuilder()
                .setAndroidLiveUpdateEvent(AndroidLiveUpdateEvent.UPDATE)
                .setName("Foxes-Tigers")
                .setDismissalDate(1234)
                .setType("test")
                .setTimestamp(1234)
                .addContentState("key", "value")
                .addContentState("key2", "value2")
                .build();

        AndroidDevicePayload payload = AndroidDevicePayload.newBuilder()
                .setAndroidLiveUpdate(androidLiveUpdate)
                .build();

        String json = MAPPER.writeValueAsString(payload);

        String expected = "{\"live_update\":{\"event\":\"update\",\"name\":\"Foxes-Tigers\",\"content_state\":{\"key\":\"value\",\"key2\":\"value2\"},\"dismissal_date\":1234,\"timestamp\":1234,\"type\":\"test\"}}";

        assertEquals(expected, json);
    }
}
