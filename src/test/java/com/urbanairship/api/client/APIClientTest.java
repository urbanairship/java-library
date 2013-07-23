package com.urbanairship.api.client;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.model.PlatformData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.Notifications;

import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.apache.log4j.BasicConfigurator;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

public class APIClientTest {

    private static final Logger logger = LoggerFactory.getLogger(APIClientTest.class);

    public final static String CONTENT_TYPE_KEY = "Content-type";
    public final static String APP_JSON = "application/json";
    public final static String UA_APP_JSON =
            "application/vnd.urbanairship+json; version=3";

    static {
        BasicConfigurator.configure();
    }

    @ClassRule
    @Rule
    public static WireMockClassRule wireMockClassRule = new WireMockClassRule();


    @Test(expected = IllegalArgumentException.class)
    public void testAPIClientThrowsForNoAppKey(){
        APIClient apiClient = APIClient.newBuilder().setKey("foo")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAPIClientThrowsForNoAppSecret(){
        APIClient apiClient = APIClient.newBuilder().setSecret("foo")
                                       .build();
    }

    @Test
    public void testAPIClientBuilder(){
        APIClient client = APIClient.newBuilder()
                .setKey("key")
                .setSecret("secret")
                .build();
        assertEquals("App key incorrect", "key", client.getAppKey());
        assertEquals("App secret incorrect", "secret", client.getAppSecret());
    }

    /* Test the following attributes of the push method on the APIClient object
        1. Method produces a post request
        2. Request has proper headers
        3. URL path is correct
        4. The request body produced during the building/sending process can actually
        be parsed by the server.

     */
//    @Test
//    public void testPush(){
//
//        // Setup a client and a push payload
//        APIClient client = APIClient.newBuilder()
//                .setBaseURI("http://localhost:8080")
//                .setAppKey("key")
//                .setAppSecret("secret")
//                .build();
//
//        PushPayload payload = PushPayload.newBuilder()
//                .setAudience(Selectors.all())
//                .setPlatforms(PlatformData.of(Platform.IOS))
//                .setNotification(Notifications.alert("Foo"))
//                .build();
//
//        // Setup a stubbed response for the server
//        String pushJSON = "{\"ok\" : true,\"operation_id\" : \"df6a6b50\", \"push_ids\":[\"PushID\"]}";
//        stubFor(post(urlEqualTo("/api/push/"))
//                        .willReturn(aResponse()
//                                            .withHeader(CONTENT_TYPE_KEY, "application/json")
//                                            .withBody(pushJSON)
//                                            .withStatus(201)));
//
//
//        try {
//            APIClientResponse<APIPushResponse> response = client.push(payload);
//
//            // Verify components of the underlying HttpRequest
//            verify(postRequestedFor(urlEqualTo("/api/push/"))
//                           .withHeader(CONTENT_TYPE_KEY, equalTo(UA_APP_JSON)));
//            List<LoggedRequest> requests = findAll(postRequestedFor(
//                    urlEqualTo("/api/push/")));
//            // There should only be one request
//            assertEquals(requests.size(), 1);
//            // Parse the request using the server side deserializer and check
//            // results
//            String requestPayload = requests.get(0).getBodyAsString();
//            ObjectMapper mapper = PushObjectMapper.getInstance();
//            PushPayload result = mapper.readValue(requestPayload, PushPayload.class);
//            Selector audience = result.getAudience();
//            assertEquals(audience, Selectors.all());
//            PlatformData platformData = result.getPlatforms();
//            assertEquals(platformData, PlatformData.of(Platform.IOS));
//            Notification notification = result.getNotification().get();
//            assertEquals(notification.getAlert().get(), "Foo");
//            // The response is tested elsewhere, just check that it exists
//            assertNotNull(response);
//        }
//        catch (Exception ex){
//            fail("Exception thrown " + ex);
//        }
//    }
//
//    @Test
//    public void testSchedule(){
//
//        // Setup a client and a schedule payload
//        APIClient client = APIClient.newBuilder()
//                                    .setBaseURI("http://localhost:8080")
//                                    .setAppKey("key")
//                                    .setAppSecret("secret")
//                                    .build();
//
//        PushPayload pushPayload = PushPayload.newBuilder()
//                                         .setAudience(Selectors.all())
//                                         .setPlatforms(PlatformData.of(Platform.IOS))
//                                         .setNotification(Notifications.alert("Foo"))
//                                         .build();
//
//        DateTime dateTime = DateTime.now(DateTimeZone.UTC).plusSeconds(60);
//        Schedule schedule = Schedule.newBuilder()
//                                    .setScheduledTimestamp(dateTime)
//                                    .build();
//
//        SchedulePayload schedulePayload = SchedulePayload.newBuilder()
//                                                         .setName("Test")
//                                                         .setPushPayload(pushPayload)
//                                                         .setSchedule(schedule)
//                                                         .build();
//
//        // Stub out endpoint
//        // Setup a stubbed response for the server
//        String pushJSON = "{\"ok\" : true,\"operation_id\" : \"OpID\", \"schedule_urls\":[\"ScheduleURL\"]}";
//        stubFor(post(urlEqualTo("/api/schedules/"))
//                        .willReturn(aResponse()
//                                            .withHeader(CONTENT_TYPE_KEY, APP_JSON)
//                                            .withBody(pushJSON)
//                                            .withStatus(201)));
//
//        try {
//            APIClientResponse<APIScheduleResponse> response = client.schedule(schedulePayload);
//
//            // Verify components of the underlying request
//            verify(postRequestedFor(urlEqualTo("/api/schedules/"))
//                           .withHeader(CONTENT_TYPE_KEY, equalTo(UA_APP_JSON)));
//            List<LoggedRequest> requests = findAll(postRequestedFor(urlEqualTo("/api/schedules/")));
//            assertEquals(requests.size(), 1);
//            String receivedBody = requests.get(0).getBodyAsString();
//            ObjectMapper mapper = PushObjectMapper.getInstance();
//            SchedulePayload receivedPayload = mapper.readValue(receivedBody, SchedulePayload.class);
//            assertEquals(receivedPayload.getPushPayload(), pushPayload);
//            assertEquals(receivedPayload.getName().get(), "Test");
//            DateTime receivedDateTime = receivedPayload.getSchedule().getScheduledTimestamp();
//            // Server truncates milliseconds off request
//            assertEquals(dateTime.getMillis(), receivedDateTime.getMillis(), 1000);
//
//        }
//        catch (Exception ex){
//            fail("Exception " + ex);
//        }
//    }
//
//    /*
//    Validate is the exact workflow as push, with the exception of the URL,
//    so focus test on that.
//     */
//    @Test
//    public void testValidate(){
//
//        // Setup a client and a push payload
//        APIClient client = APIClient.newBuilder()
//                                    .setBaseURI("http://localhost:8080")
//                                    .setAppKey("key")
//                                    .setAppSecret("secret")
//                                    .build();
//
//        PushPayload payload = PushPayload.newBuilder()
//                                         .setAudience(Selectors.all())
//                                         .setPlatforms(PlatformData.of(Platform.IOS))
//                                         .setNotification(Notifications.alert("Foo"))
//                                         .build();
//
//        // Setup a stubbed response for the server
//        String pushJSON = "{\"ok\" : true,\"operation_id\" : \"df6a6b50\", \"push_ids\":[\"PushID\"]}";
//        stubFor(post(urlEqualTo("/api/validate/"))
//                        .willReturn(aResponse()
//                                            .withHeader(CONTENT_TYPE_KEY, "application/json")
//                                            .withBody(pushJSON)
//                                            .withStatus(201)));
//        try {
//            APIClientResponse<APIPushResponse> response = client.validate(payload);
//
//            // Verify components of the underlying HttpRequest
//            verify(postRequestedFor(urlEqualTo("/api/validate/"))
//                           .withHeader(CONTENT_TYPE_KEY, equalTo(UA_APP_JSON)));
//            assertNotNull(response);
//        }
//        catch (Exception ex){
//            fail("Exception thrown " + ex);
//        }
//
//    }
//
}
