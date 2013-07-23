/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

/**
 Exception thrown during JSON parsing.
 */
class APIParsingException extends RuntimeException {

    APIParsingException(String message){
        super(message);
    }
}
