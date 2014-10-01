package com.urbanairship.api.push.model;

import com.urbanairship.api.push.model.audience.*;
import static com.urbanairship.api.push.model.audience.Selectors.*;
import org.junit.Test;
import static org.junit.Assert.*;
import com.google.common.collect.ImmutableSet;

public class DeviceTypeDataTest {

    @Test
    public void testDeviceTypeDataOf() {
        DeviceTypeData data = DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID, DeviceType.AMAZON);
        assertTrue(data.getDeviceTypes().isPresent());
        assertFalse(data.isAll());
        assertEquals(3, data.getDeviceTypes().get().size());
        assertTrue(data.getDeviceTypes().get().contains(DeviceType.IOS));
        assertTrue(data.getDeviceTypes().get().contains(DeviceType.ANDROID));
        assertTrue(data.getDeviceTypes().get().contains(DeviceType.AMAZON));
        assertFalse(data.getDeviceTypes().get().contains(DeviceType.WNS));
        assertFalse(data.getDeviceTypes().get().contains(DeviceType.MPNS));
        assertFalse(data.getDeviceTypes().get().contains(DeviceType.BLACKBERRY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeviceTypeDataValidation() {
        DeviceTypeData.newBuilder()
                .setAll(true)
                .addDeviceType(DeviceType.IOS)
                .build();
    }

    @Test
    public void testEquality() {
        DeviceTypeData d = DeviceTypeData.all();
        assertEquals(d, d);
        assertSame(d, d);
        assertFalse(d.equals(null));
        DeviceTypeData d2 = DeviceTypeData.all();
        assertEquals(d, d2);
        assertNotSame(d, d2);
        assertEquals(DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID),
                DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID));
        assertTrue(!DeviceTypeData.all().equals(DeviceTypeData.of(DeviceType.AMAZON)));
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
        assertEquals(DeviceTypeData.all().hashCode(), DeviceTypeData.newBuilder().setAll(true).build().hashCode());
        assertEquals(DeviceTypeData.of(DeviceType.IOS).hashCode(), DeviceTypeData.of(DeviceType.IOS).hashCode());
    }

    @Test
    public void testApplicableDeviceTypes_BasicSelectors() throws Exception {
        assertEquals(DeviceTypeData.of(DeviceType.IOS), deviceToken("69C5B7D878810A96F2485712AC050D3A2DDBD69864BC18FDC6E821EA5A5196C6").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.IOS), iosChannel("8516d389-88fb-1fa8-474b-bcf2464cc997").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.ANDROID), apid("8516d389-88fb-1fa8-474b-bcf2464cc997").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.ANDROID), androidChannel("8516d389-88fb-1fa8-474b-bcf2464cc997").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.MPNS), mpns("8516d389-88fb-1fa8-474b-bcf2464cc997").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.WNS), wns("8516d389-88fb-1fa8-474b-bcf2464cc997").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.BLACKBERRY), devicePin("6832977c").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.all(), all().getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.all(), triggered().getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.all(), tag("T").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.all(), alias("A").getApplicableDeviceTypes());
    }

    @Test
    public void testApplicableDeviceTypes_NOT() throws Exception {
        assertEquals(DeviceTypeData.all(), not(apid("89f3167f-b148-1391-9349-b61449678acb")).getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.all(), not(and(tag("T"), deviceToken("852C1C580CF5BA045676D71E491291D653506869505BF0B9FAE7D9BC0321F796")))
                .getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.all(), or(and(tag("T1"),
                        devicePin("344657a9")),
                not(deviceToken("852C1C580CF5BA045676D71E491291D653506869505BF0B9FAE7D9BC0321F796")))
                .getApplicableDeviceTypes());
    }
}
