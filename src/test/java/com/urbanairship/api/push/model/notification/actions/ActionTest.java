package com.urbanairship.api.push.model.notification.actions;

import com.urbanairship.api.push.model.notification.Notification;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ActionTest {
    @Test
    public void testLandingPageContentEquality() {
        LandingPageContent l1 = LandingPageContent.newBuilder()
                .setContentType("text/plain")
                .setEncoding(LandingPageContent.Encoding.UTF8)
                .setBody("A")
                .build();
        LandingPageContent l2 = LandingPageContent.newBuilder()
                .setContentType("text/plain")
                .setEncoding(LandingPageContent.Encoding.UTF8)
                .setBody("A")
                .build();
        LandingPageContent l3 = LandingPageContent.newBuilder()
                .setContentType("text/blah")
                .setEncoding(LandingPageContent.Encoding.UTF8)
                .setBody("B")
                .build();
        assertEquals(l1, l2);
        assertEquals(l2, l1);
        assertEquals(l1.hashCode(), l1.hashCode());
        assertEquals(l1.hashCode(), l2.hashCode());
        assertNotEquals(l1.hashCode(), l3.hashCode());
        assertNotEquals(l1, l3);
        assertNotEquals(l3, l1);
        assertNotEquals(l3, l2);
        assertNotEquals(LandingPageContent.newBuilder().setContentType("text/plain").setBody("A").setEncoding(LandingPageContent.Encoding.UTF8).build(), LandingPageContent.newBuilder().setContentType("text/blah").setBody("A").setEncoding(LandingPageContent.Encoding.UTF8).build());
        assertNotEquals(LandingPageContent.newBuilder().setContentType("text/plain").setBody("A").setEncoding(LandingPageContent.Encoding.UTF8).build(), LandingPageContent.newBuilder().setContentType("text/plain").setBody("B").setEncoding(LandingPageContent.Encoding.UTF8).build());
        assertNotEquals(LandingPageContent.newBuilder().setContentType("text/plain").setBody("A").setEncoding(LandingPageContent.Encoding.UTF8).build(), LandingPageContent.newBuilder().setContentType("text/plain").setBody("A").setEncoding(LandingPageContent.Encoding.Base64).build());
    }

    @Test
    public void testOpenLandingPageWithContentActionEquality() {
        OpenLandingPageWithContentAction a1 = new OpenLandingPageWithContentAction(LandingPageContent.newBuilder()
                .setContentType("text/plain")
                .setBody("A")
                .setEncoding(LandingPageContent.Encoding.UTF8).build());
        OpenLandingPageWithContentAction a2 = new OpenLandingPageWithContentAction(LandingPageContent.newBuilder()
                .setContentType("text/plain")
                .setBody("A")
                .setEncoding(LandingPageContent.Encoding.UTF8).build());
        OpenLandingPageWithContentAction a3 = new OpenLandingPageWithContentAction(LandingPageContent.newBuilder()
                .setContentType("text/plain")
                .setBody("B")
                .setEncoding(LandingPageContent.Encoding.UTF8).build());
        assertEquals(a1, a1);
        assertEquals(a1, a2);
        assertEquals(a2, a1);
        assertEquals(a1.hashCode(), a1.hashCode());
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1, a3);
        assertNotEquals(a3, a1);
    }

    @Test
    public void testActionsEquality() {
        Actions a1 = Actions.newBuilder()
                .setOpen(new OpenLandingPageWithContentAction(LandingPageContent.newBuilder()
                        .setContentType("text/plain")
                        .setBody("A")
                        .setEncoding(LandingPageContent.Encoding.UTF8).build()))
                .build();
        Actions a2 = Actions.newBuilder()
                .setOpen(new OpenLandingPageWithContentAction(LandingPageContent.newBuilder()
                        .setContentType("text/plain")
                        .setBody("A")
                        .setEncoding(LandingPageContent.Encoding.UTF8).build()))
                .build();
        Actions a3 = Actions.newBuilder()
                .setOpen(new OpenLandingPageWithContentAction(LandingPageContent.newBuilder()
                        .setContentType("text/plain")
                        .setBody("B")
                        .setEncoding(LandingPageContent.Encoding.UTF8).build()))
                .build();
        assertEquals(a1, a1);
        assertEquals(a1, a2);
        assertEquals(a2, a1);
        assertEquals(a1.hashCode(), a1.hashCode());
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1, a3);
        assertNotEquals(a3, a1);
    }

    @Test
    public void testNotificationWithActionsEquality() {
        Notification n1 = Notification.newBuilder()
                .setAlert("alert")
                .setActions(Actions.newBuilder()
                        .setOpen(new OpenLandingPageWithContentAction(LandingPageContent.newBuilder()
                                .setContentType("text/plain")
                                .setBody("A")
                                .setEncoding(LandingPageContent.Encoding.UTF8).build()))
                        .build())
                .build();
        Notification n2 = Notification.newBuilder()
                .setAlert("alert")
                .setActions(Actions.newBuilder()
                        .setOpen(new OpenLandingPageWithContentAction(LandingPageContent.newBuilder()
                                .setContentType("text/plain")
                                .setBody("A")
                                .setEncoding(LandingPageContent.Encoding.UTF8).build()))
                        .build())
                .build();
        Notification n3 = Notification.newBuilder()
                .setAlert("alert")
                .setActions(Actions.newBuilder()
                        .setOpen(new OpenLandingPageWithContentAction(LandingPageContent.newBuilder()
                                .setContentType("text/plain")
                                .setBody("B")
                                .setEncoding(LandingPageContent.Encoding.UTF8).build()))
                        .build())
                .build();

        assertEquals(n1, n1);
        assertEquals(n1, n2);
        assertEquals(n2, n1);
        assertEquals(n1.hashCode(), n1.hashCode());
        assertEquals(n1.hashCode(), n2.hashCode());
        assertNotEquals(n1.hashCode(), n3.hashCode());
        assertNotEquals(n1, n3);
        assertNotEquals(n3, n1);
    }
}
