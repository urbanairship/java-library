package com.urbanairship.api.push.model.notification.mpns;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ToastDataTest extends MpnsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidText1() {
        MPNSToastData.newBuilder()
                .setText1(valueTooLong)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidText2() {
        MPNSToastData.newBuilder()
                .setText2(valueTooLong)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidNoTextValues() {
        MPNSToastData.newBuilder()
                .setParam("/p1")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidParamTooLong() {
        MPNSToastData.newBuilder()
                .setText1("text1")
                .setParam("/" + pathTooLong)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidParamBadPath() {
        MPNSToastData.newBuilder()
                .setText1("text1")
                .setParam("not a path")
                .build();
    }

    @Test
    public void testBuild() {
        MPNSToastData data = MPNSToastData.newBuilder()
                .setText1("text1")
                .setText2("text2")
                .setParam("/param")
                .build();
        assertEquals("text1", data.getText1().get());
        assertEquals("text2", data.getText2().get());
        assertEquals("/param", data.getParam().get());
    }
}
