package com.urbanairship.api.client;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.google.common.base.Optional;
import com.urbanairship.api.client.model.*;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.location.model.BoundedBox;
import com.urbanairship.api.location.model.Point;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.reports.model.*;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.segments.model.AudienceSegment;
import com.urbanairship.api.segments.model.TagPredicateBuilder;
import com.urbanairship.api.tag.model.AddRemoveDeviceFromTagPayload;
import com.urbanairship.api.tag.model.AddRemoveSet;
import com.urbanairship.api.tag.model.BatchModificationPayload;
import com.urbanairship.api.tag.model.BatchTagSet;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.log4j.BasicConfigurator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

public class APIClientTest {

    public final static String CONTENT_TYPE_KEY = "Content-type";
    public final static String APP_JSON = "application/json";

    static {
        BasicConfigurator.configure();
    }

    private static final Logger logger = LoggerFactory.getLogger(APIClientTest.class);
    @ClassRule
    @Rule
    public static WireMockClassRule wireMockClassRule = new WireMockClassRule();


    @Test(expected = NullPointerException.class)
    public void testAPIClientThrowsForNoAppKey() {
        @SuppressWarnings("UnusedAssignment") APIClient apiClient = APIClient.newBuilder().setKey("foo")
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void testAPIClientThrowsForNoAppSecret() {
        @SuppressWarnings("UnusedAssignment") APIClient apiClient = APIClient.newBuilder().setSecret("foo")
                .build();
    }

    @Test
    public void testAPIClientBuilder() {
        APIClient client = APIClient.newBuilder()
                .setKey("key")
                .setSecret("secret")
                .build();
        assertEquals("App key incorrect", "key", client.getAppKey());
        assertEquals("App secret incorrect", "secret", client.getAppSecret());
        assertFalse(client.getProxyInfo().isPresent());
    }

    @Test
    public void testAPIClientBuilderWithOptionalProxyInfoOptionalCredential() {
        APIClient proxyClient = APIClient.newBuilder()
                .setKey("key")
                .setSecret("secret")
                .setProxyInfo(ProxyInfo.newBuilder()
                        .setProxyHost(new HttpHost("host"))
                        .setProxyCredentials(new UsernamePasswordCredentials("user", "password"))
                        .build())
                .build();

        assertTrue(proxyClient.getProxyInfo().isPresent());
        assertTrue(proxyClient.getProxyInfo().get().getProxyCredentials().isPresent());

        assertEquals(new HttpHost("host"), proxyClient.getProxyInfo().get().getProxyHost());
        assertEquals(new UsernamePasswordCredentials("user", "password"), proxyClient.getProxyInfo().get().getProxyCredentials().get());
    }

    @Test
    public void testAPIClientBuilderWithOptionalProxyInfoNoCredential() {
        APIClient proxyClient = APIClient.newBuilder()
                .setKey("key")
                .setSecret("secret")
                .setProxyInfo(ProxyInfo.newBuilder()
                        .setProxyHost(new HttpHost("host"))
                        .build())
                .build();

        assertTrue(proxyClient.getProxyInfo().isPresent());
        assertFalse(proxyClient.getProxyInfo().get().getProxyCredentials().isPresent());

        assertEquals(new HttpHost("host"), proxyClient.getProxyInfo().get().getProxyHost());
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
    public void testPush() {

        // Setup a client and a push payload
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        assertFalse(client.getProxyInfo().isPresent());

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
                            new TypeReference<Map<String, Object>>() {
                            });
            // Audience
            String audience = (String) result.get("audience");
            assertTrue(audience.equals("ALL"));

            // DeviceType
            List<String> deviceTypeData = (List<String>) result.get("device_types");
            assertTrue(deviceTypeData.get(0).equals("ios"));
            assertEquals(DeviceType.find(deviceTypeData.get(0)).get(), DeviceType.IOS);

            // Notification
            Map<String, String> notification =
                    (Map<String, String>) result.get("notification");
            assertTrue(notification.get("alert").equals("Foo"));

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception thrown " + ex);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPushWithProxyClient(){

        // Setup a client and a push payload
        APIClient proxyClient = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .setProxyInfo(ProxyInfo.newBuilder()
                        .setProxyHost(new HttpHost("localhost", 8080))
                        .setProxyCredentials(new UsernamePasswordCredentials("user", "password"))
                        .build())
                .build();

        assertTrue(proxyClient.getProxyInfo().isPresent());
        assertTrue(proxyClient.getProxyInfo().get().getProxyCredentials().isPresent());

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
                        .withStatus(201)
                ));


        try {
            APIClientResponse<APIPushResponse> response = proxyClient.push(payload);

            // Verify components of the underlying HttpRequest
            verify(postRequestedFor(urlEqualTo("/api/push/"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON))
            );
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
            ex.printStackTrace();
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
            assertNotNull(response.getApiResponse().getOk());
            assertNotNull(response.getApiResponse().getCount());
            assertNotNull(response.getApiResponse().getTotal_Count());
            assertNotNull(response.getApiResponse().getSchedules());
        } catch (Exception ex) {
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
        } catch (Exception ex) {
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
            assertNotNull(response.getApiResponse().getOk());
            assertNotNull(response.getApiResponse().getCount());
            assertNotNull(response.getApiResponse().getTotal_Count());
            assertNotNull(response.getApiResponse().getSchedules());
        } catch (Exception ex) {
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
            assertNotNull(response.getApiResponse().getOk());
            assertNotNull(response.getApiResponse().getCount());
            assertNotNull(response.getApiResponse().getTotal_Count());
            assertNotNull(response.getApiResponse().getSchedules());
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSchedule() {

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
        String pushJSON = "{\"ok\" : true, \"operation_id\" : \"OpID\", \"schedule_urls\":[\"ScheduleURL\"]}";
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
            String name = (String) result.get("name");
            assertTrue(name.equals("Test"));
            Map<String, String> scheduleMap =
                    (Map<String, String>) result.get("schedule");
            //When testing local schedule test instead use
            //String dateTimeString = scheduleMap.get("local_scheduled_time");
            String dateTimeString = scheduleMap.get("scheduled_time");

            // Test DateTime
            DateTime receivedDateTime = DateTime.parse(dateTimeString, DateFormats.DATE_FORMATTER);
            // Server truncates milliseconds off request
            assertEquals(receivedDateTime.getMillis(), dateTime.getMillis(), 1000);

        } catch (Exception ex) {
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
        String responseJson = "{\"ok\" : true, \"operation_id\" : \"OpID\" }";
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

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testDeleteSpecificSchedule() {
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
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    //
//    /*
//    Validate is the exact workflow as push, with the exception of the URL,
//    so focus test on that.
//     */
    @Test
    public void testValidate() {

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
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }

    }

    @Test
    public void testListTags() {
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
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testCreateTag() {
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
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testDeleteTag() {
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
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testAddRemoveDevicesFromTag() {
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
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testBatchModificationofTags() {
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
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testGetLocationBoundaryInformationQueryType() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String jsonResponse = "{\n" +
                "  \"features\":[\n" +
                "    {\n" +
                "      \"type\":\"Feature\",\n" +
                "      \"id\":\"4oFkxX7RcUdirjtaenEQIV\",\n" +
                "      \"properties\":{\n" +
                "        \"source\":\"tiger.census.gov\",\n" +
                "        \"boundary_type_string\":\"City/Place\",\n" +
                "        \"name\":\"San Francisco\",\n" +
                "        \"context\":{\n" +
                "          \"us_state_name\":\"California\",\n" +
                "          \"us_state\":\"CA\"\n" +
                "        },\n" +
                "        \"boundary_type\":\"city\"\n" +
                "      },\n" +
                "      \"bounds\":[\n" +
                "        37.63983,\n" +
                "        -123.173825,\n" +
                "        37.929824,\n" +
                "        -122.28178\n" +
                "      ],\n" +
                "      \"centroid\":[\n" +
                "        37.759715,\n" +
                "        -122.693976\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\":\"Feature\",\n" +
                "      \"id\":\"44jJFKMJg1oeYvv9SImLEx\",\n" +
                "      \"properties\":{\n" +
                "        \"source\":\"tiger.census.gov\",\n" +
                "        \"boundary_type_string\":\"City/Place\",\n" +
                "        \"name\":\"South San Francisco\",\n" +
                "        \"context\":{\n" +
                "          \"us_state_name\":\"California\",\n" +
                "          \"us_state\":\"CA\"\n" +
                "        },\n" +
                "        \"boundary_type\":\"city\"\n" +
                "      },\n" +
                "      \"bounds\":[\n" +
                "        37.633916,\n" +
                "        -122.471883,\n" +
                "        37.673132,\n" +
                "        -122.220531\n" +
                "      ],\n" +
                "      \"centroid\":[\n" +
                "        37.652731,\n" +
                "        -122.343222\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        stubFor(get(urlEqualTo("/api/location/?q=San+Francisco&type=city"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(jsonResponse)
                        .withStatus(200)));

        try {
            APIClientResponse<APILocationResponse> response = client.queryLocationInformation("San Francisco", "city");

            // Verify components of the underlying request
            verify(getRequestedFor(urlEqualTo("/api/location/?q=San+Francisco&type=city"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/location/?q=San+Francisco&type=city")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testGetLocationBoundaryInformationQuery() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String jsonResponse = "{\n" +
                "  \"features\":[\n" +
                "    {\n" +
                "      \"type\":\"Feature\",\n" +
                "      \"id\":\"4oFkxX7RcUdirjtaenEQIV\",\n" +
                "      \"properties\":{\n" +
                "        \"source\":\"tiger.census.gov\",\n" +
                "        \"boundary_type_string\":\"City/Place\",\n" +
                "        \"name\":\"San Francisco\",\n" +
                "        \"context\":{\n" +
                "          \"us_state_name\":\"California\",\n" +
                "          \"us_state\":\"CA\"\n" +
                "        },\n" +
                "        \"boundary_type\":\"city\"\n" +
                "      },\n" +
                "      \"bounds\":[\n" +
                "        37.63983,\n" +
                "        -123.173825,\n" +
                "        37.929824,\n" +
                "        -122.28178\n" +
                "      ],\n" +
                "      \"centroid\":[\n" +
                "        37.759715,\n" +
                "        -122.693976\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\":\"Feature\",\n" +
                "      \"id\":\"44jJFKMJg1oeYvv9SImLEx\",\n" +
                "      \"properties\":{\n" +
                "        \"source\":\"tiger.census.gov\",\n" +
                "        \"boundary_type_string\":\"City/Place\",\n" +
                "        \"name\":\"South San Francisco\",\n" +
                "        \"context\":{\n" +
                "          \"us_state_name\":\"California\",\n" +
                "          \"us_state\":\"CA\"\n" +
                "        },\n" +
                "        \"boundary_type\":\"city\"\n" +
                "      },\n" +
                "      \"bounds\":[\n" +
                "        37.633916,\n" +
                "        -122.471883,\n" +
                "        37.673132,\n" +
                "        -122.220531\n" +
                "      ],\n" +
                "      \"centroid\":[\n" +
                "        37.652731,\n" +
                "        -122.343222\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        stubFor(get(urlEqualTo("/api/location/?q=San+Francisco"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(jsonResponse)
                        .withStatus(200)));

        try {
            APIClientResponse<APILocationResponse> response = client.queryLocationInformation("San Francisco");

            // Verify components of the underlying request
            verify(getRequestedFor(urlEqualTo("/api/location/?q=San+Francisco"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/location/?q=San+Francisco")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testGetLocationBoundaryInformationPointType() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String jsonResponse = "{\n" +
                "  \"features\":[\n" +
                "    {\n" +
                "      \"type\":\"Feature\",\n" +
                "      \"id\":\"4oFkxX7RcUdirjtaenEQIV\",\n" +
                "      \"properties\":{\n" +
                "        \"source\":\"tiger.census.gov\",\n" +
                "        \"boundary_type_string\":\"City/Place\",\n" +
                "        \"name\":\"San Francisco\",\n" +
                "        \"context\":{\n" +
                "          \"us_state_name\":\"California\",\n" +
                "          \"us_state\":\"CA\"\n" +
                "        },\n" +
                "        \"boundary_type\":\"city\"\n" +
                "      },\n" +
                "      \"bounds\":[\n" +
                "        37.63983,\n" +
                "        -123.173825,\n" +
                "        37.929824,\n" +
                "        -122.28178\n" +
                "      ],\n" +
                "      \"centroid\":[\n" +
                "        37.759715,\n" +
                "        -122.693976\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\":\"Feature\",\n" +
                "      \"id\":\"44jJFKMJg1oeYvv9SImLEx\",\n" +
                "      \"properties\":{\n" +
                "        \"source\":\"tiger.census.gov\",\n" +
                "        \"boundary_type_string\":\"City/Place\",\n" +
                "        \"name\":\"South San Francisco\",\n" +
                "        \"context\":{\n" +
                "          \"us_state_name\":\"California\",\n" +
                "          \"us_state\":\"CA\"\n" +
                "        },\n" +
                "        \"boundary_type\":\"city\"\n" +
                "      },\n" +
                "      \"bounds\":[\n" +
                "        37.633916,\n" +
                "        -122.471883,\n" +
                "        37.673132,\n" +
                "        -122.220531\n" +
                "      ],\n" +
                "      \"centroid\":[\n" +
                "        37.652731,\n" +
                "        -122.343222\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Point portland = Point.newBuilder()
                .setLatitude(45.52)
                .setLongitude(-122.681944)
                .build();

        stubFor(get(urlEqualTo("/api/location/45.52,-122.681944?type=city"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(jsonResponse)
                        .withStatus(200)));

        try {
            APIClientResponse<APILocationResponse> response = client.queryLocationInformation(portland, "city");

            // Verify components of the underlying request
            verify(getRequestedFor(urlEqualTo("/api/location/45.52,-122.681944?type=city"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/location/45.52,-122.681944?type=city")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testGetLocationBoundaryInformationPoint() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String jsonResponse = "{\n" +
                "  \"features\":[\n" +
                "    {\n" +
                "      \"type\":\"Feature\",\n" +
                "      \"id\":\"4oFkxX7RcUdirjtaenEQIV\",\n" +
                "      \"properties\":{\n" +
                "        \"source\":\"tiger.census.gov\",\n" +
                "        \"boundary_type_string\":\"City/Place\",\n" +
                "        \"name\":\"San Francisco\",\n" +
                "        \"context\":{\n" +
                "          \"us_state_name\":\"California\",\n" +
                "          \"us_state\":\"CA\"\n" +
                "        },\n" +
                "        \"boundary_type\":\"city\"\n" +
                "      },\n" +
                "      \"bounds\":[\n" +
                "        37.63983,\n" +
                "        -123.173825,\n" +
                "        37.929824,\n" +
                "        -122.28178\n" +
                "      ],\n" +
                "      \"centroid\":[\n" +
                "        37.759715,\n" +
                "        -122.693976\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\":\"Feature\",\n" +
                "      \"id\":\"44jJFKMJg1oeYvv9SImLEx\",\n" +
                "      \"properties\":{\n" +
                "        \"source\":\"tiger.census.gov\",\n" +
                "        \"boundary_type_string\":\"City/Place\",\n" +
                "        \"name\":\"South San Francisco\",\n" +
                "        \"context\":{\n" +
                "          \"us_state_name\":\"California\",\n" +
                "          \"us_state\":\"CA\"\n" +
                "        },\n" +
                "        \"boundary_type\":\"city\"\n" +
                "      },\n" +
                "      \"bounds\":[\n" +
                "        37.633916,\n" +
                "        -122.471883,\n" +
                "        37.673132,\n" +
                "        -122.220531\n" +
                "      ],\n" +
                "      \"centroid\":[\n" +
                "        37.652731,\n" +
                "        -122.343222\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Point portland = Point.newBuilder()
                .setLatitude(45.52)
                .setLongitude(-122.681944)
                .build();

        stubFor(get(urlEqualTo("/api/location/45.52,-122.681944"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(jsonResponse)
                        .withStatus(200)));

        try {
            APIClientResponse<APILocationResponse> response = client.queryLocationInformation(portland);

            // Verify components of the underlying request
            verify(getRequestedFor(urlEqualTo("/api/location/45.52,-122.681944"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/location/45.52,-122.681944")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testGetLocationBoundaryInformationBoxType() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String jsonResponse = "{\n" +
                "  \"features\":[\n" +
                "    {\n" +
                "      \"type\":\"Feature\",\n" +
                "      \"id\":\"4oFkxX7RcUdirjtaenEQIV\",\n" +
                "      \"properties\":{\n" +
                "        \"source\":\"tiger.census.gov\",\n" +
                "        \"boundary_type_string\":\"City/Place\",\n" +
                "        \"name\":\"San Francisco\",\n" +
                "        \"context\":{\n" +
                "          \"us_state_name\":\"California\",\n" +
                "          \"us_state\":\"CA\"\n" +
                "        },\n" +
                "        \"boundary_type\":\"city\"\n" +
                "      },\n" +
                "      \"bounds\":[\n" +
                "        37.63983,\n" +
                "        -123.173825,\n" +
                "        37.929824,\n" +
                "        -122.28178\n" +
                "      ],\n" +
                "      \"centroid\":[\n" +
                "        37.759715,\n" +
                "        -122.693976\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\":\"Feature\",\n" +
                "      \"id\":\"44jJFKMJg1oeYvv9SImLEx\",\n" +
                "      \"properties\":{\n" +
                "        \"source\":\"tiger.census.gov\",\n" +
                "        \"boundary_type_string\":\"City/Place\",\n" +
                "        \"name\":\"South San Francisco\",\n" +
                "        \"context\":{\n" +
                "          \"us_state_name\":\"California\",\n" +
                "          \"us_state\":\"CA\"\n" +
                "        },\n" +
                "        \"boundary_type\":\"city\"\n" +
                "      },\n" +
                "      \"bounds\":[\n" +
                "        37.633916,\n" +
                "        -122.471883,\n" +
                "        37.673132,\n" +
                "        -122.220531\n" +
                "      ],\n" +
                "      \"centroid\":[\n" +
                "        37.652731,\n" +
                "        -122.343222\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        BoundedBox california = new BoundedBox(Point.newBuilder()
                .setLatitude(32.5343)
                .setLongitude(-124.4096)
                .build(), Point.newBuilder()
                .setLatitude(42.0095)
                .setLongitude(-114.1308)
                .build());

        stubFor(get(urlEqualTo("/api/location/32.5343,-124.4096,42.0095,-114.1308?type=city"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(jsonResponse)
                        .withStatus(200)));

        try {
            APIClientResponse<APILocationResponse> response = client.queryLocationInformation(california, "city");

            // Verify components of the underlying request
            verify(getRequestedFor(urlEqualTo("/api/location/32.5343,-124.4096,42.0095,-114.1308?type=city"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/location/32.5343,-124.4096,42.0095,-114.1308?type=city")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testGetLocationBoundaryInformationBox() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String jsonResponse = "{\n" +
                "  \"features\":[\n" +
                "    {\n" +
                "      \"type\":\"Feature\",\n" +
                "      \"id\":\"4oFkxX7RcUdirjtaenEQIV\",\n" +
                "      \"properties\":{\n" +
                "        \"source\":\"tiger.census.gov\",\n" +
                "        \"boundary_type_string\":\"City/Place\",\n" +
                "        \"name\":\"San Francisco\",\n" +
                "        \"context\":{\n" +
                "          \"us_state_name\":\"California\",\n" +
                "          \"us_state\":\"CA\"\n" +
                "        },\n" +
                "        \"boundary_type\":\"city\"\n" +
                "      },\n" +
                "      \"bounds\":[\n" +
                "        37.63983,\n" +
                "        -123.173825,\n" +
                "        37.929824,\n" +
                "        -122.28178\n" +
                "      ],\n" +
                "      \"centroid\":[\n" +
                "        37.759715,\n" +
                "        -122.693976\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\":\"Feature\",\n" +
                "      \"id\":\"44jJFKMJg1oeYvv9SImLEx\",\n" +
                "      \"properties\":{\n" +
                "        \"source\":\"tiger.census.gov\",\n" +
                "        \"boundary_type_string\":\"City/Place\",\n" +
                "        \"name\":\"South San Francisco\",\n" +
                "        \"context\":{\n" +
                "          \"us_state_name\":\"California\",\n" +
                "          \"us_state\":\"CA\"\n" +
                "        },\n" +
                "        \"boundary_type\":\"city\"\n" +
                "      },\n" +
                "      \"bounds\":[\n" +
                "        37.633916,\n" +
                "        -122.471883,\n" +
                "        37.673132,\n" +
                "        -122.220531\n" +
                "      ],\n" +
                "      \"centroid\":[\n" +
                "        37.652731,\n" +
                "        -122.343222\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        BoundedBox california = new BoundedBox(Point.newBuilder()
                .setLatitude(32.5343)
                .setLongitude(-124.4096)
                .build(), Point.newBuilder()
                .setLatitude(42.0095)
                .setLongitude(-114.1308)
                .build());

        stubFor(get(urlEqualTo("/api/location/32.5343,-124.4096,42.0095,-114.1308"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(jsonResponse)
                        .withStatus(200)));

        try {
            APIClientResponse<APILocationResponse> response = client.queryLocationInformation(california);

            // Verify components of the underlying request
            verify(getRequestedFor(urlEqualTo("/api/location/32.5343,-124.4096,42.0095,-114.1308"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/location/32.5343,-124.4096,42.0095,-114.1308")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());

        } catch (Exception ex) {
            fail("Exception " + ex);
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

        } catch (Exception ex) {
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

        } catch (Exception ex) {
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

        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListSegment() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String testresponse = "{  \n" +
                "  \"display_name\":\"2014-11-07T14:26:56.749-08:00\",\n" +
                "  \"criteria\":{  \n" +
                "    \"and\":[  \n" +
                "      {  \n" +
                "        \"location\":{  \n" +
                "          \"us_state\":\"OR\",\n" +
                "          \"date\":{  \n" +
                "            \"days\":{  \n" +
                "              \"start\":\"2014-11-02\",\n" +
                "              \"end\":\"2014-11-07\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      {  \n" +
                "        \"location\":{  \n" +
                "          \"us_state\":\"CA\",\n" +
                "          \"date\":{  \n" +
                "            \"recent\":{  \n" +
                "              \"months\":3\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      {  \n" +
                "        \"or\":[  \n" +
                "          {  \n" +
                "            \"tag\":\"tag1\"\n" +
                "          },\n" +
                "          {  \n" +
                "            \"tag\":\"tag2\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {  \n" +
                "        \"not\":{  \n" +
                "          \"tag\":\"not-tag\"\n" +
                "        }\n" +
                "      },\n" +
                "      {  \n" +
                "        \"not\":{  \n" +
                "          \"and\":[  \n" +
                "            {  \n" +
                "              \"location\":{  \n" +
                "                \"us_state\":\"WA\",\n" +
                "                \"date\":{  \n" +
                "                  \"months\":{  \n" +
                "                    \"start\":\"2011-05\",\n" +
                "                    \"end\":\"2012-02\"\n" +
                "                  }\n" +
                "                }\n" +
                "              }\n" +
                "            },\n" +
                "            {  \n" +
                "              \"tag\":\"woot\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        stubFor(get(urlEqualTo("/api/segments/abc"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(testresponse)
                        .withStatus(200)));

        try {
            APIClientResponse<AudienceSegment> response = client.listSegment("abc");

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/segments/abc"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));

            List<LoggedRequest> requests = findAll(getRequestedFor(
                    urlEqualTo("/api/segments/abc")));

            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertNotNull(response.getApiResponse());
            assertNotNull(response.getHttpResponse());
            assertNotNull(response.getApiResponse().getDisplayName());
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testCreateSegment() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        stubFor(post(urlEqualTo("/api/segments/"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withStatus(200)));

        try {
            HttpResponse response = client.createSegment(
                    AudienceSegment.newBuilder()
                            .setDisplayName("hi")
                            .setRootPredicate(TagPredicateBuilder.newInstance()
                                    .setTag("tag")
                                    .build())
                            .build()
            );

            // Verify components of the underlying HttpRequest
            verify(postRequestedFor(urlEqualTo("/api/segments/"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));

            List<LoggedRequest> requests = findAll(postRequestedFor(
                    urlEqualTo("/api/segments/")));

            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testChangeSegment() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        stubFor(put(urlEqualTo("/api/segments/abc"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withStatus(200)));

        try {
            HttpResponse response = client.changeSegment("abc",
                    AudienceSegment.newBuilder()
                            .setDisplayName("hi")
                            .setRootPredicate(TagPredicateBuilder.newInstance()
                                    .setTag("tag")
                                    .build())
                            .build()
            );

            // Verify components of the underlying HttpRequest
            verify(putRequestedFor(urlEqualTo("/api/segments/abc"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));

            List<LoggedRequest> requests = findAll(putRequestedFor(
                    urlEqualTo("/api/segments/abc")));

            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testDeleteSegment() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        stubFor(delete(urlEqualTo("/api/segments/abc"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withStatus(200)));

        try {
            HttpResponse response = client.deleteSegment("abc");

            // Verify components of the underlying HttpRequest
            verify(deleteRequestedFor(urlEqualTo("/api/segments/abc"))
                    .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));

            List<LoggedRequest> requests = findAll(deleteRequestedFor(
                    urlEqualTo("/api/segments/abc")));

            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListAllChannels() {

        String fiveresponse = "{\n" +
                "  \"ok\": true,\n" +
                "  \"channels\": [\n" +
                "    {\n" +
                "      \"channel_id\": \"00000000-0000-0000-0000-000000000000\",\n" +
                "      \"device_type\": \"android\",\n" +
                "      \"installed\": false,\n" +
                "      \"opt_in\": false,\n" +
                "      \"push_address\": null,\n" +
                "      \"created\": \"2012-06-05T20:37:37\",\n" +
                "      \"last_registration\": null,\n" +
                "      \"alias\": null,\n" +
                "      \"tags\": [\n" +
                "        \"test01\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"channel_id\": \"00662346-9e39-4f5f-80e7-3f8fae58863c\",\n" +
                "      \"device_type\": \"android\",\n" +
                "      \"installed\": true,\n" +
                "      \"opt_in\": true,\n" +
                "      \"background\": true,\n" +
                "      \"push_address\": \"APA91bFPOUF6KNHXjoG0vaQSP4VLXirGDpy0_CRcb6Jhvnrya2bdRmlUoMiJ12JJevjONZzUwFETYa8uzyiE_9WaL3mzZrdjqOv2YuzYlQ_TrXVgo61JmIyw-M_pshIjVvkvtOuZ4MnRJJ_MiQDYwpB4ZhOTMlyqRw\",\n" +
                "      \"created\": \"2014-03-06T18:52:59\",\n" +
                "      \"last_registration\": \"2014-10-07T21:28:35\",\n" +
                "      \"alias\": \"aaron-device\",\n" +
                "      \"tags\": [\n" +
                "        \"aaron-tag\",\n" +
                "        \"rhtgeg\",\n" +
                "        \"tnrvrg\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"channel_id\": \"00d174cd-0a31-427e-95c9-52d5785bcd50\",\n" +
                "      \"device_type\": \"ios\",\n" +
                "      \"installed\": true,\n" +
                "      \"opt_in\": true,\n" +
                "      \"background\": true,\n" +
                "      \"push_address\": \"E4EA0D96092A9213BB186BEF66E83EE226401F82B3A77A1AC8217A8FE8ED4614\",\n" +
                "      \"created\": \"2014-07-09T18:08:37\",\n" +
                "      \"last_registration\": \"2014-10-02T01:41:42\",\n" +
                "      \"alias\": null,\n" +
                "      \"tags\": [\n" +
                "        \"version_1.5.0\"\n" +
                "      ],\n" +
                "      \"ios\": {\n" +
                "        \"badge\": 1,\n" +
                "        \"quiettime\": {\n" +
                "          \"start\": \"17:00\",\n" +
                "          \"end\": \"9:00\"\n" +
                "        },\n" +
                "        \"tz\": \"America\\/Los_Angeles\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"channel_id\": \"00d8cb94-eac9-49fb-bad0-29298a06730e\",\n" +
                "      \"device_type\": \"ios\",\n" +
                "      \"installed\": false,\n" +
                "      \"opt_in\": false,\n" +
                "      \"push_address\": \"21F34C9ED37EAF8D7DC43561C07AA398CA5C6F503196C9E8230C50C0959B8653\",\n" +
                "      \"created\": \"2014-02-22T22:48:37\",\n" +
                "      \"last_registration\": null,\n" +
                "      \"alias\": \"iPhone 7,1\",\n" +
                "      \"tags\": [\n" +
                "        \"kablam\",\n" +
                "        \"version_1.3\"\n" +
                "      ],\n" +
                "      \"ios\": {\n" +
                "        \"badge\": 1,\n" +
                "        \"quiettime\": {\n" +
                "          \"start\": null,\n" +
                "          \"end\": null\n" +
                "        },\n" +
                "        \"tz\": null\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"channel_id\": \"01257ecd-8182-41fe-a741-9fed91b993cb\",\n" +
                "      \"device_type\": \"android\",\n" +
                "      \"installed\": false,\n" +
                "      \"opt_in\": false,\n" +
                "      \"push_address\": null,\n" +
                "      \"created\": \"2013-01-25T00:55:05\",\n" +
                "      \"last_registration\": null,\n" +
                "      \"alias\": null,\n" +
                "      \"tags\": [\n" +
                "        \n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"next_page\": \"https:\\/\\/go.urbanairship.com\\/api\\/channels?limit=5&start=0143e4d6-724c-4fc8-bbc6-ca647b8993bf\"\n" +
                "}";

        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        stubFor(get(urlEqualTo("/api/channels/"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(fiveresponse)
                        .withStatus(200)));

        try {
            APIClientResponse<APIListAllChannelsResponse> response = client.listAllChannels();

            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/channels/")));
            assertEquals(1, requests.size());

            assertNotNull(response);
            assertEquals(200, response.getHttpResponse().getStatusLine().getStatusCode());

        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListPushStatisticsInCSVString() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/push/stats/?start=2014-10-01T12%3A00%3A00.000&end=2014-10-03T12%3A00%3A00.000&format=csv";

        String responseString = "2014-10-01 19:00:00,19,0,0,0,60,0,0\n" +
                "2014-10-01 20:00:00,133,0,0,0,67,0,0\n" +
                "2014-10-01 21:00:00,11,0,0,0,60,0,0\n" +
                "2014-10-01 22:00:00,7,0,0,0,60,0,0\n" +
                "2014-10-01 23:00:00,533,0,0,0,60,0,0\n" +
                "2014-10-02 00:00:00,116,0,0,0,129,0,0\n" +
                "2014-10-02 01:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 02:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 03:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 04:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 05:00:00,1,0,0,0,60,0,0\n" +
                "2014-10-02 06:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 07:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 08:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 09:00:00,1,0,0,0,60,0,0\n" +
                "2014-10-02 10:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 11:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 12:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 13:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 14:00:00,1,0,0,0,60,0,0\n" +
                "2014-10-02 15:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 16:00:00,374,0,0,0,275,0,0\n" +
                "2014-10-02 17:00:00,1,0,0,0,60,0,0\n" +
                "2014-10-02 18:00:00,0,0,0,0,132,0,0\n" +
                "2014-10-02 19:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-02 20:00:00,0,0,0,0,132,0,0\n" +
                "2014-10-02 21:00:00,0,0,0,0,62,0,0\n" +
                "2014-10-02 22:00:00,122,0,0,0,132,0,0\n" +
                "2014-10-02 23:00:00,488,0,0,0,132,0,0\n" +
                "2014-10-03 00:00:00,121,0,0,0,132,0,0\n" +
                "2014-10-03 01:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-03 02:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-03 03:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-03 04:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-03 05:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-03 06:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-03 07:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-03 08:00:00,0,0,0,0,61,0,0\n" +
                "2014-10-03 09:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-03 10:00:00,0,0,0,0,62,0,0\n" +
                "2014-10-03 11:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-03 12:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-03 13:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-03 14:00:00,0,0,0,0,60,0,0\n" +
                "2014-10-03 15:00:00,115,0,0,0,130,0,0\n" +
                "2014-10-03 16:00:00,124,0,0,0,132,0,0\n" +
                "2014-10-03 17:00:00,7,0,0,0,76,0,0\n" +
                "2014-10-03 18:00:00,19,0,0,0,70,0,0\n" +
                "2014-10-03 19:00:00,0,0,0,0,60,0,0";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseString)
                        .withStatus(200)));

        try {
            DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
            DateTime end = start.plus(Period.hours(48));

            APIClientResponse<String> response = client.listPushStatisticsInCSVString(start, end);

            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
            assertEquals(1, requests.size());
            assertNotNull(response);

            assertEquals(200, response.getHttpResponse().getStatusLine().getStatusCode());

            assertEquals(responseString, response.getApiResponse().toString());

        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListPushStatistics() {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/push/stats/?start=2014-10-01T12%3A00%3A00.000&end=2014-10-03T12%3A00%3A00.000&format=json";

        String responseString = "[\n" +
                "    {\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"gcm_messages\": 3,\n" +
                "        \"messages\": 2,\n" +
                "        \"wns_messages\": 0,\n" +
                "        \"start\": \"2014-06-22 00:00:00\",\n" +
                "        \"mpns_messages\": 0,\n" +
                "        \"bb_messages\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"gcm_messages\": 2,\n" +
                "        \"messages\": 0,\n" +
                "        \"wns_messages\": 0,\n" +
                "        \"start\": \"2014-06-22 01:00:00\",\n" +
                "        \"mpns_messages\": 0,\n" +
                "        \"bb_messages\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"messages\": 0,\n" +
                "        \"mpns_messages\": 0,\n" +
                "        \"gcm_messages\": 0,\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"wns_messages\": 0,\n" +
                "        \"start\": \"2014-06-22 02:00:00\",\n" +
                "        \"bb_messages\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"gcm_messages\": 1,\n" +
                "        \"messages\": 3,\n" +
                "        \"wns_messages\": 0,\n" +
                "        \"start\": \"2014-06-22 03:00:00\",\n" +
                "        \"mpns_messages\": 0,\n" +
                "        \"bb_messages\": 0\n" +
                "    }\n" +
                "]";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseString)
                        .withStatus(200)));

        try {
            DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
            DateTime end = start.plus(Period.hours(48));

            APIClientResponse<List<AppStats>> response = client.listPushStatistics(start, end);

            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
            assertEquals(1, requests.size());
            assertNotNull(response);

            assertEquals(200, response.getHttpResponse().getStatusLine().getStatusCode());

        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testListPushStatisticsStartAfterEnd() throws IOException, URISyntaxException, IllegalArgumentException {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/push/stats/?start=2014-10-01T12%3A00%3A00.000&end=2014-10-03T12%3A00%3A00.000&format=json";

        String responseString = "[\n" +
                "    {\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"gcm_messages\": 3,\n" +
                "        \"messages\": 2,\n" +
                "        \"wns_messages\": 0,\n" +
                "        \"start\": \"2014-06-22 00:00:00\",\n" +
                "        \"android_messages\": 0,\n" +
                "        \"mpns_messages\": 0,\n" +
                "        \"bb_messages\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"gcm_messages\": 2,\n" +
                "        \"messages\": 0,\n" +
                "        \"wns_messages\": 0,\n" +
                "        \"start\": \"2014-06-22 01:00:00\",\n" +
                "        \"android_messages\": 0,\n" +
                "        \"mpns_messages\": 0,\n" +
                "        \"bb_messages\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"messages\": 0,\n" +
                "        \"mpns_messages\": 0,\n" +
                "        \"gcm_messages\": 0,\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"wns_messages\": 0,\n" +
                "        \"start\": \"2014-06-22 02:00:00\",\n" +
                "        \"android_messages\": 0,\n" +
                "        \"bb_messages\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"gcm_messages\": 1,\n" +
                "        \"messages\": 3,\n" +
                "        \"wns_messages\": 0,\n" +
                "        \"start\": \"2014-06-22 03:00:00\",\n" +
                "        \"android_messages\": 0,\n" +
                "        \"mpns_messages\": 0,\n" +
                "        \"bb_messages\": 0\n" +
                "    }\n" +
                "]";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseString)
                        .withStatus(200)));

        DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime end = start.minus(Period.hours(48));

        APIClientResponse<List<AppStats>> response = client.listPushStatistics(start, end);
        assertNotNull(response);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testListPushStatisticsStartAfterEndCSV() throws IOException, URISyntaxException, IllegalArgumentException {
        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/push/stats/?start=2014-10-01T12%3A00%3A00.000&end=2014-10-03T12%3A00%3A00.000&format=csv";

        String responseString = "[\n" +
                "    {\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"gcm_messages\": 3,\n" +
                "        \"messages\": 2,\n" +
                "        \"wns_messages\": 0,\n" +
                "        \"start\": \"2014-06-22 00:00:00\",\n" +
                "        \"android_messages\": 0,\n" +
                "        \"mpns_messages\": 0,\n" +
                "        \"bb_messages\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"gcm_messages\": 2,\n" +
                "        \"messages\": 0,\n" +
                "        \"wns_messages\": 0,\n" +
                "        \"start\": \"2014-06-22 01:00:00\",\n" +
                "        \"android_messages\": 0,\n" +
                "        \"mpns_messages\": 0,\n" +
                "        \"bb_messages\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"messages\": 0,\n" +
                "        \"mpns_messages\": 0,\n" +
                "        \"gcm_messages\": 0,\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"wns_messages\": 0,\n" +
                "        \"start\": \"2014-06-22 02:00:00\",\n" +
                "        \"android_messages\": 0,\n" +
                "        \"bb_messages\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"gcm_messages\": 1,\n" +
                "        \"messages\": 3,\n" +
                "        \"wns_messages\": 0,\n" +
                "        \"start\": \"2014-06-22 03:00:00\",\n" +
                "        \"android_messages\": 0,\n" +
                "        \"mpns_messages\": 0,\n" +
                "        \"bb_messages\": 0\n" +
                "    }\n" +
                "]";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseString)
                        .withStatus(200)));

        DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime end = start.minus(Period.hours(48));

        APIClientResponse<String> response = client.listPushStatisticsInCSVString(start, end);
        assertNotNull(response);
    }

    @Test
    public void testListIndividualPushResponseStatistics() throws IOException {

        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/reports/responses/abc";

        String responseString = "{  \n" +
                "  \"push_uuid\":\"5e42ddfc-fa2d-11e2-9ca2-90e2ba025cd0\",\n" +
                "  \"push_time\":\"2013-07-31 22:05:53\",\n" +
                "  \"push_type\":\"BROADCAST_PUSH\",\n" +
                "  \"direct_responses\":4,\n" +
                "  \"sends\":176,\n" +
                "  \"group_id\":\"5e42ddfc-fa2d-11e2-9ca2-90e2ba025cd0\"\n" +
                "}";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseString)
                        .withStatus(200)));

        APIClientResponse<SinglePushInfoResponse> response = client.listIndividualPushResponseStatistics("abc");
        assertNotNull(response);

    }

    @Test
    public void testListReportsListingResponse() throws IOException {

        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/reports/responses/list?start=2014-10-01T12%3A00%3A00.000&end=2014-10-03T12%3A00%3A00.000";

        String responseString = "{  \n" +
                "  \"next_page\":\"Value for Next Page\",\n" +
                "  \"pushes\":[  \n" +
                "    {  \n" +
                "      \"push_uuid\":\"df31cae0-fa3c-11e2-97ce-14feb5d317b8\",\n" +
                "      \"push_time\":\"2013-07-31 23:56:52\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_uuid\":\"3043779a-fa3c-11e2-a22b-d4bed9a887d4\",\n" +
                "      \"push_time\":\"2013-07-31 23:51:58\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_uuid\":\"1c06d01a-fa3c-11e2-aa2d-d4bed9a88699\",\n" +
                "      \"push_time\":\"2013-07-31 23:51:24\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_uuid\":\"a50eb7de-fa3b-11e2-912f-90e2ba025998\",\n" +
                "      \"push_time\":\"2013-07-31 23:48:05\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_uuid\":\"90483c8a-fa3b-11e2-92d0-90e2ba0253a0\",\n" +
                "      \"push_time\":\"2013-07-31 23:47:30\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseString)
                        .withStatus(200)));

        DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime end = start.plus(Period.hours(48));

        APIClientResponse<APIReportsPushListingResponse> response =
                client.listReportsResponseListing(start, end, Optional.<Integer>absent(), Optional.<String>absent());
        assertNotNull(response);
    }

    @Test
    public void testListReportsListingResponseWithOptionalParameters() throws IOException {

        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/reports/responses/list?start=2014-10-01T12%3A00%3A00.000&end=2014-10-03T12%3A00%3A00.000&limit=1&push_id_start=start_push";

        String responseString = "{  \n" +
                "  \"next_page\":\"Value for Next Page\",\n" +
                "  \"pushes\":[  \n" +
                "    {  \n" +
                "      \"push_uuid\":\"df31cae0-fa3c-11e2-97ce-14feb5d317b8\",\n" +
                "      \"push_time\":\"2013-07-31 23:56:52\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_uuid\":\"3043779a-fa3c-11e2-a22b-d4bed9a887d4\",\n" +
                "      \"push_time\":\"2013-07-31 23:51:58\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_uuid\":\"1c06d01a-fa3c-11e2-aa2d-d4bed9a88699\",\n" +
                "      \"push_time\":\"2013-07-31 23:51:24\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_uuid\":\"a50eb7de-fa3b-11e2-912f-90e2ba025998\",\n" +
                "      \"push_time\":\"2013-07-31 23:48:05\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_uuid\":\"90483c8a-fa3b-11e2-92d0-90e2ba0253a0\",\n" +
                "      \"push_time\":\"2013-07-31 23:47:30\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseString)
                        .withStatus(200)));

        DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime end = start.plus(Period.hours(48));

        APIClientResponse<APIReportsPushListingResponse> response =
                client.listReportsResponseListing(start, end, Optional.of(1), Optional.of("start_push"));
        assertNotNull(response);
    }

    @Test
    public void testListPerPushDetail() throws IOException {

        ObjectMapper mapper = APIResponseObjectMapper.getInstance();

        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/reports/perpush/detail/push_id";

        String json = "{  \n" +
                "  \"app_key\":\"some_app_key\",\n" +
                "  \"push_id\":\"57ef3728-79dc-46b1-a6b9-20081e561f97\",\n" +
                "  \"created\":\"2013-07-31 22:05:53\",\n" +
                "  \"push_body\":\"PEJhc2U2NC1lbmNvZGVkIHN0cmluZz4=\",\n" +
                "  \"rich_deletions\":1,\n" +
                "  \"rich_responses\":2,\n" +
                "  \"rich_sends\":3,\n" +
                "  \"sends\":58,\n" +
                "  \"direct_responses\":4,\n" +
                "  \"influenced_responses\":5,\n" +
                "  \"platforms\":{  \n" +
                "    \"android\":{  \n" +
                "      \"direct_responses\":6,\n" +
                "      \"influenced_responses\":7,\n" +
                "      \"sends\":22\n" +
                "    },\n" +
                "    \"ios\":{  \n" +
                "      \"direct_responses\":8,\n" +
                "      \"influenced_responses\":9,\n" +
                "      \"sends\":36\n" +
                "    }\n" +
                "  }\n" +
                "}";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(json)
                        .withStatus(200)));

        APIClientResponse<PerPushDetailResponse> response = client.listPerPushDetail("push_id");
        assertNotNull(response);

        PerPushDetailResponse obj = mapper.readValue(json, PerPushDetailResponse.class);
        assertEquals(obj, response.getApiResponse());
    }

    @Test
    public void testListPerPushSeries() throws IOException {

        ObjectMapper mapper = APIResponseObjectMapper.getInstance();

        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/reports/perpush/series/push_id";

        String json = "{  \n" +
                "  \"app_key\":\"some_app_key\",\n" +
                "  \"push_id\":\"57ef3728-79dc-46b1-a6b9-20081e561f97\",\n" +
                "  \"start\":\"2013-07-25 23:00:00\",\n" +
                "  \"end\":\"2013-07-26 11:00:00\",\n" +
                "  \"precision\":\"HOURLY\",\n" +
                "  \"counts\":[  \n" +
                "    {  \n" +
                "      \"push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"direct_responses\":1,\n" +
                "          \"influenced_responses\":2,\n" +
                "          \"sends\":58\n" +
                "        },\n" +
                "        \"android\":{  \n" +
                "          \"direct_responses\":3,\n" +
                "          \"influenced_responses\":4,\n" +
                "          \"sends\":22\n" +
                "        },\n" +
                "        \"ios\":{  \n" +
                "          \"direct_responses\":5,\n" +
                "          \"influenced_responses\":6,\n" +
                "          \"sends\":36\n" +
                "        }\n" +
                "      },\n" +
                "      \"rich_push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"responses\":7,\n" +
                "          \"sends\":8\n" +
                "        }\n" +
                "      },\n" +
                "      \"time\":\"2013-07-25 23:00:00\"\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"direct_responses\":9,\n" +
                "          \"influenced_responses\":10,\n" +
                "          \"sends\":11\n" +
                "        },\n" +
                "        \"android\":{  \n" +
                "          \"direct_responses\":12,\n" +
                "          \"influenced_responses\":13,\n" +
                "          \"sends\":14\n" +
                "        },\n" +
                "        \"ios\":{  \n" +
                "          \"direct_responses\":15,\n" +
                "          \"influenced_responses\":16,\n" +
                "          \"sends\":17\n" +
                "        }\n" +
                "      },\n" +
                "      \"rich_push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"responses\":18,\n" +
                "          \"sends\":19\n" +
                "        }\n" +
                "      },\n" +
                "      \"time\":\"2013-07-26 00:00:00\"\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"direct_responses\":20,\n" +
                "          \"influenced_responses\":21,\n" +
                "          \"sends\":22\n" +
                "        },\n" +
                "        \"android\":{  \n" +
                "          \"direct_responses\":23,\n" +
                "          \"influenced_responses\":24,\n" +
                "          \"sends\":25\n" +
                "        },\n" +
                "        \"ios\":{  \n" +
                "          \"direct_responses\":26,\n" +
                "          \"influenced_responses\":27,\n" +
                "          \"sends\":28\n" +
                "        }\n" +
                "      },\n" +
                "      \"rich_push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"responses\":29,\n" +
                "          \"sends\":30\n" +
                "        }\n" +
                "      },\n" +
                "      \"time\":\"2013-07-26 01:00:00\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(json)
                        .withStatus(200)));

        APIClientResponse<PerPushSeriesResponse> response = client.listPerPushSeries("push_id");
        assertNotNull(response);

        PerPushSeriesResponse obj = mapper.readValue(json, PerPushSeriesResponse.class);
        assertEquals(obj, response.getApiResponse());
    }

    @Test
    public void testListPerPushSeriesWithPrecision() throws IOException {

        ObjectMapper mapper = APIResponseObjectMapper.getInstance();

        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/reports/perpush/series/push_id?precision=DAILY";

        String json = "{  \n" +
                "  \"app_key\":\"some_app_key\",\n" +
                "  \"push_id\":\"57ef3728-79dc-46b1-a6b9-20081e561f97\",\n" +
                "  \"start\":\"2013-07-25 23:00:00\",\n" +
                "  \"end\":\"2013-07-26 11:00:00\",\n" +
                "  \"precision\":\"HOURLY\",\n" +
                "  \"counts\":[  \n" +
                "    {  \n" +
                "      \"push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"direct_responses\":1,\n" +
                "          \"influenced_responses\":2,\n" +
                "          \"sends\":58\n" +
                "        },\n" +
                "        \"android\":{  \n" +
                "          \"direct_responses\":3,\n" +
                "          \"influenced_responses\":4,\n" +
                "          \"sends\":22\n" +
                "        },\n" +
                "        \"ios\":{  \n" +
                "          \"direct_responses\":5,\n" +
                "          \"influenced_responses\":6,\n" +
                "          \"sends\":36\n" +
                "        }\n" +
                "      },\n" +
                "      \"rich_push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"responses\":7,\n" +
                "          \"sends\":8\n" +
                "        }\n" +
                "      },\n" +
                "      \"time\":\"2013-07-25 23:00:00\"\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"direct_responses\":9,\n" +
                "          \"influenced_responses\":10,\n" +
                "          \"sends\":11\n" +
                "        },\n" +
                "        \"android\":{  \n" +
                "          \"direct_responses\":12,\n" +
                "          \"influenced_responses\":13,\n" +
                "          \"sends\":14\n" +
                "        },\n" +
                "        \"ios\":{  \n" +
                "          \"direct_responses\":15,\n" +
                "          \"influenced_responses\":16,\n" +
                "          \"sends\":17\n" +
                "        }\n" +
                "      },\n" +
                "      \"rich_push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"responses\":18,\n" +
                "          \"sends\":19\n" +
                "        }\n" +
                "      },\n" +
                "      \"time\":\"2013-07-26 00:00:00\"\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"direct_responses\":20,\n" +
                "          \"influenced_responses\":21,\n" +
                "          \"sends\":22\n" +
                "        },\n" +
                "        \"android\":{  \n" +
                "          \"direct_responses\":23,\n" +
                "          \"influenced_responses\":24,\n" +
                "          \"sends\":25\n" +
                "        },\n" +
                "        \"ios\":{  \n" +
                "          \"direct_responses\":26,\n" +
                "          \"influenced_responses\":27,\n" +
                "          \"sends\":28\n" +
                "        }\n" +
                "      },\n" +
                "      \"rich_push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"responses\":29,\n" +
                "          \"sends\":30\n" +
                "        }\n" +
                "      },\n" +
                "      \"time\":\"2013-07-26 01:00:00\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(json)
                        .withStatus(200)));

        APIClientResponse<PerPushSeriesResponse> response = client.listPerPushSeries("push_id", "DAILY");
        assertNotNull(response);

        PerPushSeriesResponse obj = mapper.readValue(json, PerPushSeriesResponse.class);
        assertEquals(obj, response.getApiResponse());
    }

    @Test
    public void testListPerPushSeriesWithPrecisionWithRange() throws IOException {

        ObjectMapper mapper = APIResponseObjectMapper.getInstance();

        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/reports/perpush/series/push_id?precision=DAILY&start=2014-10-01T12%3A00%3A00.000&end=2014-10-03T12%3A00%3A00.000";

        String json = "{  \n" +
                "  \"app_key\":\"some_app_key\",\n" +
                "  \"push_id\":\"57ef3728-79dc-46b1-a6b9-20081e561f97\",\n" +
                "  \"start\":\"2013-07-25 23:00:00\",\n" +
                "  \"end\":\"2013-07-26 11:00:00\",\n" +
                "  \"precision\":\"HOURLY\",\n" +
                "  \"counts\":[  \n" +
                "    {  \n" +
                "      \"push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"direct_responses\":1,\n" +
                "          \"influenced_responses\":2,\n" +
                "          \"sends\":58\n" +
                "        },\n" +
                "        \"android\":{  \n" +
                "          \"direct_responses\":3,\n" +
                "          \"influenced_responses\":4,\n" +
                "          \"sends\":22\n" +
                "        },\n" +
                "        \"ios\":{  \n" +
                "          \"direct_responses\":5,\n" +
                "          \"influenced_responses\":6,\n" +
                "          \"sends\":36\n" +
                "        }\n" +
                "      },\n" +
                "      \"rich_push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"responses\":7,\n" +
                "          \"sends\":8\n" +
                "        }\n" +
                "      },\n" +
                "      \"time\":\"2013-07-25 23:00:00\"\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"direct_responses\":9,\n" +
                "          \"influenced_responses\":10,\n" +
                "          \"sends\":11\n" +
                "        },\n" +
                "        \"android\":{  \n" +
                "          \"direct_responses\":12,\n" +
                "          \"influenced_responses\":13,\n" +
                "          \"sends\":14\n" +
                "        },\n" +
                "        \"ios\":{  \n" +
                "          \"direct_responses\":15,\n" +
                "          \"influenced_responses\":16,\n" +
                "          \"sends\":17\n" +
                "        }\n" +
                "      },\n" +
                "      \"rich_push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"responses\":18,\n" +
                "          \"sends\":19\n" +
                "        }\n" +
                "      },\n" +
                "      \"time\":\"2013-07-26 00:00:00\"\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"direct_responses\":20,\n" +
                "          \"influenced_responses\":21,\n" +
                "          \"sends\":22\n" +
                "        },\n" +
                "        \"android\":{  \n" +
                "          \"direct_responses\":23,\n" +
                "          \"influenced_responses\":24,\n" +
                "          \"sends\":25\n" +
                "        },\n" +
                "        \"ios\":{  \n" +
                "          \"direct_responses\":26,\n" +
                "          \"influenced_responses\":27,\n" +
                "          \"sends\":28\n" +
                "        }\n" +
                "      },\n" +
                "      \"rich_push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"responses\":29,\n" +
                "          \"sends\":30\n" +
                "        }\n" +
                "      },\n" +
                "      \"time\":\"2013-07-26 01:00:00\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(json)
                        .withStatus(200)));

        DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime end = start.plus(Period.hours(48));

        APIClientResponse<PerPushSeriesResponse> response = client.listPerPushSeries("push_id", "DAILY", start, end);
        assertNotNull(response);

        PerPushSeriesResponse obj = mapper.readValue(json, PerPushSeriesResponse.class);
        assertEquals(obj, response.getApiResponse());
    }

    @Test
    public void testListAppsOpenReport() throws IOException {

        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/reports/opens/?precision=MONTHLY&start=2014-10-01T12%3A00%3A00.000&end=2014-10-03T12%3A00%3A00.000";

        String responseString = "{  \n" +
                "  \"opens\":[  \n" +
                "    {  \n" +
                "      \"date\":\"2013-07-01 00:00:00\",\n" +
                "      \"ios\":1470,\n" +
                "      \"android\":458\n" +
                "    },\n" +
                "    {  \n" +
                "      \"date\":\"2013-08-01 00:00:00\",\n" +
                "      \"ios\":1662,\n" +
                "      \"android\":523\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseString)
                        .withStatus(200)));

        DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime end = start.plus(Period.hours(48));

        APIClientResponse<ReportsAPIOpensResponse> response = client.listAppsOpenReport(start, end, "monthly");
        assertNotNull(response);

    }

    @Test
    public void testListTimeInAppReport() throws IOException {

        // Setup a client
        APIClient client = APIClient.newBuilder()
                .setBaseURI("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .build();

        String queryPathString = "/api/reports/timeinapp/?precision=MONTHLY&start=2014-10-01T12%3A00%3A00.000&end=2014-10-03T12%3A00%3A00.000";

        String responseString = "{  \n" +
                "  \"timeinapp\":[  \n" +
                "    {  \n" +
                "      \"date\":\"2013-07-01 00:00:00\",\n" +
                "      \"ios\":145436.44,\n" +
                "      \"android\":193246.86\n" +
                "    },\n" +
                "    {  \n" +
                "      \"date\":\"2013-08-01 00:00:00\",\n" +
                "      \"ios\":45608.027,\n" +
                "      \"android\":100203.02\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseString)
                        .withStatus(200)));

        DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime end = start.plus(Period.hours(48));

        APIClientResponse<ReportsAPITimeInAppResponse> response = client.listTimeInAppReport(start, end, "monthly");
        assertNotNull(response);

    }
}
