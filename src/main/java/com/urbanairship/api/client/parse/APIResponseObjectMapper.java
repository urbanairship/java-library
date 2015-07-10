/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.urbanairship.api.channel.information.model.ChannelView;
import com.urbanairship.api.channel.information.model.DeviceType;
import com.urbanairship.api.channel.information.model.ios.IosSettings;
import com.urbanairship.api.channel.information.model.ios.QuietTime;
import com.urbanairship.api.channel.information.parse.ChannelViewDeserializer;
import com.urbanairship.api.channel.information.parse.DeviceTypeDeserializer;
import com.urbanairship.api.channel.information.parse.ios.IosSettingsDeserializer;
import com.urbanairship.api.channel.information.parse.ios.QuietTimeDeserializer;
import com.urbanairship.api.client.APIError;
import com.urbanairship.api.client.APIErrorDetails;
import com.urbanairship.api.feedback.model.APIApidsFeedbackResponse;
import com.urbanairship.api.feedback.parse.APIApidsFeedbackResponseDeserializer;
import com.urbanairship.api.feedback.model.APIDeviceTokensFeedbackResponse;
import com.urbanairship.api.feedback.parse.APIDeviceTokensFeedbackResponseDeserializer;
import com.urbanairship.api.client.model.APIListAllChannelsResponse;
import com.urbanairship.api.client.model.APIListAllSchedulesResponse;
import com.urbanairship.api.client.model.APIListAllSegmentsResponse;
import com.urbanairship.api.client.model.APIListSingleChannelResponse;
import com.urbanairship.api.client.model.APIListTagsResponse;
import com.urbanairship.api.client.model.APILocationResponse;
import com.urbanairship.api.client.model.APIPushResponse;
import com.urbanairship.api.client.model.APIReportsPushListingResponse;
import com.urbanairship.api.client.model.APIScheduleResponse;
import com.urbanairship.api.client.model.SegmentInformation;
import com.urbanairship.api.location.model.Location;
import com.urbanairship.api.location.parse.LocationDeserializer;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.push.parse.PushPayloadDeserializer;
import com.urbanairship.api.reports.model.AppStats;
import com.urbanairship.api.reports.model.Opens;
import com.urbanairship.api.reports.model.PerPushCounts;
import com.urbanairship.api.reports.model.PerPushDetailResponse;
import com.urbanairship.api.reports.model.PerPushSeriesResponse;
import com.urbanairship.api.reports.model.PlatformCounts;
import com.urbanairship.api.reports.model.ReportsAPIOpensResponse;
import com.urbanairship.api.reports.model.ReportsAPITimeInAppResponse;
import com.urbanairship.api.reports.model.RichPerPushCounts;
import com.urbanairship.api.reports.model.SinglePushInfoResponse;
import com.urbanairship.api.reports.model.TimeInApp;
import com.urbanairship.api.reports.parse.AppStatsDeserializer;
import com.urbanairship.api.reports.parse.OpensDeserializer;
import com.urbanairship.api.reports.parse.PerPushCountsDeserializer;
import com.urbanairship.api.reports.parse.PerPushDetailResponseDeserializer;
import com.urbanairship.api.reports.parse.PerPushSeriesResponseDeserializer;
import com.urbanairship.api.reports.parse.PlatformCountsDeserializer;
import com.urbanairship.api.reports.parse.ReportsAPIOpensResponseDeserializer;
import com.urbanairship.api.reports.parse.ReportsAPITimeInAppResponseDeserializer;
import com.urbanairship.api.reports.parse.RichPerPushCountsDeserializer;
import com.urbanairship.api.reports.parse.SinglePushInfoResponseDeserializer;
import com.urbanairship.api.reports.parse.TimeInAppDeserializer;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.schedule.parse.ScheduleDeserializer;
import com.urbanairship.api.schedule.parse.SchedulePayloadDeserializer;
import com.urbanairship.api.segments.model.AudienceSegment;
import com.urbanairship.api.segments.model.LocationPredicate;
import com.urbanairship.api.segments.model.Operator;
import com.urbanairship.api.segments.model.Predicate;
import com.urbanairship.api.segments.parse.AudienceSegmentDeserializer;
import com.urbanairship.api.segments.parse.AudienceSegmentSerializer;
import com.urbanairship.api.segments.parse.LocationPredicateDeserializer;
import com.urbanairship.api.segments.parse.LocationPredicateSerializer;
import com.urbanairship.api.segments.parse.OperatorDeserializer;
import com.urbanairship.api.segments.parse.OperatorSerializer;
import com.urbanairship.api.segments.parse.PredicateDeserializer;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

/*
This is where object serialization and deserialization are registered with
Jackson to enable object parsing.
 */
public final class APIResponseObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Urban Airship API Client Module", new Version(1, 0, 0, null));

    static {
        MODULE.addDeserializer(APIPushResponse.class, new APIPushResponseDeserializer());
        MODULE.addDeserializer(APIErrorDetails.Location.class, new StreamLocationDeserializer());
        MODULE.addDeserializer(APIErrorDetails.class, new APIErrorDetailsDeserializer());
        MODULE.addDeserializer(APIError.class, new APIErrorDeserializer());
        MODULE.addDeserializer(APIScheduleResponse.class, new APIScheduleResponseDeserializer());
        MODULE.addDeserializer(APIListAllSchedulesResponse.class, new APIListAllSchedulesResponseDeserializer());
        MODULE.addDeserializer(Schedule.class, ScheduleDeserializer.INSTANCE);
        MODULE.addDeserializer(SchedulePayload.class, SchedulePayloadDeserializer.INSTANCE);
        MODULE.addDeserializer(PushPayload.class, new PushPayloadDeserializer());
        MODULE.addDeserializer(APIListTagsResponse.class, new APIListTagsResponseDeserializer());
        MODULE.addDeserializer(SegmentInformation.class, new SegmentInformationDeserializer());
        MODULE.addDeserializer(APIListAllSegmentsResponse.class, new APIListAllSegmentsResponseDeserializer());
        MODULE.addDeserializer(AudienceSegment.class, AudienceSegmentDeserializer.INSTANCE);
        MODULE.addSerializer(AudienceSegment.class, AudienceSegmentSerializer.INSTANCE);
        MODULE.addDeserializer(LocationPredicate.class, LocationPredicateDeserializer.INSTANCE);
        MODULE.addSerializer(LocationPredicate.class, LocationPredicateSerializer.INSTANCE);
        MODULE.addDeserializer(Operator.class, OperatorDeserializer.INSTANCE);
        MODULE.addSerializer(Operator.class, OperatorSerializer.INSTANCE);
        MODULE.addDeserializer(Predicate.class, PredicateDeserializer.INSTANCE);
        MODULE.addDeserializer(IosSettings.class, new IosSettingsDeserializer());
        MODULE.addDeserializer(QuietTime.class, new QuietTimeDeserializer());
        MODULE.addDeserializer(ChannelView.class, new ChannelViewDeserializer());
        MODULE.addDeserializer(DeviceType.class, new DeviceTypeDeserializer());
        MODULE.addDeserializer(APIListSingleChannelResponse.class, new APIListSingleChannelResponseDeserializer());
        MODULE.addDeserializer(APIListAllChannelsResponse.class, new APIListAllChannelsResponseDeserializer());
        MODULE.addDeserializer(AppStats.class, new AppStatsDeserializer());
        MODULE.addDeserializer(PerPushCounts.class, new PerPushCountsDeserializer());
        MODULE.addDeserializer(RichPerPushCounts.class, new RichPerPushCountsDeserializer());
        MODULE.addDeserializer(PerPushDetailResponse.class, new PerPushDetailResponseDeserializer());
        MODULE.addDeserializer(PlatformCounts.class, new PlatformCountsDeserializer());
        MODULE.addDeserializer(PerPushSeriesResponse.class, new PerPushSeriesResponseDeserializer());
        MODULE.addDeserializer(Opens.class, new OpensDeserializer());
        MODULE.addDeserializer(TimeInApp.class, new TimeInAppDeserializer());
        MODULE.addDeserializer(ReportsAPIOpensResponse.class, new ReportsAPIOpensResponseDeserializer());
        MODULE.addDeserializer(ReportsAPITimeInAppResponse.class, new ReportsAPITimeInAppResponseDeserializer());
        MODULE.addDeserializer(SinglePushInfoResponse.class, new SinglePushInfoResponseDeserializer());
        MODULE.addDeserializer(APIReportsPushListingResponse.class, new APIReportsListingResponseDeserializer());
        MODULE.addDeserializer(Location.class, new LocationDeserializer());
        MODULE.addDeserializer(APILocationResponse.class, new APILocationResponseDeserializer());
        MODULE.addDeserializer(APIApidsFeedbackResponse.class, new APIApidsFeedbackResponseDeserializer());
        MODULE.addDeserializer(APIDeviceTokensFeedbackResponse.class, new APIDeviceTokensFeedbackResponseDeserializer());

        MAPPER.registerModule(PushObjectMapper.getModule());
        MAPPER.registerModule(MODULE);
    }

    private APIResponseObjectMapper() {
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

}
