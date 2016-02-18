/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIListAllChannelsResponse;
import com.urbanairship.api.client.model.APIListAllSchedulesResponse;
import com.urbanairship.api.client.model.APIListAllSegmentsResponse;
import com.urbanairship.api.client.model.APIListSingleChannelResponse;
import com.urbanairship.api.client.model.APIListTagsResponse;
import com.urbanairship.api.client.model.APILocationResponse;
import com.urbanairship.api.client.model.APIPushResponse;
import com.urbanairship.api.client.model.APIReportsPushListingResponse;
import com.urbanairship.api.client.model.APIScheduleResponse;
import com.urbanairship.api.location.model.BoundedBox;
import com.urbanairship.api.location.model.Point;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.reports.model.AppStats;
import com.urbanairship.api.reports.model.PerPushDetailResponse;
import com.urbanairship.api.reports.model.PerPushSeriesResponse;
import com.urbanairship.api.reports.model.ReportsAPIOpensResponse;
import com.urbanairship.api.reports.model.ReportsAPITimeInAppResponse;
import com.urbanairship.api.reports.model.SinglePushInfoResponse;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.segments.model.AudienceSegment;
import com.urbanairship.api.tag.model.AddRemoveDeviceFromTagPayload;
import com.urbanairship.api.tag.model.AddRemoveNamedUserFromTagPayload;
import com.urbanairship.api.tag.model.BatchModificationPayload;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.params.BasicHttpParams;
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
    private final static String ACCEPT_KEY = "Accept";
    private final static String CONTENT_TYPE_JSON = "application/json";
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
    private final Optional<ProxyInfo> proxyInfo;
    private final Optional<BasicHttpParams> httpParams;


    private APIClient(String appKey, String appSecret, String baseURI, Number version, Optional<ProxyInfo> proxyInfoOptional, Optional<BasicHttpParams> httpParams) {
        Preconditions.checkArgument(StringUtils.isNotBlank(appKey),
                "App key must be provided.");
        Preconditions.checkArgument(StringUtils.isNotBlank(appSecret),
                "App secret must be provided");
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.baseURI = URI.create(baseURI);
        this.version = version;
        this.uaHost = new HttpHost(URI.create(baseURI).getHost(), 443, "https");
        this.proxyInfo = proxyInfoOptional;
        this.httpParams = httpParams;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<ProxyInfo> getProxyInfo() {
        return proxyInfo;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getAppKey() {
        return appKey;
    }

    public Optional<BasicHttpParams> getHttpParams() {
        return httpParams;
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
        object.config(CoreProtocolPNames.USER_AGENT, getUserAgent())
                .addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_JSON)
                .addHeader(ACCEPT_KEY, versionedAcceptHeader(version));

        if (httpParams.isPresent()) {
            for (String name : httpParams.get().getNames()) {
                object.config(name, httpParams.get().getParameter(name));
            }
        }

        if (proxyInfo.isPresent()) {
            object.viaProxy(proxyInfo.get().getProxyHost());
        }

        return object;
    }

    private Executor provisionExecutor() {
        Executor executor = Executor.newInstance()
                .auth(uaHost, appKey, appSecret)
                .authPreemptive(uaHost);

        if (proxyInfo.isPresent()) {

            HttpHost host = proxyInfo.get().getProxyHost();
            executor.authPreemptiveProxy(host);

            if (proxyInfo.get().getProxyCredentials().isPresent()) {
                executor.auth(host, proxyInfo.get().getProxyCredentials().get());
            }
        }

        return executor;
    }

    /* A method to resolve base URIs without excluding the original path */

    public static URI baseURIResolution(URI baseURI, String path) {
        if (!baseURI.getPath().endsWith("/")) {
            try {
                baseURI = new URI(baseURI.toString() + "/");
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        if (path.startsWith("/"))  {
            path = path.substring(1);
        }

        return baseURI.resolve(path);
    }

    /* Push API */

    public APIClientResponse<APIPushResponse> push(PushPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload required when executing a push operation");
        Request request = provisionRequest(Request.Post(baseURIResolution(baseURI, API_PUSH_PATH)));
        request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing push request %s", request));
        }

        return provisionExecutor()
            .execute(request)
            .handleResponse(new APIClientResponseHandler<APIPushResponse>(APIPushResponse.class));
    }

    public APIClientResponse<APIPushResponse> validate(PushPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload required when executing a validate push operation");
        Request request = provisionRequest(Request.Post(baseURIResolution(baseURI, API_VALIDATE_PATH)));
        request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing validate push request %s", request));
        }

        return provisionExecutor()
            .execute(request)
            .handleResponse(new APIClientResponseHandler<APIPushResponse>(APIPushResponse.class));
    }

    /* Schedules API */

    public APIClientResponse<APIScheduleResponse> schedule(SchedulePayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload required when scheduling a push request");
        Request request = provisionRequest(Request.Post(baseURIResolution(baseURI, API_SCHEDULE_PATH)));
        request.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing schedule request %s", request));
        }

        return provisionExecutor()
            .execute(request)
            .handleResponse(new APIClientResponseHandler<APIScheduleResponse>(APIScheduleResponse.class));
    }

    public APIClientResponse<APIListAllSchedulesResponse> listAllSchedules() throws IOException {
        Request request = provisionRequest(Request.Get(baseURIResolution(baseURI, API_SCHEDULE_PATH)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all schedules request %s", request));
        }

        return provisionExecutor()
            .execute(request)
            .handleResponse(new APIClientResponseHandler<APIListAllSchedulesResponse>(APIListAllSchedulesResponse.class));
    }

    public APIClientResponse<APIListAllSchedulesResponse> listAllSchedules(String start, int limit, String order) throws IOException {
        String path = "/api/schedules" + "?" + "start=" + start + "&limit=" + limit + "&order=" + order;
        Request request = provisionRequest(Request.Get(baseURIResolution(baseURI, path)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all schedules request %s", request));
        }

        return provisionExecutor()
            .execute(request)
            .handleResponse(new APIClientResponseHandler<APIListAllSchedulesResponse>(APIListAllSchedulesResponse.class));
    }

    public APIClientResponse<APIListAllSchedulesResponse> listAllSchedules(String next_page) throws IOException, URISyntaxException {
        URI np = new URI(next_page);
        Request request = provisionRequest(Request.Get(baseURIResolution(baseURI, np.getPath() + "?" + np.getQuery())));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all schedules request %s", request));
        }

        return provisionExecutor()
            .execute(request)
            .handleResponse(new APIClientResponseHandler<APIListAllSchedulesResponse>(APIListAllSchedulesResponse.class));
    }

    public APIClientResponse<SchedulePayload> listSchedule(String id) throws IOException {
        Request request = provisionRequest(Request.Get(baseURIResolution(baseURI, API_SCHEDULE_PATH + id)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list specific schedule request %s", request));
        }

        return provisionExecutor()
            .execute(request)
            .handleResponse(new APIClientResponseHandler<SchedulePayload>(SchedulePayload.class));
    }

    public APIClientResponse<APIScheduleResponse> updateSchedule(SchedulePayload payload, String id) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when updating schedule");
        Request req = provisionRequest(Request.Put(baseURIResolution(baseURI, API_SCHEDULE_PATH + id)));
        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing update schedule request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APIScheduleResponse>(APIScheduleResponse.class));
    }

    public HttpResponse deleteSchedule(String id) throws IOException {
        Request req = provisionRequest(Request.Delete(baseURIResolution(baseURI, API_SCHEDULE_PATH + id)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing delete schedule request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    /* Tags API */

    public APIClientResponse<APIListTagsResponse> listTags() throws IOException {
        Request req = provisionRequest(Request.Get(baseURIResolution(baseURI, API_TAGS_PATH)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list tags request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APIListTagsResponse>(APIListTagsResponse.class));
    }

    public HttpResponse createTag(String tag) throws IOException {
        Request req = provisionRequest(Request.Put(baseURIResolution(baseURI, API_TAGS_PATH + tag)));

        req.removeHeaders(CONTENT_TYPE_KEY);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing create tag request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse deleteTag(String tag) throws IOException {
        Request req = provisionRequest(Request.Delete(baseURIResolution(baseURI, API_TAGS_PATH + tag)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing delete tag request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse addRemoveDevicesFromTag(String tag, AddRemoveDeviceFromTagPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when adding and/or removing devices from a tag");
        Request req = provisionRequest(Request.Post(baseURIResolution(baseURI, API_TAGS_PATH + tag)));
        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing add/remove tags from named user request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse addRemoveNamedUserFromTag(String tag, AddRemoveNamedUserFromTagPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when adding and/or removing tags from a named user");
        Request req = provisionRequest(Request.Post(baseURIResolution(baseURI, API_TAGS_PATH + tag)));
        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing add/remove devices from tag request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse batchModificationOfTags(BatchModificationPayload payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when performing batch modification of tags");
        Request req = provisionRequest(Request.Post(baseURIResolution(baseURI, API_TAGS_BATCH_PATH)));
        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing batch modification of tags request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    /* Location API */

    public APIClientResponse<APILocationResponse> queryLocationInformation(String query) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotBlank(query), "Query text cannot be blank");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_LOCATION_PATH));
        builder.addParameter("q", query);

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class));
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(String query, String type) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotBlank(query), "Query text cannot be blank");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_LOCATION_PATH));
        builder.addParameter("q", query);
        builder.addParameter("type", type);

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class));
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(Point point) throws IOException {
        Preconditions.checkNotNull(point, "Point must not be null");
        Preconditions.checkArgument(point.isValid(), "Point must be a valid coordinate");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_LOCATION_PATH + point.getLatitude() + "," + point.getLongitude()));

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class));
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(Point point, String type) throws IOException {
        Preconditions.checkNotNull(point, "Point must not be null");
        Preconditions.checkArgument(point.isValid(), "Point must be a valid coordinate");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_LOCATION_PATH + point.getLatitude() + "," + point.getLongitude()));
        builder.addParameter("type", type);

        Request req = provisionRequest(Request.Get(builder.toString()));

        req.removeHeaders(ACCEPT_KEY);      // Workaround for v3 routing bug

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing query location information without type request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class));
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(BoundedBox box) throws IOException {
        Preconditions.checkNotNull(box, "Box must not be null");
        Preconditions.checkArgument(box.isValid(), "Box must be a valid coordinate");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_LOCATION_PATH +
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

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class));
    }

    public APIClientResponse<APILocationResponse> queryLocationInformation(BoundedBox box, String type) throws IOException {
        Preconditions.checkNotNull(box, "Box must not be null");
        Preconditions.checkArgument(box.isValid(), "Box must be a valid coordinate");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_LOCATION_PATH +
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

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class));
    }

    /* Segments API */

    public APIClientResponse<APIListAllSegmentsResponse> listAllSegments() throws IOException {
        Request req = provisionRequest(Request.Get(baseURIResolution(baseURI, API_SEGMENTS_PATH)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all segments request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APIListAllSegmentsResponse>(APIListAllSegmentsResponse.class));
    }

    public APIClientResponse<APIListAllSegmentsResponse> listAllSegments(String nextPage) throws IOException, URISyntaxException {
        URI np = new URI(nextPage);
        Request req = provisionRequest(Request.Get(baseURIResolution(baseURI, np.getPath() + "?" + np.getQuery())));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all segments request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APIListAllSegmentsResponse>(APIListAllSegmentsResponse.class));
    }

    public APIClientResponse<APIListAllSegmentsResponse> listAllSegments(String start, int limit, String order) throws IOException {
        String path = "/api/segments" + "?" + "start=" + start + "&limit=" + limit + "&order=" + order;
        Request req = provisionRequest(Request.Get(baseURIResolution(baseURI, path)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all segments request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APIListAllSegmentsResponse>(APIListAllSegmentsResponse.class));
    }

    public APIClientResponse<AudienceSegment> listSegment(String segmentID) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotBlank(segmentID), "segmentID is required when listing segment");

        String path = API_SEGMENTS_PATH + segmentID;
        Request req = provisionRequest(Request.Get(baseURIResolution(baseURI, path)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all segments request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<AudienceSegment>(AudienceSegment.class));
    }

    public HttpResponse createSegment(AudienceSegment payload) throws IOException {
        Preconditions.checkNotNull(payload, "Payload is required when creating segment");
        Request req = provisionRequest(Request.Post(baseURIResolution(baseURI, API_SEGMENTS_PATH)));


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
        Request req = provisionRequest(Request.Put(baseURIResolution(baseURI, path)));


        req.bodyString(payload.toJSON(), ContentType.APPLICATION_JSON);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing change segment request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    public HttpResponse deleteSegment(String segmentID) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotBlank(segmentID), "segmentID is required when deleting segment");

        String path = API_SEGMENTS_PATH + segmentID;
        Request req = provisionRequest(Request.Delete(baseURIResolution(baseURI, path)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing delete segment request %s", req));
        }

        return provisionExecutor().execute(req).returnResponse();
    }

    /* Device Information API */

    public APIClientResponse<APIListSingleChannelResponse> listChannel(String channel) throws IOException {

        String path = API_DEVICE_CHANNELS_PATH + channel;
        Request req = provisionRequest(Request.Get(baseURIResolution(baseURI, path)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing get single channels request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APIListSingleChannelResponse>(APIListSingleChannelResponse.class));
    }

    public APIClientResponse<APIListAllChannelsResponse> listAllChannels() throws IOException {
        Request req = provisionRequest(Request.Get(baseURIResolution(baseURI, API_DEVICE_CHANNELS_PATH)));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list all channels request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APIListAllChannelsResponse>(APIListAllChannelsResponse.class));
    }

    /* Reports API */

    public APIClientResponse<PerPushDetailResponse> listPerPushDetail(String pushID) throws IOException {
        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_REPORTS_PER_PUSH_DETAIL_PATH + pushID));

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list per push detail request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<PerPushDetailResponse>(PerPushDetailResponse.class));
    }

    public APIClientResponse<PerPushSeriesResponse> listPerPushSeries(String pushID) throws IOException {
        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_REPORTS_PER_PUSH_SERIES_PATH + pushID));

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list per push series request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<PerPushSeriesResponse>(PerPushSeriesResponse.class));
    }

    public APIClientResponse<PerPushSeriesResponse> listPerPushSeries(String pushID, String precision) throws IOException {
        Preconditions.checkArgument(HOURLY.equals(precision) ||
                        DAILY.equals(precision) ||
                        MONTHLY.equals(precision),
                "Precision must be specified as HOURLY, DAILY or MONTHLY");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_REPORTS_PER_PUSH_SERIES_PATH + pushID));

        builder.addParameter("precision", precision.toUpperCase());

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list per push series with precision request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<PerPushSeriesResponse>(PerPushSeriesResponse.class));
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

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_REPORTS_PER_PUSH_SERIES_PATH + pushID));

        builder.addParameter("precision", precision.toUpperCase());
        builder.addParameter("start", start.toLocalDateTime().toString());
        builder.addParameter("end", end.toLocalDateTime().toString());

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list per push series with precision and range request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<PerPushSeriesResponse>(PerPushSeriesResponse.class));
    }

    public APIClientResponse<SinglePushInfoResponse> listIndividualPushResponseStatistics(String id) throws IOException {
        Preconditions.checkNotNull(id, "Push id is required when performing listing of individual push response statistics.");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_REPORTS_PUSH_RESPONSE_PATH + id));

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list Statistics in CSV String format request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<SinglePushInfoResponse>(SinglePushInfoResponse.class));
    }

    public APIClientResponse<APIReportsPushListingResponse> listReportsResponseListing(DateTime start,
                                                                                       DateTime end,
                                                                                       Optional<Integer> limit,
                                                                                       Optional<String> pushIDStart)
            throws IOException {

        Preconditions.checkNotNull(start, "Start time is required when performing listing of push statistics");
        Preconditions.checkNotNull(end, "End time is required when performing listing of push statistics");
        Preconditions.checkArgument(start.isBefore(end), "Start time must be before End time");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_REPORTS_PUSH_RESPONSE_PATH + "list"));

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

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<APIReportsPushListingResponse>(APIReportsPushListingResponse.class));

    }

    public APIClientResponse<ReportsAPIOpensResponse> listAppsOpenReport(DateTime start, DateTime end, String precision) throws IOException {

        Preconditions.checkArgument(precision.toUpperCase().equals("HOURLY") ||
                precision.toUpperCase().equals("DAILY") ||
                precision.toUpperCase().equals("MONTHLY"),
            "Precision must be specified as HOURLY, DAILY or MONTHLY");

        Preconditions.checkNotNull(start, "Start time is required when performing listing of apps open");
        Preconditions.checkNotNull(end, "End time is required when performing listing of apps open");
        Preconditions.checkArgument(start.isBefore(end), "Start time must be before End time");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_REPORTS_APPS_OPEN_PATH));

        builder.addParameter("precision", precision.toUpperCase());
        builder.addParameter("start", start.toLocalDateTime().toString());
        builder.addParameter("end", end.toLocalDateTime().toString());

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list apps open report request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<ReportsAPIOpensResponse>(ReportsAPIOpensResponse.class));
    }

    public APIClientResponse<ReportsAPITimeInAppResponse> listTimeInAppReport(DateTime start, DateTime end, String precision) throws IOException {

        Preconditions.checkArgument(precision.toUpperCase().equals("HOURLY") ||
                precision.toUpperCase().equals("DAILY") ||
                precision.toUpperCase().equals("MONTHLY"),
            "Precision must be specified as HOURLY, DAILY or MONTHLY");

        Preconditions.checkNotNull(start, "Start time is required when performing listing of time in app");
        Preconditions.checkNotNull(end, "End time is required when performing listing of time in app");
        Preconditions.checkArgument(start.isBefore(end), "Start time must be before End time");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_REPORTS_TIME_IN_APP_PATH));

        builder.addParameter("precision", precision.toUpperCase());
        builder.addParameter("start", start.toLocalDateTime().toString());
        builder.addParameter("end", end.toLocalDateTime().toString());

        Request req = provisionRequest(Request.Get(builder.toString()));

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Executing list time in app report request %s", req));
        }

        return provisionExecutor()
            .execute(req)
            .handleResponse(new APIClientResponseHandler<ReportsAPITimeInAppResponse>(ReportsAPITimeInAppResponse.class));
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
    public APIClientResponse<List<AppStats>> listPushStatistics(DateTime start, DateTime end) throws IOException {
        Preconditions.checkNotNull(start, "Start time is required when performing listing of push statistics");
        Preconditions.checkNotNull(end, "End time is required when performing listing of push statistics");
        Preconditions.checkArgument(start.isBefore(end), "Start time must be before End time");

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_STATISTICS_PATH));

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

        URIBuilder builder = new URIBuilder(baseURIResolution(baseURI, API_STATISTICS_PATH));

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
    public int hashCode() {
        return Objects.hashCode(appKey, appSecret, baseURI, version, uaHost, proxyInfo, httpParams);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final APIClient other = (APIClient) obj;
        return Objects.equal(this.appKey, other.appKey)
            && Objects.equal(this.appSecret, other.appSecret)
            && Objects.equal(this.baseURI, other.baseURI)
            && Objects.equal(this.version, other.version)
            && Objects.equal(this.uaHost, other.uaHost)
            && Objects.equal(this.proxyInfo, other.proxyInfo)
            && Objects.equal(this.httpParams, other.httpParams);
    }

    @Override
    public String toString() {
        return "APIClient{ +" +
            "appKey=" + appKey +
            ", appSecret=" + appSecret +
            ", baseURI=" + baseURI +
            ", version=" + version +
            ", uaHost=" + uaHost +
            ", proxyInfo=" + proxyInfo +
            ", httpParams=" + httpParams +
            '}';
    }

    /* Builder for APIClient */

    public static class Builder {

        private String key;
        private String secret;
        private String baseURI;
        private Number version;
        private ProxyInfo proxyInfoOptional;
        private BasicHttpParams httpParams;

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

        public Builder setProxyInfo(ProxyInfo value) {
            this.proxyInfoOptional = value;
            return this;
        }

        public Builder setHttpParams(BasicHttpParams httpParams) {
            this.httpParams = httpParams;
            return this;
        }

        public APIClient build() {
            Preconditions.checkNotNull(key, "app key needed to build APIClient");
            Preconditions.checkNotNull(secret, "app secret needed to build APIClient");
            Preconditions.checkNotNull(baseURI, "base URI needed to build APIClient");
            Preconditions.checkNotNull(version, "version needed to build APIClient");

            return new APIClient(key, secret, baseURI, version, Optional.fromNullable(proxyInfoOptional), Optional.fromNullable(httpParams));
        }

    }
}
