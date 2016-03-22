/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */


package com.urbanairship.api.client;

/**
 * Exception thrown when the client fails to connect to the API after retrying on 5xxs.
 */
public class ServerException extends RuntimeException {

    public ServerException(String msg) {
        super(msg);
    }

    public ServerException(String msg, Exception e) {
        super(msg, e);
    }
}
