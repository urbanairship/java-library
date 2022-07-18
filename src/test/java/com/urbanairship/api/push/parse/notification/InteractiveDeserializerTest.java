package com.urbanairship.api.push.parse.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AddTagAction;
import com.urbanairship.api.push.model.notification.actions.LandingPageContent;
import com.urbanairship.api.push.model.notification.actions.OpenLandingPageWithContentAction;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class InteractiveDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testDeserialize() throws Exception {
        String json
            = "{"
            + "  \"type\" : \"ua_yes_no_foreground\","
            + "  \"button_actions\" : {"
            + "    \"yes\" : {"
            + "      \"add_tag\" : \"foo\""
            + "    },"
            + "    \"no\" : {"
            + "      \"add_tag\" : \"bar\""
            + "    }"
            + "  }"
            + "}";

        Interactive interactive = mapper.readValue(json, Interactive.class);
        assertEquals("ua_yes_no_foreground", interactive.getType());
        ImmutableMap<String, Actions> actionsMap = interactive.getButtonActions();
        assertEquals(2, actionsMap.size());

        Actions yesActions = actionsMap.get("yes");
        Optional<AddTagAction> yesAddTagAction = yesActions.getAddTags();
        assertTrue(yesAddTagAction.isPresent());
        assertTrue(yesAddTagAction.get().getValue().isSingle());
        assertEquals("foo", yesAddTagAction.get().getValue().getSingleTag());

        Actions noActions = actionsMap.get("no");
        Optional<AddTagAction> noAddTagAction = noActions.getAddTags();
        assertTrue(noAddTagAction.isPresent());
        assertTrue(noAddTagAction.get().getValue().isSingle());
        assertEquals("bar", noAddTagAction.get().getValue().getSingleTag());
    }

    @Test
    public void testDeserializeEmptyButtonActions() throws Exception {
        String json
            = "{"
            + "  \"type\" : \"ua_yes_no_foreground\","
            + "  \"button_actions\" : {"
            + "  }"
            + "}";

        mapper.readValue(json, Interactive.class);
    }

    @Test
    public void testDeserializeMissingType() throws Exception {
        String json
            = "{"
            + "  \"button_actions\" : {"
            + "    \"yes\" : {"
            + "      \"add_tag\" : \"foo\""
            + "    },"
            + "    \"no\" : {"
            + "      \"add_tag\" : \"bar\""
            + "    }"
            + "  }"
            + "}";

        try {
            mapper.readValue(json, Interactive.class);
            fail("APIParsingException expected.");
        } catch (APIParsingException exc) {
            assertEquals("interactive payload requires a 'type' field", exc.getMessage());
        }
    }

    @Test
    public void testDeserializeEmptyObject() throws Exception {
        String json
            = "{"
            + "}";

        try {
            mapper.readValue(json, Interactive.class);
            fail("APIParsingException expected.");
        } catch (APIParsingException exc) {
            assertEquals("interactive payload requires a 'type' field", exc.getMessage());
        }
    }

    @Test
    public void testDeserializeMissingButtonActions() throws Exception {
        String json
            = "{"
            + "  \"type\" : \"ua_yes_no_foreground\""
            + "}";

        // "button_actions" is optional, so this is valid
        mapper.readValue(json, Interactive.class);
    }

    @Test
    public void testDeserializeLandingPage() throws IOException {
        String json
            = "{"
            + "  \"type\" : \"acme_interactive\","
            + "  \"button_actions\" : {"
            + "    \"yes\" : {"
            + "      \"open\": {\n"
            + "        \"type\": \"landing_page\","
            + "        \"content\": {\n"
            + "          \"body\": \"<html>Landing Page.</html>\","
            + "          \"content_type\": \"text/html\","
            + "          \"content_encoding\": \"utf-8\""
            + "        }"
            + "      }"
            + "    }"
            + "  }"
            + "}";
        Interactive interactive = mapper.readValue(json, Interactive.class);
        assertNotNull(interactive.getButtonActions().get("yes"));
        Actions actions = interactive.getButtonActions().get("yes");

        Actions expectedActions = Actions.newBuilder()
            .setOpen(
                new OpenLandingPageWithContentAction(
                    LandingPageContent.newBuilder()
                        .setBody("<html>Landing Page.</html>")
                        .setContentType("text/html")
                        .setEncoding(LandingPageContent.Encoding.UTF8)
                        .build()))
            .build();
        assertEquals(expectedActions, actions);
    }
}

