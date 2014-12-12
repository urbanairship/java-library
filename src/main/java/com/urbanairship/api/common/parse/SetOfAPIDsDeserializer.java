/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Set;

public class SetOfAPIDsDeserializer {

    public static final SetOfAPIDsDeserializer INSTANCE = new SetOfAPIDsDeserializer();
    private static final String APID_FORMAT_ERROR_MESSAGE = "%s is not a valid device identifier";
    private static final int UUID_STRING_LENGTH = 36;

    private SetOfAPIDsDeserializer() { }

    public Set<String> deserialize(JsonParser parser, String fieldName) throws IOException {
        Set<String> apids = SetOfStringsDeserializer.INSTANCE.deserialize(parser, fieldName);
        for (String apid : apids) {
            if (apid.length() < UUID_STRING_LENGTH) {
                APIParsingException.raise(String.format(APID_FORMAT_ERROR_MESSAGE, apid), parser);
            }
        }
        return apids;
    }
}
