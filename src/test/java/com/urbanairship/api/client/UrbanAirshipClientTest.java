package com.urbanairship.api.client;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIPushResponse;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.PushRequest;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.log4j.BasicConfigurator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.findAll;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
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

    @ClassRule
    @Rule
    public static WireMockClassRule wireMockClassRule = new WireMockClassRule();


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
        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
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
            APIClientResponse<APIPushResponse> response = client.execute(PushRequest.createPushRequest(payload));

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
        UrbanAirshipClient proxyClient = UrbanAirshipClient.newBuilder()
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
            APIClientResponse<APIPushResponse> response = proxyClient.execute(PushRequest.createPushRequest(payload));

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

        // Setup a client and a push payload
        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
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
            APIClientResponse<APIPushResponse> response = client.execute(PushRequest.createValidateRequest(payload));

            // Verify components of the underlying HttpRequest
            verify(postRequestedFor(urlEqualTo("/api/push/validate/"))
                .withHeader(CONTENT_TYPE_KEY, equalTo(APP_JSON)));
            assertNotNull(response);
        } catch (Exception ex) {
            fail("Exception thrown " + ex);
        }

    }
}
