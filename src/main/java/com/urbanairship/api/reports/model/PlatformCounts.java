/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

public final class PlatformCounts {

    private final ImmutableMap<PlatformType, PerPushCounts> pushPlatforms;
    private final ImmutableMap<PlatformType, RichPerPushCounts> richPushPlatforms;
    private final DateTime time;
}
