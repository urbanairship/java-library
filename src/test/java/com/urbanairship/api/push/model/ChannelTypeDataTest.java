package com.urbanairship.api.push.model;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import static com.urbanairship.api.push.model.audience.Selectors.androidChannel;
import static com.urbanairship.api.push.model.audience.Selectors.apid;
import static com.urbanairship.api.push.model.audience.Selectors.deviceToken;
import static com.urbanairship.api.push.model.audience.Selectors.iosChannel;
import static com.urbanairship.api.push.model.audience.Selectors.wns;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChannelTypeDataTest {

    @Test
    public void testDeviceTypeDataOf() {
        DeviceTypeData data = DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID, DeviceType.AMAZON);
        assertTrue(data.getDeviceTypes().isPresent());
        assertEquals(3, data.getDeviceTypes().get().size());
        assertTrue(data.getDeviceTypes().get().contains(DeviceType.IOS));
        assertTrue(data.getDeviceTypes().get().contains(DeviceType.ANDROID));
        assertTrue(data.getDeviceTypes().get().contains(DeviceType.AMAZON));
        assertFalse(data.getDeviceTypes().get().contains(DeviceType.WNS));
    }

    @Test
    public void testBuilder() {
        assertEquals(DeviceTypeData.newBuilder()
                        .addDeviceType(DeviceType.IOS)
                        .addDeviceType(DeviceType.WNS)
                        .addDeviceType(DeviceType.AMAZON)
                        .build(),
                DeviceTypeData.newBuilder()
                        .addAllDeviceTypes(ImmutableSet.of(DeviceType.IOS,
                                DeviceType.AMAZON,
                                DeviceType.WNS))
                        .build());
    }

    @Test
    public void testHash() {
        assertEquals(DeviceTypeData.of(DeviceType.IOS).hashCode(), DeviceTypeData.of(DeviceType.IOS).hashCode());
    }

    @Test
    public void testApplicableDeviceTypes_BasicSelectors() {
        assertEquals(DeviceTypeData.of(DeviceType.IOS), deviceToken("69C5B7D878810A96F2485712AC050D3A2DDBD69864BC18FDC6E821EA5A5196C6").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.IOS), iosChannel("8516d389-88fb-1fa8-474b-bcf2464cc997").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.ANDROID), apid("8516d389-88fb-1fa8-474b-bcf2464cc997").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.ANDROID), androidChannel("8516d389-88fb-1fa8-474b-bcf2464cc997").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.WNS), wns("8516d389-88fb-1fa8-474b-bcf2464cc997").getApplicableDeviceTypes());
    }
}
