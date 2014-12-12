/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.client.model.*;
import com.urbanairship.api.location.model.BoundedBox;
import com.urbanairship.api.location.model.Point;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.reports.model.*;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.segments.model.AudienceSegment;
import com.urbanairship.api.tag.model.AddRemoveDeviceFromTagPayload;
import com.urbanairship.api.tag.model.BatchModificationPayload;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.params.CoreProtocolPNames;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

/**
 * The APIClient class handles HTTP requests to the Urban Airship API
 */

public class APIClient {

    /* Static Strings */
    private final static String HOURLY = "HOURLY";
    private final static String MONTHLY = "MONTHLY";
    private final static String DAILY = "DAILY";

    /* Header keys/values */
    private final static String CONTENT_TYPE_KEY = "Content-type";
    private final static String CONTENT_TYPE_VALUE = "application/json";
    private final static String ACCEPT_KEY = "Accept";
    private final static String UA_APPLICATION_JSON = "application/vnd.urbanairship+json;";

    /* URI Paths */
    private final static String API_PUSH_PATH = "/api/push/";
    private final static String API_VALIDATE_PATH = "/api/push/validate/";
    private final static String API_SCHEDULE_PATH = "/api/schedules/";
    private final static String API_TAGS_PATH = "/api/tags/";
    private final static String API_TAGS_BATCH_PATH = "/api/tags/batch/";
    private final static String API_LOCATION_PATH = "/api/location/";
    private final static String API_SEGMENTS_PATH = "/api/segments/";
    private final static String API_DEVICE_CHANNELS_PATH = "/api/channels/";
    private final static String API_STATISTICS_PATH = "/api/push/stats/";
    private final static String API_REPORTS_PER_PUSH_DETAIL_PATH = "/api/reports/perpush/detail/";
    private final static String API_REPORTS_PER_PUSH_SERIES_PATH = "/api/reports/perpush/series/";
    private final static String API_REPORTS_PUSH_RESPONSE_PATH = "/api/reports/responses/";
    private final static String API_REPORTS_APPS_OPEN_PATH = "/api/reports/opens/";
    private final static String API_REPORTS_TIME_IN_APP_PATH = "/api/reports/timeinapp/";
    private final static Logger logger = LoggerFactory.getLogger("com.urbanairship.api");
    /* User auth */
    private final String appKey;
    private final String appSecret;
    /* URI, version */
    private final URI baseURI;
    private final Number version;
    /* HTTP */
    private final HttpHost uaHost;

    private APIClient(String appKey, String appSecret, String baseURI, Number version) {
        Preconditions.checkArgument(StringUtils.isNotBlank(appKey),
                "App key must be provided.");
        Preconditions.checkArgument(StringUtils.isNotBlank(appSecret),
                "App secret must be provided");
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.baseURI = URI.create(baseURI);
        this.version = version;
        this.uaHost = new HttpHost(URI.create(baseURI).getHost(), 443, "https");
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getAppKey() {
        return appKey;
    }

    /* Add the version number to the default version header */

    private String versionedAcceptHeader(Number version) {
        return String.format("%s version=%s;", UA_APPLICATION_JSON, version.toString());
    }

    /* Retrieves Java Client API Version */

    public String getUserAgent() {
        InputStream stream = getClass().getResourceAsStream("/client.properties");

        if (stream == null) {
            return "UNKNOWN";
        }

        Properties props = new Properties();

        try {
            props.load(stream);
            stream.close();
            return "UAJavaLib/" + props.get("client.version");
        } catch (IOException e) {
            return "UNKNOWN";
        }
    }
    
    /* Provisioning Methods */

    private Request provisionRequest(Request object) {
        return object
                .config(CoreProtocolPNames.USER_AGENT, getUserAgent())
                .addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE)
                .addHeader(ACCEPT_KEY, versionedAcceptHeader(version));
    }

    private Executor provisionExecutor() {
        return Executor.newInstance()
                .auth(uaHost, appKey, appSecret)
                .authPreemptive(uaHost);
    }

    /* Push API */

    public APIClientResponse<APIPushResponse> push(PushPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload required when executing a push operation");
        Request request = provisionRequest(Request.Post(baseURI.resolve(API_PUSH_PATH)));
        request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing push request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new PushAPIResponseHandler());
    }

    public APIClientResponse<APIPushResponse> validate(PushPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload required when executing a validate push operation");
        Request request = provisionRequest(Request.Post(baseURI.resolve(API_VALIDATE_PATH)));
        request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing validate push request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new PushAPIResponseHandler());
    }

    /* Schedules API */

    public APIClientResponse<APIScheduleResponse> schedule(SchedulePayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload required when scheduling a push request");
        Request request = provisionRequest(Request.Post(baseURI.resolve(API_SCHEDULE_PATH)));
        request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing schedule request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new ScheduleAPIResponseHandler());
    }

    public APIClientResponse<APIListAllSchedulesResponse> listAllSchedules() throws IOException {
        Request request = provisionRequest(Request.Get(baseURI.resolve(API_SCHEDULE_PATH)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all schedules request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new ListAllSchedulesAPIResponseHandler());
    }

    public APIClientResponse<APIListAllSchedulesResponse> listAllSchedules(String start, int limit, String order) throws IOException {
        String path = "/api/schedules" + "?" + "start=" + start + "&limit=" + limit + "&order=" + order;
        Request request = provisionRequest(Request.Get(baseURI.resolve(path)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all schedules request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new ListAllSchedulesAPIResponseHandler());
    }

    public APIClientResponse<APIListAllSchedulesResponse> listAllSchedules(String next_page) throws IOException, URISyntaxException {
        URI np = new URI(next_page);
        Request request = provisionRequest(Request.Get(baseURI.resolve(np.getPath() + "?" + np.getQuery())));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all schedules request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new ListAllSchedulesAPIResponseHandler());
    }

    public APIClientResponse<SchedulePayload> listSchedule(String id) throws IOException {
        Request request = provisionRequest(Request.Get(baseURI.resolve(API_SCHEDULE_PATH + id)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list specific schedule request %s", request));
        }

        return provisionExecutor().execute(request).handleResponse(new ListScheduleAPIResponseHandler());
    }

    public APIClientResponse<APIScheduleResponse> updateSchedule(SchedulePayload payload, String id) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when updating schedule");
        Request req = provisionRequest(Request.Put(baseURI.resolve(API_SCHEDULE_PATH + id)));
        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing update schedule request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ScheduleAPIResponseHandler());
    }

    public HttpResponse deleteSchedule(String id) throws IOException {
        Request req = provisionRequest(Request.Delete(baseURI.resolve(API_SCHEDULE_PATH + id)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing delete schedule request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    /* Tags API */

    public APIClientResponse<APIListTagsResponse> listTags() throws IOException {
        Request req = provisionRequest(Request.Get(baseURI.resolve(API_TAGS_PATH)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list tags request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListTagsAPIResponseHandler());
    }

    public HttpResponse createTag(String tag) throws IOException {
        Request req = provisionRequest(Request.Put(baseURI.resolve(API_TAGS_PATH + tag)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing create tag request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse deleteTag(String tag) throws IOException {
        Request req = provisionRequest(Request.Delete(baseURI.resolve(API_TAGS_PATH + tag)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing delete tag request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse addRemoveDevicesFromTag(String tag, AddRemoveDeviceFromTagPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when adding and/or removing devices from a tag");
        Request req = provisionRequest(Request.Post(baseURI.resolve(API_TAGS_PATH + tag)));
        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing add/remove devices from tag request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse batchModificationOfTags(BatchModificationPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when performing batch modification of tags");
        Request req = provisionRequest(Request.Post(baseURI.resolve(API_TAGS_BATCH_PATH)));
        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing batch modification of tags request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    /* Location API */

    public APIClientResponse<APILocationResponse> queryLocationInformation(String query) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotBlank(query), "Query text cannot be blank");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_LOCATION_PATH));
        builder.addParameter("q", query);

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new LocationAPIResponseHandler());
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(String query, String type) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotBlank(query), "Query text cannot be blank");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_LOCATION_PATH));
        builder.addParameter("q", query);
        builder.addParameter("type", type);

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new LocationAPIResponseHandler());
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(Point point) throws IOException {
        Preconditions.checkNotNull(point, "Point must not be null");
        Preconditions.checkArgument(point.isValid(), "Point must be a valid coordinate");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_LOCATION_PATH + point.getLatitude() + "," + point.getLongitude()));

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new LocationAPIResponseHandler());
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(Point point, String type) throws IOException {
        Preconditions.checkNotNull(point, "Point must not be null");
        Preconditions.checkArgument(point.isValid(), "Point must be a valid coordinate");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_LOCATION_PATH + point.getLatitude() + "," + point.getLongitude()));
        builder.addParameter("type", type);

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new LocationAPIResponseHandler());
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(BoundedBox box) throws IOException {
        Preconditions.checkNotNull(box, "Box must not be null");
        Preconditions.checkArgument(box.isValid(), "Box must be a valid coordinate");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_LOCATION_PATH +
                        box.getCornerOne().getLatitude() + "," +
                        box.getCornerOne().getLongitude() + "," +
                        box.getCornerTwo().getLatitude() + "," +
                        box.getCornerTwo().getLongitude()
        ));

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new LocationAPIResponseHandler());
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(BoundedBox box, String type) throws IOException {
        Preconditions.checkNotNull(box, "Box must not be null");
        Preconditions.checkArgument(box.isValid(), "Box must be a valid coordinate");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_LOCATION_PATH +
                        box.getCornerOne().getLatitude() + "," +
                        box.getCornerOne().getLongitude() + "," +
                        box.getCornerTwo().getLatitude() + "," +
                        box.getCornerTwo().getLongitude()
        ));

        builder.addParameter("type", type);

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new LocationAPIResponseHandler());
    }

    /* Segments API */

    public APIClientResponse<APIListAllSegmentsResponse> listAllSegments() throws IOException {
        Request req = provisionRequest(Request.Get(baseURI.resolve(API_SEGMENTS_PATH)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all segments request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListAllSegmentsAPIResponseHandler());
    }

    public APIClientResponse<APIListAllSegmentsResponse> listAllSegments(String nextPage) throws IOException, URISyntaxException {
        URI np = new URI(nextPage);
        Request req = provisionRequest(Request.Get(baseURI.resolve(np.getPath() + "?" + np.getQuery())));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all segments request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListAllSegmentsAPIResponseHandler());
    }

    public APIClientResponse<APIListAllSegmentsResponse> listAllSegments(String start, int limit, String order) throws IOException, URISyntaxException {
        String path = "/api/segments" + "?" + "start=" + start + "&limit=" + limit + "&order=" + order;
        Request req = provisionRequest(Request.Get(baseURI.resolve(path)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all segments request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListAllSegmentsAPIResponseHandler());
    }

    public APIClientResponse<AudienceSegment> listSegment(String segmentID) throws IOException, URISyntaxException {
        Preconditions.checkArgument(StringUtils.isNotBlank(segmentID), "segmentID is required when listing segment");

        String path = API_SEGMENTS_PATH + segmentID;
        Request req = provisionRequest(Request.Get(baseURI.resolve(path)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all segments request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new AudienceSegmentAPIResponseHandler());
    }

    public HttpResponse createSegment(AudienceSegment payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when creating segment");
        Request req = provisionRequest(Request.Post(baseURI.resolve(API_SEGMENTS_PATH)));


        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing create segment request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse changeSegment(String segmentID, AudienceSegment payload) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotBlank(segmentID), "segmentID is required when updating segment");
        Preconditions.checkNotNull(payload, "Payload is required when updating segment");

        String path = API_SEGMENTS_PATH + segmentID;
        Request req = provisionRequest(Request.Put(baseURI.resolve(path)));


        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing change segment request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse deleteSegment(String segmentID) throws IOException, URISyntaxException {
        Preconditions.checkArgument(StringUtils.isNotBlank(segmentID), "segmentID is required when deleting segment");

        String path = API_SEGMENTS_PATH + segmentID;
        Request req = provisionRequest(Request.Delete(baseURI.resolve(path)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing delete segment request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    /* Device Information API */

    public APIClientResponse<APIListSingleChannelResponse> listChannel(String channel) throws IOException, URISyntaxException {

        String path = API_DEVICE_CHANNELS_PATH + channel;
        Request req = provisionRequest(Request.Get(baseURI.resolve(path)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing get single channels request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListSingleChannelAPIResponseHandler());
    }

    public APIClientResponse<APIListAllChannelsResponse> listAllChannels() throws IOException {
        Request req = provisionRequest(Request.Get(baseURI.resolve(API_DEVICE_CHANNELS_PATH)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all channels request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListAllChannelsAPIResponseHandler());
    }

    /* Reports API */

    public APIClientResponse<PerPushDetailResponse> listPerPushDetail(String pushID) throws IOException {
        URIBuilder builder = new URIBuilder(baseURI.resolve(API_REPORTS_PER_PUSH_DETAIL_PATH + pushID));

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list per push detail request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListPerPushDetailAPIResponseHandler());
    }

    public APIClientResponse<PerPushSeriesResponse> listPerPushSeries(String pushID) throws IOException {
        URIBuilder builder = new URIBuilder(baseURI.resolve(API_REPORTS_PER_PUSH_SERIES_PATH + pushID));

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list per push series request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListPerPushSeriesResponseHandler());
    }

    public APIClientResponse<PerPushSeriesResponse> listPerPushSeries(String pushID, String precision) throws IOException {
        Preconditions.checkArgument(HOURLY.equals(precision) ||
                        DAILY.equals(precision) ||
                        MONTHLY.equals(precision),
                "Precision must be specified as HOURLY, DAILY or MONTHLY");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_REPORTS_PER_PUSH_SERIES_PATH + pushID));

        builder.addParameter("precision", precision.toUpperCase());

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list per push series with precision request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListPerPushSeriesResponseHandler());
    }

    public APIClientResponse<PerPushSeriesResponse> listPerPushSeries(String pushID,
                                                                      String precision,
                                                                      DateTime start,
                                                                      DateTime end) throws IOException {
        Preconditions.checkArgument(HOURLY.equals(precision) ||
                        DAILY.equals(precision) ||
                        MONTHLY.equals(precision),
                "Precision must be specified as HOURLY, DAILY or MONTHLY");

        Preconditions.checkNotNull(start, "Start time is required when performing listing of per push series");
        Preconditions.checkNotNull(end, "End time is required when performing listing of per push series");
        Preconditions.checkArgument(start.isBefore(end), "Start time must be before End time");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_REPORTS_PER_PUSH_SERIES_PATH + pushID));

        builder.addParameter("precision", precision.toUpperCase());
        builder.addParameter("start", start.toLocalDateTime().toString());
        builder.addParameter("end", end.toLocalDateTime().toString());

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list per push series with precision and range request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListPerPushSeriesResponseHandler());
    }

    public APIClientResponse<SinglePushInfoResponse> listIndividualPushResponseStatistics(String id) throws IOException {
        Preconditions.checkNotNull(id, "Push id is required when performing listing of individual push response statistics.");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_REPORTS_PUSH_RESPONSE_PATH + id));

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list Statistics in CSV String format request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListIndividualPushAPIResponseHandler());
    }

    public APIClientResponse<APIReportsPushListingResponse> listReportsResponseListing(DateTime start,
                                                                                       DateTime end,
                                                                                       Optional<Integer> limit,
                                                                                       Optional<String> pushIDStart)
            throws IOException {

        Preconditions.checkNotNull(start, "Start time is required when performing listing of push statistics");
        Preconditions.checkNotNull(end, "End time is required when performing listing of push statistics");
        Preconditions.checkArgument(start.isBefore(end), "Start time must be before End time");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_REPORTS_PUSH_RESPONSE_PATH + "list"));

        builder.addParameter("start", start.toLocalDateTime().toString());
        builder.addParameter("end", end.toLocalDateTime().toString());

        if (limit.isPresent()) {
            builder.addParameter("limit", limit.get().toString());
        }
        if (pushIDStart.isPresent()) {
            builder.addParameter("push_id_start", pushIDStart.get());
        }

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list Statistics in CSV String format request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListReportsListingResponseHandler());

    }

    public APIClientResponse<ReportsAPIOpensResponse> listAppsOpenReport(DateTime start, DateTime end, String precision) throws IOException {

        Preconditions.checkArgument(precision.toUpperCase().equals("HOURLY") ||
                        precision.toUpperCase().equals("DAILY") ||
                        precision.toUpperCase().equals("MONTHLY"),
                "Precision must be specified as HOURLY, DAILY or MONTHLY");

        Preconditions.checkNotNull(start, "Start time is required when performing listing of apps open");
        Preconditions.checkNotNull(end, "End time is required when performing listing of apps open");
        Preconditions.checkArgument(start.isBefore(end), "Start time must be before End time");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_REPORTS_APPS_OPEN_PATH));

        builder.addParameter("precision", precision.toUpperCase());
        builder.addParameter("start", start.toLocalDateTime().toString());
        builder.addParameter("end", end.toLocalDateTime().toString());

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list apps open report request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new AppsOpenReportAPIResponseHandler());
    }

    public APIClientResponse<ReportsAPITimeInAppResponse> listTimeInAppReport(DateTime start, DateTime end, String precision) throws IOException {

        Preconditions.checkArgument(precision.toUpperCase().equals("HOURLY") ||
                        precision.toUpperCase().equals("DAILY") ||
                        precision.toUpperCase().equals("MONTHLY"),
                "Precision must be specified as HOURLY, DAILY or MONTHLY");

        Preconditions.checkNotNull(start, "Start time is required when performing listing of time in app");
        Preconditions.checkNotNull(end, "End time is required when performing listing of time in app");
        Preconditions.checkArgument(start.isBefore(end), "Start time must be before End time");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_REPORTS_TIME_IN_APP_PATH));

        builder.addParameter("precision", precision.toUpperCase());
        builder.addParameter("start", start.toLocalDateTime().toString());
        builder.addParameter("end", end.toLocalDateTime().toString());

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list time in app report request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new TimeInAppReportAPIResponseHandler());
    }

    /**
     * Returns hourly counts for pushes sent for this application.
     * JSON format
     *
     * @param start Start Time
     * @param end   Hours
     * @return APIClientResponse of List of AppStats in JSON. Times are in UTC, and data is provided for each push platform.
     * @throws IOException
     */
    public APIClientResponse<List<AppStats>> listPushStatistics(DateTime start, DateTime end) throws IOException, URISyntaxException {
        Preconditions.checkNotNull(start, "Start time is required when performing listing of push statistics");
        Preconditions.checkNotNull(end, "End time is required when performing listing of push statistics");
        Preconditions.checkArgument(start.isBefore(end), "Start time must be before End time");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_STATISTICS_PATH));

        builder.addParameter("start", start.toLocalDateTime().toString());
        builder.addParameter("end", end.toLocalDateTime().toString());
        builder.addParameter("format", "json");

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list Statistics in CSV String format request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new ListAppStatsAPIResponseHandler());
    }

    /**
     * Returns hourly counts for pushes sent for this application.
     * CSV format for easy importing into spreadsheets.
     *
     * @param start Start Time
     * @param end   End Time
     * @return APIClientResponse of String in CSV. Times are in UTC, and data is provided for each push platform.
     * (Currently: iOS, Helium, BlackBerry, C2DM, GCM, Windows 8, and Windows Phone 8, in that order.)
     * @throws IOException
     */
    public APIClientResponse<String> listPushStatisticsInCSVString(DateTime start, DateTime end) throws IOException {
        Preconditions.checkNotNull(start, "Start time is required when performing listing of push statistics");
        Preconditions.checkNotNull(end, "End time is required when performing listing of push statistics");
        Preconditions.checkArgument(start.isBefore(end), "Start time must be before End time");

        URIBuilder builder = new URIBuilder(baseURI.resolve(API_STATISTICS_PATH));

        builder.addParameter("start", start.toLocalDateTime().toString());
        builder.addParameter("end", end.toLocalDateTime().toString());
        builder.addParameter("format", "csv");

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list Statistics in CSV String format request %s", req));
        }

        return provisionExecutor().execute(req).handleResponse(new StringAPIResponseHandler());
    }

    /* Object methods */

    @Override
    public String toString() {
        return "APIClient\nAppKey:" + appKey + "\nAppSecret:" + appSecret + "\n";
    }

    /* Builder for APIClient */

    public static class Builder {

        private String key;
        private String secret;
        private String baseURI;
        private Number version;

        private Builder() {
            baseURI = "https://go.urbanairship.com";
            version = 3;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setSecret(String appSecret) {
            this.secret = appSecret;
            return this;
        }

        public Builder setBaseURI(String URI) {
            this.baseURI = URI;
            return this;
        }

        public Builder setVersion(Number version) {
            this.version = version;
            return this;
        }

        public APIClient build() {
            Preconditions.checkNotNull(key, "app key needed to build APIClient");
            Preconditions.checkNotNull(secret, "app secret needed to build APIClient");
            Preconditions.checkNotNull(baseURI, "base URI needed to build APIClient");
            Preconditions.checkNotNull(version, "version needed to build APIClient");
            return new APIClient(key, secret, baseURI, version);
        }

    }
}
