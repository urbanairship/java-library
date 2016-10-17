package com.urbanairship.api.push.parse.notification.android;


import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AddTagAction;
import com.urbanairship.api.push.model.notification.actions.RemoveTagAction;
import com.urbanairship.api.push.model.notification.actions.TagActionData;
import com.urbanairship.api.push.model.notification.android.Wearable;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WearableSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testWearableSerializer() throws Exception {
        String json =
                "{" +
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
                "}";

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

        String parsedJson = MAPPER.writeValueAsString(wearable);
        assertEquals(json,parsedJson);
    }
}
