package com.urbanairship.api.oauth;

import com.urbanairship.api.client.Request;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.apache.http.ProtocolVersion;

public class PublicKeyVerificationRequestTest {

    @Test
    public void testRequestWithEuropeanSite() throws URISyntaxException {
        String keyId = "8817e96";
        PublicKeyVerificationRequest request = PublicKeyVerificationRequest.newRequest(keyId, true);

        assertEquals(Request.HttpMethod.GET, request.getHttpMethod());
        assertNull(request.getRequestBody());
        assertEquals(ContentType.TEXT_PLAIN, request.getContentType());

        URI expectedUri = new URI("https://oauth2.asnapieu.com/verify/public_key/" + keyId);
        assertEquals(expectedUri, request.getUri(new URI("https://oauth2.asnapieu.com")));

        Map<String, String> headers = request.getRequestHeaders();
        assertNotNull( headers);
        assertTrue( headers.containsKey("Accept"));
        assertEquals( "text/plain", headers.get("Accept"));
    }

    @Test
    public void testRequestWithNorthAmericanSite() throws URISyntaxException {
        String keyId = "8817e96";
        PublicKeyVerificationRequest request = PublicKeyVerificationRequest.newRequest(keyId);

        URI expectedUri = new URI("https://oauth2.asnapius.com/verify/public_key/" + keyId);
        assertEquals(expectedUri, request.getUri(new URI("https://oauth2.asnapius.com")));
    }
    @Test
    public void testResponseParsing() throws Exception {
        HttpResponse httpResponse = new BasicHttpResponse(new ProtocolVersion("HTTP", 1, 1), HttpStatus.SC_OK, "OK");
        String publicKeyPem = "-----BEGIN PUBLIC KEY-----\nMHYwEAYHKoZIzj0CAQYFK4EEACID+fxyDr9Ye/oiUewMwwlp0z5AMPjgBUIKS\n-----END PUBLIC KEY-----";
        httpResponse.setEntity(new StringEntity(publicKeyPem));

        String responseString = EntityUtils.toString(httpResponse.getEntity());

        assertNotNull(responseString);
        assertEquals(publicKeyPem, responseString);
    }
}
