package com.urbanairship.api.push.model.notification.mpns;

import org.junit.BeforeClass;

public class MpnsTest {
    protected static String valueTooLong;
    protected static String uriTooLong;
    protected static String pathTooLong;

    @BeforeClass
    public static void setUpMpnsValues() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= Validation.MAX_STRING_LENGTH; i++) {
            sb.append('A');
        }
        valueTooLong = sb.toString();

        sb = new StringBuffer();
        sb.append("http://");
        for (int i = 0; i <= Validation.MAX_URI_LENGTH; i++) {
            sb.append('A');
        }
        uriTooLong = sb.toString();

        sb = new StringBuffer();
        sb.append("/");
        for (int i = 0; i <= Validation.MAX_URI_LENGTH; i++) {
            sb.append('A');
        }
        pathTooLong = sb.toString();
    }
}
