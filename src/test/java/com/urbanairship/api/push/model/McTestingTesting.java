package com.urbanairship.api.push.model;

import com.urbanairship.api.client.Response;
import com.urbanairship.api.client.UrbanAirshipClient;
import com.urbanairship.api.push.PushRequest;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.push.model.notification.android.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.List;
import java.util.Arrays;

public class McTestingTesting {

    public static void main(String[] args) {

        org.apache.log4j.BasicConfigurator.configure();
        Logger logger = LoggerFactory.getLogger(McTestingTesting.class);

        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
                .setKey("DqSiO1kbS96Qkr1tbkzVVA")
                .setSecret("PmI7cJ4CQqm2kxPaIpgSeg")
                .build();
        List<String> categoryNames = Arrays.asList("c1", "c2", "c3");

        Campaigns campaigns = Campaigns.newBuilder()
        //        .addCategory("addFirst")
         //       .addAllCategories(categoryNames)
                .build();

        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Selectors.all())
                .setNotification(Notifications.alert("Here's a push!"))
                .setDeviceTypes(DeviceTypeData.all())
                .setCampaigns(campaigns)
                .build();

        PushRequest request = PushRequest.newRequest(payload);
        Response<PushResponse> response = null;
        try {
            response = client.execute(request);
            logger.debug(String.format("Response %s", response.toString()));
        } catch (IOException e) {
            logger.error("IOException in API request " + e.getMessage());
        }

    }
}



