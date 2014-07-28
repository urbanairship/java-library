package com.urbanairship.api.client;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.apache.log4j.BasicConfigurator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

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
        @SuppressWarnings("UnusedAssignment") APIClient apiClient = APIClient.newBuilder().setKey("foo")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAPIClientThrowsForNoAppSecret(){
        @SuppressWarnings("UnusedAssignment") APIClient apiClient = APIClient.newBuilder().setSecret("foo")
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
    @Test
    @SuppressWarnings("unchecked")
    public void testPush(){

        // Setup a client and a push payload
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Selectors.all())
                .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                .setNotification(Notifications.alert("Foo"))
                .build();

        // Setup a stubbed response for the server
        String pushJSON = "{\"ok\" : true,\"operation_id\" : \"df6a6b50\", \"push_ids\":[\"PushID\"]}";
        stubFor(post(urlEqualTo("/api/push/"))
                        .willReturn(aResponse()
                                            .withHeader(CONTENT_TYPE_KEY, "application/json")
                                            .withBody(pushJSON)
                                            .withStatus(201)));


        try {
            APIClientResponse<APIPushResponse> response = client.push(payload);

            // Verify components of the underlying HttpRequest
            verify(postRequestedFor(urlEqualTo("/api/push/"))
                           .withHeader(CONTENT_TYPE_KEY, equalTo(UA_APP_JSON)));
            List<LoggedRequest> requests = findAll(postRequestedFor(
                    urlEqualTo("/api/push/")));
            // There should only be one request
            assertEquals(requests.size(), 1);
            // Parse the request using the server side deserializer and check
            // results
            String requestPayload = requests.get(0).getBodyAsString();
            ObjectMapper mapper = PushObjectMapper.getInstance();
            Map<String, Object> result =
                    mapper.readValue(requestPayload,
                                     new TypeReference<Map<String,Object>>(){});
            // Audience
            String audience = (String)result.get("audience");
            assertTrue(audience.equals("ALL"));

            // DeviceType
            List<String> deviceTypeData = (List<String>)result.get("device_types");
            assertTrue(deviceTypeData.get(0).equals("ios"));
            assertEquals(DeviceType.find(deviceTypeData.get(0)).get(), DeviceType.IOS);

            // Notification
            Map<String, String> notification =
                    (Map<String,String>)result.get("notification");
            assertTrue(notification.get("alert").equals("Foo"));

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListSchedules() {
        // Setup a client and a schedule payload
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        // Setup a stubbed response for the server
        String listscheduleresponse = "{\"ok\":true,\"count\":5,\"total_count\":6,\"schedules\":" +
                "[{\"url\":\"https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3\",\"" +
                "schedule\":{\"scheduled_time\":\"2015-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device" +
                "_types\":[\"android\",\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2015!\",\"android\"" +
                ":{},\"ios\":{}}},\"push_ids\":[\"8430f2e0-ec07-4c1e-adc4-0c7c7978e648\"]},{\"url\":\"https://go" +
                ".urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973\",\"schedule\":{\"schedule" +
                "d_time\":\"2016-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device_types\":[\"android\"," +
                "\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2016!\",\"android\":{},\"ios\":{}}},\"pus" +
                "h_ids\":[\"b217a321-922f-4aee-b239-ca1b58c6b652\"]}]}";

        stubFor(get(urlEqualTo("/api/schedules/"))
                        .willReturn(aResponse()
                                            .withHeader(CONTENT_TYPE_KEY, "application/json")
                                            .withBody(listscheduleresponse)
                                            .withStatus(201)));

        try {
            APIClientResponse<APIListScheduleResponse> response = client.listschedules();

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/schedules/"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(UA_APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(
                    urlEqualTo("/api/schedules/")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());
            assertNotNull(response.getApiResponse().getCount());
            assertNotNull(response.getApiResponse().getTotal_Count());
            assertNotNull(response.getApiResponse().getSchedules());
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListSchedulesWithParameters() {
        // Setup a client and a schedule payload
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        // Setup a stubbed response for the server
        String listscheduleresponse = "{\"ok\":true,\"count\":5,\"total_count\":6,\"schedules\":" +
                "[{\"url\":\"https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3\",\"" +
                "schedule\":{\"scheduled_time\":\"2015-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device" +
                "_types\":[\"android\",\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2015!\",\"android\"" +
                ":{},\"ios\":{}}},\"push_ids\":[\"8430f2e0-ec07-4c1e-adc4-0c7c7978e648\"]},{\"url\":\"https://go" +
                ".urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973\",\"schedule\":{\"schedule" +
                "d_time\":\"2016-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device_types\":[\"android\"," +
                "\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2016!\",\"android\":{},\"ios\":{}}},\"pus" +
                "h_ids\":[\"b217a321-922f-4aee-b239-ca1b58c6b652\"]}]}";

        stubFor(get(urlEqualTo("/api/schedules?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(listscheduleresponse)
                        .withStatus(201)));

        try {
            APIClientResponse<APIListScheduleResponse> response = client.listschedules("643a297a-7313-45f0-853f-e68785e54c77",25,"asc");

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/schedules?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(UA_APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(
                    urlEqualTo("/api/schedules?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());
            assertNotNull(response.getApiResponse().getCount());
            assertNotNull(response.getApiResponse().getTotal_Count());
            assertNotNull(response.getApiResponse().getSchedules());
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListSchedulesNextPage() {
        // Setup a client and a schedule payload
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        // Setup a stubbed response for the server
        String listscheduleresponse = "{\"ok\":true,\"count\":5,\"total_count\":6,\"schedules\":" +
                "[{\"url\":\"https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3\",\"" +
                "schedule\":{\"scheduled_time\":\"2015-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device" +
                "_types\":[\"android\",\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2015!\",\"android\"" +
                ":{},\"ios\":{}}},\"push_ids\":[\"8430f2e0-ec07-4c1e-adc4-0c7c7978e648\"]},{\"url\":\"https://go" +
                ".urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973\",\"schedule\":{\"schedule" +
                "d_time\":\"2016-01-01T08:00:00\"},\"push\":{\"audience\":\"ALL\",\"device_types\":[\"android\"," +
                "\"ios\"],\"notification\":{\"alert\":\"Happy New Year 2016!\",\"android\":{},\"ios\":{}}},\"pus" +
                "h_ids\":[\"b217a321-922f-4aee-b239-ca1b58c6b652\"]}]}";

        stubFor(get(urlEqualTo("/api/schedules?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(listscheduleresponse)
                        .withStatus(201)));

        try {
            APIClientResponse<APIListScheduleResponse> response = client.listschedules("https://go.urbanairship.com/api/schedules?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc");

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/schedules?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(UA_APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(
                    urlEqualTo("/api/schedules?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());
            assertNotNull(response.getApiResponse().getCount());
            assertNotNull(response.getApiResponse().getTotal_Count());
            assertNotNull(response.getApiResponse().getSchedules());
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSchedule(){

        // Setup a client and a schedule payload
        APIClient client = APIClient.newBuilder()
                                    .setBaseURI("http://localhost:8080")
                                    .setKey("key")
                                    .setSecret("secret")
                                    .build();

        PushPayload pushPayload = PushPayload.newBuilder()
                                         .setAudience(Selectors.all())
                                         .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                                         .setNotification(Notifications.alert("Foo"))
                                         .build();

        DateTime dateTime = DateTime.now(DateTimeZone.UTC).plusSeconds(60);
        Schedule schedule = Schedule.newBuilder()
                                    .setScheduledTimestamp(dateTime)
                                    .build();

        SchedulePayload schedulePayload = SchedulePayload.newBuilder()
                                                         .setName("Test")
                                                         .setPushPayload(pushPayload)
                                                         .setSchedule(schedule)
                                                         .build();

        // Stub out endpoint
        // Setup a stubbed response for the server
        String pushJSON = "{\"ok\" : true,\"operation_id\" : \"OpID\", \"schedule_urls\":[\"ScheduleURL\"]}";
        stubFor(post(urlEqualTo("/api/schedules/"))
                        .willReturn(aResponse()
                                            .withHeader(CONTENT_TYPE_KEY, APP_JSON)
                                            .withBody(pushJSON)
                                            .withStatus(201)));

        try {
            APIClientResponse<APIScheduleResponse> response = client.schedule(schedulePayload);

            // Verify components of the underlying request
            verify(postRequestedFor(urlEqualTo("/api/schedules/"))
                           .withHeader(CONTENT_TYPE_KEY, equalTo(UA_APP_JSON)));
            List<LoggedRequest> requests = findAll(postRequestedFor(urlEqualTo("/api/schedules/")));
            assertEquals(requests.size(), 1);
            String receivedBody = requests.get(0).getBodyAsString();
            ObjectMapper mapper = PushObjectMapper.getInstance();
            Map<String, Object> result =
                    mapper.readValue(receivedBody,
                                     new TypeReference<Map<String, Object>>() {
                                     });
            String name = (String)result.get("name");
            assertTrue(name.equals("Test"));
            Map<String, String> scheduleMap =
                    (Map<String,String>)result.get("schedule");
            String dateTimeString = scheduleMap.get("scheduled_time");

            // Test DateTime
            DateTime receivedDateTime = DateTime.parse(dateTimeString, DateFormats.DATE_FORMATTER);
            // Server truncates milliseconds off request
            assertEquals(receivedDateTime.getMillis(), dateTime.getMillis(), 1000);

        }
        catch (Exception ex){
            fail("Exception " + ex);
        }
    }
//
//    /*
//    Validate is the exact workflow as push, with the exception of the URL,
//    so focus test on that.
//     */
    @Test
    public void testValidate(){

        // Setup a client and a push payload
        APIClient client = APIClient.newBuilder()
                                    .setBaseURI("http://localhost:8080")
                                    .setKey("key")
                                    .setSecret("secret")
                                    .build();

        PushPayload payload = PushPayload.newBuilder()
                                         .setAudience(Selectors.all())
                                         .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                                         .setNotification(Notifications.alert("Foo"))
                                         .build();

        // Setup a stubbed response for the server
        String pushJSON = "{\"ok\" : true,\"operation_id\" : \"df6a6b50\", \"push_ids\":[\"PushID\"]}";
        stubFor(post(urlEqualTo("/api/push/validate/"))
                        .willReturn(aResponse()
                                            .withHeader(CONTENT_TYPE_KEY, "application/json")
                                            .withBody(pushJSON)
                                            .withStatus(201)));
        try {
            APIClientResponse<APIPushResponse> response = client.validate(payload);

            // Verify components of the underlying HttpRequest
            verify(postRequestedFor(urlEqualTo("/api/push/validate/"))
                           .withHeader(CONTENT_TYPE_KEY, equalTo(UA_APP_JSON)));
            assertNotNull(response);
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }

    }
//
}
