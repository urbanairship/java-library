package com.urbanairship.api.push.parse.notification;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AddTagAction;
import com.urbanairship.api.push.model.notification.actions.LandingPageContent;
import com.urbanairship.api.push.model.notification.actions.OpenLandingPageWithContentAction;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

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
    public void testDeserializeCustom() throws Exception {
        String json
            = "{"
            + "  \"type\" : \"acme_this_that\","
            + "  \"button_actions\" : {"
            + "    \"this\" : {"
            + "      \"add_tag\" : \"foo\""
            + "    },"
            + "    \"that\" : {"
            + "      \"add_tag\" : \"bar\""
            + "    }"
            + "  }"
            + "}";

        Interactive interactive = mapper.readValue(json, Interactive.class);
        assertEquals("acme_this_that", interactive.getType());
        ImmutableMap<String, Actions> actionsMap = interactive.getButtonActions();
        assertEquals(2, actionsMap.size());

        Actions thisActions = actionsMap.get("this");
        Optional<AddTagAction> thisAddTagAction = thisActions.getAddTags();
        assertTrue(thisAddTagAction.isPresent());
        assertTrue(thisAddTagAction.get().getValue().isSingle());
        assertEquals("foo", thisAddTagAction.get().getValue().getSingleTag());

        Actions thatActions = actionsMap.get("that");
        Optional<AddTagAction> thatAddTagAction = thatActions.getAddTags();
        assertTrue(thatAddTagAction.isPresent());
        assertTrue(thatAddTagAction.get().getValue().isSingle());
        assertEquals("bar", thatAddTagAction.get().getValue().getSingleTag());
    }

    @Test
    public void testDeserializeSubsetOfButtons() throws Exception {
        String json
            = "{"
            + "  \"type\" : \"ua_yes_no_foreground\","
            + "  \"button_actions\" : {"
            + "    \"yes\" : {"
            + "      \"add_tag\" : \"foo\""
            + "    }"
            + "  }"
            + "}";

        mapper.readValue(json, Interactive.class);
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
    public void testDeserializeWrongButtons() throws Exception {
        String json
            = "{"
            + "  \"type\" : \"ua_yes_no_foreground\","
            + "  \"button_actions\" : {"
            + "    \"yes\" : {"
            + "      \"add_tag\" : \"foo\""
            + "    },"
            + "    \"wrong_button_id\" : {"
            + "      \"add_tag\" : \"bar\""
            + "    }"
            + "  }"
            + "}";

        try {
            mapper.readValue(json, Interactive.class);
            fail("PushPayloadException expected.");
        } catch (APIParsingException exc) {
//            assertEquals(PushPayloadError.INT_WRONG_BUTTON_IDS.subCode, exc.getErrorCode() - 40000);
        }
    }

    @Test
    public void testDeserializeExtraButtons() throws Exception {
        String json
            = "{"
            + "  \"type\" : \"ua_yes_no_foreground\","
            + "  \"button_actions\" : {"
            + "    \"yes\" : {"
            + "      \"add_tag\" : \"foo\""
            + "    },"
            + "    \"no\" : {"
            + "      \"add_tag\" : \"qux\""
            + "    },"
            + "    \"wrong_button_id\" : {"
            + "      \"add_tag\" : \"bar\""
            + "    }"
            + "  }"
            + "}";

        try {
            mapper.readValue(json, Interactive.class);
            fail("PushPayloadException expected.");
        } catch (APIParsingException exc) {
//            assertEquals(PushPayloadError.INT_WRONG_BUTTON_IDS.subCode, exc.getErrorCode() - 40000);
        }
    }

    @Test
    public void testDeserializeUnknownPredefined() throws Exception {
        String json
            = "{"
            + "  \"type\" : \"ua_what_me_worry\","
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
            fail("PushPayloadException expected.");
        } catch (APIParsingException exc) {
//            assertEquals(PushPayloadError.INT_MISSING_PREDEFINED.subCode, exc.getErrorCode() - 40000);
        }
    }

    @Test
    public void testDeserializeUnknownPredefinedNoAction() throws Exception {
        String json
            = "{"
            + "  \"type\" : \"ua_what_me_worry\""
            + "}";

        try {
            mapper.readValue(json, Interactive.class);
            fail("PushPayloadException expected.");
        } catch (APIParsingException exc) {
//            assertEquals(PushPayloadError.INT_MISSING_PREDEFINED.subCode, exc.getErrorCode() - 40000);
        }
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
            fail("PushPayloadException expected.");
        } catch (APIParsingException exc) {
//            assertEquals(PushPayloadError.INT_MISSING_FIELD.subCode, exc.getErrorCode() - 40000);
        }
    }

    @Test
    public void testDeserializeEmptyObject() throws Exception {
        String json
            = "{"
            + "}";

        try {
            mapper.readValue(json, Interactive.class);
            fail("PushPayloadException expected.");
        } catch (APIParsingException exc) {
//            assertEquals(PushPayloadError.INT_MISSING_FIELD.subCode, exc.getErrorCode() - 40000);
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

        String json2
            = "{"
            + "  \"type\" : \"some_custom_type\""
            + "}";

        // should be OK for custom types, too
        mapper.readValue(json2, Interactive.class);
    }

    @Test
    public void testDeserializeExtraField() throws Exception {
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
            + "  },"
            + "  \"extra\" : \"blar\""
            + "}";
        try {
            mapper.readValue(json, Interactive.class);
            fail("APIParsingException expected.");
        } catch (APIParsingException exc) {
            assertEquals("The key 'extra' is not allowed in this context", exc.getMessage());
        }
    }

    @Test
    public void testDeserializeCustomNoButtonActions() throws Exception {
        String json
            = "{"
            + "  \"type\" : \"acme_interactive\","
            + "  \"button_actions\" : {"
            + "  }"
            + "}";
        mapper.readValue(json, Interactive.class);
    }

    @Test
    public void testDeserializeCustomTooManyButtonActions() throws Exception {
        String json
            = "{"
            + "  \"type\" : \"acme_interactive\","
            + "  \"button_actions\" : {"
            + "    \"yes\" : {"
            + "      \"add_tag\" : \"foo\""
            + "    },"
            + "    \"no\" : {"
            + "      \"add_tag\" : \"bar\""
            + "    },"
            + "    \"maybe\" : {"
            + "      \"add_tag\" : \"wut\""
            + "    },"
            + "    \"perhaps\" : {"
            + "      \"add_tag\" : \"why\""
            + "    },"
            + "    \"vague\" : {"
            + "      \"add_tag\" : \"nope\""
            + "    },"
            + "    \"never\" : {"
            + "      \"add_tag\" : \"stop\""
            + "    }"
            + "  }"
            + "}";
        try {
            mapper.readValue(json, Interactive.class);
            fail("PushPayloadException expected.");
        } catch (APIParsingException exc) {
//            assertEquals(PushPayloadError.INT_TOO_MANY_BUTTON_IDS.subCode, exc.getErrorCode() - 40000);
        }
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

