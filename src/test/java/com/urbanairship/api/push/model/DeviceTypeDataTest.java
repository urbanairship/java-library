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

    @Test(expected=IllegalArgumentException.class)
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
        assertTrue(! DeviceTypeData.all().equals(DeviceTypeData.of(DeviceType.AMAZON)));
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
    public void testApplicableDeviceTypes_UniformOR() throws Exception {
        assertEquals(DeviceTypeData.of(DeviceType.IOS), deviceTokens("69C5B7D878810A96F2485712AC050D3A2DDBD69864BC18FDC6E821EA5A5196C6",
                "B74A47C55C524CA6C7FB82AF265EE31D90155A0F632ECE2EA2CFE3FF2F673CF3").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.IOS), iosChannel("8516d389-88fb-1fa8-474b-bcf2464cc997",
                "d4d36d2d-3825-9981-2f06-0d492f63e6d1").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.ANDROID), apids("8516d389-88fb-1fa8-474b-bcf2464cc997",
                "34838925-3470-56ad-8c00-c9c313ca07f9").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.ANDROID), androidChannels("8516d389-88fb-1fa8-474b-bcf2464cc997",
                "34838925-3470-56ad-8c00-c9c313ca07f9").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.WNS), wnsDevices("8516d389-88fb-1fa8-474b-bcf2464cc997",
                "c1daaa89-6700-c6df-6798-7daa95c9707f").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.MPNS), mpnsDevices("8516d389-88fb-1fa8-474b-bcf2464cc997",
                "caa39693-f4d4-f977-dfa4-e62bb5243575").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.AMAZON), amazonDevices("8516d389-88fb-1fa8-474b-bcf2464cc997",
                "caa39693-f4d4-f977-dfa4-e62bb5243575").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.BLACKBERRY), devicePins("6832977c",
                "bb319b7e").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.all(), tags("T1", "T2", "T3").getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.all(), aliases("A", "B", "C").getApplicableDeviceTypes());
    }

    @Test
    public void testApplicableDeviceTypes_OR() throws Exception {
        assertEquals(DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID),
                or(apid("7c18e2e6-7e6a-2412-c908-edcc357df84c"),
                        iosChannel("d1f7a9fd-15b5-2d1f-ac12-62452acfd70c")).getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.all(),
                or(tag("T"),
                        iosChannel("d1f7a9fd-15b5-2d1f-ac12-62452acfd70c")).getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID),
                or(or(deviceToken("6BBD5C2C6B08302FD3C73DF9AF215CBD970259324575C252BA1E0A69320B22C0"),
                                iosChannel("6bc076a3-ab22-02ae-6e44-bbb4d1264dbe")),
                        and(tag("T"),
                                apid("127028a5-04dc-609a-6b54-eee4377b2789"))).getApplicableDeviceTypes());
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

    @Test
    public void testApplicableDeviceTypes_AND() throws Exception {
        assertEquals(DeviceTypeData.of(DeviceType.IOS), and(tag("T"),
                deviceToken("C0F18665F349BE522CC226E7354CE608407E7AB9C10450A5DF47C1050DA2D768"))
                .getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.IOS), and(or(tag("T1"),
                        tag("T2")),
                iosChannel("6758f94b-3ed2-a5a3-319c-dc4e6f4098f8"))
                .getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.IOS, DeviceType.BLACKBERRY),
                or(and(tag("T1"),
                                devicePin("78534224")),
                        and(tag("T2"),
                                deviceToken("E0597AC26DDBF038B657A595861192093D5AE331041D76EC6A684D13B60CCCFE")))
                        .getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.ANDROID),
                and(not(tag("T")),
                        not(deviceToken("C1BD16A3F2D1D2EE7A5D6EFD47906B3831865AC088B95F05AB713376B98C215B")),
                        apid("49a02019-8a97-a4b7-c014-60bd1cda83fb"))
                        .getApplicableDeviceTypes());
        assertEquals(DeviceTypeData.of(DeviceType.MPNS, DeviceType.WNS),
                and(and(tag("T"),
                                or(mpns("a6e88242-04b5-1247-2f06-0ae5317a1c0a"),
                                        wns("6a1c2f5a-8109-9f27-2b67-70ee21d831f1"))),
                        or(tag("T"),
                                iosChannel("5effbc5a-be21-3a54-07e4-b1d84d49becd")))
                        .getApplicableDeviceTypes());
    }
}
