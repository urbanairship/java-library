/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Optional;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.commons.lang.StringUtils;
import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Asynchronous API response handler.
 * @param <T> The response type.
 */
class ResponseAsyncHandler<T> implements AsyncHandler<Response> {
    private static final Logger log = LoggerFactory.getLogger(ResponseAsyncHandler.class);
    private static final String CONTENT_TYPE_KEY = "Content-type";

    private final Response.Builder<T> responseBuilder = new Response.Builder<>();
    private final ClientException.Builder exceptionBuilder = ClientException.newBuilder();
    private final ServerException.Builder serverExceptionBuilder = ServerException.newBuilder();


    private final Optional<ResponseCallback> clientCallback;
    private final ResponseParser<T> parser;
    private final StringBuilder bodyBuilder = new StringBuilder();

    private AtomicInteger retryCount = new AtomicInteger(0);
    private String exceptionContentType;
    private boolean isSuccessful;
    private Integer statusCode;
    /**
     * ResponseAsyncHandler constructor.
     *
     * @param clientCallback An optional ResponseCallback for handling the response on completion or error.
     * @param parser The response parser.
     */
    public ResponseAsyncHandler(Optional<ResponseCallback> clientCallback, ResponseParser<T> parser) {
        this.clientCallback = clientCallback;
        this.parser = parser;
    }

    @Override
    public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
        statusCode = responseStatus.getStatusCode();

        if (statusCode == 401 || statusCode == 403) {
            exceptionBuilder.setStatusCode(statusCode);
            exceptionBuilder.setStatusText(responseStatus.getStatusText());
            isSuccessful = false;
        } else {
            responseBuilder.setStatus(responseStatus.getStatusCode());
            isSuccessful = true;
        }

        return State.CONTINUE;
    }

    @Override
    public State onHeadersReceived(HttpHeaders httpHeaders) throws Exception {
        if (isSuccessful) {
            responseBuilder.setHeaders(getHeaders(httpHeaders));
        } else {
            exceptionContentType = httpHeaders.get(CONTENT_TYPE_KEY);
        }

        return State.CONTINUE;
    }

    @Override
    public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
        String body = new String(bodyPart.getBodyPartBytes(), StandardCharsets.UTF_8);

        if (!isSuccessful) {
            // The response body for an error won't be very big, so we can throw here without needing to aggregate.
            RequestError error = RequestError.errorFromResponse(body, exceptionContentType);
            exceptionBuilder.setRequestError(error);
            throw exceptionBuilder.build();
        }

        bodyBuilder.append(body);
        return State.CONTINUE;
    }

    @Override
    public Response onCompleted() throws Exception {
        if (StringUtils.isNotBlank(bodyBuilder.toString())) {
            responseBuilder.setBody(parser.parse(bodyBuilder.toString()));
        }

        Response response = responseBuilder.build();
        if (clientCallback.isPresent()) {
            clientCallback.get().completed(response);
        }

        log.debug("Response processing completed for " + response.getBody());
        return response;
    }

    @Override
    public void onThrowable(Throwable t) {
        log.error("Exception thrown during response processing", t);
        if (clientCallback.isPresent()) {
            clientCallback.get().error(t);
        }
    }

    /**
     * Retrieves the response headers.
     * @param httpResponse The HttpResponse.
     * @return An immutable map of response headers.
     */
    private Map<String, String> getHeaders(HttpHeaders httpResponse) {
        Map<String, String> headers = new HashMap<>();
        for (Map.Entry<String, String> entry : httpResponse.entries()) {
            headers.put(entry.getKey(), entry.getValue());
        }
        return headers;
    }

    /**
     * Retrieves the request retry count.
     *
     * @return The retry count.
     */
    public int getRetryCount() {
        return retryCount.get();
    }

    /**
     * Increment the request retry count.
     */
    public void incrementRetryCount() {
        retryCount.incrementAndGet();
    }
}
