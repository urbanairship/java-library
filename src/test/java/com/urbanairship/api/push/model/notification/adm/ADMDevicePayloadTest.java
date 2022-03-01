package com.urbanairship.api.push.model.notification.adm;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.ShareAction;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ADMDevicePayloadTest {

    @Test
    public void testBuildNotificationMinimal() {
        ADMDevicePayload payload = ADMDevicePayload.newBuilder()
            .setAlert("hello")
            .build();
        assertTrue(payload.getAlert().isPresent());
        assertEquals("hello", payload.getAlert().get());
    }

    @Test
    public void testBuildNotificationInteractive() throws IOException {
        Interactive interactive = Interactive.newBuilder()
            .setType("ua_yes_no_foreground")
            .setButtonActions(
                ImmutableMap.of(
                    "yes",
                    Actions.newBuilder()
                        .setShare(new ShareAction("foo"))
                        .build()))
            .build();
        ADMDevicePayload payload = ADMDevicePayload.newBuilder()
            .setAlert("hello")
            .setInteractive(interactive)
            .build();
        assertTrue(payload.getAlert().isPresent());
        assertTrue(payload.getInteractive().isPresent());
        assertEquals("ua_yes_no_foreground", payload.getInteractive().get().getType());
        assertTrue(payload.getInteractive().get().getButtonActions().containsKey("yes"));
        assertTrue(payload.getInteractive().get().getButtonActions().containsValue(Actions.newBuilder()
            .setShare(new ShareAction("foo"))
            .build()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectTemplateConfiguration() {
        ADMFields admFields = ADMFields.newBuilder()
                .setAlert("alert field")
                .build();

        ADMTemplate.newBuilder()
                .setTemplateId("templateId")
                .setFields(admFields)
                .build();

    }
}
