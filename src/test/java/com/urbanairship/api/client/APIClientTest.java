package com.urbanairship.api.client;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.urbanairship.api.client.model.*;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.push.model.*;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.tag.model.AddRemoveDeviceFromTagPayload;
import com.urbanairship.api.tag.model.AddRemoveSet;
import com.urbanairship.api.tag.model.BatchModificationPayload;
import com.urbanairship.api.tag.model.BatchTagSet;
import org.apache.http.HttpResponse;
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
            "application/vnd.urbanairship+json; version=3;";

    static {
        BasicConfigurator.configure();
    }

    @ClassRule
    @Rule
    public static WireMockClassRule wireMockClassRule = new WireMockClassRule();


    @Test(expected = NullPointerException.class)
    public void testAPIClientThrowsForNoAppKey(){
        @SuppressWarnings("UnusedAssignment") APIClient apiClient = APIClient.newBuilder().setKey("foo")
                .build();
    }

    @Test(expected = NullPointerException.class)
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

    @Test
    public void testGetUserAgent(){
        APIClient client = APIClient.newBuilder()
                .setKey("key")
                .setSecret("secret")
                .build();
        String userAgent = client.getUserAgent();
        assertNotNull(userAgent);
        assertFalse(userAgent.equals("UNKNOWN"));
        assertFalse(userAgent.equals("UAJavaLib/UNKNOWN"));
        assertFalse(userAgent.equals("UAJavaLib/"));
        assertFalse(userAgent.endsWith("/"));
        assertTrue(userAgent.startsWith("UAJavaLib/"));
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
                           .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
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
    public void testListAllSchedules() {
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
            APIClientResponse<APIListAllSchedulesResponse> response = client.listAllSchedules();

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/schedules/"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
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
    public void testListSpecificSchedule() {
        // Setup a client and a schedule payload
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        // Setup a stubbed response for the server
        String listscheduleresponse = "{\"schedule\":{\"scheduled_time\":\"2015-08-07T22:10:44\"},\"name\":\"Special Scheduled Push 20\",\"push\":{\"audience\":\"ALL\",\"device_types\":\"all\",\"notification\":{\"alert\":\"Scheduled Push 20\"}},\"push_ids\":[\"274f9aa4-2d00-4911-a043-70129f29adf2\"]}";

        stubFor(get(urlEqualTo("/api/schedules/ee0dd92c-de3b-46dc-9937-c9dcaef0170f"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(listscheduleresponse)
                        .withStatus(201)));

        try {
            APIClientResponse<SchedulePayload> response = client.listSchedule("ee0dd92c-de3b-46dc-9937-c9dcaef0170f");

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/schedules/ee0dd92c-de3b-46dc-9937-c9dcaef0170f"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(
                    urlEqualTo("/api/schedules/ee0dd92c-de3b-46dc-9937-c9dcaef0170f")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListAllSchedulesWithParameters() {
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
            APIClientResponse<APIListAllSchedulesResponse> response = client.listAllSchedules("643a297a-7313-45f0-853f-e68785e54c77", 25, "asc");

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/schedules?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
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
    public void testListAllSchedulesNextPage() {
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
            APIClientResponse<APIListAllSchedulesResponse> response = client.listAllSchedules("https://go.urbanairship.com/api/schedules?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc");

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/schedules?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
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
                //To test local schedule time instead build
                //                  .setLocalScheduledTimestamp(dateTime)
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
                           .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
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
            //When testing local schedule test instead use
            //String dateTimeString = scheduleMap.get("local_scheduled_time");
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

    @Test
    public void testUpdateSchedule() {

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
        String responseJson = "{\"ok\" : true,\"operation_id\" : \"OpID\" }";
        stubFor(put(urlEqualTo("/api/schedules/id"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, APP_JSON)
                        .withBody(responseJson)
                        .withStatus(201)));

        try {
            APIClientResponse<APIScheduleResponse> response = client.updateSchedule(schedulePayload, "id");

            // Verify components of the underlying request
            verify(putRequestedFor(urlEqualTo("/api/schedules/id"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(putRequestedFor(urlEqualTo("/api/schedules/id")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());

        }
        catch (Exception ex){
            fail("Exception " + ex);
        }
    }

    @Test
    public void testDeleteSpecificSchedule(){
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        stubFor(delete(urlEqualTo("/api/schedules/puppies"))
                .willReturn(aResponse()
                        .withStatus(204)));

        try {
            HttpResponse response = client.deleteSchedule("puppies");

            // Verify components of the underlying HttpRequest
            verify(deleteRequestedFor(urlEqualTo("/api/schedules/puppies"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(deleteRequestedFor(
                    urlEqualTo("/api/schedules/puppies")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertEquals(204, response.getStatusLine().getStatusCode());
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
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
                           .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            assertNotNull(response);
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }

    }

    @Test
    public void testListTags(){
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        // Setup a stubbed response for the server
        String listtagresponse = "{\"tags\":[\"Puppies\",\"Kitties\",\"GrumpyCat\"]}";

        stubFor(get(urlEqualTo("/api/tags/"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(listtagresponse)
                        .withStatus(200)));

        try {
            APIClientResponse<APIListTagsResponse> response = client.listTags();

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/tags/"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(
                    urlEqualTo("/api/tags/")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testCreateTag(){
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        stubFor(put(urlEqualTo("/api/tags/puppies"))
                .willReturn(aResponse()
                        .withStatus(201)));

        try {
            HttpResponse response = client.createTag("puppies");

            // Verify components of the underlying HttpRequest
            verify(putRequestedFor(urlEqualTo("/api/tags/puppies"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(putRequestedFor(
                    urlEqualTo("/api/tags/puppies")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertEquals(201, response.getStatusLine().getStatusCode());
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testDeleteTag(){
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        stubFor(delete(urlEqualTo("/api/tags/puppies"))
                .willReturn(aResponse()
                        .withStatus(204)));

        try {
            HttpResponse response = client.deleteTag("puppies");

            // Verify components of the underlying HttpRequest
            verify(deleteRequestedFor(urlEqualTo("/api/tags/puppies"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(deleteRequestedFor(
                    urlEqualTo("/api/tags/puppies")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertEquals(204, response.getStatusLine().getStatusCode());
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testAddRemoveDevicesFromTag(){
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        stubFor(post(urlEqualTo("/api/tags/puppies"))
                .willReturn(aResponse()
                        .withStatus(200)));

        try {
            HttpResponse response = client.addRemoveDevicesFromTag("puppies", AddRemoveDeviceFromTagPayload.newBuilder()
                    .setApids(AddRemoveSet.newBuilder().add("device1").build())
                    .build());

            // Verify components of the underlying HttpRequest
            List<LoggedRequest> requests = findAll(postRequestedFor(
                    urlEqualTo("/api/tags/puppies")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testBatchModificationofTags(){
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        stubFor(post(urlEqualTo("/api/tags/batch/"))
                .willReturn(aResponse()
                        .withStatus(200)));

        try {
            HttpResponse response = client.batchModificationOfTags(BatchModificationPayload.newBuilder()
                    .addBatchObject(BatchTagSet.newBuilder()
                            .setDevice(BatchTagSet.DEVICEIDTYPES.APID, "device1")
                            .addTag("tag1")
                            .addTag("tag2")
                            .build())
                    .build());

            // Verify components of the underlying HttpRequest
            List<LoggedRequest> requests = findAll(postRequestedFor(
                    urlEqualTo("/api/tags/batch/")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListAllSegments() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String testresponse = "{\n" +
                "   \"next_page\": \"https://go.urbanairship.com/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64\",\n" +
                "   \"segments\": [\n" +
                "      {\n" +
                "         \"creation_date\": 1346248822220,\n" +
                "         \"display_name\": \"A segment\",\n" +
                "         \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6b\",\n" +
                "         \"modification_date\": 1346248822221\n" +
                "      }\n" +
                "   ]\n" +
                "}";

        stubFor(get(urlEqualTo("/api/segments/"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withHeader("Link", "NextPage")
                        .withBody(testresponse)
                        .withStatus(200)));

        try {
            APIClientResponse<APIListAllSegmentsResponse> response = client.listAllSegments();

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/segments/"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));

            List<LoggedRequest> requests = findAll(getRequestedFor(
                    urlEqualTo("/api/segments/")));

            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());
            assertNotNull(response.getApiResponse().getNextPage());
            assertNotNull(response.getApiResponse().getSegments());

        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListAllSegmentsNextPage() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String testresponse = "{\n" +
                "   \"next_page\": \"https://go.urbanairship.com/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64\",\n" +
                "   \"segments\": [\n" +
                "      {\n" +
                "         \"creation_date\": 1346248822220,\n" +
                "         \"display_name\": \"A segment\",\n" +
                "         \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6b\",\n" +
                "         \"modification_date\": 1346248822221\n" +
                "      }\n" +
                "   ]\n" +
                "}";

        stubFor(get(urlEqualTo("/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withHeader("Link", "NextPage")
                        .withBody(testresponse)
                        .withStatus(200)));

        String nextPage = "https://go.urbanairship.com/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64";

        try {
            APIClientResponse<APIListAllSegmentsResponse> response = client.listAllSegments(nextPage);

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));

            List<LoggedRequest> requests = findAll(getRequestedFor(
                    urlEqualTo("/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64")));

            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());
            assertNotNull(response.getApiResponse().getNextPage());
            assertNotNull(response.getApiResponse().getSegments());

        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListAllSegmentsWithParameters() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String testresponse = "{\n" +
                "   \"next_page\": \"https://go.urbanairship.com/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64\",\n" +
                "   \"segments\": [\n" +
                "      {\n" +
                "         \"creation_date\": 1346248822220,\n" +
                "         \"display_name\": \"A segment\",\n" +
                "         \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6b\",\n" +
                "         \"modification_date\": 1346248822221\n" +
                "      }\n" +
                "   ]\n" +
                "}";

        stubFor(get(urlEqualTo("/api/segments?start=3832cf72-cb44-4132-a11f-eafb41b82f64&limit=1&order=asc"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withHeader("Link", "NextPage")
                        .withBody(testresponse)
                        .withStatus(200)));

        try {
            APIClientResponse<APIListAllSegmentsResponse> response = client.listAllSegments("3832cf72-cb44-4132-a11f-eafb41b82f64", 1, "asc");

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/segments?start=3832cf72-cb44-4132-a11f-eafb41b82f64&limit=1&order=asc"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));

            List<LoggedRequest> requests = findAll(getRequestedFor(
                    urlEqualTo("/api/segments?start=3832cf72-cb44-4132-a11f-eafb41b82f64&limit=1&order=asc")));

            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());
            assertNotNull(response.getApiResponse().getNextPage());
            assertNotNull(response.getApiResponse().getSegments());

        }
        catch (Exception ex){
            fail("Exception thrown " + ex);
        }
    }
}
