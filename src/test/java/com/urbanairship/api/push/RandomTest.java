package com.urbanairship.api.push;

import com.urbanairship.api.client.*;
import com.urbanairship.api.experiments.ExperimentRequest;
import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.experiments.model.ExperimentResponse;
import com.urbanairship.api.experiments.model.PartialPushPayload;
import com.urbanairship.api.experiments.model.Variant;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by jennifermiller on 5/28/17.
 */
public class RandomTest {

    private static final Logger log = LoggerFactory.getLogger(UrbanAirshipClient.class);

    public static void main(String args[]) {
        RandomTest test = new RandomTest();
        test.createExperiment();
    }

    public void createExperiment() {

        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
                .setKey("ISex_TTJRuarzs9-o_Gkhg")
                .setSecret("nDq-bQ3CT92PqCIXNtQyCQ")
                .build();

        Variant variantOne = Variant.newBuilder()
                .setPushPayload(PartialPushPayload.newBuilder()
                        .setNotification(Notification.newBuilder()
                                .setAlert("Hello Jenn!")
                                .build()
                        )
                        .build())
                .build();

        Experiment experiment = Experiment.newBuilder()
                .setName("Jenn's experiment!")
                .setDescription("It's a test, hoo boy!")
                .setDeviceType(DeviceTypeData.of(DeviceType.IOS))
                .setAudience(Selectors.namedUser("birdperson"))
                .addVariant(variantOne)
                .build();
        try {
            Response<ExperimentResponse> response = client.execute(ExperimentRequest.newRequest(experiment));
            log.debug(String.format("Response %s", response.toString()));
        } catch (IOException e) {
            log.error("IOException in API request " + e.getMessage());
        }

        client.close();

    }
}
