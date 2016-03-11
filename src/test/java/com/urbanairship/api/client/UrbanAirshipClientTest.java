package com.urbanairship.api.client;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ProxyServer;
import com.ning.http.client.uri.Uri;
import com.urbanairship.api.channel.ChannelRequest;
import com.urbanairship.api.channel.ChannelTagRequest;
import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.location.LocationRequest;
import com.urbanairship.api.location.model.BoundedBox;
import com.urbanairship.api.location.model.LocationResponse;
import com.urbanairship.api.location.model.Point;
import com.urbanairship.api.nameduser.NamedUserListingRequest;
import com.urbanairship.api.nameduser.NamedUserRequest;
import com.urbanairship.api.nameduser.NamedUserTagRequest;
import com.urbanairship.api.nameduser.model.NamedUserListingResponse;
import com.urbanairship.api.push.PushRequest;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.PushResponse;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.reports.PlatformStatsRequest;
import com.urbanairship.api.reports.PushDetailRequest;
import com.urbanairship.api.reports.PushInfoRequest;
import com.urbanairship.api.reports.PushListingRequest;
import com.urbanairship.api.reports.PushSeriesRequest;
import com.urbanairship.api.reports.StatisticsCsvRequest;
import com.urbanairship.api.reports.StatisticsRequest;
import com.urbanairship.api.reports.model.PlatformStatsResponse;
import com.urbanairship.api.reports.model.Precision;
import com.urbanairship.api.reports.model.PushDetailResponse;
import com.urbanairship.api.reports.model.PushInfoResponse;
import com.urbanairship.api.reports.model.PushListingResponse;
import com.urbanairship.api.reports.model.PushSeriesResponse;
import com.urbanairship.api.reports.model.StatisticsResponse;
import com.urbanairship.api.schedule.DeleteScheduleRequest;
import com.urbanairship.api.schedule.ListSchedulesOrderType;
import com.urbanairship.api.schedule.ListSchedulesRequest;
import com.urbanairship.api.schedule.ScheduleRequest;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import com.urbanairship.api.segments.SegmentDeleteRequest;
import com.urbanairship.api.segments.SegmentListingRequest;
import com.urbanairship.api.segments.SegmentLookupRequest;
import com.urbanairship.api.segments.SegmentRequest;
import com.urbanairship.api.segments.model.SegmentListingResponse;
import com.urbanairship.api.segments.model.SegmentView;
import org.apache.log4j.BasicConfigurator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.findAll;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UrbanAirshipClientTest {

    public final static String CONTENT_TYPE_KEY = "Content-type";
    public final static String APP_JSON = "application/json";

    static {
        BasicConfigurator.configure();
    }

    private UrbanAirshipClient client;

    // Set up the client
    @Before
    public void setup() {
        client = UrbanAirshipClient.newBuilder()
                .setBaseUri("http://localhost:8080")
                .setKey("key")
                .setSecret("secret")
                .setMaxRetries(5)
                .setMaxPostRetries(5)
                .build();
    }

    @After
    public void takeDown() {
        client.close();
    }

    @ClassRule
    @Rule
    public static WireMockClassRule wireMockClassRule = new WireMockClassRule();


    @Test(expected = NullPointerException.class)
    public void testAPIClientThrowsForNoAppKey() {
        @SuppressWarnings("UnusedAssignment") UrbanAirshipClient apiClient = UrbanAirshipClient.newBuilder()
            .setKey("foo")
            .build();
    }

    @Test(expected = NullPointerException.class)
    public void testAPIClientThrowsForNoAppSecret() {
        @SuppressWarnings("UnusedAssignment") UrbanAirshipClient apiClient = UrbanAirshipClient.newBuilder()
            .setSecret("foo")
            .build();
    }

    @Test
    public void testAPIClientBuilder() {
        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
            .setKey("key")
            .setSecret("secret")
            .build();
        assertEquals("App key incorrect", "key", client.getAppKey());
        assertEquals("App secret incorrect", "secret", client.getAppSecret());
        client.close();
    }

    @Test
    public void testAPIClientBuilderWithOptionalProxyInfo() throws Exception {
        ProxyInfo proxyInfo = ProxyInfo.newBuilder()
            .setHost("host")
            .setProtocol(ProxyInfo.ProxyInfoProtocol.HTTPS)
            .setPrincipal("user")
            .setPassword("password")
            .setPort(8080)
            .build();

        ProxyInfo proxyInfoCopy = ProxyInfo.newBuilder()
            .setHost("host")
            .setProtocol(ProxyInfo.ProxyInfoProtocol.HTTPS)
            .setPrincipal("user")
            .setPassword("password")
            .setPort(8080)
            .build();

        assertEquals(proxyInfo, proxyInfoCopy);

        UrbanAirshipClient proxyClient = UrbanAirshipClient.newBuilder()
            .setKey("key")
            .setSecret("secret")
            .setProxyInfo(proxyInfo)
            .build();

        ProxyServer proxyServer = proxyClient.getClient().getConfig().getProxyServerSelector().select(Uri.create("https://host:8080"));
        assertEquals("host", proxyServer.getHost());
        assertEquals(8080, proxyServer.getPort());
        assertEquals(ProxyServer.Protocol.HTTPS, proxyServer.getProtocol());
        assertEquals("user", proxyServer.getPrincipal());
        assertEquals("password", proxyServer.getPassword());
        proxyClient.close();
    }

    @Test
    public void testGetUserAgent() {
        String userAgent = client.getUserAgent();
        assertNotNull(userAgent);
        assertFalse(userAgent.equals("UNKNOWN"));
        assertFalse(userAgent.equals("UAJavaLib/UNKNOWN"));
        assertFalse(userAgent.equals("UAJavaLib/"));
        assertFalse(userAgent.endsWith("/"));
        assertTrue(userAgent.startsWith("UAJavaLib/"));
    }

    @Test
    public void testAPIClientBuilderWithParams() {
        AsyncHttpClientConfig.Builder configBuilder = new AsyncHttpClientConfig.Builder()
            .setConnectTimeout(20)
            .setWebSocketTimeout(10);

        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
            .setKey("key")
            .setSecret("secret")
            .setClientConfigBuilder(configBuilder)
            .build();

        assertEquals(20, client.getClient().getConfig().getConnectTimeout());
        assertEquals(10, client.getClient().getConfig().getWebSocketTimeout());
        client.close();
    }

    @Test
    public void testBaseUriResolutionWithPath() throws URISyntaxException {
        String base = "https://test.com/big/fun/path/";
        String relative = "/api/push/";
        String expected = "https://test.com/big/fun/path/api/push/";

        URI uriBase = new URI(base);
        URI uriNuResolved = RequestUtils.resolveURI(uriBase, relative);
        assertEquals(expected, uriNuResolved.toString());
    }

    @Test
    public void testBaseUriResolutionWithPathWithoutSlash() throws URISyntaxException {
        String base = "https://test.com/big/fun/path";
        String relative = "/api/push/";
        String expected = "https://test.com/big/fun/path/api/push/";

        URI uriBase = new URI(base);
        URI uriNuResolved = RequestUtils.resolveURI(uriBase, relative);
        assertEquals(expected, uriNuResolved.toString());
    }

    @Test
    public void testBaseUriResolutionWithoutPath() throws URISyntaxException {
        String base = "https://test.com/";
        String relative = "/api/push/";
        String expected = "https://test.com/api/push/";

        URI uriBase = new URI(base);
        URI uriNuResolved = RequestUtils.resolveURI(uriBase, relative);
        assertEquals(expected, uriNuResolved.toString());
    }

    @Test
    public void testBaseUriResolutionWithoutSlash() throws URISyntaxException {
        String base = "https://test.com";
        String relative = "/api/push/";
        String expected = "https://test.com/api/push/";

        URI uriBase = new URI(base);
        URI uriNuResolved = RequestUtils.resolveURI(uriBase, relative);
        assertEquals(expected, uriNuResolved.toString());
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
            final CountDownLatch latch = new CountDownLatch(1);
            Response<PushResponse> response = client.execute(PushRequest.newRequest(payload), new ResponseCallback() {
                @Override
                public void completed(Response response) {
                    latch.countDown();
                }

                @Override
                public void error(Throwable throwable) {

                }
            });
            latch.await();

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
    public void testPushRetry() {

        PushPayload payload = PushPayload.newBuilder()
            .setAudience(Selectors.all())
            .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
            .setNotification(Notifications.alert("Foo"))
            .build();

        // Setup a stubbed response for the server
        String pushJSON = "{\"ok\" : true,\"operation_id\" : \"df6a6b50\", \"push_ids\":[\"PushID\"]}";
        stubFor(post(urlEqualTo("/api/push/")).inScenario("test")
            .whenScenarioStateIs("Started")
            .willReturn(aResponse()
                .withStatus(503))
            .willSetStateTo("Retry"));

        stubFor(post(urlEqualTo("/api/push/")).inScenario("test")
            .whenScenarioStateIs("Retry")
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/json")
                .withBody(pushJSON)
                .withStatus(201)));


        try {
            Response<PushResponse> response = client.execute(PushRequest.newRequest(payload));


            // Verify components of the underlying HttpRequest
            verify(postRequestedFor(urlEqualTo("/api/push/"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(postRequestedFor(
                urlEqualTo("/api/push/")));
            // There should only be one request
            assertEquals(requests.size(), 2);
            // Parse the request using the server side deserializer and check
            // results
            String requestPayload = requests.get(1).getBodyAsString();
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
    public void testRetryIsNonBlocking() {
        client = UrbanAirshipClient.newBuilder()
            .setBaseUri("http://localhost:8080")
            .setKey("key")
            .setSecret("secret")
            .setMaxRetries(1000)
            .build();


        stubFor(get(urlEqualTo("/api/named_users/"))
            .willReturn(aResponse()
                .withStatus(503)));

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

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder()
            .setDaemon(false)
            .build());

        try {
            final Future future = client.executeAsync(NamedUserListingRequest.newRequest());
            Response<PushResponse> response = client.execute(PushRequest.newRequest(payload));

            // Verify components of the underlying HttpRequest
            verify(postRequestedFor(urlEqualTo("/api/push/"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(postRequestedFor(
                urlEqualTo("/api/push/")));
            // There should only be one request
            assertEquals(requests.size(), 1);
            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);

            scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    future.cancel(true);
                }
            }, 5, TimeUnit.MILLISECONDS);

        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception thrown " + ex);
        } finally {
            scheduledExecutorService.shutdown();
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testClientException() {

        PushPayload payload = PushPayload.newBuilder()
            .setAudience(Selectors.all())
            .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
            .setNotification(Notifications.alert("Foo"))
            .build();


        // Setup a stubbed response for the server
        final String errorJSON = "{\"ok\" : false,\"operation_id\" : \"operation id\",\"error\" : \"Invalid push content\",\"error_code\" : 40001,\"details\" : {\"error\" : \"error message\",\"path\" : \"push.wns.text\",\"location\" : {\"line\" : 47,\"column\" : 12}}}";

        stubFor(post(urlEqualTo("/api/push/"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/vnd.urbanairship+json")
                .withBody(errorJSON)
                .withStatus(401)));

        try {
            final CountDownLatch latch = new CountDownLatch(1);
            client.executeAsync(PushRequest.newRequest(payload), new ResponseCallback() {
                @Override
                public void completed(Response response) {
                }

                @Override
                public void error(Throwable throwable) {
                    assertTrue(throwable instanceof ClientException);
                    ClientException clientException = (ClientException) throwable;
                    RequestError error = clientException.getError().get();

                    assertTrue("Operation ID is incorrect",
                        error.getOperationId().get().equals("operation id"));
                    assertTrue("Error code is incorrect",
                        error.getErrorCode().get().equals(40001));
                    RequestErrorDetails details = error.getDetails().get();
                    RequestErrorDetails.Location errorLocation = details.getLocation().get();
                    assertTrue("Location not setup properly",
                        errorLocation.getLine().equals(47));
                    latch.countDown();
                }
            });

            latch.await();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception thrown " + ex);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testServerException() {

        PushPayload payload = PushPayload.newBuilder()
            .setAudience(Selectors.all())
            .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
            .setNotification(Notifications.alert("Foo"))
            .build();


        // Setup a stubbed response for the server
        stubFor(post(urlEqualTo("/api/push/"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/vnd.urbanairship+json")
                .withStatus(503)));

        try {
            final CountDownLatch latch = new CountDownLatch(1);
            client.executeAsync(PushRequest.newRequest(payload), new ResponseCallback() {
                @Override
                public void completed(Response response) {
                }

                @Override
                public void error(Throwable throwable) {
                    assertTrue(throwable instanceof ServerException);
                    latch.countDown();
                }
            });

            latch.await();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception thrown " + ex);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPushWithProxyClient(){

        // Setup a client and a push payload
        UrbanAirshipClient proxyClient = UrbanAirshipClient.newBuilder()
            .setBaseUri("http://localhost:8080")
            .setKey("key")
            .setSecret("secret")
            .setProxyInfo(ProxyInfo.newBuilder()
                .setHost("localhost")
                .setPort(8080)
                .setPrincipal("user")
                .setPassword("password")
                .build())
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
                    .withStatus(201)
            ));


        try {
            Response<PushResponse> response = proxyClient.execute(PushRequest.newRequest(payload));

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

      /*
      Validate is the exact workflow as push, with the exception of the URL,
      so focus test on that.
      */

    @Test
    public void testValidate() {

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
            Response<PushResponse> response = client.execute(PushRequest.newRequest(payload).setValidateOnly(true));

            // Verify components of the underlying HttpRequest
            verify(postRequestedFor(urlEqualTo("/api/push/validate/"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            assertNotNull(response);
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }

    }

    @Test
    public void testListAllSchedules() {

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
            Response<ListAllSchedulesResponse> response = client.execute(ListSchedulesRequest.newRequest());

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/schedules/"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(
                urlEqualTo("/api/schedules/")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertTrue(response.getBody().isPresent());
            assertNotNull(response.getHeaders());
            assertNotNull(response.getStatus());
            assertNotNull(response.getBody().get().getCount());
            assertNotNull(response.getBody().get().getTotal_Count());
            assertNotNull(response.getBody().get().getSchedules());
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListSpecificSchedule() {

        // Setup a stubbed response for the server
        String listscheduleresponse = "{\"schedule\":{\"scheduled_time\":\"2015-08-07T22:10:44\"},\"name\":\"Special Scheduled Push 20\",\"push\":{\"audience\":\"ALL\",\"device_types\":\"all\",\"notification\":{\"alert\":\"Scheduled Push 20\"}},\"push_ids\":[\"274f9aa4-2d00-4911-a043-70129f29adf2\"]}";

        stubFor(get(urlEqualTo("/api/schedules/ee0dd92c-de3b-46dc-9937-c9dcaef0170f"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/json")
                .withBody(listscheduleresponse)
                .withStatus(201)));

        try {
            Response<ListAllSchedulesResponse> response = client.execute(ListSchedulesRequest.newRequest("ee0dd92c-de3b-46dc-9937-c9dcaef0170f"));

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/schedules/ee0dd92c-de3b-46dc-9937-c9dcaef0170f"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(
                urlEqualTo("/api/schedules/ee0dd92c-de3b-46dc-9937-c9dcaef0170f")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertNotNull(response.getStatus());
            assertNotNull(response.getHeaders());
            assertTrue(response.getBody().isPresent());
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListAllSchedulesWithParameters() {
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

        stubFor(get(urlEqualTo("/api/schedules/?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/json")
                .withBody(listscheduleresponse)
                .withStatus(201)));

        try {
            Response<ListAllSchedulesResponse> response = client.execute(ListSchedulesRequest.newRequest(UUID.fromString("643a297a-7313-45f0-853f-e68785e54c77"), 25, ListSchedulesOrderType.ASC));

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/schedules/?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(
                urlEqualTo("/api/schedules/?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertTrue(response.getBody().isPresent());
            assertNotNull(response.getStatus());
            assertNotNull(response.getHeaders());
            assertNotNull(response.getBody().get().getOk());
            assertNotNull(response.getBody().get().getCount());
            assertNotNull(response.getBody().get().getTotal_Count());
            assertNotNull(response.getBody().get().getSchedules());
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListAllSchedulesNextPage() {
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

        stubFor(get(urlEqualTo("/api/schedules/?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/json")
                .withBody(listscheduleresponse)
                .withStatus(201)));

        try {
            Response<ListAllSchedulesResponse> response = client.execute(ListSchedulesRequest.newRequest(URI.create("https://go.urbanairship.com/api/schedules/?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc")));

            // Verify components of the underlying HttpRequest
            verify(getRequestedFor(urlEqualTo("/api/schedules/?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(
                urlEqualTo("/api/schedules/?start=643a297a-7313-45f0-853f-e68785e54c77&limit=25&order=asc")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertTrue(response.getBody().isPresent());
            assertNotNull(response.getHeaders());
            assertNotNull(response.getStatus());
            assertNotNull(response.getBody().get().getOk());
            assertNotNull(response.getBody().get().getCount());
            assertNotNull(response.getBody().get().getTotal_Count());
            assertNotNull(response.getBody().get().getSchedules());
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSchedule() {
        PushPayload pushPayload = PushPayload.newBuilder()
            .setAudience(Selectors.all())
            .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
            .setNotification(Notifications.alert("Foo"))
            .build();

        DateTime dateTime = DateTime.now(DateTimeZone.UTC).plusSeconds(60);
        Schedule schedule = Schedule.newBuilder()
            .setScheduledTimestamp(dateTime)
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
            Response<ScheduleResponse> response = client.execute(ScheduleRequest.newRequest(schedule, pushPayload).setName("Test"));

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

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testUpdateSchedule() {
        PushPayload pushPayload = PushPayload.newBuilder()
            .setAudience(Selectors.all())
            .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
            .setNotification(Notifications.alert("Foo"))
            .build();

        DateTime dateTime = DateTime.now(DateTimeZone.UTC).plusSeconds(60);
        Schedule schedule = Schedule.newBuilder()
            .setScheduledTimestamp(dateTime)
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
            Response<ScheduleResponse> response = client.execute(ScheduleRequest.newUpdateRequest(schedule, pushPayload, "id").setName("test"));

            // Verify components of the underlying request
            verify(putRequestedFor(urlEqualTo("/api/schedules/id"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(putRequestedFor(urlEqualTo("/api/schedules/id")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertNotNull(response.getHeaders());
            assertNotNull(response.getStatus());
            assertTrue(response.getBody().isPresent());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testDeleteSpecificSchedule() {
        stubFor(delete(urlEqualTo("/api/schedules/puppies"))
            .willReturn(aResponse()
                .withStatus(204)));

        try {
            Response<String> response = client.execute(DeleteScheduleRequest.newRequest("puppies"));

            // Verify components of the underlying HttpRequest
            verify(deleteRequestedFor(urlEqualTo("/api/schedules/puppies")));
            List<LoggedRequest> requests = findAll(deleteRequestedFor(
                urlEqualTo("/api/schedules/puppies")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertEquals(204, response.getStatus());
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testChannelTagMutations() {
        stubFor(post(urlEqualTo("/api/channels/tags/"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/json")
                .withStatus(200)));

        ImmutableSet<String> iosChannels = ImmutableSet.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        ChannelTagRequest request = ChannelTagRequest.newRequest()
            .addIOSChannels(iosChannels)
            .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .removeTags("tag_group1", ImmutableSet.of("tag4", "tag5", "tag6"));

        try {
            Response<String> response = client.execute(request);

            List<LoggedRequest> requests = findAll(postRequestedFor(urlEqualTo("/api/channels/tags/")));
            assertEquals(1, requests.size());

            assertNotNull(response);
            assertEquals(200, response.getStatus());

        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }


    @Test
    public void testListChannels() {

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
            "      ]," +
            "      \"tag_groups\": {\n" +
            "        \"testGroup01\" : [\n" +
            "          \"testGroup01Tag01\"\n" +
            "        ]\n" +
            "      }\n" +
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
            "      ],\n" +
            "      \"tag_groups\": {\n" +
            "        \"testGroup02\" : [\n" +
            "          \"testGroup02Tag01\",\n" +
            "          \"testGroup02Tag02\"\n" +
            "        ]\n" +
            "      }\n" +
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
            "      \"tag_groups\": {},\n" +
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
            "      \"tag_groups\": {\n" +
            "        \"testGroup03\": [\n" +
            "          \"testGroup03Tag01\",\n" +
            "          \"testGroup03Tag02\",\n" +
            "          \"testGroup03Tag03\"\n" +
            "        ],\n" +
            "        \"testGroup04\": [\n" +
            "          \"testGroup04Tag01\"\n" +
            "        ]\n" +
            "      },\n" +
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
            "      ],\n" +
            "      \"tag_groups\": {}\n" +
            "    }\n" +
            "  ],\n" +
            "  \"next_page\": \"https:\\/\\/go.urbanairship.com\\/api\\/channels?limit=5&start=0143e4d6-724c-4fc8-bbc6-ca647b8993bf\"\n" +
            "}";

        stubFor(get(urlEqualTo("/api/channels/"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/json")
                .withBody(fiveresponse)
                .withStatus(200)));

        ChannelRequest request = ChannelRequest.newRequest();

        try {
            Response<ChannelResponse> response = client.execute(request);

            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/channels/")));
            assertEquals(1, requests.size());

            assertNotNull(response);
            assertEquals(200, response.getStatus());

        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testNamedUserAssociation() throws IOException {

        stubFor(post(urlEqualTo("/api/named_users/associate"))
            .willReturn(aResponse()
                .withStatus(200)));

        NamedUserRequest request = NamedUserRequest.newAssociationRequest()
            .setNamedUserid("name")
            .setChannel(UUID.randomUUID().toString(), ChannelType.IOS);

        try {
            Response<String> response = client.execute(request);

            // Verify components of the underlying HttpRequest
            verify(postRequestedFor(urlEqualTo("/api/named_users/associate"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(postRequestedFor(
                urlEqualTo("/api/named_users/associate")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertEquals(200, response.getStatus());
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testNamedUserDisassociation() throws IOException {

        stubFor(post(urlEqualTo("/api/named_users/disassociate"))
            .willReturn(aResponse()
                .withStatus(200)));

        NamedUserRequest request = NamedUserRequest.newDisassociationRequest()
            .setChannel(UUID.randomUUID().toString(), ChannelType.IOS);

        try {
            Response<String> response = client.execute(request);

            // Verify components of the underlying HttpRequest
            verify(postRequestedFor(urlEqualTo("/api/named_users/disassociate"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(postRequestedFor(
                urlEqualTo("/api/named_users/disassociate")));
            // There should only be one request
            assertEquals(requests.size(), 1);

            // The response is tested elsewhere, just check that it exists
            assertNotNull(response);
            assertEquals(200, response.getStatus());
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testNamedUsersTagMutations() {

        stubFor(post(urlEqualTo("/api/named_users/tags/"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/json")
                .withStatus(200)));

        ImmutableSet<String> namedUsers = ImmutableSet.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        NamedUserTagRequest request = NamedUserTagRequest.newRequest()
            .addNamedUsers(namedUsers)
            .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .removeTags("tag_group1", ImmutableSet.of("tag4", "tag5", "tag6"));

        try {
            Response<String> response = client.execute(request);

            List<LoggedRequest> requests = findAll(postRequestedFor(urlEqualTo("/api/named_users/tags/")));
            assertEquals(1, requests.size());

            assertNotNull(response);
            assertEquals(200, response.getStatus());

        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListNamedUser() {
        String body = "{  \n" +
            "  \"ok\": true," +
            "  \"named_user\": {\n" +
            "    \"named_user_id\": \"user-id-1234\",\n" +
            "    \"tags\": {\n" +
            "      \"my_fav_tag_group\": [\"tag1\", \"tag2\"]\n" +
            "    },\n" +
            "    \"channels\": [\n" +
            "      {\n" +
            "        \"channel_id\":\"01234567-890a-bcde-f012-34567890abc0\",\n" +
            "        \"device_type\":\"android\",\n" +
            "        \"installed\":true,\n" +
            "        \"opt_in\":false,\n" +
            "        \"push_address\":null,\n" +
            "        \"created\":\"2014-07-12T00:45:01\",\n" +
            "        \"last_registration\":\"2014-08-06T00:33:25\",\n" +
            "        \"alias\":null,\n" +
            "        \"tags\":[  \n" +
            "        ],\n" +
            "        \"tag_groups\": {\n" +
            "          \"tagGroup\": []\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

        stubFor(get(urlEqualTo("/api/named_users/?id=user-id-1234"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/json")
                .withBody(body)
                .withStatus(200)));

        NamedUserListingRequest request = NamedUserListingRequest.newRequest("user-id-1234");

        try {
            Response<NamedUserListingResponse> response = client.execute(request);

            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/named_users/?id=user-id-1234")));
            assertEquals(1, requests.size());

            assertNotNull(response);
            assertEquals(200, response.getStatus());

        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testListAllNamedUsers() {
        String body = "{  \n" +
            "  \"ok\": true," +
            "  \"next_page\": \"https://go.urbanairship.com/api/named_users?start=user-1234\",\n" +
            "  \"named_users\": [\n" +
            "    {\n" +
            "      \"named_user_id\": \"user-id-1234\",\n" +
            "      \"tags\": {\n" +
            "        \"my_fav_tag_group\": [\"tag1\", \"tag2\"]\n" +
            "      },\n" +
            "      \"channels\": [\n" +
            "        {\n" +
            "          \"channel_id\":\"01234567-890a-bcde-f012-34567890abc0\",\n" +
            "          \"device_type\":\"android\",\n" +
            "          \"installed\":true,\n" +
            "          \"opt_in\":false,\n" +
            "          \"push_address\":null,\n" +
            "          \"created\":\"2014-07-12T00:45:01\",\n" +
            "          \"last_registration\":\"2014-08-06T00:33:25\",\n" +
            "          \"alias\":null,\n" +
            "          \"tags\":[  \n" +
            "          ],\n" +
            "          \"tag_groups\": {\n" +
            "            \"tagGroup\": \n" +
            "              [\n" +
            "                \"tag1\",\n" +
            "                \"tag2\"\n" +
            "              ]\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"named_user_id\": \"user-id-5678\",\n" +
            "      \"tags\": {\n" +
            "        \"my_fav_tag_group\": [\"tag3\", \"tag4\"]\n" +
            "      },\n" +
            "      \"channels\": [\n" +
            "        {\n" +
            "          \"channel_id\": \"00000000-0000-0000-0000-000000000000\",\n" +
            "          \"device_type\": \"android\",\n" +
            "          \"installed\": false,\n" +
            "          \"opt_in\": false,\n" +
            "          \"push_address\": null,\n" +
            "          \"created\": \"2012-06-05T20:37:37\",\n" +
            "          \"last_registration\": null,\n" +
            "          \"alias\": null,\n" +
            "          \"tags\": [\"test01\"],\n" +
            "          \"tag_groups\": {\n" +
            "            \"testGroup01\" : [\n" +
            "              \"testGroup01Tag01\"\n" +
            "            ]\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

        stubFor(get(urlEqualTo("/api/named_users/"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/json")
                .withBody(body)
                .withStatus(200)));

        NamedUserListingRequest request = NamedUserListingRequest.newRequest();

        try {
            Response<NamedUserListingResponse> response = client.execute(request);

            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/named_users/")));
            assertEquals(1, requests.size());

            assertNotNull(response);
            assertEquals(200, response.getStatus());

        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }
    }

    @Test
    public void testSinglePushInfo() throws Exception {

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

        PushInfoRequest request = PushInfoRequest.newRequest("abc");

        Response<PushInfoResponse> response = client.execute(request);

        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
        assertEquals(1, requests.size());

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testPushListing() throws Exception {

        String queryPathString = "/api/reports/responses/list/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00";

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

        PushListingRequest request = PushListingRequest.newRequest()
                .start(start)
                .end(end);

        Response<PushListingResponse> response = client.execute(request);

        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
        assertEquals(1, requests.size());

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testPushListingWithOptionalParams() throws Exception {

        String queryPathString = "/api/reports/responses/list/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00&limit=2&push_id_start=start";

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

        PushListingRequest request = PushListingRequest.newRequest()
                .start(start)
                .end(end)
                .limit(2)
                .pushIdStart("start");

        Response<PushListingResponse> response = client.execute(request);

        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
        assertEquals(1, requests.size());

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testStatisticsCsv() throws Exception {
        String queryPathString = "/api/push/stats/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00&format=csv";

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

        DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime end = start.plus(Period.hours(48));

        StatisticsCsvRequest request = StatisticsCsvRequest.newRequest(start, end);

        Response<String> response = client.execute(request);

        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
        assertEquals(1, requests.size());

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testStatistics() throws Exception {

        String queryPathString = "/api/push/stats/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00";

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

        DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime end = start.plus(Period.hours(48));

        StatisticsRequest request = StatisticsRequest.newRequest(start, end);

        Response<List<StatisticsResponse>> response = client.execute(request);

        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
        assertEquals(1, requests.size());

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }


    @Test
    public void testAppOpensReport() throws Exception {

        String queryPathString = "/api/reports/opens/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00&precision=HOURLY";

        String responseString = "{  \n" +
                "  \"next_page\":\"Value for Next Page\",\n" +
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

        DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime end = start.plus(Period.hours(48));
        
        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseString)
                        .withStatus(200)));

        PlatformStatsRequest request = PlatformStatsRequest.newAppOpensRequest()
                .start(start)
                .end(end)
                .precision(Precision.HOURLY);

        Response<PlatformStatsResponse> response = client.execute(request);

        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
        assertEquals(1, requests.size());

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }


    @Test
    public void testTimeInAppReport() throws Exception {

        String queryPathString = "/api/reports/timeinapp/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00&precision=MONTHLY";

        String responseString = "{  \n" +
                "  \"next_page\":\"Value for Next Page\",\n" +
                "   \"timeinapp\":[  \n" +
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

        PlatformStatsRequest request = PlatformStatsRequest.newTimeInAppRequest()
                .start(start)
                .end(end)
                .precision(Precision.MONTHLY);

        Response<PlatformStatsResponse> response = client.execute(request);

        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
        assertEquals(1, requests.size());

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }


    @Test
    public void testOptInsReport() throws Exception {

        String queryPathString = "/api/reports/optins/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00&precision=MONTHLY";

        String responseString = "{  \n" +
                "  \"next_page\":\"Value for Next Page\",\n" +
                "   \"optins\":[  \n" +
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

        PlatformStatsRequest request = PlatformStatsRequest.newOptInsRequest()
                .start(start)
                .end(end)
                .precision(Precision.MONTHLY);

        try {
            Response<PlatformStatsResponse> response = client.execute(request);

            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
            assertEquals(1, requests.size());

            assertNotNull(response);
            assertEquals(200, response.getStatus());

        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }

    }

    @Test
    public void testOptOutsReport() throws Exception {

        String queryPathString = "/api/reports/optouts/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00&precision=MONTHLY";

        String responseString = "{  \n" +
                "  \"next_page\":\"Value for Next Page\",\n" +
                "   \"optouts\":[  \n" +
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

        PlatformStatsRequest request = PlatformStatsRequest.newOptOutsRequest()
                .start(start)
                .end(end)
                .precision(Precision.MONTHLY);

        Response<PlatformStatsResponse> response = client.execute(request);

        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
        assertEquals(1, requests.size());

        assertNotNull(response);
        assertEquals(200, response.getStatus());

    }

    @Test
    public void testPushSendsReport() throws Exception {

        String queryPathString = "/api/reports/sends/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00&precision=MONTHLY";

        String responseString = "{  \n" +
                "  \"next_page\":\"Value for Next Page\",\n" +
                "   \"sends\":[  \n" +
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

        PlatformStatsRequest request = PlatformStatsRequest.newPushSendsRequest()
                .start(start)
                .end(end)
                .precision(Precision.MONTHLY);

        Response<PlatformStatsResponse> response = client.execute(request);

        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
        assertEquals(1, requests.size());

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testPushDetail() throws IOException {
        String queryPathString = "/api/reports/perpush/detail/";

        String responseString = "[ \n" +
                "{  \n" +
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
                "},\n" +
                "{  \n" +
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
                "}\n" +
                "]";

        stubFor(post(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, "application/json")
                        .withBody(responseString)
                        .withStatus(200)));

        PushDetailRequest request = PushDetailRequest.newRequest();
        request.addPushIds("67ef3728-79dc-46b1-a6b9-20081e561f97","57ef3728-79dc-46b1-a6b9-20081e561f97");

        Response<List<PushDetailResponse>> response = client.execute(request);
        List<LoggedRequest> requests = findAll(postRequestedFor(urlEqualTo(queryPathString)));

        assertEquals(1, requests.size());
        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testListPerPushSeries() throws IOException {

        String queryPathString = "/api/reports/perpush/series/push_id";

        String responseString = "{  \n" +
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
                        .withBody(responseString)
                        .withStatus(200)));

        PushSeriesRequest request = PushSeriesRequest.newRequest("push_id");

        Response<PushSeriesResponse> response = client.execute(request);
        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));

        assertEquals(1, requests.size());
        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testListPerPushSeriesWithPrecision() throws IOException {

        String queryPathString = "/api/reports/perpush/series/push_id?precision=DAILY";

        String responseString = "{  \n" +
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
                        .withBody(responseString)
                        .withStatus(200)));

        PushSeriesRequest request = PushSeriesRequest.newRequest("push_id")
                .precision(Precision.DAILY);

        Response<PushSeriesResponse> response = client.execute(request);
        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));

        assertEquals(1, requests.size());
        assertNotNull(response);
        assertEquals(200, response.getStatus());

    }

    @Test
    public void testListPerPushSeriesWithPrecisionWithRange() throws Exception {

        String queryPathString = "/api/reports/perpush/series/push_id?precision=DAILY&start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00";

        String responseString = "{  \n" +
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

        DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime end = start.plus(Period.hours(48));

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseString)
                        .withStatus(200)));

        PushSeriesRequest request = PushSeriesRequest.newRequest("push_id")
                .start(start)
                .end(end)
                .precision(Precision.DAILY);

        Response<PushSeriesResponse> response = client.execute(request);
        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));

        assertEquals(1, requests.size());
        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testLookupSegment() throws Exception {
        String queryPathString = "/api/segments/abc";
        String responseString = "{  \n" +
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

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseString)
                        .withStatus(200)));

        SegmentLookupRequest request = SegmentLookupRequest.newRequest("abc");

        Response<SegmentView> response = client.execute(request);

        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
        assertEquals(1, requests.size());

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testListSegments() throws Exception {
        String queryPathString = "/api/segments/";
        String responseList = "{\n" +
                "   \"next_page\": \"https://go.urbanairship.com/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64\",\n" +
                "   \"segments\": [\n" +
                "      {\n" +
                "        \"creation_date\": 1346248822221,\n" +
                "        \"display_name\": \"segment1\",\n" +
                "        \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6a\",\n" +
                "        \"modification_date\": 1346248822221\n" +
                "      },\n" +
                "      {\n" +
                "        \"creation_date\": 1346248822222,\n" +
                "        \"display_name\": \"segment2\",\n" +
                "        \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6b\",\n" +
                "        \"modification_date\": 1346248822222\n" +
                "      },\n" +
                "      {\n" +
                "        \"creation_date\": 1346248822223,\n" +
                "        \"display_name\": \"segment3\",\n" +
                "        \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6c\",\n" +
                "        \"modification_date\": 1346248822223\n" +
                "      },\n" +
                "      {\n" +
                "        \"creation_date\": 1346248822224,\n" +
                "        \"display_name\": \"segment4\",\n" +
                "        \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6d\",\n" +
                "        \"modification_date\": 1346248822224\n" +
                "      },\n" +
                "      {\n" +
                "        \"creation_date\": 1346248822225,\n" +
                "        \"display_name\": \"segment5\",\n" +
                "        \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6e\",\n" +
                "        \"modification_date\": 1346248822225\n" +
                "      }\n" +
                "   ]\n" +
                "}";

        stubFor(get(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withBody(responseList)
                        .withStatus(200)));

        SegmentListingRequest request = SegmentListingRequest.newRequest();

        Response<SegmentListingResponse> response = client.execute(request);

        List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo(queryPathString)));
        assertEquals(1, requests.size());

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testCreateSegment() throws Exception {
        String queryPathString = "/api/segments/";

        stubFor(post(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, APP_JSON)
                        .withStatus(201)));

        Selector orSelector = Selectors.tags("java", "lib");
        Selector compound = Selectors.and(orSelector, Selectors.tag("mfd"));

        SegmentRequest request = SegmentRequest.newRequest();
        request.setDisplayName("test")
                .setCriteria(compound);

        Response<String> response = client.execute(request);

        verify(postRequestedFor(urlEqualTo(queryPathString)));
        List<LoggedRequest> requests = findAll(postRequestedFor(
                urlEqualTo(queryPathString)));

        assertEquals(requests.size(), 1);
        assertNotNull(response);
        assertEquals(201, response.getStatus());
    }

    @Test
    public void testUpdateSegment() throws Exception {
        String queryPathString = "/api/segments/abc";

        stubFor(put(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE_KEY, APP_JSON)
                        .withStatus(200)));

        Selector orSelector = Selectors.tags("java", "lib");
        Selector compound = Selectors.and(orSelector, Selectors.not(Selectors.tag("mfd")));

        SegmentRequest request = SegmentRequest.newRequest("abc");
        request.setDisplayName("test")
                .setCriteria(compound);

        Response<String> response = client.execute(request);

        verify(putRequestedFor(urlEqualTo(queryPathString)));
        List<LoggedRequest> requests = findAll(putRequestedFor(
                urlEqualTo(queryPathString)));

        assertEquals(requests.size(), 1);
        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testDeleteSegment() throws Exception {
        String queryPathString = "/api/segments/abc";

        stubFor(delete(urlEqualTo(queryPathString))
                .willReturn(aResponse()
                        .withStatus(204)));

        SegmentDeleteRequest request = SegmentDeleteRequest.newRequest("abc");

        Response<String> response = client.execute(request);

        verify(deleteRequestedFor(urlEqualTo(queryPathString)));
        List<LoggedRequest> requests = findAll(deleteRequestedFor(
                urlEqualTo(queryPathString)));

        assertEquals(requests.size(), 1);
        assertNotNull(response);
        assertEquals(204, response.getStatus());
    }

    public void testGetLocationBoundaryInformationQueryType() {

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
            Request<LocationResponse> request = LocationRequest.newQueryRequest("San Francisco").setType("city");
            Response<LocationResponse> response = client.execute(request);

            // Verify components of the underlying request
            verify(getRequestedFor(urlEqualTo("/api/location/?q=San+Francisco&type=city"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/location/?q=San+Francisco&type=city")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertEquals(200, response.getStatus());
            assertTrue(response.getBody().isPresent());
        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testGetLocationBoundaryInformationQuery() {

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
            Request<LocationResponse> request = LocationRequest.newQueryRequest("San Francisco");
            Response<LocationResponse> response = client.execute(request);

            // Verify components of the underlying request
            verify(getRequestedFor(urlEqualTo("/api/location/?q=San+Francisco"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/location/?q=San+Francisco")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertEquals(200, response.getStatus());
            assertTrue(response.getBody().isPresent());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testGetLocationBoundaryInformationPointType() {

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
            Request<LocationResponse> request = LocationRequest.newLatLongRequest(portland).setType("city");
            Response<LocationResponse> response = client.execute(request);

            // Verify components of the underlying request
            verify(getRequestedFor(urlEqualTo("/api/location/45.52,-122.681944?type=city"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/location/45.52,-122.681944?type=city")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertEquals(200, response.getStatus());
            assertTrue(response.getBody().isPresent());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testGetLocationBoundaryInformationPoint() {

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
            Request<LocationResponse> request = LocationRequest.newLatLongRequest(portland);
            Response<LocationResponse> response = client.execute(request);

            // Verify components of the underlying request
            verify(getRequestedFor(urlEqualTo("/api/location/45.52,-122.681944"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/location/45.52,-122.681944")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertEquals(200, response.getStatus());
            assertTrue(response.getBody().isPresent());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testGetLocationBoundaryInformationBoxType() {

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

        BoundedBox california = BoundedBox.newBuilder()
            .setCornerOne(
                Point.newBuilder()
                    .setLatitude(32.5343)
                    .setLongitude(-124.4096)
                    .build())
            .setCornerTwo(
                Point.newBuilder()
                    .setLatitude(42.0095)
                    .setLongitude(-114.1308)
                    .build())
            .build();

        stubFor(get(urlEqualTo("/api/location/32.5343,-124.4096,42.0095,-114.1308?type=city"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/json")
                .withBody(jsonResponse)
                .withStatus(200)));

        try {
            Request<LocationResponse> request = LocationRequest.newBoundingBoxRequest(california).setType("city");
            Response<LocationResponse> response = client.execute(request);

            // Verify components of the underlying request
            verify(getRequestedFor(urlEqualTo("/api/location/32.5343,-124.4096,42.0095,-114.1308?type=city"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/location/32.5343,-124.4096,42.0095,-114.1308?type=city")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertEquals(200, response.getStatus());
            assertTrue(response.getBody().isPresent());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testGetLocationBoundaryInformationBox() {

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

        BoundedBox california = BoundedBox.newBuilder()
            .setCornerOne(
                Point.newBuilder()
                    .setLatitude(32.5343)
                    .setLongitude(-124.4096)
                    .build())
            .setCornerTwo(
                Point.newBuilder()
                    .setLatitude(42.0095)
                    .setLongitude(-114.1308)
                    .build())
            .build();

        stubFor(get(urlEqualTo("/api/location/32.5343,-124.4096,42.0095,-114.1308"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE_KEY, "application/json")
                .withBody(jsonResponse)
                .withStatus(200)));

        try {
            Request<LocationResponse> request = LocationRequest.newBoundingBoxRequest(california);
            Response<LocationResponse> response = client.execute(request);

            // Verify components of the underlying request
            verify(getRequestedFor(urlEqualTo("/api/location/32.5343,-124.4096,42.0095,-114.1308"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            List<LoggedRequest> requests = findAll(getRequestedFor(urlEqualTo("/api/location/32.5343,-124.4096,42.0095,-114.1308")));
            assertEquals(requests.size(), 1);

            assertNotNull(response);
            assertEquals(200, response.getStatus());
            assertTrue(response.getBody().isPresent());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

}
