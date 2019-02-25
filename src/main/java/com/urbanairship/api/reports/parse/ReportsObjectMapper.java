/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.reports.model.PushInfoResponse;
import com.urbanairship.api.reports.model.PushListingResponse;
import com.urbanairship.api.reports.model.ResponseReportResponse;
import com.urbanairship.api.reports.model.StatisticsResponse;
import com.urbanairship.api.reports.model.PlatformStats;
import com.urbanairship.api.reports.model.PlatformStatsResponse;
import com.urbanairship.api.reports.model.DeviceStats;
import com.urbanairship.api.reports.model.ResponseReport;


public class ReportsObjectMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Reports API Module", new Version(1, 0, 0, null));

    static {
        MODULE.addDeserializer(PushInfoResponse.class, new PushInfoResponseDeserializer());
        MODULE.addDeserializer(PushListingResponse.class, new PushListingResponseDeserializer());
        MODULE.addDeserializer(StatisticsResponse.class, new StatisticsResponseDeserializer());
        MODULE.addDeserializer(PlatformStats.class, new PlatformStatsDeserializer());
        MODULE.addDeserializer(PlatformStatsResponse.class, new PlatformStatsResponseDeserializer());
        MODULE.addDeserializer(DeviceStats.class, new DeviceStatsDeserializer());
        MODULE.addDeserializer(ResponseReportResponse.class, new ResponseReportResponseDeserializer());
        MODULE.addDeserializer(ResponseReport.class, new ReportDeserializer());

        MAPPER.registerModule(MODULE);
        MAPPER.registerModule(PushObjectMapper.getModule());
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private ReportsObjectMapper() {
    }
}
