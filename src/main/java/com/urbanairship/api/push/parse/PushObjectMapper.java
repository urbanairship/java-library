/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.model.email.EmailChannelResponse;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.channel.model.email.UninstallEmailChannel;
import com.urbanairship.api.channel.model.open.OpenChannel;
import com.urbanairship.api.channel.model.open.Channel;
import com.urbanairship.api.createandsend.model.notification.email.CreateAndSendEmailPayload;
import com.urbanairship.api.createandsend.model.notification.email.EmailFields;
import com.urbanairship.api.createandsend.model.notification.email.EmailTemplate;
import com.urbanairship.api.createandsend.model.notification.email.VariableDetail;
import com.urbanairship.api.createandsend.model.notification.sms.SmsFields;
import com.urbanairship.api.createandsend.model.notification.sms.SmsTemplate;
import com.urbanairship.api.createandsend.parse.audience.CreateAndSendAudienceSerializer;
import com.urbanairship.api.createandsend.parse.notification.email.CreateAndSendEmailPayloadSerializer;
import com.urbanairship.api.channel.parse.email.RegisterEmailChannelResponseDeserializer;
import com.urbanairship.api.channel.parse.email.RegisterEmailChannelSerializer;
import com.urbanairship.api.channel.parse.email.UninstallEmailChannelSerializer;
import com.urbanairship.api.channel.parse.open.ChannelSerializer;
import com.urbanairship.api.channel.parse.open.OpenChannelSerializer;
import com.urbanairship.api.common.parse.CommonObjectMapper;
import com.urbanairship.api.createandsend.model.notification.*;
import com.urbanairship.api.createandsend.parse.notification.CreateAndSendPayloadSerializer;
import com.urbanairship.api.createandsend.parse.notification.email.EmailFieldsSerializer;
import com.urbanairship.api.createandsend.parse.notification.email.EmailTemplateSerializer;
import com.urbanairship.api.createandsend.parse.notification.email.VariableDetailSerializer;
import com.urbanairship.api.createandsend.parse.notification.sms.SmsFieldsSerializer;
import com.urbanairship.api.createandsend.parse.notification.sms.SmsTemplateSerializer;
import com.urbanairship.api.customevents.model.CustomEventPayload;
import com.urbanairship.api.customevents.model.CustomEventBody;
import com.urbanairship.api.customevents.model.CustomEventResponse;
import com.urbanairship.api.customevents.model.CustomEventUser;
import com.urbanairship.api.customevents.parse.CustomEventBodySerializer;
import com.urbanairship.api.customevents.parse.CustomEventResponseDeserializer;
import com.urbanairship.api.customevents.parse.CustomEventSerializer;
import com.urbanairship.api.customevents.parse.CustomEventUserSerializer;
import com.urbanairship.api.push.model.Campaigns;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.Display;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.PushOptions;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.PushResponse;
import com.urbanairship.api.createandsend.model.audience.CreateAndSendAudience;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.location.AbsoluteDateRange;
import com.urbanairship.api.push.model.audience.location.DateRange;
import com.urbanairship.api.push.model.audience.location.LocationSelector;
import com.urbanairship.api.push.model.audience.location.RecentDateRange;
import com.urbanairship.api.push.model.audience.location.SegmentDefinition;
import com.urbanairship.api.push.model.audience.sms.SmsSelector;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.actions.ActionNameRegistry;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AddTagAction;
import com.urbanairship.api.push.model.notification.actions.AppDefinedAction;
import com.urbanairship.api.push.model.notification.actions.DeepLinkAction;
import com.urbanairship.api.push.model.notification.actions.OpenExternalURLAction;
import com.urbanairship.api.push.model.notification.actions.OpenLandingPageWithContentAction;
import com.urbanairship.api.push.model.notification.actions.RemoveTagAction;
import com.urbanairship.api.push.model.notification.actions.ShareAction;
import com.urbanairship.api.push.model.notification.actions.TagActionData;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.model.notification.android.BigPictureStyle;
import com.urbanairship.api.push.model.notification.android.BigTextStyle;
import com.urbanairship.api.push.model.notification.android.Category;
import com.urbanairship.api.push.model.notification.android.InboxStyle;
import com.urbanairship.api.push.model.notification.android.PublicNotification;
import com.urbanairship.api.push.model.notification.android.Wearable;
import com.urbanairship.api.push.model.notification.email.EmailPayload;
import com.urbanairship.api.push.model.notification.ios.*;
import com.urbanairship.api.push.model.notification.open.OpenPayload;
import com.urbanairship.api.push.model.notification.richpush.RichPushIcon;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.model.notification.sms.SmsPayload;
import com.urbanairship.api.push.model.notification.web.WebDevicePayload;
import com.urbanairship.api.push.model.notification.web.WebIcon;
import com.urbanairship.api.push.model.notification.wns.WNSAudioData;
import com.urbanairship.api.push.model.notification.wns.WNSBadgeData;
import com.urbanairship.api.push.model.notification.wns.WNSBinding;
import com.urbanairship.api.push.model.notification.wns.WNSDevicePayload;
import com.urbanairship.api.push.model.notification.wns.WNSPush;
import com.urbanairship.api.push.model.notification.wns.WNSTileData;
import com.urbanairship.api.push.model.notification.wns.WNSToastData;
import com.urbanairship.api.push.parse.audience.SelectorDeserializer;
import com.urbanairship.api.push.parse.audience.SelectorSerializer;
import com.urbanairship.api.push.parse.audience.location.AbsoluteDateRangeDeserializer;
import com.urbanairship.api.push.parse.audience.location.AbsoluteDateRangeSerializer;
import com.urbanairship.api.push.parse.audience.location.DateRangeDeserializer;
import com.urbanairship.api.push.parse.audience.location.LocationSelectorDeserializer;
import com.urbanairship.api.push.parse.audience.location.LocationSelectorSerializer;
import com.urbanairship.api.push.parse.audience.location.RecentDateRangeDeserializer;
import com.urbanairship.api.push.parse.audience.location.RecentDateRangeSerializer;
import com.urbanairship.api.push.parse.audience.location.SegmentDefinitionDeserializer;
import com.urbanairship.api.push.parse.audience.sms.SmsSelectorSerializer;
import com.urbanairship.api.push.parse.notification.InteractiveDeserializer;
import com.urbanairship.api.push.parse.notification.InteractiveSerializer;
import com.urbanairship.api.push.parse.notification.NotificationDeserializer;
import com.urbanairship.api.push.parse.notification.NotificationSerializer;
import com.urbanairship.api.push.parse.notification.actions.ActionsDeserializer;
import com.urbanairship.api.push.parse.notification.actions.ActionsSerializer;
import com.urbanairship.api.push.parse.notification.actions.AddTagActionSerializer;
import com.urbanairship.api.push.parse.notification.actions.AppDefinedSerializer;
import com.urbanairship.api.push.parse.notification.actions.DeepLinkSerializer;
import com.urbanairship.api.push.parse.notification.actions.ExternalURLSerializer;
import com.urbanairship.api.push.parse.notification.actions.LandingPageWithContentSerializer;
import com.urbanairship.api.push.parse.notification.actions.RemoveTagActionSerializer;
import com.urbanairship.api.push.parse.notification.actions.ShareActionDeserializer;
import com.urbanairship.api.push.parse.notification.actions.ShareActionSerializer;
import com.urbanairship.api.push.parse.notification.actions.TagActionDataDeserializer;
import com.urbanairship.api.push.parse.notification.actions.TagActionDataSerializer;
import com.urbanairship.api.push.parse.notification.adm.ADMDevicePayloadDeserializer;
import com.urbanairship.api.push.parse.notification.adm.ADMDevicePayloadSerializer;
import com.urbanairship.api.push.parse.notification.android.AndroidDevicePayloadDeserializer;
import com.urbanairship.api.push.parse.notification.android.AndroidDevicePayloadSerializer;
import com.urbanairship.api.push.parse.notification.android.BigPictureStyleDeserializer;
import com.urbanairship.api.push.parse.notification.android.BigPictureStyleSerializer;
import com.urbanairship.api.push.parse.notification.android.BigTextStyleDeserializer;
import com.urbanairship.api.push.parse.notification.android.BigTextStyleSerializer;
import com.urbanairship.api.push.parse.notification.android.CategoryDeserializer;
import com.urbanairship.api.push.parse.notification.android.InboxStyleDeserializer;
import com.urbanairship.api.push.parse.notification.android.InboxStyleSerializer;
import com.urbanairship.api.push.parse.notification.android.PublicNotificationDeserializer;
import com.urbanairship.api.push.parse.notification.android.PublicNotificationSerializer;
import com.urbanairship.api.push.parse.notification.android.WearableDeserializer;
import com.urbanairship.api.push.parse.notification.android.WearableSerializer;
import com.urbanairship.api.push.parse.notification.email.EmailPayloadDeserializer;
import com.urbanairship.api.push.parse.notification.email.EmailPayloadSerializer;
import com.urbanairship.api.push.parse.notification.ios.*;
import com.urbanairship.api.push.parse.notification.open.OpenPayloadSerializer;
import com.urbanairship.api.push.parse.notification.richpush.RichPushIconDeserializer;
import com.urbanairship.api.push.parse.notification.richpush.RichPushIconSerializer;
import com.urbanairship.api.push.parse.notification.richpush.RichPushMessageDeserializer;
import com.urbanairship.api.push.parse.notification.richpush.RichPushMessageSerializer;
import com.urbanairship.api.push.parse.notification.sms.SmsPayloadSerializer;
import com.urbanairship.api.push.parse.notification.web.WebDevicePayloadDeserializer;
import com.urbanairship.api.push.parse.notification.web.WebDevicePayloadSerializer;
import com.urbanairship.api.push.parse.notification.web.WebIconDeserializer;
import com.urbanairship.api.push.parse.notification.web.WebIconSerializer;
import com.urbanairship.api.push.parse.notification.wns.WNSAudioDeserializer;
import com.urbanairship.api.push.parse.notification.wns.WNSAudioSerializer;
import com.urbanairship.api.push.parse.notification.wns.WNSBadgeDeserializer;
import com.urbanairship.api.push.parse.notification.wns.WNSBadgeSerializer;
import com.urbanairship.api.push.parse.notification.wns.WNSBindingDeserializer;
import com.urbanairship.api.push.parse.notification.wns.WNSBindingSerializer;
import com.urbanairship.api.push.parse.notification.wns.WNSCachePolicyDeserializer;
import com.urbanairship.api.push.parse.notification.wns.WNSCachePolicySerializer;
import com.urbanairship.api.push.parse.notification.wns.WNSDevicePayloadDeserializer;
import com.urbanairship.api.push.parse.notification.wns.WNSDevicePayloadSerializer;
import com.urbanairship.api.push.parse.notification.wns.WNSDurationDeserializer;
import com.urbanairship.api.push.parse.notification.wns.WNSDurationSerializer;
import com.urbanairship.api.push.parse.notification.wns.WNSGlyphDeserializer;
import com.urbanairship.api.push.parse.notification.wns.WNSGlyphSerializer;
import com.urbanairship.api.push.parse.notification.wns.WNSSoundDeserializer;
import com.urbanairship.api.push.parse.notification.wns.WNSSoundSerializer;
import com.urbanairship.api.push.parse.notification.wns.WNSTileDeserializer;
import com.urbanairship.api.push.parse.notification.wns.WNSTileSerializer;
import com.urbanairship.api.push.parse.notification.wns.WNSToastDeserializer;
import com.urbanairship.api.push.parse.notification.wns.WNSToastSerializer;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.ScheduleDetails;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.schedule.parse.ScheduleDeserializer;
import com.urbanairship.api.schedule.parse.ScheduleDetailsSerializer;
import com.urbanairship.api.schedule.parse.SchedulePayloadDeserializer;
import com.urbanairship.api.schedule.parse.ScheduleSerializer;
import com.urbanairship.api.schedule.parse.ScheduledPayloadSerializer;

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
        IOSDevicePayloadDeserializer iosPayloadDS = new IOSDevicePayloadDeserializer();
        AndroidDevicePayloadDeserializer androidPayloadDS = new AndroidDevicePayloadDeserializer();
        ADMDevicePayloadDeserializer admPayloadDS = new ADMDevicePayloadDeserializer();
        WebDevicePayloadDeserializer webPayloadDS = new WebDevicePayloadDeserializer();
        EmailPayloadDeserializer emailPayloadDS = new EmailPayloadDeserializer();

        NotificationDeserializer notificationDeserializer = new NotificationDeserializer(
                ImmutableMap.<DeviceType, JsonDeserializer<? extends DevicePayloadOverride>>builder()
                        .put(DeviceType.WNS, wnsPayloadDS)
                        .put(DeviceType.IOS, iosPayloadDS)
                        .put(DeviceType.ANDROID, androidPayloadDS)
                        .put(DeviceType.AMAZON, admPayloadDS)
                        .put(DeviceType.EMAIL, emailPayloadDS)
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
                .addSerializer(InApp.class, new InAppSerializer())
                .addDeserializer(InApp.class, new InAppDeserializer())
                .addSerializer(Display.class, new DisplaySerializer())
                .addDeserializer(Display.class, new DisplayDeserializer())
                .addSerializer(DeviceType.class, new DeviceTypeSerializer())
                .addDeserializer(DeviceType.class, new PlatformDeserializer())
                .addSerializer(Campaigns.class, new CampaignsSerializer())
                .addSerializer(Selector.class, new SelectorSerializer())
                .addDeserializer(Selector.class, new SelectorDeserializer())
                .addSerializer(LocationSelector.class, new LocationSelectorSerializer())
                .addDeserializer(LocationSelector.class, new LocationSelectorDeserializer())
                .addSerializer(SmsSelector.class, new SmsSelectorSerializer())
                .addSerializer(AbsoluteDateRange.class, new AbsoluteDateRangeSerializer())
                .addDeserializer(AbsoluteDateRange.Builder.class, new AbsoluteDateRangeDeserializer())
                .addSerializer(RecentDateRange.class, new RecentDateRangeSerializer())
                .addDeserializer(RecentDateRange.Builder.class, new RecentDateRangeDeserializer())
                .addSerializer(DeviceTypeData.class, new DeviceTypeDataSerializer())
                .addDeserializer(DeviceTypeData.class, new PlatformDataDeserializer())
                .addDeserializer(DateRange.class, new DateRangeDeserializer())
                .addSerializer(PushExpiry.class, new PushExpirySerializer())
                .addDeserializer(PushExpiry.class, new PushExpiryDeserializer())
                .addDeserializer(PushResponse.class, new PushResponseDeserializer())


                /* IOS */
                .addSerializer(IOSDevicePayload.class, new IOSDevicePayloadSerializer())
                .addDeserializer(IOSDevicePayload.class, iosPayloadDS)
                .addSerializer(IOSBadgeData.class, new IOSBadgeDataSerializer())
                .addSerializer(IOSAlertData.class, new IOSAlertDataSerializer())
                .addDeserializer(IOSAlertData.class, new IOSAlertDataDeserializer())
                .addSerializer(MediaAttachment.class, new MediaAttachmentSerializer())
                .addDeserializer(MediaAttachment.class, new MediaAttachmentDeserializer())
                .addSerializer(IOSMediaOptions.class, new IOSMediaOptionsSerializer())
                .addDeserializer(IOSMediaOptions.class, new IOSMediaOptionsDeserializer())
                .addSerializer(Crop.class, new CropSerializer())
                .addDeserializer(Crop.class, new CropDeserializer())
                .addSerializer(IOSMediaContent.class, new IOSMediaContentSerializer())
                .addDeserializer(IOSMediaContent.class, new IOSMediaContentDeserializer())

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

                /* DeviceStats */
                .addSerializer(AndroidDevicePayload.class, new AndroidDevicePayloadSerializer())
                .addDeserializer(AndroidDevicePayload.class, androidPayloadDS)
                .addSerializer(Wearable.class, new WearableSerializer())
                .addDeserializer(Wearable.class, new WearableDeserializer())
                .addSerializer(BigPictureStyle.class, new BigPictureStyleSerializer())
                .addDeserializer(BigPictureStyle.class, new BigPictureStyleDeserializer())
                .addSerializer(BigTextStyle.class, new BigTextStyleSerializer())
                .addDeserializer(BigTextStyle.class, new BigTextStyleDeserializer())
                .addSerializer(InboxStyle.class, new InboxStyleSerializer())
                .addDeserializer(InboxStyle.class, new InboxStyleDeserializer())
                .addDeserializer(Category.class, new CategoryDeserializer())
                .addSerializer(PublicNotification.class, new PublicNotificationSerializer())
                .addDeserializer(PublicNotification.class, new PublicNotificationDeserializer())

                /* WebSettings */
                .addSerializer(WebDevicePayload.class, new WebDevicePayloadSerializer())
                .addDeserializer(WebDevicePayload.class, webPayloadDS)
                .addSerializer(WebIcon.class, new WebIconSerializer())
                .addDeserializer(WebIcon.class, new WebIconDeserializer())

                /* SMS */
                .addSerializer(SmsPayload.class, new SmsPayloadSerializer())

                /* AMAZON */
                .addSerializer(ADMDevicePayload.class, new ADMDevicePayloadSerializer())
                .addDeserializer(ADMDevicePayload.class, admPayloadDS)

                /* Rich Push */
                .addSerializer(RichPushMessage.class, new RichPushMessageSerializer())
                .addDeserializer(RichPushMessage.class, new RichPushMessageDeserializer())
                .addSerializer(RichPushIcon.class, new RichPushIconSerializer())
                .addDeserializer(RichPushIcon.class, new RichPushIconDeserializer())

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

                /* Custom Events */
                .addSerializer(CustomEventUser.class, new CustomEventUserSerializer())
                .addSerializer(CustomEventPayload.class, new CustomEventSerializer())
                .addSerializer(CustomEventBody.class, new CustomEventBodySerializer())

                .addDeserializer(CustomEventResponse.class, new CustomEventResponseDeserializer())

                /* Open Channel */
                .addSerializer(Channel.class, new ChannelSerializer())
                .addSerializer(OpenChannel.class, new OpenChannelSerializer())
                .addSerializer(OpenPayload.class, new OpenPayloadSerializer())

                /* Segments */
                .addDeserializer(SegmentDefinition.class, new SegmentDefinitionDeserializer())

                /* Email */
                .addSerializer(RegisterEmailChannel.class, new RegisterEmailChannelSerializer())
                .addDeserializer(EmailChannelResponse.class, new RegisterEmailChannelResponseDeserializer())
                .addSerializer(UninstallEmailChannel.class,
                        new UninstallEmailChannelSerializer())
                .addSerializer((EmailPayload.class), new EmailPayloadSerializer())

                /* Create And Send */
                .addSerializer(SmsFields.class, new SmsFieldsSerializer())
                .addSerializer(SmsTemplate.class, new SmsTemplateSerializer())
                .addSerializer(com.urbanairship.api.createandsend.model.notification.sms.SmsPayload.class, new com.urbanairship.api.createandsend.parse.notification.sms.SmsPayloadSerializer())
                .addSerializer(CreateAndSendPayload.class, new CreateAndSendPayloadSerializer())
                .addSerializer(EmailTemplate.class, new EmailTemplateSerializer())
                .addSerializer(EmailFields.class, new EmailFieldsSerializer())
                .addSerializer(VariableDetail.class, new VariableDetailSerializer())
                .addSerializer(CreateAndSendAudience.class, new CreateAndSendAudienceSerializer())
                .addSerializer(CreateAndSendEmailPayload.class, new CreateAndSendEmailPayloadSerializer());


        MAPPER.registerModule(MODULE);
        MAPPER.registerModule(CommonObjectMapper.getModule());
        MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private PushObjectMapper() { }
}