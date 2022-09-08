package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.android.Wearable;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public class WearableDeserializerTest {
    private final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testWearable() throws Exception {
        String wearableJson =
                "{" +
                    "\"background_image\": \"https://yolo.pizza.biz/\"," +
                    "\"extra_pages\": [" +
                        "{" +
                            "\"title\": \"Title1\"," +
                            "\"alert\": \"An alert\"" +
                        "}," +
                        "{" +
                            "\"title\": \"Title2\"," +
                            "\"alert\": \"An alert again\"" +
                        "}" +
                    "]," +
                    "\"interactive\": {" +
                        "\"type\": \"ua_yes_no_foreground\"," +
                        "\"button_actions\": {" +
                            "\"yes\": {" +
                                "\"add_tag\": \"butter\"," +
                                "\"remove_tag\": \"cake\"" +
                            "}," +
                            "\"no\": {" +
                                "\"remove_tag\": \"cool\"" +
                            "}" +
                        "}" +
                    "}" +
                "}";

        Wearable wearable = mapper.readValue(wearableJson, Wearable.class);
        Assert.assertNotNull(wearable);
        Assert.assertEquals(wearable.getBackgroundImage().get(), "https://yolo.pizza.biz/");

        ImmutableList<ImmutableMap<String,String>> extraPages = wearable.getExtraPages().get();
        Assert.assertNotNull(extraPages);
        Assert.assertEquals(extraPages.get(0).get("title"), "Title1");
        Assert.assertEquals(extraPages.get(0).get("alert"), "An alert");
        Assert.assertEquals(extraPages.get(1).get("title"), "Title2");
        Assert.assertEquals(extraPages.get(1).get("alert"), "An alert again");

        Interactive interactive = wearable.getInteractive().get();
        Assert.assertNotNull(interactive);
        Assert.assertEquals(interactive.getType(), "ua_yes_no_foreground");
        Assert.assertEquals(Objects.requireNonNull(interactive.getButtonActions().get("yes")).getAddTags().get().getValue().getSingleTag(), "butter");
        Assert.assertEquals(Objects.requireNonNull(interactive.getButtonActions().get("yes")).getRemoveTags().get().getValue().getSingleTag(), "cake");
        Assert.assertEquals(Objects.requireNonNull(interactive.getButtonActions().get("no")).getRemoveTags().get().getValue().getSingleTag(), "cool");
    }
}