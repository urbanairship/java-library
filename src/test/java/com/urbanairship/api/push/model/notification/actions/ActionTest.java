package com.urbanairship.api.push.model.notification.actions;

import com.urbanairship.api.push.model.notification.Notification;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
        assertFalse(l1.hashCode() == l3.hashCode());
        assertFalse(l1.equals(l3));
        assertFalse(l3.equals(l1));
        assertFalse(l3.equals(l2));
        assertFalse(LandingPageContent.newBuilder().setContentType("text/plain").setBody("A").setEncoding(LandingPageContent.Encoding.UTF8).build()
                .equals(LandingPageContent.newBuilder().setContentType("text/blah").setBody("A").setEncoding(LandingPageContent.Encoding.UTF8).build()));
        assertFalse(LandingPageContent.newBuilder().setContentType("text/plain").setBody("A").setEncoding(LandingPageContent.Encoding.UTF8).build()
                .equals(LandingPageContent.newBuilder().setContentType("text/plain").setBody("B").setEncoding(LandingPageContent.Encoding.UTF8).build()));
        assertFalse(LandingPageContent.newBuilder().setContentType("text/plain").setBody("A").setEncoding(LandingPageContent.Encoding.UTF8).build()
                .equals(LandingPageContent.newBuilder().setContentType("text/plain").setBody("A").setEncoding(LandingPageContent.Encoding.Base64).build()));
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
        assertFalse(a1.hashCode() == a3.hashCode());
        assertFalse(a1.equals(a3));
        assertFalse(a3.equals(a1));
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
        assertFalse(a1.hashCode() == a3.hashCode());
        assertFalse(a1.equals(a3));
        assertFalse(a3.equals(a1));
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
        assertFalse(n1.hashCode() == n3.hashCode());
        assertFalse(n1.equals(n3));
        assertFalse(n3.equals(n1));
    }
}
