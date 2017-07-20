package com.urbanairship.api.push.parse.notification.android;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AddTagAction;
import com.urbanairship.api.push.model.notification.actions.RemoveTagAction;
import com.urbanairship.api.push.model.notification.actions.TagActionData;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.model.notification.android.BigTextStyle;
import com.urbanairship.api.push.model.notification.android.Category;
import com.urbanairship.api.push.model.notification.android.PublicNotification;
import com.urbanairship.api.push.model.notification.android.Style;
import com.urbanairship.api.push.model.notification.android.Wearable;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

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

        Style bigTextStyle = BigTextStyle.newBuilder()
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

        AndroidDevicePayload payload = AndroidDevicePayload.newBuilder()
                .setAlert("Hi")
                .setCategory(Category.ALARM)
                .setCollapseKey("blah")
                .setDelayWhileIdle(true)
                .setDeliveryPriority("high")
                .setInteractive(interactive)
                .setLocalOnly(true)
                .setPriority(1)
                .setPublicNotification(publicNotification)
                .setSound("cowbell.mp3")
                .setStyle(bigTextStyle)
                .setSummary("A summary")
                .setTimeToLive(expiry)
                .setTitle("A title")
                .setVisibility(1)
                .setWearable(wearable) 
                .build();

        String json = "{" +
                "\"alert\":\"Hi\"," +
                "\"collapse_key\":\"blah\"," +
                "\"time_to_live\":12345," +
                "\"delivery_priority\":\"high\"," +
                "\"delay_while_idle\":true," +
                "\"interactive\":{" +
                    "\"type\":\"ua_yes_no_foreground\"," +
                    "\"button_actions\":{" +
                        "\"yes\":{" +
                            "\"add_tag\":\"butter\"" +
                         "}," +
                         "\"no\":{" +
                            "\"remove_tag\":\"cool\"" +
                        "}" +
                    "}" +
                "}," +
                "\"title\":\"A title\"," +
                "\"local_only\":true," +
                "\"wearable\":{" +
                    "\"background_image\":\"https://yolo.pizza.biz/\"," +
                    "\"interactive\":{" +
                        "\"type\":\"ua_yes_no_foreground\"," +
                        "\"button_actions\":{" +
                            "\"yes\":{" +
                               "\"add_tag\":\"butter\"" +
                            "}," +
                            "\"no\":{" +
                               "\"remove_tag\":\"cool\"" +
                            "}" +
                         "}" +
                     "}," +
                     "\"extra_pages\":[" +
                         "{" +
                            "\"title\":\"Title1\"," +
                            "\"alert\":\"An alert\"" +
                         "}," +
                         "{" +
                            "\"title\":\"Title2\"," +
                            "\"alert\":\"An alert again\"" +
                         "}" +
                     "]" +
                "}," +
                "\"summary\":\"A summary\"," +
                "\"sound\":\"cowbell.mp3\"," +
                "\"category\":\"alarm\"," +
                "\"priority\":1," +
                "\"style\":{" +
                    "\"type\":\"big_text\"," +
                    "\"big_text\":\"big text\"," +
                    "\"title\":\"big text title\"," +
                    "\"summary\":\"big text summary\"" +
                "}," +
                "\"time_to_live\":12345," +
                "\"visibility\":1" +
        "}";
        String parsedJson = MAPPER.writeValueAsString(payload);

        JsonNode parsedJsonNode = MAPPER.readTree(parsedJson);
        JsonNode jsonNode = MAPPER.readTree(json);
        assertEquals(parsedJsonNode, jsonNode);
    }
}
