package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.Campaigns;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CampaignsSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testCampaignSerializer() throws Exception {

        String campaignJson = "{" +
                "\"categories\":[\"this\",\"that\",\"other\"]" +
                "}";

        List<String> categoryList = Arrays.asList("that", "other");

        Campaigns campaigns = Campaigns.newBuilder()
                .addCategory("this")
                .addAllCategories(categoryList)
                .build();

        String parsedCampaignText = MAPPER.writeValueAsString(campaigns);

        JsonNode parsedCampaignNode = MAPPER.readTree(parsedCampaignText);
        JsonNode campaignJsonNode = MAPPER.readTree(campaignJson);
        assertEquals(parsedCampaignNode, campaignJsonNode);
    }

}
