/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */


package com.urbanairship.api.staticlists;

/**
 * The LifecycleListType enum supplies a listing of available UA Lifecycle Lists to
 * be used in the {@link com.urbanairship.api.staticlists.StaticListDownloadRequest} class.
 */
public enum LifecycleListType {
    APP_OPEN_LAST_MONTH("ua_app_open_last_30_days"),
    DIRECT_OPEN_LAST_MONTH("ua_direct_open_last_30_days"),
    FIRST_APP_OPEN_LAST_MONTH("ua_first_app_open_last_30_days"),
    HAS_NOT_OPENED_LAST_MONTH("ua_has_not_opened_last_30_days"),
    MESSAGE_SENT_LAST_MONTH("ua_message_sent_last_30_days"),
    UNINSTALLS_LAST_MONTH("ua_uninstalls_last_30_days"),
    APP_OPEN_LAST_WEEK("ua_app_open_last_7_days"),
    DIRECT_OPEN_LAST_WEEK("ua_direct_open_last_7_days"),
    FIRST_APP_OPEN_LAST_WEEK("ua_first_app_open_last_7_days"),
    HAS_NOT_OPENED_LAST_WEEK("ua_has_not_opened_last_7_days"),
    MESSAGE_SENT_LAST_WEEK("ua_message_sent_last_7_days"),
    UNINSTALLS_LAST_WEEK("ua_uninstalls_last_7_days"),
    APP_OPEN_LAST_DAY("ua_app_open_last_1_day"),
    DIRECT_OPEN_LAST_DAY("ua_direct_open_last_1_day"),
    FIRST_APP_OPEN_LAST_DAY("ua_first_app_open_last_1_day"),
    HAS_NOT_OPENED_LAST_DAY("ua_has_not_opened_last_1_day"),
    MESSAGE_SENT_LAST_DAY("ua_message_sent_last_1_day"),
    UNINSTALLS_LAST_DAY("ua_uninstalls_last_1_day");

    private final String type;

    private LifecycleListType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
