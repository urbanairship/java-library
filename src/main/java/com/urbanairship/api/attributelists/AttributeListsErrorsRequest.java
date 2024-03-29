/*
 * Copyright (c) 2013-2022.  Airship and Contributors
 */

package com.urbanairship.api.attributelists;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import com.urbanairship.api.common.CSVUtils;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
 * The AttributeListsErrorsRequest class attribute list errors request to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class AttributeListsErrorsRequest implements Request<String> {
    private final static String ATTRIBUTE_LISTS_PATH = "/api/attribute-lists/";
    private final String path;

    private Optional<FileOutputStream> fileOutputStream = Optional.empty();

    private AttributeListsErrorsRequest(String name) {
        this.path = ATTRIBUTE_LISTS_PATH + name + "/errors";
    }

    /**
     * Build a attribute list errors request.
     *
     * @param name The name of the list as a string.
     * @return AttributeListsErrorsRequest
     */
    public static AttributeListsErrorsRequest newRequest(String name) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(name), "List name cannot be empty.");
        return new AttributeListsErrorsRequest(name);
    }


    /**
     * Specify a file output stream to route the response data to.
     *
     * @param fileOutputStream The output stream to write to.
     * @return AttributeListsErrorsRequest
     */
    public AttributeListsErrorsRequest setOutputStream(FileOutputStream fileOutputStream) {
        this.fileOutputStream = Optional.of(fileOutputStream);
        return this;
    }

    @Override
    public ContentType getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_CSV);
        return headers;
    }

    @Override
    public Request.HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<String> getResponseParser() {
        return response -> {
            if (fileOutputStream.isPresent()) {
                try (OutputStreamWriter stream = new OutputStreamWriter(fileOutputStream.get())) {
                    String[] rows = response.split("\n");
                    for (String row : rows) {
                        String[] columns = row.split(",");

                        // Manually format each row for CSV
                        String csvRow = CSVUtils.formatRowForCSV(columns);
                        stream.write(csvRow + "\n");
                    }
                } finally {
                    fileOutputStream.get().close();
                }
            }
            return response;
        };
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return true;
    }
}
