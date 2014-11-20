/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

public final class PerPushSeriesResponse {

    private final String appKey;
    private final UUID pushID;
    private final Optional<DateTime> start;
    private final Optional<DateTime> end;
    private final String precision;

    private final List<...> counts;
}
