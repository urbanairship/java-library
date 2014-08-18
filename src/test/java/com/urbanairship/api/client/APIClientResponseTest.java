package com.urbanairship.api.client;

import com.google.common.collect.ImmutableList;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class APIClientResponseTest {


    @Test
    public void testAPIScheduleResponse(){
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));
        APIScheduleResponse scheduleResponse = APIScheduleResponse.newBuilder()
                .addAllScheduleUrls(Arrays.asList("ID1", "ID2"))
                .setOperationId("ID")
                .build();
        APIClientResponse.Builder<APIScheduleResponse> builder =
                APIClientResponse.newScheduleResponseBuilder()
                                 .setApiResponse(scheduleResponse)
                                 .setHttpResponse(httpResponse);
        APIClientResponse<APIScheduleResponse> testResponse = builder.build();
        assertTrue("HTTP response not set properly",
                   testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                   testResponse.getApiResponse().equals(scheduleResponse));
    }

    @Test
    public void testAPIListScheduleResponse(){
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));


        SchedulePayload sample = SchedulePayload.newBuilder()
                                        .setSchedule(Schedule.newBuilder()
                                                .setScheduledTimestamp(DateTime.now())
                                                .build())
                                        .setPushPayload(PushPayload.newBuilder()
                                                .setAudience(Selectors.all())
                                                .setNotification(Notification.newBuilder()
                                                        .setAlert("Derp")
                                                        .build())
                                                .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                                                .build())
                                        .setUrl("http://sample.com/")
                                        .build();

        APIListScheduleResponse listScheduleResponse = APIListScheduleResponse.newBuilder()
                .setCount(5)
                .setTotalCount(6)
                .addSchedule(SchedulePayload.newBuilder()
                        .setSchedule(Schedule.newBuilder()
                                .setScheduledTimestamp(DateTime.now())
                                .build())
                        .setPushPayload(PushPayload.newBuilder()
                                                   .setAudience(Selectors.deviceToken("token"))
                                                   .setNotification(Notifications.notification("UA Push"))
                                                   .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                                                   .build())
                        .build())
                .build();

        APIClientResponse.Builder<APIListScheduleResponse> builder =
                APIClientResponse.newListScheduleResponseBuilder()
                        .setApiResponse(listScheduleResponse)
                        .setHttpResponse(httpResponse);
        APIClientResponse<APIListScheduleResponse> testResponse = builder.build();
        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(listScheduleResponse));

    }

    @Test
    public void testAPIListTagsResponse(){
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));

        List<String> listOTags = new ArrayList<String>();
        listOTags.add("Puppies");
        listOTags.add("Kitties");

        APIListTagsResponse listTagsResponse = APIListTagsResponse.newBuilder()
                .allAllTags(listOTags)
                .build();
        APIClientResponse.Builder<APIListTagsResponse> builder =
                APIClientResponse.newListTagsResponseBuilder()
                        .setApiResponse(listTagsResponse)
                        .setHttpResponse(httpResponse);
        APIClientResponse<APIListTagsResponse> testResponse = builder.build();
        assertTrue("HTTP response not set properly",
                testResponse.getHttpResponse().equals(httpResponse));

        assertTrue("APIResponse not set properly",
                testResponse.getApiResponse().equals(listTagsResponse));
    }

    @Test
    public void testAPIPushResponse(){
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));
        APIPushResponse pushResponse = APIPushResponse.newBuilder()
                .addAllPushIds(Arrays.asList("ID1", "ID2"))
                .setOperationId("OpID")
                .build();
        APIClientResponse.Builder<APIPushResponse>builder =
                APIClientResponse.newPushResponseBuilder()
                .setApiResponse(pushResponse)
                .setHttpResponse(httpResponse);
        APIClientResponse<APIPushResponse> apiResponse = builder.build();
        assertTrue("HTTP response not set properly",
                   apiResponse.getHttpResponse().equals(httpResponse));
        assertTrue("APIResponse not set properly",
                   apiResponse.getApiResponse().equals(pushResponse));
    }

}
