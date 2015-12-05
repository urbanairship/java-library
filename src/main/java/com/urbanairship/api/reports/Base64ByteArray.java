/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.glassfish.jersey.internal.util.Base64;

import com.google.common.base.Preconditions;

public final class Base64ByteArray {

    public byte[] binary;

    public Base64ByteArray(String value) {
            	
    	Preconditions.checkArgument(checkForEncode(value));

        this.binary = Base64.decode(value.getBytes());
    }

    public byte[] getByteArray() {
        return binary;
    }

    public String getBase64EncodedString() {
        return new String(Base64.encode(binary));
    }

    @Override
    public String toString() {
        return new String(binary);
    }

    /**
     * Internal implementation of check is required as Jersey 2.x no longer has method Base64.isBase64().
     * As a transient dependency Apache Commons library is providing it's Base64 implementation with appropriate method for checks,
     * but this library can produce false positives as it treats whitespaces as positives in evaluation.
     * 
     * @param string Value to be checked.
     * @return Flag indicating if input string is Base64 encoded.
     */
    private boolean checkForEncode(String string) {
        String pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(string);
        return m.find();
    }
    
    // Does not use Guava::Objects, gives inconsistent results with byte array
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Base64ByteArray that = (Base64ByteArray) o;

        return Arrays.equals(binary, that.binary);

    }

    @Override
    public int hashCode() {
        return binary != null ? Arrays.hashCode(binary) : 0;
    }
}
