package com.urbanairship.api.push.model.notification.android;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PublicNotificationTest {

    @Test(expected = Exception.class)
    public void testEmptyPublicNotification() {
        PublicNotification.newBuilder().build();
    }

    @Test
    public void testPublicNotification() {
        PublicNotification publicNotificationMinimal = PublicNotification.newBuilder()
                .setAlert("Hi!")
                .build();

        PublicNotification publicNotificationMaximal = PublicNotification.newBuilder()
                .setAlert("Hi!")
                .setSummary("A summary")
                .setTitle("A title")
                .build();

        assertNotNull(publicNotificationMinimal);
        assertEquals(publicNotificationMinimal.getAlert().get(), "Hi!");

        assertNotNull(publicNotificationMaximal);
        assertEquals(publicNotificationMaximal.getAlert().get(), "Hi!");
        assertEquals(publicNotificationMaximal.getSummary().get(), "A summary");
        assertEquals(publicNotificationMaximal.getTitle().get(), "A title");
    }
}
