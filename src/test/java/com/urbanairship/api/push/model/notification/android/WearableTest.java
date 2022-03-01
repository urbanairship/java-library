package com.urbanairship.api.push.model.notification.android;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AddTagAction;
import com.urbanairship.api.push.model.notification.actions.RemoveTagAction;
import com.urbanairship.api.push.model.notification.actions.TagActionData;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
public class WearableTest {
    @Test(expected = Exception.class)
    public void testEmptyWearable() {
        Wearable.newBuilder().build();
    }

    @Test
    public void testWearable() throws Exception {
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

        Assert.assertNotNull(wearable);
        Assert.assertEquals(wearable.getBackgroundImage().get(), "https://yolo.pizza.biz/");

        List<ImmutableMap<String,String>> extraPages = wearable.getExtraPages().get();
        Assert.assertNotNull(extraPages);
        Assert.assertEquals(extraPages.get(0).get("title"), "Title1");
        Assert.assertEquals(extraPages.get(0).get("alert"), "An alert");
        Assert.assertEquals(extraPages.get(1).get("title"), "Title2");
        Assert.assertEquals(extraPages.get(1).get("alert"), "An alert again");

        Interactive interactivePayload = wearable.getInteractive().get();
        Assert.assertNotNull(interactivePayload);
        Assert.assertEquals(interactivePayload.getType(), "ua_yes_no_foreground");
        Assert.assertEquals(interactivePayload.getButtonActions().get("yes").getAddTags().get().getValue().getSingleTag(), "butter");
        Assert.assertEquals(interactivePayload.getButtonActions().get("no").getRemoveTags().get().getValue().getSingleTag(), "cool");
    }
}
