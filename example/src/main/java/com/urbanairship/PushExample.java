package com.urbanairship;

import com.urbanairship.api.client.*;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;

import org.apache.log4j.BasicConfigurator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.joda.time.DateTime;

import java.io.IOException;

/*
 * Copyright 2013 Urban Airship and Contributors
 */
public class PushExample {

    private static final Logger logger = LoggerFactory.getLogger("com.urbanairship.api");

    public void sendPush(){

        String appKey = "appKey";
        String appSecret = "appSecret";
        // This is optional, one of many options for push.
        String deviceToken = "deviceToken";


        logger.debug(String.format("Make sure key and secret are set Key:%s Secret:%s",
                                   appKey, appSecret));

        APIClient apiClient = APIClient.newBuilder()
                                       .setKey(appKey)
                                       .setSecret(appSecret)
                                       .build();
        logger.debug(String.format("Setup an APIClient to handle the API call %s", apiClient.toString()));
        logger.debug("Send the message");

        PushPayload payload = PushPayload.newBuilder()
                                         .setAudience(Selectors.deviceToken(deviceToken))
                                         .setNotification(Notifications.notification("Urban Airship Push"))
                                         .setPlatforms(DeviceTypeData.of(DeviceType.IOS))
                                         .build();

        try {
            APIClientResponse<APIPushResponse> response = apiClient.push(payload);
            logger.debug("PUSH SUCCEEDED");
            logger.debug(String.format("RESPONSE:%s", response.toString()));
        }
        catch (APIRequestException ex){
            logger.error(String.format("APIRequestException " + ex));
            logger.error("EXCEPTION " + ex.toString());

            APIError apiError = ex.getError().get();
            logger.error("Error " + apiError.getError());
            if (apiError.getDetails().isPresent())     {
                APIErrorDetails apiErrorDetails = apiError.getDetails().get();
                logger.error("Error details " + apiErrorDetails.getError());
            }
        }
        catch (IOException e){
            logger.error("IOException in API request " + e.getMessage());
        }

    }

    public void sendScheduledPush(){
        String appKey = "appKey";
        String appSecret = "appSecret";

        APIClient apiClient = APIClient.newBuilder()
                                       .setKey(appKey)
                                       .setSecret(appSecret)
                                       .setBaseURI("https://go.urbanairship.com")
                                       .setVersion(3)
                                       .build();

        PushPayload payload = PushPayload.newBuilder()
                                         .setAudience(Selectors.all())
                                         .setNotification(Notifications.alert("Scheduled API v3"))
                                         .setPlatforms(DeviceTypeData.of(DeviceType.IOS))
                                         .build();

        DateTime dt = DateTime.now().plusSeconds(60);
        Schedule schedule = Schedule.newBuilder()
                                    .setScheduledTimestamp(dt)
                                    .build();

        SchedulePayload schedulePayload = SchedulePayload.newBuilder()
                                                         .setName("Urban Airship Scheduled Push")
                                                         .setPushPayload(payload)
                                                         .setSchedule(schedule)
                                                         .build();

        try {
            APIClientResponse<APIScheduleResponse> response = apiClient.schedule(schedulePayload);
            logger.debug("SCHEDULE PUSH SUCCEEDED");
            logger.debug(String.format("RESPONSE:%s", response.toString()));
        }
        catch (APIRequestException ex){
            logger.error(String.format("APIRequestException " + ex));
            logger.error("EXCEPTION " + ex.toString());
        }
        catch (IOException e){
            logger.error("Exception in API request -> " + e.getMessage());

        }
    }

    public static void main(String args[]){
        BasicConfigurator.configure();
        logger.debug("Starting test push");
        PushExample example = new PushExample();
        example.sendPush();
        example.sendScheduledPush();

    }
}

