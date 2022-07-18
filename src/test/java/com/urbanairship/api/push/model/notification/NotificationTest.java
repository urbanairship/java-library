package com.urbanairship.api.push.model.notification;

import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AddTagAction;
import com.urbanairship.api.push.model.notification.actions.TagActionData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NotificationTest {
    @Test
    public void testNotificationToBuilder() {
        Notification notification = Notification.newBuilder()
                .setAlert("foo")
                .setActions(Actions.newBuilder()
                        .addTags(new AddTagAction(TagActionData.single("foo")))
                        .build())
                .setInteractive(Interactive.newBuilder()
                        .setType("acme_interact")
                        .build())
                .build();
        Notification notification1 = notification.toBuilder().build();
        assertEquals(notification, notification1);
    }
}