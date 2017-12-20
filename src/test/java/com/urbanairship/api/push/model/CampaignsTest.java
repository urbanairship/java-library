package com.urbanairship.api.push.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CampaignsTest {

    @Test
    public void testCampaigns() {

        List<String> categoryList = Arrays.asList("that", "other");
        List<String> expected = Arrays.asList("this", "that", "other");

        Campaigns campaigns = Campaigns.newBuilder()
                .addCategory("this")
                .addAllCategories(categoryList)
                .build();

        assertNotNull(campaigns);
        assertEquals(campaigns.getCategories(), expected);
        assertEquals(campaigns.getCategories().size(), 3);
    }
}
