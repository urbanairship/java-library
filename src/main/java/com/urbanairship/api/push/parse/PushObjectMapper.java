/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.urbanairship.api.common.parse.CommonObjectMapper;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.location.LocationSelector;
import com.urbanairship.api.push.model.audience.location.AbsoluteDateRange;
import com.urbanairship.api.push.model.audience.location.RecentDateRange;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.model.notification.ios.IOSBadgeData;
import com.urbanairship.api.push.model.notification.ios.IOSAlertData;
import com.urbanairship.api.push.model.notification.mpns.MPNSTileData;
import com.urbanairship.api.push.model.notification.mpns.MPNSToastData;
import com.urbanairship.api.push.model.notification.wns.WNSDevicePayload;
import com.urbanairship.api.push.model.notification.wns.WNSPush;
import com.urbanairship.api.push.model.notification.wns.WNSBinding;
import com.urbanairship.api.push.model.notification.wns.WNSAudioData;
import com.urbanairship.api.push.model.notification.wns.WNSBadgeData;
import com.urbanairship.api.push.model.notification.wns.WNSTileData;
import com.urbanairship.api.push.model.notification.wns.WNSToastData;
import com.urbanairship.api.push.model.notification.mpns.MPNSDevicePayload;
import com.urbanairship.api.push.model.notification.mpns.MPNSPush;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.parse.notification.android.AndroidDevicePayloadSerializer;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.push.parse.notification.adm.ADMDevicePayloadSerializer;
import com.urbanairship.api.push.model.notification.blackberry.BlackberryDevicePayload;
import com.urbanairship.api.push.parse.notification.blackberry.BlackberryDevicePayloadSerializer;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.parse.notification.richpush.RichPushMessageSerializer;
import com.urbanairship.api.push.parse.notification.ios.*;
import com.urbanairship.api.push.parse.notification.wns.*;
import com.urbanairship.api.push.parse.notification.mpns.*;
import com.urbanairship.api.push.parse.notification.*;
import com.urbanairship.api.push.parse.audience.*;
import com.urbanairship.api.push.parse.audience.location.*;
import com.urbanairship.api.schedule.model.*;
import com.urbanairship.api.schedule.parse.*;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;

public class PushObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Push API Module", new Version(1, 0, 0, null));

    static {

        MODULE
            .addSerializer(PushPayload.class, new PushPayloadSerializer())
            .addSerializer(Notification.class, new NotificationSerializer())
            .addSerializer(DeviceType.class, new DeviceTypeSerializer())
            .addSerializer(Selector.class, new SelectorSerializer())
            .addSerializer(LocationSelector.class, new LocationSelectorSerializer())
            .addSerializer(AbsoluteDateRange.class, new AbsoluteDateRangeSerializer())
            .addSerializer(RecentDateRange.class, new RecentDateRangeSerializer())
            .addSerializer(DeviceTypeData.class, new DeviceTypeDataSerializer())

            /* IOS */
            .addSerializer(IOSDevicePayload.class, new IOSDevicePayloadSerializer())
            .addSerializer(IOSBadgeData.class, new IOSBadgeDataSerializer())
            .addSerializer(IOSAlertData.class, new IOSAlertDataSerializer())

            /* WNS enums */
            .addSerializer(WNSToastData.Duration.class, new WNSDurationSerializer())
            .addSerializer(WNSAudioData.Sound.class, new WNSSoundSerializer())
            .addSerializer(WNSBadgeData.Glyph.class, new WNSGlyphSerializer())
            .addSerializer(WNSPush.CachePolicy.class, new WNSCachePolicySerializer())

            /* WNS composite types */
            .addSerializer(WNSDevicePayload.class, new WNSDevicePayloadSerializer())
            .addSerializer(WNSBinding.class, new WNSBindingSerializer())
            .addSerializer(WNSToastData.class, new WNSToastSerializer())
            .addSerializer(WNSTileData.class, new WNSTileSerializer())
            .addSerializer(WNSBadgeData.class, new WNSBadgeSerializer())
            .addSerializer(WNSAudioData.class, new WNSAudioSerializer())

            /* MPNS Enums */
            .addSerializer(MPNSPush.BatchingInterval.class, new MPNSBatchingIntervalSerializer())

            /* MPNS composite types */
            .addSerializer(MPNSDevicePayload.class, new MPNSDevicePayloadSerializer())
            .addSerializer(MPNSToastData.class, new MPNSToastSerializer())
            .addSerializer(MPNSTileData.class, new MPNSTileSerializer())

            /* Android */
            .addSerializer(AndroidDevicePayload.class, new AndroidDevicePayloadSerializer())

            /* Blackberry */
            .addSerializer(BlackberryDevicePayload.class, new BlackberryDevicePayloadSerializer())

            /* ADM */
            .addSerializer(ADMDevicePayload.class, new ADMDevicePayloadSerializer())

            /* Rich Push */
            .addSerializer(RichPushMessage.class, new RichPushMessageSerializer())

            /* Schedules */
            .addSerializer(SchedulePayload.class, ScheduledPayloadSerializer.INSTANCE)
            .addSerializer(Schedule.class, ScheduleSerializer.INSTANCE);


        MAPPER.registerModule(MODULE);
        MAPPER.registerModule(CommonObjectMapper.getModule());
        MAPPER.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private PushObjectMapper() { }
}
