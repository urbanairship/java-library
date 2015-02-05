/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.CommonObjectMapper;
import com.urbanairship.api.push.model.*;
import com.urbanairship.api.push.model.audience.location.*;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.notification.actions.*;
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
import com.urbanairship.api.push.parse.notification.actions.*;
import com.urbanairship.api.push.parse.notification.android.AndroidDevicePayloadDeserializer;
import com.urbanairship.api.push.parse.notification.android.AndroidDevicePayloadSerializer;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.push.parse.notification.adm.ADMDevicePayloadDeserializer;
import com.urbanairship.api.push.parse.notification.adm.ADMDevicePayloadSerializer;
import com.urbanairship.api.push.model.notification.blackberry.BlackberryDevicePayload;
import com.urbanairship.api.push.parse.notification.blackberry.BlackberryDevicePayloadDeserializer;
import com.urbanairship.api.push.parse.notification.blackberry.BlackberryDevicePayloadSerializer;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.parse.notification.richpush.RichPushMessageDeserializer;
import com.urbanairship.api.push.parse.notification.richpush.RichPushMessageSerializer;
import com.urbanairship.api.push.parse.notification.ios.*;
import com.urbanairship.api.push.parse.notification.wns.*;
import com.urbanairship.api.push.parse.notification.mpns.*;
import com.urbanairship.api.push.parse.notification.*;
import com.urbanairship.api.push.parse.audience.*;
import com.urbanairship.api.push.parse.audience.location.*;
import com.urbanairship.api.schedule.model.*;
import com.urbanairship.api.schedule.parse.*;
import com.urbanairship.api.tag.model.AddRemoveDeviceFromTagPayload;
import com.urbanairship.api.tag.model.BatchModificationPayload;
import com.urbanairship.api.tag.parse.AddRemoveDeviceFromTagPayloadSerializer;
import com.urbanairship.api.tag.parse.BatchModificationPayloadSerializer;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;

public class PushObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Push API Module", new Version(1, 0, 0, null));

    static {
        WNSBindingDeserializer bindingDS = new WNSBindingDeserializer();
        WNSAudioDeserializer audioDS = new WNSAudioDeserializer();
        WNSToastDeserializer wnsToastDS = new WNSToastDeserializer(bindingDS, audioDS);
        WNSTileDeserializer wnsTileDS = new WNSTileDeserializer(bindingDS);
        WNSBadgeDeserializer badgeDS = new WNSBadgeDeserializer();
        WNSDevicePayloadDeserializer wnsPayloadDS = new WNSDevicePayloadDeserializer(wnsToastDS, wnsTileDS, badgeDS);
        MPNSToastDeserializer mpnsToastDS = new MPNSToastDeserializer();
        MPNSTileDeserializer mpnsTileDS = new MPNSTileDeserializer();
        MPNSDevicePayloadDeserializer mpnsPayloadDS = new MPNSDevicePayloadDeserializer(mpnsToastDS, mpnsTileDS);
        IOSDevicePayloadDeserializer iosPayloadDS = new IOSDevicePayloadDeserializer();
        AndroidDevicePayloadDeserializer androidPayloadDS = new AndroidDevicePayloadDeserializer();
        ADMDevicePayloadDeserializer admPayloadDS = new ADMDevicePayloadDeserializer();
        BlackberryDevicePayloadDeserializer blackberryPayloadDS = new BlackberryDevicePayloadDeserializer();


        NotificationDeserializer notificationDeserializer = new NotificationDeserializer(
                ImmutableMap.<DeviceType, JsonDeserializer<? extends DevicePayloadOverride>>builder()
                        .put(DeviceType.WNS, wnsPayloadDS)
                        .put(DeviceType.MPNS, mpnsPayloadDS)
                        .put(DeviceType.IOS, iosPayloadDS)
                        .put(DeviceType.ANDROID, androidPayloadDS)
                        .put(DeviceType.BLACKBERRY, blackberryPayloadDS)
                        .put(DeviceType.AMAZON, admPayloadDS)
                        .build());

        MODULE
                .addSerializer(PushPayload.class, new PushPayloadSerializer())
                .addDeserializer(PushPayload.class, new PushPayloadDeserializer())
                .addSerializer(PushOptions.class, new PushOptionsSerializer())
                .addDeserializer(PushOptions.class, new PushOptionsDeserializer())
                .addSerializer(Notification.class, new NotificationSerializer())
                .addDeserializer(Notification.class, notificationDeserializer)
                .addSerializer(Interactive.class, new InteractiveSerializer())
                .addDeserializer(Interactive.class, new InteractiveDeserializer())
                .addSerializer(DeviceType.class, new DeviceTypeSerializer())
                .addDeserializer(DeviceType.class, new PlatformDeserializer())
                .addSerializer(Selector.class, new SelectorSerializer())
                .addDeserializer(Selector.class, new SelectorDeserializer())
                .addSerializer(LocationSelector.class, new LocationSelectorSerializer())
                .addDeserializer(LocationSelector.class, new LocationSelectorDeserializer())
                .addSerializer(AbsoluteDateRange.class, new AbsoluteDateRangeSerializer())
                .addDeserializer(AbsoluteDateRange.Builder.class, new AbsoluteDateRangeDeserializer())
                .addSerializer(RecentDateRange.class, new RecentDateRangeSerializer())
                .addDeserializer(RecentDateRange.Builder.class, new RecentDateRangeDeserializer())
                .addSerializer(DeviceTypeData.class, new DeviceTypeDataSerializer())
                .addDeserializer(DeviceTypeData.class, new PlatformDataDeserializer())
                .addDeserializer(DateRange.class, new DateRangeDeserializer())
                .addSerializer(PushExpiry.class, new PushExpirySerializer())

            /* IOS */
                .addSerializer(IOSDevicePayload.class, new IOSDevicePayloadSerializer())
                .addDeserializer(IOSDevicePayload.class, iosPayloadDS)
                .addSerializer(IOSBadgeData.class, new IOSBadgeDataSerializer())
                .addSerializer(IOSAlertData.class, new IOSAlertDataSerializer())
                .addDeserializer(IOSAlertData.class, new IOSAlertDataDeserializer())

            /* WNS enums */
                .addSerializer(WNSToastData.Duration.class, new WNSDurationSerializer())
                .addDeserializer(WNSToastData.Duration.class, new WNSDurationDeserializer())
                .addSerializer(WNSAudioData.Sound.class, new WNSSoundSerializer())
                .addDeserializer(WNSAudioData.Sound.class, new WNSSoundDeserializer())
                .addSerializer(WNSBadgeData.Glyph.class, new WNSGlyphSerializer())
                .addDeserializer(WNSBadgeData.Glyph.class, new WNSGlyphDeserializer())
                .addSerializer(WNSPush.CachePolicy.class, new WNSCachePolicySerializer())
                .addDeserializer(WNSPush.CachePolicy.class, new WNSCachePolicyDeserializer())

            /* WNS composite types */
                .addSerializer(WNSDevicePayload.class, new WNSDevicePayloadSerializer())
                .addDeserializer(WNSDevicePayload.class, wnsPayloadDS)
                .addSerializer(WNSBinding.class, new WNSBindingSerializer())
                .addDeserializer(WNSBinding.class, bindingDS)
                .addSerializer(WNSToastData.class, new WNSToastSerializer())
                .addDeserializer(WNSToastData.class, wnsToastDS)
                .addSerializer(WNSTileData.class, new WNSTileSerializer())
                .addDeserializer(WNSTileData.class, wnsTileDS)
                .addSerializer(WNSBadgeData.class, new WNSBadgeSerializer())
                .addDeserializer(WNSBadgeData.class, badgeDS)
                .addSerializer(WNSAudioData.class, new WNSAudioSerializer())
                .addDeserializer(WNSAudioData.class, audioDS)

            /* MPNS Enums */
                .addSerializer(MPNSPush.BatchingInterval.class, new MPNSBatchingIntervalSerializer())
                .addDeserializer(MPNSPush.BatchingInterval.class, new MPNSBatchingIntervalDeserializer())

            /* MPNS composite types */
                .addSerializer(MPNSDevicePayload.class, new MPNSDevicePayloadSerializer())
                .addDeserializer(MPNSDevicePayload.class, mpnsPayloadDS)
                .addSerializer(MPNSToastData.class, new MPNSToastSerializer())
                .addDeserializer(MPNSToastData.class, mpnsToastDS)
                .addSerializer(MPNSTileData.class, new MPNSTileSerializer())
                .addDeserializer(MPNSTileData.class, mpnsTileDS)

            /* Android */
                .addSerializer(AndroidDevicePayload.class, new AndroidDevicePayloadSerializer())
                .addDeserializer(AndroidDevicePayload.class, androidPayloadDS)

            /* Blackberry */
                .addSerializer(BlackberryDevicePayload.class, new BlackberryDevicePayloadSerializer())
                .addDeserializer(BlackberryDevicePayload.class, blackberryPayloadDS)

            /* AMAZON */
                .addSerializer(ADMDevicePayload.class, new ADMDevicePayloadSerializer())
                .addDeserializer(ADMDevicePayload.class, admPayloadDS)

            /* Rich Push */
                .addSerializer(RichPushMessage.class, new RichPushMessageSerializer())
                .addDeserializer(RichPushMessage.class, new RichPushMessageDeserializer())

            /* Schedules */
                .addDeserializer(SchedulePayload.class, SchedulePayloadDeserializer.INSTANCE)
                .addSerializer(SchedulePayload.class, ScheduledPayloadSerializer.INSTANCE)
                .addDeserializer(Schedule.class, ScheduleDeserializer.INSTANCE)
                .addSerializer(Schedule.class, ScheduleSerializer.INSTANCE)
                .addSerializer(ScheduleDetails.class, ScheduleDetailsSerializer.INSTANCE)

           /* Actions */
                .addDeserializer(Actions.class, new ActionsDeserializer())
                .addSerializer(Actions.class, new ActionsSerializer(ActionNameRegistry.INSTANCE))

                .addSerializer(OpenLandingPageWithContentAction.class, new LandingPageWithContentSerializer())
                .addSerializer(OpenExternalURLAction.class, new ExternalURLSerializer())
                .addSerializer(AddTagAction.class, new AddTagActionSerializer())
                .addSerializer(RemoveTagAction.class, new RemoveTagActionSerializer())
                .addSerializer(TagActionData.class, new TagActionDataSerializer())
                .addSerializer(AppDefinedAction.class, new AppDefinedSerializer())
                .addSerializer(DeepLinkAction.class, new DeepLinkSerializer())
                .addDeserializer(ShareAction.class, new ShareActionDeserializer())
                .addSerializer(ShareAction.class, new ShareActionSerializer())

                .addDeserializer(TagActionData.class, new TagActionDataDeserializer())

            /* Tags */
                .addSerializer(AddRemoveDeviceFromTagPayload.class, new AddRemoveDeviceFromTagPayloadSerializer())
                .addSerializer(BatchModificationPayload.class, new BatchModificationPayloadSerializer())

            /* Segments */
                .addDeserializer(SegmentDefinition.class, new SegmentDefinitionDeserializer());


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
