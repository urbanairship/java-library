package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.push.model.notification.mpns.MPNSCycleTileData;
import com.urbanairship.api.push.model.notification.mpns.MPNSFlipTileData;
import com.urbanairship.api.push.model.notification.mpns.MPNSIconicTileData;
import com.urbanairship.api.push.model.notification.mpns.MPNSTileData;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testDeserializeFlipTile() throws Exception {
        String json = "{"
            + "\"id\": \"/MyPage.xaml\","
            + "\"template\": \"FlipTile\","
            + "\"count\": 10,"
            + "\"title\": \"Flippy\","
            + "\"back_title\": \"FlippyBack\","
            + "\"back_content\": \"WAT\","
            + "\"wide_back_content\": \"NO WAI\","
            + "\"background_image\": \"back.png\","
            + "\"back_background_image\": \"backback.png\","
            + "\"wide_background_image\": \"wideback.png\","
            + "\"wide_back_background_image\": \"widebackback.png\","
            + "\"small_background_image\": \"smallback.png\""
            + "}";
        MPNSTileData parsed = mapper.readValue(json, MPNSTileData.class);
        assertTrue(parsed instanceof MPNSFlipTileData);

        MPNSFlipTileData tile = (MPNSFlipTileData)parsed;
        assertTrue(tile.getId().isPresent());
        assertTrue(tile.getTemplate() != null);
        assertTrue(tile.getCount().isPresent());
        assertTrue(tile.getTitle().isPresent());
        assertTrue(tile.getBackTitle().isPresent());
        assertTrue(tile.getBackContent().isPresent());
        assertTrue(tile.getWideBackContent().isPresent());
        assertTrue(tile.getBackgroundImage().isPresent());
        assertTrue(tile.getBackBackgroundImage().isPresent());
        assertTrue(tile.getWideBackgroundImage().isPresent());
        assertTrue(tile.getWideBackBackgroundImage().isPresent());
        assertTrue(tile.getSmallBackgroundImage().isPresent());

        assertEquals("/MyPage.xaml", tile.getId().get());
        assertEquals("FlipTile", tile.getTemplate());
        assertEquals(10, tile.getCount().get().intValue());
        assertEquals("Flippy", tile.getTitle().get());
        assertEquals("FlippyBack", tile.getBackTitle().get());
        assertEquals("WAT", tile.getBackContent().get());
        assertEquals("NO WAI", tile.getWideBackContent().get());

        assertEquals("back.png", tile.getBackgroundImage().get());
        assertEquals("backback.png", tile.getBackBackgroundImage().get());
        assertEquals("wideback.png", tile.getWideBackgroundImage().get());
        assertEquals("widebackback.png", tile.getWideBackBackgroundImage().get());
        assertEquals("smallback.png", tile.getSmallBackgroundImage().get());
    }

    @Test
    public void testDeserializeIconicTile() throws Exception {
        String json = "{"
            +  "\"template\": \"IconicTile\","
            +  "\"icon_image\": \"icon.png\","
            +  "\"small_icon_image\": \"smallicon.png\","
            +  "\"background_color\": \"#FFAABBCC\","
            +  "\"wide_content_1\": \"w1\","
            +  "\"wide_content_2\": \"w2\","
            +  "\"wide_content_3\": \"w3\""
            + "}";

        MPNSTileData parsed = mapper.readValue(json, MPNSTileData.class);
        assertTrue(parsed instanceof MPNSIconicTileData);

        MPNSIconicTileData tile = (MPNSIconicTileData)parsed;
        assertTrue(tile.getIconImage().isPresent());
        assertTrue(tile.getSmallIconImage().isPresent());
        assertTrue(tile.getBackgroundColor().isPresent());
        assertTrue(tile.getWideContent1().isPresent());
        assertTrue(tile.getWideContent2().isPresent());
        assertTrue(tile.getWideContent3().isPresent());

        assertEquals("icon.png", tile.getIconImage().get());
        assertEquals("smallicon.png", tile.getSmallIconImage().get());
        assertEquals("#FFAABBCC", tile.getBackgroundColor().get());
        assertEquals("w1", tile.getWideContent1().get());
        assertEquals("w2", tile.getWideContent2().get());
        assertEquals("w3", tile.getWideContent3().get());
    }

    @Test
    public void testDeserializeCycleTile() throws Exception {
        String json = "{"
            + "\"template\": \"CycleTile\","
            + "\"cycle_image\": ["
            + "\"/image1\","
            + "\"/image2\","
            + "\"/image3\""
            + "]"
            + "}";

        MPNSTileData parsed = mapper.readValue(json, MPNSTileData.class);
        assertTrue(parsed instanceof MPNSCycleTileData);

        MPNSCycleTileData tile = (MPNSCycleTileData)parsed;
        assertEquals(3, tile.getImageCount());
        assertEquals("/image1", tile.getImage(0));
        assertEquals("/image2", tile.getImage(1));
        assertEquals("/image3", tile.getImage(2));
    }

    @Test(expected=APIParsingException.class)
    public void testDeserializeCycleTile_ImagePathValidation() throws Exception {
        String json = "{"
            + "\"template\": \"CycleTile\","
            + "\"cycle_image\": ["
            + "\"image1\""
            + "]"
            + "}";

        mapper.readValue(json, MPNSTileData.class);
    }
}
