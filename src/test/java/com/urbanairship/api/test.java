package com.urbanairship.api;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test {
    private static final Logger logger = LoggerFactory.getLogger("com.urbanairship.api");

//    public void sendPush(){
//
//        String appKey = "ISex_TTJRuarzs9-o_Gkhg";
//        String appSecret = "cqEQS-QzSFW4TdssghiBLQ";
//        String deviceToken = "87F2BC6C0113EE5C81103297CD1F1A220DD5FA9C0BEC59DA71304D4923846665";
//
//
//        logger.debug(String.format("Make sure key and secret are set Key:%s Secret:%s",
//            appKey, appSecret));
//
//        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
//            .setKey(appKey)
//            .setSecret(appSecret)
//            .build();
//
//        logger.debug("Send the message");
//        PushPayload payload = PushPayload.newBuilder()
//            .setAudience(Selectors.deviceToken(deviceToken))
//            .setNotification(Notifications.notification("Urban Airship Push"))
//            .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
//            .build();
//
//        try {
//            Response<PushResponse> response = client.execute(PushRequest.newRequest(payload));
//            logger.debug("PUSH SUCCEEDED");
//            logger.debug(String.format("RESPONSE:%s", response.toString()));
//        }
//        catch (APIRequestException ex){
//            logger.error(String.format("APIRequestException " + ex));
//            logger.error("EXCEPTION " + ex.toString());
//
//            APIError apiError = ex.getError().get();
//            logger.error("Error " + apiError.getError());
//            if (apiError.getDetails().isPresent())     {
//                APIErrorDetails apiErrorDetails = apiError.getDetails().get();
//                logger.error("Error details " + apiErrorDetails.getError());
//            }
//        }
//        catch (IOException e){
//            logger.error("IOException in API request " + e.getMessage());
//        }
//
//    }
//
//    public void sendScheduledPush(){
//        String appKey = "ISex_TTJRuarzs9-o_Gkhg";
//        String appSecret = "cqEQS-QzSFW4TdssghiBLQ";
//        String deviceToken = "87F2BC6C0113EE5C81103297CD1F1A220DD5FA9C0BEC59DA71304D4923846665";
//
//        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
//            .setKey(appKey)
//            .setSecret(appSecret)
//            .build();
//
//        PushPayload payload = PushPayload.newBuilder()
//            .setAudience(Selectors.deviceToken(deviceToken))
//            .setNotification(Notifications.alert("Scheduled API v3"))
//            .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
//            .build();
//
//        DateTime dt = DateTime.now().plusSeconds(600);
//        Schedule schedule = Schedule.newBuilder()
//            .setScheduledTimestamp(dt)
//            .build();
//
//        ScheduleRequest request = ScheduleRequest.newRequest(schedule, payload)
//            .setName("Urban Airship Scheduled Push");
//
//        try {
//            Response<ScheduleResponse> response = client.execute(request);
//            logger.debug("SCHEDULE PUSH SUCCEEDED");
//            logger.debug(String.format("RESPONSE:%s", response.toString()));
//        }
//        catch (APIRequestException ex){
//            logger.error(String.format("APIRequestException " + ex));
//            logger.error("EXCEPTION " + ex.toString());
//        }
//        catch (IOException e){
//            logger.error("Exception in API request -> " + e.getMessage());
//
//        }
//    }
//
//    public void listScheduledPush(){
//        String appKey = "ISex_TTJRuarzs9-o_Gkhg";
//        String appSecret = "cqEQS-QzSFW4TdssghiBLQ";
//
//        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
//            .setKey(appKey)
//            .setSecret(appSecret)
//            .build();
//
//        ListSchedulesRequest request = ListSchedulesRequest.newRequest();
//
//        try {
//            Response<ListAllSchedulesResponse> response = client.execute(request);
//            logger.debug("SCHEDULE PUSH SUCCEEDED");
//            logger.debug(String.format("RESPONSE:%s", response.toString()));
//        }
//        catch (APIRequestException ex){
//            logger.error(String.format("APIRequestException " + ex));
//            logger.error("EXCEPTION " + ex.toString());
//        }
//        catch (IOException e){
//            logger.error("Exception in API request -> " + e.getMessage());
//
//        }
//    }
//
//    public void deleteScheduledPush(){
//        String appKey = "ISex_TTJRuarzs9-o_Gkhg";
//        String appSecret = "cqEQS-QzSFW4TdssghiBLQ";
//
//        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
//            .setKey(appKey)
//            .setSecret(appSecret)
//            .build();
//
//        DeleteScheduleRequest request = DeleteScheduleRequest.newRequest("b55208de-0105-4250-9c5c-ab9680f5e123");
//
//        try {
//            Response<String> response = client.execute(request);
//            logger.debug("SCHEDULE PUSH SUCCEEDED");
//            logger.debug(String.format("RESPONSE:%s", response.toString()));
//        }
//        catch (APIRequestException ex){
//            logger.error(String.format("APIRequestException " + ex));
//            logger.error("EXCEPTION " + ex.toString());
//        }
//        catch (IOException e){
//            logger.error("Exception in API request -> " + e.getMessage());
//
//        }
//    }


    public static void main(String args[]){
        BasicConfigurator.configure();
        logger.debug("Starting test push");
        test example = new test();
//        example.sendPush();
//        example.sendScheduledPush();
//        example.listScheduledPush();
//        example.deleteScheduledPush();
    }
}
