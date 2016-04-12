/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports;

import com.google.common.io.BaseEncoding;

import java.util.Arrays;

public final class Base64ByteArray {

    public byte[] binary;

    public Base64ByteArray(String value) {
        this.binary = BaseEncoding.base64().decode(value);
    }

    public byte[] getByteArray() {
        return binary;
    }

    public String getBase64EncodedString() {
        return BaseEncoding.base64().encode(binary);
    }

    @Override
    public String toString() {
        return new String(binary);
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
