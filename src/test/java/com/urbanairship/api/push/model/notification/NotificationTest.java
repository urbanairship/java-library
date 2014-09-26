package com.urbanairship.api.push.model.notification;

import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AddTagAction;
import com.urbanairship.api.push.model.notification.actions.TagActionData;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class NotificationTest {
    @Test
    public void testNotificationToBuilder() throws IOException {
        Notification notification = Notification.newBuilder()
                .setAlert("foo")
                .setActions(Actions.newBuilder()
                        .addTags(new AddTagAction(TagActionData.single("foo")))
                        .build())
                .build();
        Notification notification1 = notification.toBuilder().build();
        assertEquals(notification, notification1);
    }
}