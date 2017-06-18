package com.urbanairship.api.push;

import com.urbanairship.api.client.*;
import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.experiments.model.PartialPushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.notification.Notification;

/**
 * Created by jennifermiller on 5/28/17.
 */
public class RandomTest {

    public static void main(String args[]) {
        RandomTest test = new RandomTest();
        test.createExperiment();
    }

    public void createExperiment() {

        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
                .setKey("CxEeUYM6TqCEAY9FaVHTVw")
                .setSecret("g9JW4M4uRLmWUoaM-mZE6g")
                .build();

        PartialPushPayload variantOne = PartialPushPayload.newBuilder()
                .setNotification(Notification.newBuilder()
                        .setAlert("Hello Jenn!")
                        .build()
                )
                .build();

        Experiment experiment = Experiment.newBuilder()
                .setName("Jenn's experiment!")
                .setDescription("It's a test, hoo boy!")
                .setDeviceType(DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID))
                .setAudience(Selectors.all())
                .addVariant(Experiment.Variant.newBuilder().
                        setPushPayload(variantOne).build())
                .build();

//        PushRequest request = PushRequest.newRequest(experiment);

//        Response<PushResponse> response = null;
//        try {
//            response = client.execute(request);
//            logger.debug(String.format("Response %s", response.toString()));
//        } catch (IOException e) {
//            logger.error("IOException in API request " + e.getMessage());
//        }

        client.close();

    }
}
