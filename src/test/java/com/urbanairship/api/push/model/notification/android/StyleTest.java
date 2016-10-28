package com.urbanairship.api.push.model.notification.android;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StyleTest {

    @Test(expected = Exception.class)
    public void testNoContent() throws Exception {
        BigPictureStyle style = BigPictureStyle.newBuilder()
                .setTitle("Hi")
                .build();
    }

    @Test
    public void testBigPictureStyle() throws Exception {
        BigPictureStyle style = BigPictureStyle.newBuilder()
                .setTitle("Title")
                .setSummary("Some summary text")
                .setContent("hello.png")
                .build();

        assertNotNull(style);
        assertEquals(style.getTitle().get(), "Title");
        assertEquals(style.getSummary().get(), "Some summary text");
        assertEquals(style.getContent(), "hello.png");
        assertEquals(style.getType().getStyleType(), "big_picture");
    }

    @Test
    public void testBigTextStyle() throws Exception {
        BigTextStyle style = BigTextStyle.newBuilder()
                .setTitle("Title")
                .setSummary("Some summary text")
                .setContent("hello")
                .build();

        assertNotNull(style);
        assertEquals(style.getTitle().get(), "Title");
        assertEquals(style.getSummary().get(), "Some summary text");
        assertEquals(style.getContent(), "hello");
        assertEquals(style.getType().getStyleType(), "big_text");
    }

    @Test
    public void testInboxStyle() throws Exception {
        List<String> lines = Arrays.asList("line1", "line2", "line3");

        InboxStyle style = InboxStyle.newBuilder()
                .setTitle("Title")
                .setSummary("Some summary text")
                .addLines(lines)
                .build();

        assertNotNull(style);
        assertEquals(style.getTitle().get(), "Title");
        assertEquals(style.getSummary().get(), "Some summary text");
        assertEquals(style.getContent(), lines);
        assertEquals(style.getType().getStyleType(), "inbox");
    }

}
