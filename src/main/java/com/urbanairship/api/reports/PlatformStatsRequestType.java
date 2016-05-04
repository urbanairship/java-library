package com.urbanairship.api.reports;

public enum PlatformStatsRequestType {
    APP_OPENS("/api/reports/opens/"),
    TIME_IN_APP("/api/reports/timeinapp/"),
    OPT_INS("/api/reports/optins/"),
    OPT_OUTS("/api/reports/optouts/"),
    SENDS("/api/reports/sends/");

    private final String path;

    private PlatformStatsRequestType(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
