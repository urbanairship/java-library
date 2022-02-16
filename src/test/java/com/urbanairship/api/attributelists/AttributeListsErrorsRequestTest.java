package com.urbanairship.api.attributelists;


import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AttributeListsErrorsRequestTest {
    private static final String TEST_LIST_NAME = "testlist";
    private static final String ATTRIBUTE_LIST_ERRORS_PATH = "/api/attribute-lists/" + TEST_LIST_NAME + "/errors";
    private static final String OUTPUT_FILE_PATH = "src/test/data/out.csv";

    AttributeListsErrorsRequest request;
    AttributeListsErrorsRequest requestWithFile;


    @Before
    public void setup() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(OUTPUT_FILE_PATH));
        request = AttributeListsErrorsRequest.newRequest(TEST_LIST_NAME);
        requestWithFile = AttributeListsErrorsRequest.newRequest(TEST_LIST_NAME)
            .setOutputStream(fileOutputStream);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), null);
        assertEquals(requestWithFile.getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(requestWithFile.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");
        URI expectedUri = URI.create("https://go.urbanairship.com" + ATTRIBUTE_LIST_ERRORS_PATH);

        assertEquals(request.getUri(baseURI), expectedUri);
        assertEquals(requestWithFile.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_CSV);
        assertEquals(request.getRequestHeaders(), headers);
        assertEquals(requestWithFile.getRequestHeaders(), headers);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(request.getRequestBody(), null);
        assertEquals(requestWithFile.getRequestBody(), null);
    }

    @Test
    public void testParser() throws Exception {
        String response = "8b4de669-16f1-4e71-9a1f-0c62a8235a65,ERROR,\"Unable to parse number: forty-two\"\n" +
                "d5ebe607-a3e6-4601-b97e-83ec604223fe,ERROR,\"Unable to parse date: monday\"\n";


        assertEquals(request.getResponseParser().parse(response), response);
        assertEquals(requestWithFile.getResponseParser().parse(response), response);

        File outFile = new File(OUTPUT_FILE_PATH);
        assertTrue(outFile.exists());

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(outFile)));
        StringBuilder fileString = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            fileString.append(line).append("\n");
        }
        assertEquals(response, fileString.toString());
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            return;
        }
    }

}
