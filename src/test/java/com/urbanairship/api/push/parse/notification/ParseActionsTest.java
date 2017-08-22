package com.urbanairship.api.push.parse.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.BaseEncoding;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.actions.Action;
import com.urbanairship.api.push.model.notification.actions.ActionType;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AddTagAction;
import com.urbanairship.api.push.model.notification.actions.AppDefinedAction;
import com.urbanairship.api.push.model.notification.actions.DeepLinkAction;
import com.urbanairship.api.push.model.notification.actions.LandingPageContent;
import com.urbanairship.api.push.model.notification.actions.OpenExternalURLAction;
import com.urbanairship.api.push.model.notification.actions.OpenLandingPageWithContentAction;
import com.urbanairship.api.push.model.notification.actions.RemoveTagAction;
import com.urbanairship.api.push.model.notification.actions.ShareAction;
import com.urbanairship.api.push.model.notification.actions.TagActionData;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ParseActionsTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = PushObjectMapper.getInstance();
    }

    @Test
    public void testAddTags() throws Exception {
        ArrayList<String> tags = Lists.newArrayList("foo", "bar", "baz");

        AddTagAction addTags = new AddTagAction(TagActionData.set(Sets.newHashSet(tags)));
        String content = mapper.writeValueAsString(Actions.newBuilder().addTags(addTags).build());
        Actions actions = mapper.readValue(content, Actions.class);

        assertNotNull("Could not round-trip add_tag action", actions);
        Optional<AddTagAction> optAddTags = getAction(actions, ActionType.ADD_TAG, AddTagAction.class);
        assertTrue("add_tag should be present", optAddTags.isPresent());
        TagActionData tagData = optAddTags.get().getValue();
        assertFalse("Multiple tags should be present", tagData.isSingle());
        Set<String> actualTags = tagData.getTagSet();
        assertEquals("Did not get 3 tags as expected", tags.size(), actualTags.size());

        for (String tag : tags) {
            assertTrue("Tag " + tag + " not found in round-trip data", actualTags.contains(tag));
        }

        for (String tag : actualTags) {
            assertTrue("Tag " + tag + " not found in round-trip data", tags.contains(tag));
        }
    }

    private <A extends Action> Optional<A> getAction(Actions actions, ActionType type, Class<A> klass) {
        for (Action a : actions.allActions()) {
            if (a.getActionType() == type && a.getClass() == klass) {
                return Optional.of((A) a);
            }
        }

        return Optional.absent();

    }

    @Test
    public void testRemoveTags() throws Exception {
        ArrayList<String> tags = Lists.newArrayList("foo", "bar", "baz");

        RemoveTagAction addTags = new RemoveTagAction(TagActionData.set(Sets.newHashSet(tags)));
        String content = mapper.writeValueAsString(Actions.newBuilder().removeTags(addTags).build());
        Actions actions = mapper.readValue(content, Actions.class);

        assertNotNull("Could not round-trip remove_tag action", actions);
        Optional<RemoveTagAction> optRemoveTags = getAction(actions, ActionType.REMOVE_TAG, RemoveTagAction.class);
        assertTrue("remove_tag should be present", optRemoveTags.isPresent());
        TagActionData tagData = optRemoveTags.get().getValue();
        assertFalse("Multiple tags should be present", tagData.isSingle());
        Set<String> actualTags = tagData.getTagSet();
        assertEquals("Did not get 3 tags as expected", tags.size(), actualTags.size());

        for (String tag : tags) {
            assertTrue("Tag " + tag + " not found in round-trip data", actualTags.contains(tag));
        }

        for (String tag : actualTags) {
            assertTrue("Tag " + tag + " not found in round-trip data", tags.contains(tag));
        }
    }

    @Test
    public void testAddTag() throws Exception {
        String tag = "foo";

        AddTagAction addTag = new AddTagAction(TagActionData.single(tag));
        Actions actions = mapper.readValue(mapper.writeValueAsString(Actions.newBuilder().addTags(addTag).build()), Actions.class);

        assertNotNull("Could not round-trip add_tag action", actions);
        Optional<AddTagAction> optAddTags = getAction(actions, ActionType.ADD_TAG, AddTagAction.class);
        assertTrue("add_tag should be present", optAddTags.isPresent());
        TagActionData tagData = optAddTags.get().getValue();
        assertTrue("Only a single tag should be present.", tagData.isSingle());
        String actualTag = tagData.getSingleTag();
        assertEquals("Did not get expected tag.", tag, actualTag);
    }

    @Test
    public void testRemoveTag() throws Exception {
        String tag = "foo";

        RemoveTagAction addTag = new RemoveTagAction(TagActionData.single(tag));
        Actions actions = mapper.readValue(mapper.writeValueAsString(Actions.newBuilder().removeTags(addTag).build()), Actions.class);

        assertNotNull("Could not round-trip remove_tag action", actions);
        Optional<RemoveTagAction> optRemoveTags = getAction(actions, ActionType.REMOVE_TAG, RemoveTagAction.class);
        assertTrue("remove_tag should be present", optRemoveTags.isPresent());
        TagActionData tagData = optRemoveTags.get().getValue();
        assertTrue("Only a single tag should be present.", tagData.isSingle());
        String actualTag = tagData.getSingleTag();
        assertEquals("Did not get expected tag.", tag, actualTag);
    }

    @Test
    public void testOpenExternal() throws Exception {
        URI url = URI.create("http://foo.com");
        OpenExternalURLAction external = new OpenExternalURLAction(url);
        String action = mapper.writeValueAsString(Actions.newBuilder()
                .setOpen(external)
                .build());
        Actions actions = mapper.readValue(action, Actions.class);
        assertNotNull("Could not round-trip open external URL action.", actions);
        Optional<OpenExternalURLAction> optOpen = getAction(actions, ActionType.OPEN_EXTERNAL_URL, OpenExternalURLAction.class);
        assertTrue("External url open action should be present.", optOpen.isPresent());
        assertEquals("URL not round-tripped as expected.", url, optOpen.get().getValue());
    }

    @Test
    public void testOpenExternalHTTPS() throws IOException {
        URI url = URI.create("https://foo.com");
        OpenExternalURLAction external = new OpenExternalURLAction(url);
        String action = mapper.writeValueAsString(Actions.newBuilder()
                .setOpen(external)
                .build());
        Actions actions = mapper.readValue(action, Actions.class);
        assertNotNull("Could not round-trip open external URL action.", actions);
        Optional<OpenExternalURLAction> optOpen = getAction(actions, ActionType.OPEN_EXTERNAL_URL, OpenExternalURLAction.class);
        assertTrue("External url open action should be present.", optOpen.isPresent());
        assertEquals("URL not round-tripped as expected.", url, optOpen.get().getValue());
    }

    @Test
    public void testBadOpenExternalWrongScheme() throws IOException {
        URI u = URI.create("ftp://foo.com");
        try {
            OpenExternalURLAction external = new OpenExternalURLAction(u);
            String content = mapper.writeValueAsString(Actions.newBuilder()
                    .setOpen(external)
                    .build());
            mapper.readValue(content, Actions.class);
            fail("URL should have caused exception: " + u);
        } catch (APIParsingException e) {
            assertEquals("The url for a url action must use either 'http' or 'https'", e.getMessage());
        }
    }

    @Test
    public void testBadOpenExternalAbsolutePathNoScheme() throws java.io.IOException {
        URI u = URI.create("/foo.com");
        try {
            OpenExternalURLAction external = new OpenExternalURLAction(u);
            mapper.readValue(mapper.writeValueAsString(Actions.newBuilder()
                    .setOpen(external)
                    .build()), Actions.class);
            fail("URL should have caused exception: " + u);
        } catch (APIParsingException e) {
            assertEquals("The url for a url action must use either 'http' or 'https'", e.getMessage());
        }
    }

    @Test
    public void testBadOpenExternalNoScheme() throws java.io.IOException {
        URI u = URI.create("foo.com");
        try {
            OpenExternalURLAction external = new OpenExternalURLAction(u);
            mapper.readValue(mapper.writeValueAsString(Actions.newBuilder()
                    .setOpen(external)
                    .build()), Actions.class);
            fail("URL should have caused exception: " + u);
        } catch (APIParsingException e) {
            assertEquals("The url for a url action must use either 'http' or 'https'", e.getMessage());
        }
    }

    @Test
    public void testOpenLandingPage() throws Exception {

        {
            LandingPageContent expected = LandingPageContent.newBuilder()
                    .setBody("⛸")
                    .setContentType("text/html")
                    .setEncoding(Optional.of(LandingPageContent.Encoding.UTF8))
                    .build();
            OpenLandingPageWithContentAction reference = new OpenLandingPageWithContentAction(expected);
            Actions actions = mapper.readValue(mapper.writeValueAsString(Actions.newBuilder()
                    .setOpen(reference)
                    .build()), Actions.class);
            assertNotNull("Could not round-trip open landing action.", actions);
            Optional<OpenLandingPageWithContentAction> optOpen = getAction(actions, ActionType.OPEN_LANDING_PAGE_WITH_CONTENT, OpenLandingPageWithContentAction.class);
            assertTrue("Open landing page action should be present.", optOpen.isPresent());
            LandingPageContent actual = optOpen.get().getValue();
            assertEquals("CustomEventBody of content not round-tripped.", expected.getBody(), actual.getBody());
            assertEquals("Content-type not round-tripped.", expected.getContentType(), actual.getContentType());
            assertEquals("Content-encoding not round-tripped.", expected.getEncoding().get(), actual.getEncoding().get());
        }

        try {
            LandingPageContent expected = LandingPageContent.newBuilder()
                    .setBody("⛸")
                    .setContentType("text/html")
                    .setEncoding(Optional.of(LandingPageContent.Encoding.Base64))
                    .build();
            OpenLandingPageWithContentAction reference = new OpenLandingPageWithContentAction(expected);
            mapper.readValue(mapper.writeValueAsString(Actions.newBuilder()
                    .setOpen(reference)
                    .build()), Actions.class);
            fail("Invalid Base64 content should have raised an exception.");
        } catch (APIParsingException ex) {
            assertEquals("Content contains invalid data that is not valid for base64 encoding.", ex.getMessage());
        }
    }

    private void openLandingPageContentType(String contentTypeHeader, boolean good) throws Exception {
        try {
            LandingPageContent expected = LandingPageContent.newBuilder()
                    .setBody("content")
                    .setContentType(contentTypeHeader)
                    .setEncoding(Optional.of(LandingPageContent.Encoding.UTF8))
                    .build();
            OpenLandingPageWithContentAction reference = new OpenLandingPageWithContentAction(expected);
            mapper.readValue(mapper.writeValueAsString(Actions.newBuilder()
                    .setOpen(reference)
                    .build()), Actions.class);
            if (!good) {
                fail("Bad content type should have raised an exception for: " + contentTypeHeader);
            }
        } catch (APIParsingException ex) {
            if (good) {
                fail("Valid content type should not have raised an exception for: " + contentTypeHeader);
            } else {
                assertNotNull(ex);
            }
        }
    }

    @Test
    public void testOpenLandingPageContentTypeValidation() throws Exception {
        ImmutableList<String> validContentTypeHeaders = ImmutableList.of(
                "text/html",
                "Text/HTML",
                "Text/HTML; charset=utf-8",
                "Text/HTML ; charset=iso8859-1; something=else",
                "application/x-rtf",
                "image/jpeg"
        );

        ImmutableList<String> invalidContentTypeHeaders = ImmutableList.of(
                "text/ html",
                "Text/H TML",
                "Text/XHTML; charset=utf-8",
                "Text/FooHTML; charset=iso8859-1;;;;",
                "application/x-treme",
                "imagine/jpeg",
                "undefined",
                "halp",
                ""
        );

        for (String contentTypeHeader : validContentTypeHeaders) {
            openLandingPageContentType(contentTypeHeader, true);
        }

        for (String contentTypeHeader : invalidContentTypeHeaders) {
            openLandingPageContentType(contentTypeHeader, false);
        }
    }

    private void openLandingPageBody(String body, LandingPageContent.Encoding encoding) throws Exception {
        LandingPageContent expected = LandingPageContent.newBuilder()
                .setBody(body)
                .setContentType("text/html")
                .setEncoding(Optional.of(encoding))
                .build();
        OpenLandingPageWithContentAction reference = new OpenLandingPageWithContentAction(expected);
        mapper.readValue(mapper.writeValueAsString(Actions.newBuilder()
                .setOpen(reference)
                .build()), Actions.class);
    }

    @Test
    public void testSmallBinaryBody() throws Exception {
        byte[] bodyBytes = new byte[32 * 1024];
        String bodyString = BaseEncoding.base64().encode(bodyBytes);

        openLandingPageBody(bodyString, LandingPageContent.Encoding.Base64);
    }

    @Test
    public void testLargeBinaryBody() throws Exception {
        byte[] bodyBytes = new byte[LandingPageContent.MAX_BODY_SIZE_BYTES];
        String bodyString = BaseEncoding.base64().encode(bodyBytes);
        assertEquals(LandingPageContent.MAX_BODY_SIZE_BASE64, bodyString.length());

        openLandingPageBody(bodyString, LandingPageContent.Encoding.Base64);
    }

    @Test(expected = APIParsingException.class)
    public void testTooLargeBinaryBody() throws Exception {
        byte[] bodyBytes = new byte[LandingPageContent.MAX_BODY_SIZE_BYTES + 24];
        String bodyString = BaseEncoding.base64().encode(bodyBytes);

        openLandingPageBody(bodyString, LandingPageContent.Encoding.Base64);
    }

    @Test
    public void testSmallTextBody() throws Exception {
        byte[] bodyBytes = new byte[32 * 1024];
        String bodyString = new String(bodyBytes);

        openLandingPageBody(bodyString, LandingPageContent.Encoding.UTF8);
    }

    @Test
    public void testLargeTextBody() throws Exception {
        byte[] bodyBytes = new byte[LandingPageContent.MAX_BODY_SIZE_BYTES];
        String bodyString = new String(bodyBytes);
        assertEquals(LandingPageContent.MAX_BODY_SIZE_BYTES, bodyString.length());

        openLandingPageBody(bodyString, LandingPageContent.Encoding.UTF8);
    }

    @Test(expected = APIParsingException.class)
    public void testTooLargeTextBody() throws Exception {
        byte[] bodyBytes = new byte[LandingPageContent.MAX_BODY_SIZE_BYTES + 24];
        String bodyString = new String(bodyBytes);

        openLandingPageBody(bodyString, LandingPageContent.Encoding.UTF8);
    }

    @Test
    public void testAppDefined() throws Exception {
        String appDefined = "{\n" +
                "                \"field1\" : \"x\",\n" +
                "                \"field2\" : \"y\"\n" +
                "            }\n";
        ObjectNode expected = (ObjectNode) mapper.readTree(appDefined);

        AppDefinedAction appDefinedAction = new AppDefinedAction(expected);
        Actions actions = mapper.readValue(mapper.writeValueAsString(Actions.newBuilder().addAppDefined(appDefinedAction).build()), Actions.class);

        assertNotNull("Could not round-trip app_defined action", actions);
        Optional<AppDefinedAction> optAppDefined = getAction(actions, ActionType.APP_DEFINED, AppDefinedAction.class);
        assertTrue("app_defined should be present", optAppDefined.isPresent());

        ObjectNode result = optAppDefined.get().getValue();

        Iterator<String> fieldNames = expected.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            assertFalse("Expected field name not present in app_defined object: " + fieldName,
                    result.path(fieldName).isMissingNode());
            assertEquals("Expected value not found for key " + fieldName + ": " + expected.path(fieldName).toString(),
                    expected.path(fieldName), result.path(fieldName));
        }

        Iterator<String> resultFields = result.fieldNames();
        while (resultFields.hasNext()) {
            String fieldName = resultFields.next();
            assertFalse("Unexpected field name in app_defined object: " + fieldName,
                    expected.path(fieldName).isMissingNode());
        }

    }

    @Test
    public void testDeepLinkActionSerialization() throws Exception {
        DeepLinkAction a = new DeepLinkAction("test");
        assertEquals("{\"type\":\"deep_link\",\"content\":\"test\"}", mapper.writeValueAsString(a));
    }

    @Test
    public void testShare() throws Exception {
        String shareText = "This is a share.";

        ShareAction shareAction = new ShareAction(shareText);

        Actions actions = mapper.readValue(mapper.writeValueAsString(Actions.newBuilder().setShare(shareAction).build()), Actions.class);

        assertNotNull("Could not round-trip share action", actions);
        Optional<ShareAction> optShareAction = getAction(actions, ActionType.SHARE, ShareAction.class);
        assertTrue("add_tag should be present", optShareAction.isPresent());
        String shareData = optShareAction.get().getValue();
        assertEquals(shareText, shareData);
    }

    @Test
    public void testNonemptyShare() throws Exception {
        String json = "{ \"share\" : \"wha\" }";
        mapper.readValue(json, Actions.class);
    }

    @Test
    public void testEmptyShare() throws Exception {
        String json = "{ \"share\" : \"\" }";
        try {
            mapper.readValue(json, Actions.class);
        } catch (APIParsingException exc) {
            assertEquals("The share text may not be an empty string.", exc.getMessage());
        }
    }
}
