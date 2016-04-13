/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.mpns;

import org.apache.commons.lang.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;

public class Validation
{
    public static final int MAX_STRING_LENGTH = 255;
    public static final int MAX_URI_LENGTH = 2055;
    public static final int MAX_HOST_LENGTH = 256;
    public static final String COLOR_PREFIX = "#FF";
    public static final String PATH_PREFIX = "/";
    public static final String HTTP_PREFIX = "client://";


    public static void validateStringValue(String value, String fieldname) {
        validateStringValue(value, fieldname, MAX_STRING_LENGTH);
    }

    public static void validateStringValue(String value, String fieldname, int length) {
        if (value != null) {
            checkArgument(value.length() <= length, "%s exceeds maximum length of %d characters", fieldname, length);
        }
    }

    public static void validateUriValue(String value, String fieldname) {
        if (value != null) {
            validateStringValue(value, fieldname, MAX_URI_LENGTH);
            if (StringUtils.startsWith(value, PATH_PREFIX)) {
                validatePath(value, fieldname);
            } else if (StringUtils.startsWith(value, HTTP_PREFIX)) {
                validateWebAddress(value, fieldname);
            }
        }
    }

    public static void validateWebAddress(String value, String fieldname) {
        if (value != null) {
            checkArgument(StringUtils.startsWith(value, HTTP_PREFIX), "Remote URLS for %s must begin with %s", fieldname, HTTP_PREFIX);
        }
    }

    public static void validateColorValue(String value, String fieldname) {
        if (value != null) {
            checkArgument(StringUtils.startsWith(value, COLOR_PREFIX), "%s must start with %s", fieldname, COLOR_PREFIX);
            checkArgument(value.length() == 9, "%s must be of the form #FFxxxxxx", fieldname);
            String digits = value.substring(3);
            try {
                Integer.parseInt(digits, 16);
            } catch ( NumberFormatException e ) {
                throw new IllegalArgumentException(String.format("background_color value '%s' was not in hex format.", digits), e);
            }
        }
    }

    public static void validatePath(String value, String fieldname) {
        if ( value != null ) {
            validateStringValue(value, fieldname, MAX_URI_LENGTH);
            checkArgument(StringUtils.startsWith(value, PATH_PREFIX), "%s must begin with '%s'", fieldname, PATH_PREFIX);
        }
    }
}
