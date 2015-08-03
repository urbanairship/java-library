package com.urbanairship.api.client;

import com.urbanairship.api.push.model.PushResponse;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ResponseTest {

    private final String CONTENT_TYPE_KEY = "content-type";
    private final String CONTENT_TYPE = "application/json";

    @Test
    public void testPushResponse() {
        PushResponse pushResponse = PushResponse.newBuilder()
            .setOk(true)
            .addAllPushIds(Arrays.asList("ID1", "ID2"))
            .setOperationId("OpID")
            .build();

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(httpResponse.getAllHeaders()[0].getName(), httpResponse.getAllHeaders()[0].getValue());

        Response<PushResponse> response = new Response<PushResponse>(pushResponse, headers, httpResponse.getStatusLine().getStatusCode());
        assertTrue("HTTP response body not set properly",
            response.getBody().get().equals(pushResponse));
        assertTrue("HTTP response headers not set properly",
            response.getHeaders().equals(headers));
        assertTrue("HTTP response status not set properly",
            response.getStatus() == httpResponse.getStatusLine().getStatusCode());
    }
}
