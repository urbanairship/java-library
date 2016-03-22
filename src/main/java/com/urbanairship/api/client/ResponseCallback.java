/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

/**
 * Interface providing callback methods for the response processing lifecycle.
 */
public interface ResponseCallback {

    /**
     * Callback for when a response has been completely processed.
     *
     * @param response A Response instance.
     */
    public void completed(Response response);

    /**
     * Callback for when an error occurs in response processing and a Throwable is thrown.
     *
     * @param throwable A Throwable instance.
     */
    public void error(Throwable throwable);
}
