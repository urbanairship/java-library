package com.urbanairship.api.attributelists;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AttributeListsUploadRequestTest {
    private static final String TEST_LIST_NAME = "testlist";
    private static final String TEST_CSV_FILE = (new File("src/test/data")).getAbsolutePath() + "/test2.csv";
    private static final String ATTRIBUTE_LISTS_UPLOAD_PATH = "/api/attribute-lists/" + TEST_LIST_NAME + "/csv";
    private static final String CONTENT_ENCODING_GZIP = "gzip";

    AttributeListsUploadRequest request;

    @Before
    public void setup() {
        request = AttributeListsUploadRequest.newRequest(TEST_LIST_NAME, TEST_CSV_FILE);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), ContentType.TEXT_PLAIN);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.PUT);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedUri = URI.create("https://go.urbanairship.com" + ATTRIBUTE_LISTS_UPLOAD_PATH);
        assertEquals(request.getUri(baseURI), expectedUri);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_TEXT_CSV);
        assertEquals(request.getRequestHeaders(), headers);

        request.setGzipEnabled(true);
        headers.put(HttpHeaders.CONTENT_ENCODING, CONTENT_ENCODING_GZIP);
        assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testBody() throws Exception {
        String expected = "channel_id,Magic Score,Preferred Sport\n" +
                "c543f3a3-bc1d-4830-8dee-7532c6a23b9a,100,Basketball\n" +
                "6ba360a0-1f73-4ee7-861e-95f6c1ed6410,,Basketball\n" +
                "15410d17-687c-46fa-bbd9-f255741a1523,2,Football\n" +
                "c2c64ef7-8f5c-470e-915f-f5e3da04e1df,22.1,Rugby\n";

        assertEquals(expected, request.getRequestBody());
    }

    @Test
    public void testParser() throws Exception {
        String response = "{\"ok\": true}";
        assertEquals(response, request.getResponseParser().parse(response));
    }
}
