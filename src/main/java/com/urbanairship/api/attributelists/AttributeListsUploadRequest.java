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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The AttributeListsUploadRequest class builds a attribute list upload request to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class AttributeListsUploadRequest implements Request<String> {
    private final static String ATTRIBUTE_LISTS_PATH = "/api/attribute-lists/";
    private final static String CSV_PATH = "/csv";
    private final String path;

    private File csv;
    private Boolean gzip = false;

    private AttributeListsUploadRequest(String path, File csv) {
        this.path = path;
        this.csv = csv;
    }

    /**
     * Create a attribute list upload request.
     *
     * @param name The name of the list as a string.
     * @param csvFile A string path to a csv file.
     * @return AttributeListsUploadRequest
     */
    public static AttributeListsUploadRequest newRequest(String name, String csvFile) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(name), "List name cannot be null.");
        File csv = new File(csvFile);
        Preconditions.checkArgument(csv.exists() && csv.isFile(), "File does not exist: " + csvFile);
        return new AttributeListsUploadRequest(ATTRIBUTE_LISTS_PATH + name + CSV_PATH, csv);
    }

    /**
     * Return whether the request uses gzip encoding.
     *
     * @return Boolean
     */
    public Boolean getGzipEnabled() {
        return this.gzip;
    }

    /**
     * Set whether the request uses gzip encoding.
     *
     * @param gzip Boolean
     * @return StaticListUploadRequest
     */
    public AttributeListsUploadRequest setGzipEnabled(Boolean gzip) {
        this.gzip = gzip;
        return this;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.TEXT_PLAIN;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_TEXT_CSV);
        if (this.gzip) {
            headers.put(HttpHeaders.CONTENT_ENCODING, CONTENT_ENCODING_GZIP);
        }
        return headers;
    }

    @Override
    public Request.HttpMethod getHttpMethod() {
        return Request.HttpMethod.PUT;
    }

    @Override
    public String getRequestBody() {
        try ( BufferedReader reader = new BufferedReader(new FileReader(this.csv)) ) {
            StringBuffer body = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line).append("\n");
            }
            return body.toString();
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<String> getResponseParser() {
        return response -> response;
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
