package com.urbanairship.api.push.parse.notification.android;

import com.google.common.collect.ImmutableList;
import com.urbanairship.api.push.model.notification.android.BigPictureStyle;
import com.urbanairship.api.push.model.notification.android.BigTextStyle;
import com.urbanairship.api.push.model.notification.android.InboxStyle;
import com.urbanairship.api.push.model.notification.android.Style;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StyleDeserializerTest {
    private final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testBigPictureStyle() throws Exception {
        String styleBigPictureJson =
                "{" +
                        "\"type\":\"big_picture\"," +
                        "\"big_picture\":\"pic.png\"," +
                        "\"title\":\"big pic title\"," +
                        "\"summary\":\"big pic summary\"" +
                "}";

        Style bigTextStyle = mapper.readValue(styleBigPictureJson, BigPictureStyle.class);
        assertNotNull(bigTextStyle);
        assertEquals(bigTextStyle.getTitle().get(), "big pic title");
        assertEquals(bigTextStyle.getSummary().get(), "big pic summary");
        assertEquals(bigTextStyle.getType(), Style.Type.BIG_PICTURE);
        assertEquals(bigTextStyle.getContent(), "pic.png");
    }

    @Test
    public void testBigTextStyle() throws Exception {
        String styleBigTextJson =
                "{" +
                        "\"type\":\"big_text\"," +
                        "\"big_text\":\"hello\"," +
                        "\"title\":\"big text title\"," +
                        "\"summary\":\"big text summary\"" +
                "}";

        Style bigTextStyle = mapper.readValue(styleBigTextJson, BigTextStyle.class);
        assertNotNull(bigTextStyle);
        assertEquals(bigTextStyle.getTitle().get(), "big text title");
        assertEquals(bigTextStyle.getSummary().get(), "big text summary");
        assertEquals(bigTextStyle.getType(), Style.Type.BIG_TEXT);
        assertEquals(bigTextStyle.getContent(), "hello");
    }

    @Test
    public void testInboxStyle() throws Exception {
        String styleLinesJson =
                "{" +
                        "\"type\":\"inbox\"," +
                        "\"lines\": [\"line_1\", \"line_2\", \"line_3\"]," +
                        "\"title\":\"lines title\"," +
                        "\"summary\":\"lines summary\"" +
                "}";

        Style inboxStyle = mapper.readValue(styleLinesJson, InboxStyle.class);
        ImmutableList<String> lines = (ImmutableList<String>) inboxStyle.getContent();
        assertNotNull(inboxStyle);
        assertEquals(inboxStyle.getTitle().get(), "lines title");
        assertEquals(inboxStyle.getSummary().get(), "lines summary");
        assertEquals(inboxStyle.getType(), Style.Type.INBOX);
        assertEquals(lines.get(0), "line_1");
        assertEquals(lines.get(1), "line_2");
        assertEquals(lines.get(2), "line_3");
    }
}
