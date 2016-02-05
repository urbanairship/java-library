package com.urbanairship.api.client;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIListTagsResponse;
import com.urbanairship.api.location.model.BoundedBox;
import com.urbanairship.api.location.model.Point;
import com.urbanairship.api.tag.model.AddRemoveDeviceFromTagPayload;
import com.urbanairship.api.tag.model.AddRemoveSet;
import com.urbanairship.api.tag.model.BatchModificationPayload;
import com.urbanairship.api.tag.model.BatchTagSet;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.log4j.BasicConfigurator;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.HEAD;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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

public class APIClientTest {

    public final static String CONTENT_TYPE_KEY = "Content-type";
    public final static String APP_JSON = "application/json";

    static {
        BasicConfigurator.configure();
    }

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
    public void testGetUserAgent() {
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

    @Test
    public void testAPIClientBuilderWithBasicHttpParams() {
        BasicHttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 10);
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20);

        APIClient client = APIClient.newBuilder()
                .setKey("key")
                .setSecret("secret")
                .setHttpParams(httpParams)
                .build();

        String socketTimeoutName = "http.socket.timeout";
        String connectionTimeoutName = "http.connection.timeout";
        BasicHttpParams retrievedParams = client.getHttpParams().get();

        assertTrue(retrievedParams.getNames().contains(socketTimeoutName));
        assertTrue(retrievedParams.getNames().contains(connectionTimeoutName));
        assertTrue(retrievedParams.getParameter(socketTimeoutName).equals(10));
        assertTrue(retrievedParams.getParameter(connectionTimeoutName).equals(20));
    }

    @Test
    public void testBaseUriResolutionWithPath() throws URISyntaxException {
        String base = "https://test.com/big/fun/path/";
        String relative = "/api/push/";
        String expected = "https://test.com/big/fun/path/api/push/";

        URI uriBase = new URI(base);
        URI uriNuResolved = APIClient.baseURIResolution(uriBase, relative);
        assertEquals(expected, uriNuResolved.toString());
    }

    @Test
    public void testBaseUriResolutionWithPathWithoutSlash() throws URISyntaxException {
        String base = "https://test.com/big/fun/path";
        String relative = "/api/push/";
        String expected = "https://test.com/big/fun/path/api/push/";

        URI uriBase = new URI(base);
        URI uriNuResolved = APIClient.baseURIResolution(uriBase, relative);
        assertEquals(expected, uriNuResolved.toString());
    }

    @Test
    public void testBaseUriResolutionWithoutPath() throws URISyntaxException {
        String base = "https://test.com/";
        String relative = "/api/push/";
        String expected = "https://test.com/api/push/";

        URI uriBase = new URI(base);
        URI uriNuResolved = APIClient.baseURIResolution(uriBase, relative);
        assertEquals(expected, uriNuResolved.toString());
    }

    @Test
    public void testBaseUriResolutionWithoutSlash() throws URISyntaxException {
        String base = "https://test.com";
        String relative = "/api/push/";
        String expected = "https://test.com/api/push/";

        URI uriBase = new URI(base);
        URI uriNuResolved = APIClient.baseURIResolution(uriBase, relative);
        assertEquals(expected, uriNuResolved.toString());
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
            verify(putRequestedFor(urlEqualTo("/api/tags/puppies")));
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
                    .setIOSChannels(AddRemoveSet.newBuilder().remove("device2").build())
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
                            .setDevice(BatchTagSet.DeviceIdTypes.APID, "device1")
                            .addTag("tag1")
                            .addTag("tag2")
                            .build())
                    .addBatchObject(BatchTagSet.newBuilder()
                            .setDevice(BatchTagSet.DeviceIdTypes.ANDROID_CHANNEL, "device2")
                            .addTag("tag3")
                            .addTag("tag4")
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

}
